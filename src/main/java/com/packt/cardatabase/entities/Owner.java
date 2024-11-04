package com.packt.cardatabase.entities;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore; // ใช้สำหรับละเว้นฟิลด์ใน serialize
import com.fasterxml.jackson.annotation.JsonIgnoreProperties; // ใช้สำหรับละเว้น properties ที่ระบุ

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;

@Entity // บอกว่าคลาสนี้เป็น Entity ที่จะถูกเก็บในฐานข้อมูล
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // ละเว้นฟิลด์ที่สร้างโดย Hibernate
public class Owner {
    @Id // กำหนดให้เป็น Primary Key
    @GeneratedValue(strategy = GenerationType.AUTO) // สร้าง ID อัตโนมัติ
    private long ownerid;
    
    @NotBlank(message = "ต้องระบุชื่อ")
    @Column(nullable = false)
    private String firstname;

    @NotBlank(message = "ต้องระบุนามสกุล")
    @Column(nullable = false)
    private String lastname;

     // เปลี่ยนเป็น EAGER loading และเพิ่ม fetch ให้ชัดเจน
    @JsonIgnore // ละเว้นฟิลด์นี้ในกระบวนการ serialize
    @ManyToMany(
        cascade = {CascadeType.PERSIST, CascadeType.MERGE},
        fetch = FetchType.EAGER
    )
    @JoinTable(
        name = "owner_car", 
        joinColumns = @JoinColumn(name = "owner_id"),
        inverseJoinColumns = @JoinColumn(name = "car_id")
    )
    private Set<Car> cars = new HashSet<>();

    // Constructor ว่างเปล่า (จำเป็นสำหรับ JPA)
    public Owner() {}

    // เพิ่มเมธอดจัดการความสัมพันธ์
    public void addCar(Car car) {
        this.cars.add(car); // เพิ่มรถเข้าไปในรายการรถของเจ้าของ
        car.getOwners().add(this); // เพิ่มเจ้าของ (ตัวเอง) เข้าไปในรายการเจ้าของของรถ
    }

    public void removeCar(Car car) {
        this.cars.remove(car);
        car.getOwners().remove(this);
    }

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

    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
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