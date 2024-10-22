package com.packt.cardatabase.domain;
import org.springframework.data.repository.CrudRepository;

import com.packt.cardatabase.entities.Owner;

// Interface นี้ใช้จัดการข้อมูลเจ้าของรถในฐานข้อมูล
// extends CrudRepository เพื่อให้สามารถเพิ่ม,อ่าน,แก้ไข,ลบข้อมูลได้
// <Owner, Long> คือ
// - Owner: ชนิดของข้อมูลที่จะจัดการ (ข้อมูลเจ้าของรถ)
// - Long: ชนิดของ ID ที่ใช้อ้างอิงข้อมูล (ตัวเลขชนิด Long)
public interface OwnerRepository extends CrudRepository<Owner, Long> {
    // ไม่จำเป็นต้องเขียนฟังก์ชันเพิ่มเพราะ CrudRepository มีฟังก์ชันพื้นฐานให้ใช้แล้ว เช่น
    // save() - บันทึกข้อมูล
    // findById() - ค้นหาจาก ID
    // findAll() - ดึงข้อมูลทั้งหมด
    // delete() - ลบข้อมูล
}
