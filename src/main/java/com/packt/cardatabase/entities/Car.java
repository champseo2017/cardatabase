package com.packt.cardatabase.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity // บอกว่านี่คือคลาสที่ใช้แทนตารางในฐานข้อมูล
public class Car {
    @Id // บอกว่านี่คือ primary key
    @GeneratedValue(strategy=GenerationType.AUTO) // สร้าง ID อัตโนมัติ
    private long id;
    private String brand, model, color, registerNumber;
    
    @Column(name = "production_year")  // เปลี่ยนชื่อคอลัมน์เป็น production_year
    private int year;
    
    private int price;

    // Constructor ว่างเปล่า (จำเป็นสำหรับ JPA)
    public Car() {}

    // Constructor ที่รับพารามิเตอร์
    public Car(String brand, String model, String color, 
               String registerNumber, int year, int price) {
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.registerNumber = registerNumber;
        this.year = year;
        this.price = price;
    }

    // Getters และ Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}