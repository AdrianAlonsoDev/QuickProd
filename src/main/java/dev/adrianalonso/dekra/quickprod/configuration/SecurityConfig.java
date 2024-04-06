package dev.adrianalonso.dekra.quickprod.configuration;

import dev.adrianalonso.dekra.quickprod.jwt.JwtAuthFilter;
import dev.adrianalonso.dekra.quickprod.user.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // Permite el acceso público a los recursos estáticos
                        .requestMatchers("/index.html", "/script.js", "/style.css").permitAll()
                        // Configuración de seguridad específica para rutas de API
                        .requestMatchers("/api/v1/demo/**").hasAuthority("USER")
                        .requestMatchers("/api/v1/demo/good-bye").hasAuthority("ADMIN")
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        // Todas las demás solicitudes requieren autenticación
                       
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        ;

        return http.build();
    }
}

