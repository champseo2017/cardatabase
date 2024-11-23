package com.packt.cardatabase.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.packt.cardatabase.entities.AccountCredentials;
import com.packt.cardatabase.service.JwtService;

@RestController  // บอกว่าคลาสนี้เป็น REST Controller
public class LoginController {
    @Autowired
    private JwtService jwtService;  // ใช้สำหรับสร้าง JWT token
    
    @Autowired
    private AuthenticationManager authenticationManager;  // ใช้สำหรับตรวจสอบการล็อกอิน
    
    // endpoint สำหรับล็อกอิน
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public ResponseEntity<?> getToken(@RequestBody AccountCredentials credentials) {
        try {
            // สร้างข้อมูลสำหรับตรวจสอบการล็อกอิน
            UsernamePasswordAuthenticationToken creds = 
                new UsernamePasswordAuthenticationToken(
                    credentials.getUsername(), 
                    credentials.getPassword()
                );
            
            // ตรวจสอบข้อมูลการล็อกอิน
            Authentication auth = authenticationManager.authenticate(creds);
            
            // ถ้าล็อกอินสำเร็จ สร้าง JWT token
            String jwts = jwtService.getToken(auth.getName());
            
            // ส่งคืน token ในส่วนหัวของการตอบกลับ
            return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwts)
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization")
                .build();
                
        } catch (Exception e) {
            // ถ้าล็อกอินไม่สำเร็จ ส่งสถานะ 401 Unauthorized
            return ResponseEntity.status(401).build();
        }
    }
}
