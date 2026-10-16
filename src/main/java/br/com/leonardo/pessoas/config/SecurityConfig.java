package br.com.leonardo.pessoas.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // DELETE é a operação mais crítica (remove dados), por isso exige usuário e senha
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // retorna 401 sem o header WWW-Authenticate, para o navegador não abrir o popup de login
        AuthenticationEntryPoint naoAutorizado = (request, response, ex) ->
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.DELETE, "/list/**").authenticated()
                        .anyRequest().permitAll())
                .exceptionHandling(e -> e.authenticationEntryPoint(naoAutorizado))
                .httpBasic(basic -> basic.authenticationEntryPoint(naoAutorizado));
        return http.build();
    }

    @Bean
    public UserDetailsService usuarios(PasswordEncoder encoder,
                                       @Value("${app.usuario}") String usuario,
                                       @Value("${app.senha}") String senha) {
        return new InMemoryUserDetailsManager(
                User.withUsername(usuario).password(encoder.encode(senha)).roles("ADMIN").build());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
