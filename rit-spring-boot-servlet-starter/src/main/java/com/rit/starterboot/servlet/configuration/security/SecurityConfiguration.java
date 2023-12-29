package com.rit.starterboot.servlet.configuration.security;

import com.rit.robusta.util.Strings;
import com.rit.starterboot.servlet.configuration.security.jwt.BearerTokenAuthenticationEntryPoint;
import com.rit.starterboot.servlet.configuration.security.jwt.BearerTokenAuthenticationFilter;
import com.rit.starterboot.servlet.configuration.security.jwt.JwtConfiguration;
import com.rit.starterboot.servlet.configuration.security.jwt.JwtDecoder;
import com.rit.starterboot.servlet.configuration.security.properties.CorsProperties;
import com.rit.starterboot.servlet.configuration.security.x509.ConfigurableX509AuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.X509Configurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.preauth.x509.X509AuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.function.Predicate;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@AllArgsConstructor
@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
@Import({JwtConfiguration.class})
@EnableConfigurationProperties({CorsProperties.class})
public class SecurityConfiguration {

    private static final String PRINCIPAL_REGEX = "OU=(.*?)(?:,|$)";
    private static final String INTERNAL_OU = "internal";

    private final CorsProperties corsProperties;
    private final JwtDecoder jwtDecoder;

    @Bean
    @ConditionalOnMissingBean(AuthenticationRequestMatcherProvider.class)
    public AuthenticationRequestMatcherProvider authenticationRequestMatcherProvider() {
        return new AuthenticationRequestMatcherProvider();
    }

    @Bean
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationRequestMatcherProvider matcherProvider) throws Exception {
        return http.cors(this::cors)
                   .csrf(this::csrf)
                   .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                   .exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint()))
                   .logout(AbstractHttpConfigurer::disable)
                   .x509(x509Configurer -> x509(x509Configurer, matcherProvider))
                   .addFilterAfter(new BearerTokenAuthenticationFilter(matcherProvider.jwtMatcher(), jwtDecoder), X509AuthenticationFilter.class)
                   .authorizeHttpRequests(authorize -> authorize.requestMatchers(matcherProvider.permitAll())
                                                                .permitAll()
                                                                .anyRequest().authenticated())
                   .build();
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

    private AuthenticationEntryPoint authenticationEntryPoint() {
        return new BearerTokenAuthenticationEntryPoint();
    }

    private void x509(X509Configurer<HttpSecurity> x509Configurer, AuthenticationRequestMatcherProvider matcherProvider) {
        Predicate<String> predicate = (it) -> Strings.equal(INTERNAL_OU, it);
        var filter = new ConfigurableX509AuthenticationFilter(PRINCIPAL_REGEX, matcherProvider.x509Matcher(), predicate);
        x509Configurer.x509AuthenticationFilter(filter);
    }
}
