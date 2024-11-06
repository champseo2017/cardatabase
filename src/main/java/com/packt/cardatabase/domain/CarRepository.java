package com.packt.cardatabase.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.packt.cardatabase.entities.Car;  // ควรมีบรรทัดนี้
@RepositoryRestResource // ระบุว่าเป็นแหล่งข้อมูล REST
public interface CarRepository extends CrudRepository<Car, Long> {

     Optional<Car> findByRegisterNumber(String registerNumber);

     // ค้นหารถยนต์ตามยี่ห้อ
     List<Car> findByBrand(@Param("brand") String brand);

     // ค้นหารถยนต์ตามสี
     List<Car> findByColor(@Param("color") String color);
 
     // ค้นหารถยนต์ตามปีผลิต
     List<Car> findByYear(int year);
 
     // ค้นหารถยนต์ตามยี่ห้อและรุ่น
     List<Car> findByBrandAndModel(String brand, String model);
 
     // ค้นหารถยนต์ตามยี่ห้อหรือสี
     List<Car> findByBrandOrColor(String brand, String color);

      // ค้นหารถยนต์ตามยี่ห้อ และเรียงตามปีผลิตจากน้อยไปมาก
    List<Car> findByBrandOrderByYearAsc(String brand);

     // ค้นหารถยนต์ตามยี่ห้อโดยใช้ SQL
//     @Query("select c from Car c where c.brand = ?1")
//     List<Car> findByBrand(String brand);

     // ค้นหารถยนต์ที่ยี่ห้อลงท้ายด้วยคำที่กำหนด
     @Query("select c from Car c where c.brand like %?1")
     List<Car> findByBrandEndsWith(String brand);
}