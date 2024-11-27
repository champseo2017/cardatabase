package com.packt.cardatabase.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity // ระบุว่าคลาสนี้เป็น entity ที่ใช้จัดเก็บข้อมูลในฐานข้อมูล
@Table(name = "users") 
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(nullable=false, updatable=false)
    private Long id; // เก็บรหัสผู้ใช้ที่เป็น primary key

    @Column(nullable=false, unique=true)
    private String username; // เก็บชื่อผู้ใช้ (ต้องไม่ซ้ำกันในระบบ)

    @Column(nullable=false)
    private String password; // เก็บรหัสผ่านผู้ใช้

    @Column(nullable=false)
    private String role; // เก็บบทบาทของผู้ใช้ (เช่น USER หรือ ADMIN)

    // คอนสตรัคเตอร์เริ่มต้น
    public User() {}

    // คอนสตรัคเตอร์ที่มีการกำหนดค่าเริ่มต้น
    public User(String username, String password, String role) {
        super();
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // getter และ setter สำหรับเข้าถึงข้อมูลแต่ละฟิลด์
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}