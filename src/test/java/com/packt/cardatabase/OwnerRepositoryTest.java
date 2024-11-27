package com.packt.cardatabase;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import com.packt.cardatabase.entities.Owner;
import com.packt.cardatabase.repositories.OwnerRepository;

@DataJpaTest  // ใช้สำหรับทดสอบ JPA โดยเฉพาะ
@ActiveProfiles("test")  // เพิ่มบรรทัดนี้
// เพิ่ม annotation นี้เพื่อไม่ให้โหลด security configuration
@TestPropertySource(properties = {
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
    "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect"
})
public class OwnerRepositoryTest {
    @Autowired
    private OwnerRepository repository;  // ตัวจัดการข้อมูล Owner

    private Owner testOwner;  // ข้อมูลทดสอบ

    @BeforeEach  // รันก่อนทุกเทสเคส
    void setUp() {
        // สร้างข้อมูลทดสอบใหม่ทุกครั้ง
        testOwner = new Owner("John", "Doe");
    }

    @Test
    @DisplayName("Test saving owner")  // ชื่อเทสที่จะแสดงในผลการทดสอบ
    void testSaveOwner() {
        // บันทึกข้อมูล Owner
        Owner savedOwner = repository.save(testOwner);
        
        // ตรวจสอบว่าบันทึกสำเร็จ
        assertThat(savedOwner.getOwnerid()).isNotNull();
        assertThat(savedOwner.getFirstname()).isEqualTo("John");
        assertThat(savedOwner.getLastname()).isEqualTo("Doe");
    }

    @Test
    @DisplayName("Test finding by firstname and lastname")
    void testFindByFirstnameAndLastname() {
        // บันทึกข้อมูลก่อนทดสอบการค้นหา
        repository.save(testOwner);
        
        // ทดสอบค้นหาด้วยชื่อและนามสกุล
        Optional<Owner> found = repository.findByFirstnameAndLastname("John", "Doe");
        
        // ตรวจสอบว่าพบข้อมูล
        assertThat(found).isPresent();
        assertThat(found.get().getFirstname()).isEqualTo("John");
        assertThat(found.get().getLastname()).isEqualTo("Doe");
    }

    @Test
    @DisplayName("Test finding by firstname")
    void testFindByFirstname() {
        // บันทึกข้อมูลก่อนทดสอบ
        repository.save(testOwner);
        
        // ทดสอบค้นหาด้วยชื่อ
        Optional<Owner> found = repository.findByFirstname("John");
        
        // ตรวจสอบผลลัพธ์
        assertThat(found).isPresent();
        assertThat(found.get().getFirstname()).isEqualTo("John");
    }

    @Test
    @DisplayName("Test finding by lastname")
    void testFindByLastname() {
        // บันทึกข้อมูลก่อนทดสอบ
        repository.save(testOwner);
        
        // ทดสอบค้นหาด้วยนามสกุล
        Optional<Owner> found = repository.findByLastname("Doe");
        
        // ตรวจสอบผลลัพธ์
        assertThat(found).isPresent();
        assertThat(found.get().getLastname()).isEqualTo("Doe");
    }

    @Test
    @DisplayName("Test deleting owner")
    void testDeleteOwner() {
        // บันทึกข้อมูลก่อนทดสอบการลบ
        Owner savedOwner = repository.save(testOwner);
        
        // ทดสอบการลบข้อมูล
        repository.deleteById(savedOwner.getOwnerid());
        
        // ตรวจสอบว่าลบสำเร็จ
        Optional<Owner> found = repository.findById(savedOwner.getOwnerid());
        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("Test updating owner")
    void testUpdateOwner() {
        // บันทึกข้อมูลก่อนทดสอบการอัพเดท
        Owner savedOwner = repository.save(testOwner);
        
        // ทดสอบการอัพเดทข้อมูล
        savedOwner.setFirstname("Jane");
        Owner updatedOwner = repository.save(savedOwner);
        
        // ตรวจสอบว่าอัพเดทสำเร็จ
        assertThat(updatedOwner.getFirstname()).isEqualTo("Jane");
        assertThat(updatedOwner.getOwnerid()).isEqualTo(savedOwner.getOwnerid());
    }
}
