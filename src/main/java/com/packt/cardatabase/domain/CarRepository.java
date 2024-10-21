package com.packt.cardatabase.domain;

import org.springframework.data.repository.CrudRepository;

import com.packt.cardatabase.entities.Car;  // ควรมีบรรทัดนี้

public interface CarRepository extends CrudRepository<Car, Long> {
    // เมธอดเพิ่มเติม (ถ้ามี)
}