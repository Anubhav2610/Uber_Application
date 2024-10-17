package com.agyat.project.uber.uberApp.repositories;

import com.agyat.project.uber.uberApp.entities.User;
import org.hibernate.boot.jaxb.mapping.JaxbUserTypeRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
