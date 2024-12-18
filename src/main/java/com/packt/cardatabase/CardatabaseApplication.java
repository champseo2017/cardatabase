package com.packt.cardatabase;

import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.packt.cardatabase.entities.Car;
import com.packt.cardatabase.entities.Owner;
import com.packt.cardatabase.entities.User;
import com.packt.cardatabase.repositories.CarRepository;
import com.packt.cardatabase.repositories.OwnerRepository;
import com.packt.cardatabase.repositories.UserRepository;

@SpringBootApplication
public class CardatabaseApplication implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(CardatabaseApplication.class);

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(CardatabaseApplication.class, args);
        logger.info("แอปพลิเคชันเริ่มทำงานแล้ว");
    }


    @Transactional
    private void createCarWithOwners(String registerNumber, Car car, Set<Owner> owners) {
        try {
            // ตรวจสอบว่ามีรถคันนี้อยู่แล้วหรือไม่
            Optional<Car> existingCar = carRepository.findByRegisterNumber(registerNumber);
            Car savedCar;

            if (existingCar.isEmpty()) {
                // บันทึกรถใหม่
                savedCar = carRepository.save(car);
                logger.info("สร้างรถใหม่: " + car.getBrand() + " " + car.getModel());
            } else {
                savedCar = existingCar.get();
                logger.info("พบรถที่มีอยู่แล้ว: " + savedCar.getBrand() + " " + savedCar.getModel());
            }

            // ล้างความสัมพันธ์เก่าก่อน (ถ้าต้องการ)
            savedCar.getOwners().clear();
            
            // สร้างความสัมพันธ์ใหม่
            for (Owner owner : owners) {
                // รวจสอบว่าความสัมพันธ์มีอยู่แล้วหรือไม่
                if (!savedCar.getOwners().contains(owner)) {
                    savedCar.getOwners().add(owner);
                    owner.getCars().add(savedCar);
                    ownerRepository.save(owner);
                    logger.info(String.format("เพิ่มความสัมพันธ์: รถ %s กับเจ้าของ %s %s",
                        savedCar.getBrand(),
                        owner.getFirstname(),
                        owner.getLastname()));
                }
            }
            
            // บันทึกรถอีกครั้งเพื่ออัพเดทความสัมพันธ์
            carRepository.save(savedCar);
            
        } catch (Exception e) {
            logger.error("เกิดข้อผิดพลาดในการสร้างรถหรือความสัมพันธ์: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        try {
            // ลรวจสอบว่ามีผู้ใช้ในระบบหรือยัง
            if (userRepository.count() == 0) {
                logger.info("=== เพิ่มผู้ใช้ระบบ ===");
                userRepository.save(new User("user",
                    "$2a$10$NVM0n8ElaRgg7zWO1CxUdei7vWoPg91Lz2aYavh9.f9q0e4bRadue",
                    "USER"));
                userRepository.save(new User("admin",
                    "$2a$10$8cjz47bjbR4Mn8GMg9IZx.vyjhLXR/SKKMSZ9.mP9vpMu0ssKi8GW",
                    "ADMIN"));
                logger.info("เพิ่มผู้ใช้ระบบเรียบร้อยแล้ว");
            }

            // ตรวจสอบว่ามีข้อมูลรถในระบบหรือยัง
            if (carRepository.count() == 0) {
                logger.info("=== เพิ่มข้อมูลรถและเจ้าของ ===");
                
                // สร้างเจ้าของรถ
                Owner owner1 = new Owner("จอห์น", "จอห์นสัน");
                Owner owner2 = new Owner("มารี", "โรบินสัน");
                
                owner1 = ownerRepository.save(owner1);
                owner2 = ownerRepository.save(owner2);
                
                // สร้างรถและความสัมพันธ์
                createCarWithOwners("ADF-1121",
                    new Car("Ford", "Mustang", "Red", "ADF-1121", 2021, 59000),
                    Set.of(owner1));

                createCarWithOwners("SSJ-3002",
                    new Car("Nissan", "Leaf", "White", "SSJ-3002", 2019, 29000),
                    Set.of(owner1, owner2));

                createCarWithOwners("KKO-0212",
                    new Car("Toyota", "Prius", "Silver", "KKO-0212", 2020, 39000),
                    Set.of(owner2));

                logger.info("เพิ่มข้อมูลรถและเจ้าของเรียบร้อยแล้ว");
            }

        } catch (Exception e) {
            logger.error("เกิดข้อผิดพลาด: ", e);
            throw e;
        }
    }
}