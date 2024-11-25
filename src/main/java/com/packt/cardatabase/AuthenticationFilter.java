package com.packt.cardatabase;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.packt.cardatabase.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
            HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        // ดึง token จาก header
        String jws = request.getHeader(HttpHeaders.AUTHORIZATION);
        
        if (jws != null && jws.startsWith("Bearer ")) {
            // ตัด Bearer prefix ออกเพื่อให้ได้ token
            String token = jws.substring(7);
            
            // ตรวจสอบ token และดึงข้อมูลผู้ใช้
            String user = jwtService.getAuthUser(request);
            
            // ตั้งค่าการยืนยันตัวตน
            Authentication authentication = 
                new UsernamePasswordAuthenticationToken(user, null, java.util.Collections.emptyList());
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}