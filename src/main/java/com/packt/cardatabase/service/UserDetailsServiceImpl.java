package com.packt.cardatabase.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.packt.cardatabase.domain.UserRepository;
import com.packt.cardatabase.entities.User;

@Service // บอกว่าคลาสนี้เป็น Service ของ Spring
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository repository; // เชื่อมต่อกับ UserRepository เพื่อดึงข้อมูลผู้ใช้

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // ค้นหาผู้ใช้จากฐานข้อมูล
        Optional<User> user = repository.findByUsername(username);
        
        UserBuilder builder = null;
        if (user.isPresent()) { // ถ้าเจอผู้ใช้
            User currentUser = user.get();
            // สร้างข้อมูลผู้ใช้สำหรับ Spring Security
            builder = org.springframework.security.core.userdetails.User.withUsername(username);
            builder.password(currentUser.getPassword()); // ใส่รหัสผ่าน
            builder.roles(currentUser.getRole()); // ใส่สิทธิ์การใช้งาน
        } else {
            // ถ้าไม่เจอผู้ใช้ให้แจ้งเตือน
            throw new UsernameNotFoundException("ไม่พบผู้ใช้: " + username);
        }

        return builder.build(); // สร้างข้อมูลผู้ใช้เสร็จแล้วส่งคืน
    }
}
