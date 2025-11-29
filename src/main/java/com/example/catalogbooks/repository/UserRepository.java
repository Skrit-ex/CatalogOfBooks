package com.example.catalogbooks.repository;

import com.example.catalogbooks.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
