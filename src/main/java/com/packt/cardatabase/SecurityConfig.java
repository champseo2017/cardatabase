package com.packt.cardatabase;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.packt.cardatabase.service.UserDetailsServiceImpl;

@Configuration // กำหนดว่า class นี้ใช้สำหรับ configuration
@EnableWebSecurity // เปิดใช้งาน Spring Security ในโปรเจค
public class SecurityConfig {

    @Autowired
    private AuthEntryPoint exceptionHandler;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthenticationFilter authenticationFilter;

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("*")); // อนุญาตทุกต้นทาง
        config.setAllowedMethods(Arrays.asList("*")); // อนุญาตทุกวิธีการ HTTP
        config.setAllowedHeaders(Arrays.asList("*")); // อนุญาตทุก headers
        config.setAllowCredentials(false); // ไม่อนุญาตให้ส่ง credentials
        config.applyPermitDefaultValues(); // ใช้ค่าเริ่มต้นที่อนุญาต
        source.registerCorsConfiguration("/**", config); // ลงทะเบียนการตั้งค่า CORS
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // อนุญาตให้เข้าถึงทุกส่วนโดยไม่ต้องยืนยันตัวตน
        http
            .csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            );

        return http.build();

        /* ปิดการทำงานของระบบความปลอดภัยไว้ก่อน
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/signup").permitAll()
                        .requestMatchers("/api/test").permitAll()
                        .anyRequest().authenticated() // ทุก request อื่น ๆ ต้องผ่านการยืนยันตัวตน
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(exceptionHandler))
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
        */
    }
}