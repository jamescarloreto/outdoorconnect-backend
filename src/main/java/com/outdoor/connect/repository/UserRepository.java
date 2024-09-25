package com.outdoor.connect.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.outdoor.connect.model.Users;

/**
 * 
 * @author James Carl Oreto
 * 
 */

public interface UserRepository extends JpaRepository<Users, Long> {

    public Optional<Users> findByUsername(String username);
}
