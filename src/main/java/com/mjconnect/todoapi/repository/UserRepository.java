package com.mjconnect.todoapi.repository;

import com.mjconnect.todoapi.entity.TbUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<TbUser, Long> {
    TbUser findByUsername(String username);
}
