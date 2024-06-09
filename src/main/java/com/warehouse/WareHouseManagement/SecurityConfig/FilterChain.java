package com.warehouse.WareHouseManagement.SecurityConfig;

import com.warehouse.WareHouseManagement.Service.CustomAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class FilterChain {
    @Autowired
    private JwtFilter jwtAuthenticationFilter;
    @Autowired
    private CustomAuthenticationEntryPoint authenticationEntryPoint;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.cors(AbstractHttpConfigurer::disable);
        httpSecurity.authorizeHttpRequests(configure-> configure
                .requestMatchers("/auth/user/register/**").permitAll()
                .requestMatchers("/auth/user/login/**").permitAll()
                .requestMatchers("/h2-console/**").permitAll()
                .anyRequest().permitAll()
        );
        httpSecurity.headers(h ->
                h.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
        httpSecurity.sessionManagement((session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)));
        httpSecurity.exceptionHandling(exception->exception.authenticationEntryPoint(authenticationEntryPoint));
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
