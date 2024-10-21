package com.packt.cardatabase.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Owner {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long ownerid;
    
    private String firstname, lastname;

    // Constructor แบบไม่มีพารามิเตอร์
    public Owner() {}

    // Constructor แบบมีพารามิเตอร์
    public Owner(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    // Getters และ Setters
    // ...
}