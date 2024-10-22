package com.packt.cardatabase.entities;

// เพิ่ม import สำหรับ annotations ที่ต้องใช้
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity // บอกว่านี่คือคลาสที่ใช้แทนตารางในฐานข้อมูล
public class Car {
    @Id // บอกว่านี่คือ primary key
    @GeneratedValue(strategy=GenerationType.AUTO) // สร้าง ID อัตโนมัติ
    private long id;
    private String brand, model, color, registerNumber;
    
    @Column(name = "production_year")  // เปลี่ยนชื่อคอลัมน์เป็น production_year
    private int year;
    
    private int price;

      // เพิ่มความสัมพันธ์กับ Owner
    @ManyToOne(fetch = FetchType.LAZY) // หลายรถ มีเจ้าของได้ 1 คน, ดึงข้อมูลเมื่อต้องการ
    @JoinColumn(name = "owner_id") // สร้างคอลัมน์ owner_id เป็น foreign key
    private Owner owner; // ตัวแปรเก็บข้อมูลเจ้าของรถ

    // Constructor ว่างเปล่า (จำเป็นสำหรับ JPA)
    public Car() {}

    // Constructor ที่รับพารามิเตอร์
    public Car(String brand, String model, String color, 
               String registerNumber, int year, int price, Owner owner) {
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.registerNumber = registerNumber;
        this.year = year;
        this.price = price;
        this.owner = owner;  // เพิ่มการกำหนดค่า owner
    }

    // เพิ่ม Getter และ Setter สำหรับ owner
    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}