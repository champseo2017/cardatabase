package com.packt.cardatabase.domain;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.packt.cardatabase.entities.Owner; 

public interface OwnerRepository extends CrudRepository<Owner, Long> {
    
    Optional<Owner> findByFirstnameAndLastname(String firstname, String lastname);

    Optional<Owner> findByFirstname(String firstname);
    Optional<Owner> findByLastname(String lastname);
}
