package com.packt.cardatabase.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;


@Component
public class JwtService {

    // กำหนดค่าคงที่
    static final long EXPIRATIONTIME = 86400000; // 1 day in ms
    static final String PREFIX = "Bearer";
    // Generate secret key
     // รับค่า secret key จาก application.properties
     @Value("${jwt.secret}")
     private String secretKey;
     
     // ตัวแปรสำหรับเก็บ signing key
     private java.security.Key signingKey;
 
     // สร้าง signing key เมื่อ bean ถูกสร้าง
     @PostConstruct
     private void init() {
         signingKey = Keys.hmacShaKeyFor(secretKey.getBytes());
     }
 
    // สร้างและส่งคืน token
    public String getToken(String username) {
        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(signingKey)
                .compact();
        return token;
    }

    // ดึง token จาก Authorization header
    public String getAuthUser(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token != null) {
            String user = Jwts.parserBuilder()
            .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token.replace(PREFIX, ""))
                    .getBody()
                    .getSubject();
            if (user != null)
                return user;
        }
        return null;
    }
}