package com.example.attendance.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
 
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/attendance/mark/**").hasRole("EMPLOYEE")
                .requestMatchers("/api/attendance/**").hasRole("MANAGER")
                .anyRequest().authenticated())
            .httpBasic();
        return http.build();
    }
 
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails emp = User.withUsername("emp")
            .password("{noop}password")
            .roles("EMPLOYEE")
            .build();
 
        UserDetails mgr = User.withUsername("mgr")
            .password("{noop}password")
            .roles("MANAGER")
            .build();
 
        return new InMemoryUserDetailsManager(emp, mgr);
    }
}
