package com.example.courseEnrollment.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.courseEnrollment.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {}
