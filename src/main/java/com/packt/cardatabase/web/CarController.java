package com.packt.cardatabase.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.packt.cardatabase.domain.CarRepository;
import com.packt.cardatabase.entities.Car;

@RestController // ระบุว่าคลาสนี้เป็น REST Controller
public class CarController {
    @Autowired // ฉีด dependencies ของ CarRepository
    private CarRepository carRepository;

    @GetMapping("/cars") // แมปคำขอที่เข้ามาที่ "/cars" มายังเมธอดนี้
    public Iterable<Car> getCars() {
        // ดึงข้อมูลรถยนต์และส่งกลับ
        return carRepository.findAll(); // สมมติว่ามี carRepository สำหรับดึงข้อมูล
    }
}
