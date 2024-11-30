package com.outdoor.connect.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.outdoor.connect.security.filter.JwtAuthFilter;
import com.outdoor.connect.security.handler.AppAuthenticationSuccessHandler;
import com.outdoor.connect.security.service.impl.UserDetailServiceImpl;

/**
 * 
 * @author James Carl Oreto
 * 
 */

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public SecurityConfig() {
    }

    @Bean
    public JwtAuthFilter authFilter() {
        return new JwtAuthFilter();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailServiceImpl();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(r -> r
                        .requestMatchers("/users/login", "/users/create", "/resources/**", "/event/all",
                                "/swagger-ui/**", "/api-docs/**")
                        .permitAll()
                        .requestMatchers("/ad/**").hasAnyRole("ADMIN", "ADMIN02")
                        .anyRequest().authenticated())
                .sessionManagement(t -> t
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(authFilter(), UsernamePasswordAuthenticationFilter.class)
                .headers(headers -> headers.frameOptions(FrameOptionsConfig::disable))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationSuccessHandler appAuthenticationSuccessHandler() {
        return new AppAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
