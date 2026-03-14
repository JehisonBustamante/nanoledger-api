package NanoLedgerAPI.NanoLedgerAPI.config;

import NanoLedgerAPI.NanoLedgerAPI.security.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Deshabilitar CSRF (común en APIs REST)
                .csrf(csrf -> csrf.disable())
                // Configurar políticas de acceso a rutas
                .authorizeHttpRequests(auth -> auth
                        // Rutas públicas: Autenticación, H2 Console (para pruebas) y Swagger UI
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        // Cualquier otra ruta requiere autenticación con JWT
                        .anyRequest().authenticated()
                )
                // Habilitar frames para H2 console
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                // Configurar manejo de sesión como Stateless (cada request debe traer su propio token)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Añadir nuestro filtro JWT antes del filtro de autenticación estándar de Spring
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
