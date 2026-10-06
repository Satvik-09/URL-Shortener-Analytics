package com.satvik.url_shortner.securityConfiguration;


import com.satvik.url_shortner.Filter.JwtFilter;
import com.satvik.url_shortner.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtfilter;

    public SecurityConfig(JwtFilter jwtfilter){
        this.jwtfilter = jwtfilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository){
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("username not found" + username));
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests( auth -> auth
                                .requestMatchers("/admin/**","/auth/protected-test").authenticated()
                .requestMatchers("/auth/**","/shorten").permitAll()

                                .requestMatchers("/{code}").permitAll()
                .anyRequest()
                .authenticated()
                )
        .addFilterBefore(jwtfilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
