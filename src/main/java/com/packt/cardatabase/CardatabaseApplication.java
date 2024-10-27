package com.packt.cardatabase;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.packt.cardatabase.domain.CarRepository;
import com.packt.cardatabase.domain.OwnerRepository;
import com.packt.cardatabase.entities.Car;
import com.packt.cardatabase.entities.Owner;

@SpringBootApplication
public class CardatabaseApplication implements CommandLineRunner {
	 // สร้างตัวบันทึกล็อก
	private static final Logger logger = LoggerFactory.getLogger(CardatabaseApplication.class);

	@Autowired
    private CarRepository carRepository;

    @Autowired
    private OwnerRepository ownerRepository;

	public static void main(String[] args) {
		 
		SpringApplication.run(CardatabaseApplication.class, args);
		logger.info("แอปพลิเคชันเริ่มทำงานแล้ว");
	}

	// เมธอดที่จะทำงานเมื่อแอปพลิเคชันเริ่มต้น
    @Override
    public void run(String... args) throws Exception {
         try {
            // ค้นหาเจ้าของรถที่มีอยู่ หรือสร้างใหม่ถ้ายังไม่มี
            Optional<Owner> owner1 = ownerRepository.findByFirstnameAndLastname("จอห์น", "จอห์นสัน");
            if (!owner1.isPresent()) {
                // สร้างเจ้าของรถคนที่ 1 ผ่าน Repository
                owner1 = Optional.of(ownerRepository.save(new Owner("จอห์น", "จอห์นสัน")));
                logger.info("สร้างเจ้าของรถคนที่ 1 สำเร็จ: " + owner1.get().getFirstname());
            }

            Optional<Owner> owner2 = ownerRepository.findByFirstnameAndLastname("มารี", "โรบินสัน");
            if (!owner2.isPresent()) {
                // สร้างเจ้าของรถคนที่ 2 ผ่าน Repository
                owner2 = Optional.of(ownerRepository.save(new Owner("มารี", "โรบินสัน")));
                logger.info("สร้างเจ้าของรถคนที่ 2 สำเร็จ: " + owner2.get().getFirstname());
            }

            // สร้างและบันทึกข้อมูลรถยนต์โดยใช้ ID ของเจ้าของ
            if (owner1.isPresent() && owner2.isPresent()) {
                // ตรวจสอบว่ารถยนต์มีอยู่แล้วหรือไม่
                if (!carRepository.findByRegisterNumber("ADF-1121").isPresent()) {
                    carRepository.save(new Car("Ford", "Mustang", "Red", 
                        "ADF-1121", 2021, 59000, owner1.get()));
                }

                if (!carRepository.findByRegisterNumber("SSJ-3002").isPresent()) {
                    carRepository.save(new Car("Nissan", "Leaf", "White", 
                        "SSJ-3002", 2019, 29000, owner2.get()));
                }

                if (!carRepository.findByRegisterNumber("KKO-0212").isPresent()) {
                    carRepository.save(new Car("Toyota", "Prius", "Silver", 
                        "KKO-0212", 2020, 39000, owner2.get()));
                }
            }

            // แสดงข้อมูลทั้งหมด
            logger.info("=== ข้อมูลรถยนต์ทั้งหมด ===");
            carRepository.findAll().forEach(car -> {
                logger.info(String.format(
                    "รถ: %s %s, ทะเบียน: %s, เจ้าของ: %s %s",
                    car.getBrand(),
                    car.getModel(),
                    car.getRegisterNumber(),
                    car.getOwner_id().getFirstname(),
                    car.getOwner_id().getLastname()
                ));
            });

            // ตัวอย่างการค้นหาข้อมูลผ่าน Repository
            logger.info("=== ค้นหารถตามเงื่อนไขต่างๆ ===");
            
            // ค้นหารถตามสี
            logger.info("รถสีขาว:");
            carRepository.findByColor("White").forEach(car -> 
                logger.info(car.getBrand() + " " + car.getModel())
            );

            // ค้นหารถตามปี
            logger.info("รถปี 2020:");
            carRepository.findByYear(2020).forEach(car -> 
                logger.info(car.getBrand() + " " + car.getModel())
            );

        } catch (Exception e) {
            logger.error("เกิดข้อผิดพลาด: " + e.getMessage());
        }
    }

}
