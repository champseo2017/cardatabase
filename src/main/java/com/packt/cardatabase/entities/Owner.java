package com.packt.cardatabase.entities;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Owner {
   @Id
   @GeneratedValue(strategy=GenerationType.AUTO)
   private long ownerid;
   
   private String firstname, lastname;

   // เปลี่ยน mappedBy จาก "owner" เป็น "owner_id" ให้ตรงกับชื่อคอลัมน์ใน Car
   // cascade = ALL คือเมื่อลบ Owner จะลบรถทั้งหมดของเจ้าของคนนั้นด้วย
   @OneToMany(cascade=CascadeType.ALL, mappedBy="owner_id")
   private List<Car> cars;

   public Owner() {}

   public Owner(String firstname, String lastname) {
       this.firstname = firstname;
       this.lastname = lastname;
   }

   // Getters และ Setters อื่นๆ คงเดิม...
}