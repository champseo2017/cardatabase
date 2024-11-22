package com.packt.cardatabase.entities;

// คลาสนี้ใช้สำหรับเก็บข้อมูลการล็อกอิน (username และ password)
public class AccountCredentials {
    // ตัวแปรสำหรับเก็บชื่อผู้ใช้
    private String username;

    // ตัวแปรสำหรับเก็บรหัสผ่าน
    private String password;

    // เมธอดสำหรับดึงค่าชื่อผู้ใช้
    public String getUsername() {
        return username;
    }

    // เมธอดสำหรับกำหนดค่าชื่อผู้ใช้
    public void setUsername(String username) {
        this.username = username;
    }

    // เมธอดสำหรับดึงค่ารหัสผ่าน
    public String getPassword() {
        return password;
    }

    // เมธอดสำหรับกำหนดค่ารหัสผ่าน
    public void setPassword(String password) {
        this.password = password;
    }
}
