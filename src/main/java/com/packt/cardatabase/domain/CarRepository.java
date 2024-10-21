package com.packt.cardatabase.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.packt.cardatabase.entities.Car;  // ควรมีบรรทัดนี้

public interface CarRepository extends CrudRepository<Car, Long> {
     // ค้นหารถยนต์ตามยี่ห้อ
     List<Car> findByBrand(String brand);

     // ค้นหารถยนต์ตามสี
     List<Car> findByColor(String color);
 
     // ค้นหารถยนต์ตามปีผลิต
     List<Car> findByYear(int year);
 
     // ค้นหารถยนต์ตามยี่ห้อและรุ่น
     List<Car> findByBrandAndModel(String brand, String model);
 
     // ค้นหารถยนต์ตามยี่ห้อหรือสี
     List<Car> findByBrandOrColor(String brand, String color);
}