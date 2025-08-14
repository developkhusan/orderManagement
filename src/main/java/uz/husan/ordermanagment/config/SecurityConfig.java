package uz.husan.ordermanagment.config;

import lombok.AllArgsConstructor;
import org.springframework.boot.web.servlet.filter.OrderedFormContentFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.husan.ordermanagment.repository.UserRepository;

@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class SecurityConfig {
    final UserRepository userRepository;
    final MyFilter myFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, OrderedFormContentFilter formContentFilter, UserDetailsService userDetailsService) throws Exception {
        return http
                .csrf(c->c.disable())
                .cors(c->c.disable())
                .authorizeHttpRequests(
                        auth ->
                            auth
                                .requestMatchers(
                                        "/auth/**",
                                        "/chef/**",
                                        "/swagger-ui/**",
                                        "/v3/**",
                                        "/webjars/**",
                                        "/swagger-ui.html").permitAll()
                                    .requestMatchers("/chicken/create,/meal/create").hasRole("ADMIN")
                                    .anyRequest()
                                .authenticated()
                )
                .addFilterBefore(myFilter, UsernamePasswordAuthenticationFilter.class)
                .userDetailsService(userDetailsService)
                .build();

    }
    @Bean
    public UserDetailsService userDetailsService() {
        return (username)-> userRepository.findByEmail(username).orElseThrow(
                () -> new RuntimeException("User not found with email: " + username)
        );
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
