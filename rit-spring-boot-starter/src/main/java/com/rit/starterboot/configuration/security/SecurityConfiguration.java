package com.rit.starterboot.configuration.security;

import com.rit.starterboot.configuration.security.properties.CorsProperties;
import com.rit.starterboot.configuration.jwt.JwtKeyStore;
import com.rit.starterboot.configuration.jwt.properties.JwtProperties;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@AllArgsConstructor
@Configuration
@EnableConfigurationProperties({JwtProperties.class, CorsProperties.class})
public class SecurityConfiguration {

    private final CorsProperties corsProperties;
    private final ObjectProvider<WithoutAuthenticationRequestMatcherProvider> withoutAuthenticationRequestMatcherProvider;

    @Bean
    public JwtKeyStore jwtKeyStore(JwtProperties jwtProperties) {
        return new JwtKeyStore(jwtProperties);
    }

    @Bean
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtKeyStore jwtKeyStore) throws Exception {
        return http.cors(this::cors)
                   .csrf(this::csrf)
                   .authorizeHttpRequests(authorize -> authorize.requestMatchers(withoutAuthenticationRequestMatchers()).permitAll()
                                                                .anyRequest().authenticated())
                   .oauth2ResourceServer((spec) -> oauth2(spec, jwtKeyStore)).build();
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

    private void oauth2(OAuth2ResourceServerConfigurer<HttpSecurity> spec, JwtKeyStore jwtKeyStore) {
        spec.jwt(jwt -> jwt.decoder(jwtDecoder(jwtKeyStore)));
    }

    private JwtDecoder jwtDecoder(JwtKeyStore jwtKeyStore) {
        return NimbusJwtDecoder.withPublicKey(jwtKeyStore.getPublicKey()).build();
    }
}
