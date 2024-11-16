package com.packt.cardatabase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.packt.cardatabase.service.UserDetailsServiceImpl;


@Configuration // กำหนดว่า class นี้ใช้สำหรับ configuration
@EnableWebSecurity // เปิดใช้งาน Spring Security ในโปรเจค
public class SecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsService; // เรียกใช้ service ที่เราสร้างไว้
    
    @Bean  // ประกาศว่านี่คือ Bean ที่จะถูกจัดการโดย Spring
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // ปิดการป้องกัน CSRF เพื่อให้สามารถเรียก API ได้ง่ายขึ้น (ใช้ในการพัฒนา)
            .csrf(csrf -> csrf.disable())
            // เปิดใช้งาน CORS ด้วยค่าเริ่มต้น เพื่อให้เรียก API จากโดเมนอื่นได้
            .cors(Customizer.withDefaults())
            // กำหนดให้ใช้ userDetailsService ที่เราสร้างขึ้นในการตรวจสอบผู้ใช้
            .userDetailsService(userDetailsService)
            // กำหนดกฎการเข้าถึง API
            .authorizeHttpRequests(auth -> auth
                // ทุกคำขอต้องยืนยันตัวตนก่อนเข้าใช้งาน
                .anyRequest().authenticated()
            )
            // เปิดใช้งานการยืนยันตัวตนแบบพื้นฐาน (username/password)
            .httpBasic(Customizer.withDefaults());

        // สร้างและส่งคืน SecurityFilterChain
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // ใช้ BCrypt ในการเข้ารหัสรหัสผ่าน
    }
}