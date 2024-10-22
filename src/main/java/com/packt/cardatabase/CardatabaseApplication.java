package com.packt.cardatabase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.packt.cardatabase.domain.CarRepository;

@SpringBootApplication
public class CardatabaseApplication implements CommandLineRunner {
	 // สร้างตัวบันทึกล็อก
	private static final Logger logger = LoggerFactory.getLogger(CardatabaseApplication.class);

	 // ใช้ @Autowired เพื่อให้ Spring จัดการการสร้าง CarRepository ให้อัตโนมัติ
	 @Autowired
	 private CarRepository repository;

	public static void main(String[] args) {
		 
		SpringApplication.run(CardatabaseApplication.class, args);
		logger.info("แอปพลิเคชันเริ่มทำงานแล้ว");
	}

	// เมธอดที่จะทำงานเมื่อแอปพลิเคชันเริ่มต้น
    @Override
    public void run(String... args) throws Exception {
        // เพิ่มข้อมูลรถยนต์ 3 คัน
		// repository.save(new Car("Ford", "Mustang", "Red", "ADF-1121", 2021, 59000));
		// repository.save(new Car("Nissan", "Leaf", "White", "SSJ-3002", 2019, 29000));
		// repository.save(new Car("Toyota", "Prius", "Silver", "KKO-0212", 2020, 39000));

		// // ดึงข้อมูลรถยนต์ทั้งหมดและแสดงในคอนโซล
		// for (Car car : repository.findAll()) {
		// 	logger.info(car.getBrand() + " " + car.getModel());
		// }
    }

}
