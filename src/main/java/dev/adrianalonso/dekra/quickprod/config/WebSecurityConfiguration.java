package dev.adrianalonso.dekra.quickprod.config;

import dev.adrianalonso.dekra.quickprod.auth.AuthenticationEntryPoint;
import dev.adrianalonso.dekra.quickprod.auth.CustomAccessDenied;
import dev.adrianalonso.dekra.quickprod.auth.util.JwtAuthConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.server.resource.authentication.DelegatingJwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    @Value("${keycloak.client-id}")
    private String kcClientId;

    @Value("${keycloak.issuer-url}")
    private String tokenIssuerUrl;

    private static final String RUTA_ADMIN = "/home/admin/**";
    private static final String RUTA_PUBLIC = "/home/public/**";
    private static final String[] RUTAS_PERMITIDAS = {"/auth/**", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        DelegatingJwtGrantedAuthoritiesConverter propertiesDelegator = new DelegatingJwtGrantedAuthoritiesConverter(
                new JwtGrantedAuthoritiesConverter(), new JwtAuthConverter(kcClientId));

        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(RUTA_ADMIN).hasRole("ADMIN_EDIT")
                                .requestMatchers(RUTA_PUBLIC).hasRole("USER_READ")
                                .requestMatchers(RUTAS_PERMITIDAS).permitAll()
                                .anyRequest().authenticated()
                )
                .httpBasic()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new AuthenticationEntryPoint())
                .accessDeniedHandler(new CustomAccessDenied())
                .and()
                .csrf().disable()
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(
                        jwt -> new JwtAuthenticationToken(jwt, propertiesDelegator.convert(jwt))
                );

        return http.build();
    }

    @Bean
    public JwtDecoder JwtDecoder() {
        return JwtDecoders.fromIssuerLocation(tokenIssuerUrl);
    }

    @Bean
    GrantedAuthorityDefaults defaultAuthGranted() {
        return new GrantedAuthorityDefaults("");
    }
}
