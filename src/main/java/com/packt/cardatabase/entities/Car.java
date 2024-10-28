package com.packt.cardatabase.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Car {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private String brand, model, color, registerNumber;
    
    @Column(name = "production_year")
    private int year;
    
    private int price;

    // เปลี่ยนจาก ManyToOne เป็น ManyToMany
    @ManyToMany(mappedBy = "cars", fetch = FetchType.EAGER)
    private Set<Owner> owners = new HashSet<>();

    // Constructor ว่างเปล่า
    public Car() {}

    public void addOwner(Owner owner) {
        this.owners.add(owner);
        owner.getCars().add(this);
    }

    public void removeOwner(Owner owner) {
        this.owners.remove(owner);
        owner.getCars().remove(this);
    }

    // ปรับ Constructor ให้ไม่ต้องรับ owner
    public Car(String brand, String model, String color, 
               String registerNumber, int year, int price) {
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.registerNumber = registerNumber;
        this.year = year;
        this.price = price;
    }

    // เปลี่ยน getter/setter สำหรับ owners
    public Set<Owner> getOwners() {
        return owners;
    }

    public void setOwners(Set<Owner> owners) {
        this.owners = owners;
    }

    // Getters และ Setters อื่นๆ คงเดิม
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