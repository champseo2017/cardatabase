package com.packt.cardatabase.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.packt.cardatabase.entities.User;

@RepositoryRestResource(exported = false)
public interface UserRepository extends CrudRepository<User, Long> {
    // ใช้ค้นหาผู้ใช้จากฐานข้อมูลโดยใช้ username
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
