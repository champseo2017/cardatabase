package com.packt.cardatabase.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity // บอกว่าคลาสนี้เป็น Entity ที่จะถูกเก็บในฐานข้อมูล
public class Owner {
    @Id // กำหนดให้เป็น Primary Key
    @GeneratedValue(strategy = GenerationType.AUTO) // สร้าง ID อัตโนมัติ
    private long ownerid;
    
    private String firstname;
    private String lastname;

    // ความสัมพันธ์แบบ One-to-Many กับ Car
    // cascade = ALL หมายถึง เมื่อลบ Owner จะลบรถทั้งหมดของเจ้าของคนนั้นด้วย
    // mappedBy="owner_id" อ้างอิงถึงชื่อฟิลด์ใน Entity Car
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner_id", fetch = FetchType.EAGER)
    private List<Car> cars;

    // Constructor ว่างเปล่า (จำเป็นสำหรับ JPA)
    public Owner() {}

    // Constructor สำหรับสร้าง Owner ใหม่
    public Owner(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    // Getters และ Setters
    public long getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(long ownerid) {
        this.ownerid = ownerid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    // Override toString() เพื่อแสดงข้อมูลในรูปแบบที่อ่านง่าย
    @Override
    public String toString() {
        return String.format(
            "Owner[id=%d, firstname='%s', lastname='%s']",
            ownerid, firstname, lastname
        );
    }
}