package com.packt.cardatabase.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.packt.cardatabase.entities.Car;
import com.packt.cardatabase.repositories.CarRepository;

@RestController
@RequestMapping("/api/v1/cars")
public class CarController {

    @Autowired
    private CarRepository carRepository;

    // ดึงข้อมูลรถทั้งหมด
    @GetMapping
    public Iterable<Car> getCars() {
        return carRepository.findAll();
    }

    // ดึงข้อมูลรถตาม id
    @GetMapping("/{id}")
    public ResponseEntity<?> getCar(@PathVariable Long id) {
        return carRepository.findById(id)
                .map(car -> ResponseEntity.ok().body(car))
                .orElse(ResponseEntity.notFound().build());
    }

    // ลบข้อมูลรถ
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> deleteCar(@PathVariable Long id) {
        return carRepository.findById(id)
                .map(car -> {
                    // ล้างความสัมพันธ์กับเจ้าของก่อน
                    car.getOwners().forEach(owner -> {
                        owner.getCars().remove(car);
                    });
                    car.getOwners().clear();
                    
                    // ลบรถ
                    carRepository.delete(car);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // เพิ่มข้อมูลรถ
    @PostMapping
    public Car addCar(@RequestBody Car car) {
        return carRepository.save(car);
    }

    // แก้ไขข้อมูลรถ
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCar(@PathVariable Long id, @RequestBody Car carDetails) {
        return carRepository.findById(id)
                .map(car -> {
                    car.setBrand(carDetails.getBrand());
                    car.setModel(carDetails.getModel());
                    car.setColor(carDetails.getColor());
                    car.setRegisterNumber(carDetails.getRegisterNumber());
                    car.setYear(carDetails.getYear());
                    car.setPrice(carDetails.getPrice());
                    Car updatedCar = carRepository.save(car);
                    return ResponseEntity.ok().body(updatedCar);
                })
                .orElse(ResponseEntity.notFound().build());
    }
} 