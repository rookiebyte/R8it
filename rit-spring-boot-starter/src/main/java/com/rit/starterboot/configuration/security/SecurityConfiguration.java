package com.rit.starterboot.configuration.security;

import com.rit.starterboot.configuration.security.jwt.BearerTokenAuthenticationEntryPoint;
import com.rit.starterboot.configuration.security.jwt.BearerTokenAuthenticationFilter;
import com.rit.starterboot.configuration.security.jwt.JwtConfiguration;
import com.rit.starterboot.configuration.security.jwt.JwtDecoder;
import com.rit.starterboot.configuration.security.properties.CorsProperties;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.preauth.x509.X509AuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@AllArgsConstructor
@Configuration
@Import(JwtConfiguration.class)
@EnableConfigurationProperties({CorsProperties.class})
public class SecurityConfiguration {

    private final CorsProperties corsProperties;
    private final ObjectProvider<WithoutAuthenticationRequestMatcherProvider> withoutAuthenticationRequestMatcherProvider;
    private final JwtDecoder jwtDecoder;

    @Bean
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.cors(this::cors)
                   .csrf(this::csrf)
                   .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                   .exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint()))
                   .logout(AbstractHttpConfigurer::disable)
                   .addFilterAfter(new BearerTokenAuthenticationFilter(jwtDecoder), X509AuthenticationFilter.class)
                   .authorizeHttpRequests(authorize -> authorize.requestMatchers(withoutAuthenticationRequestMatchers())
                                                                .permitAll().anyRequest().authenticated())
                   .build();
    }

    private AuthenticationEntryPoint authenticationEntryPoint() {
        return new BearerTokenAuthenticationEntryPoint();
    }

    private void cors(CorsConfigurer<HttpSecurity> cors) {
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration());
        cors.configurationSource(source);
    }

    private CorsConfiguration corsConfiguration() {
        var config = new CorsConfiguration().applyPermitDefaultValues();
        config.addAllowedMethod(HttpMethod.POST);
        config.addAllowedMethod(HttpMethod.GET);
        config.addAllowedMethod(HttpMethod.DELETE);
        config.addAllowedMethod(HttpMethod.PUT);
        config.setAllowedOrigins(corsProperties.allowedOrigins());
        return config;
    }

    private void csrf(CsrfConfigurer<HttpSecurity> csrf) {
        csrf.disable();
    }

    private RequestMatcher[] withoutAuthenticationRequestMatchers() {
        return withoutAuthenticationRequestMatcherProvider.getIfAvailable(WithoutAuthenticationRequestMatcherProvider::new)
                                                          .requestMatchers();
    }
}
