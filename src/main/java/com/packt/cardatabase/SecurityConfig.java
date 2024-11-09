package com.packt.cardatabase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration // กำหนดว่า class นี้ใช้สำหรับ configuration
@EnableWebSecurity // เปิดใช้งาน Spring Security ในโปรเจค
public class SecurityConfig {

    // Bean สำหรับกำหนดค่า SecurityFilterChain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // กำหนดให้ทุก request ต้องผ่านการยืนยันตัวตน
        http.authorizeHttpRequests(auth -> auth
            .anyRequest().authenticated()
        ).httpBasic(Customizer.withDefaults()); // ใช้ Basic Authentication แบบค่าเริ่มต้น

        return http.build();
    }

    // Bean สำหรับการตั้งค่าผู้ใช้ในระบบในหน่วยความจำ
    @Bean
    public UserDetailsService userDetailsService() {
        // ใช้ PasswordEncoder ที่ปลอดภัยในการเข้ารหัสรหัสผ่าน
        UserDetails user = User.builder()
            .username("user") // ตั้ง username เป็น "user"
            .password(passwordEncoder().encode("password")) // เข้ารหัส password ด้วย BCrypt
            .roles("USER") // ระบุ role เป็น "USER"
            .build();

        return new InMemoryUserDetailsManager(user); // สร้าง InMemoryUserDetailsManager สำหรับเก็บ user ในหน่วยความจำ
    }

    // Bean สำหรับ PasswordEncoder เพื่อเข้ารหัสรหัสผ่าน
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // ใช้ BCrypt สำหรับการเข้ารหัสรหัสผ่าน
    }
}