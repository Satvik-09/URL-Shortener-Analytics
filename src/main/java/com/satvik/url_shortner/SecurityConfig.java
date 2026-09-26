package com.satvik.url_shortner;


import com.satvik.url_shortner.Filter.JwtFilter;
import com.satvik.url_shortner.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;

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
        return username -> userRepository.findbyUsername(username)
                .orElseThrow(() -> new RuntimeException("username not found" + username));
    }
}
