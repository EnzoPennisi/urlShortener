package com.example.urlShortener.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${auth0.audience}")
    private String audience;

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuer;

    @Value("${web.cors.allowed-origins}")
    private String corsAllowedOrigins;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable) // Desactiva la protección CSRF (Cross-Site Request Forgery).
                .cors(Customizer.withDefaults()) // Por defecto, Spring buscará un bean con el nombre "corsConfigurationSource".
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/","/{shortenedLink}").permitAll() /// Permite el acceso publico para redireccionar
                                .anyRequest().authenticated() // Cualquier petición debe ser autenticada.
                )
                .oauth2ResourceServer(OAuth2ResourceServer ->
                        OAuth2ResourceServer.jwt(jwt -> jwt.decoder(jwtDecoder()))
                )
        ;

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList(corsAllowedOrigins)); // Define los orígenes permitidos.
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS", "HEAD")); // Define los métodos HTTP permitidos.
        configuration.setAllowCredentials(true); // Permite el uso de credenciales.
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setExposedHeaders(List.of("X-Get-Header"));
        configuration.setMaxAge(3600L); // Define el tiempo máximo de vida de la configuración.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Aplica la configuración CORS a todas las rutas.
        return source;
    }

    @Bean
    JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = JwtDecoders.fromOidcIssuerLocation(issuer); // Crea un decodificador JWT basado en la ubicación del issuer.

        OAuth2TokenValidator<Jwt> audienceValidator = new AudienceValidator(audience); // Valida que la audiencia del token coincida con la definida en las variables de entorno.
        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuer); // Valida que el emisor del token sea el definido en las variables de entorno (extraído de Auth0).
        OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator); // Combina validadores de emisor y audiencia.

        jwtDecoder.setJwtValidator(withAudience); // Establece el validador de JWT que se usará para decodificar el token recibido.

        return jwtDecoder;
    }
}
