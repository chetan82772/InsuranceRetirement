package com.retirement.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retirement.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}