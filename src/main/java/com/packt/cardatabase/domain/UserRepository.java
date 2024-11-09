package com.packt.cardatabase.domain;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.packt.cardatabase.entities.User; 

public interface UserRepository extends CrudRepository<User, Long> {
    // ใช้ค้นหาผู้ใช้จากฐานข้อมูลโดยใช้ username
    Optional<User> findByUsername(String username);
}
