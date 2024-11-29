package com.outdoor.connect.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.outdoor.connect.model.Users;

/**
 * 
 * @author James Carl Oreto
 * 
 */

public interface UserRepository extends JpaRepository<Users, Long> {

    public Optional<Users> findByUsername(String username);

    @Query(value = """
            SELECT TRUE FROM oc_users WHERE id = :principalId AND verification_code = :verificationCode AND verification_code_expiration_date >= :now
            """, nativeQuery = true)
    public Boolean verifyCode(Long principalId, String verificationCode, LocalDateTime now);

    @Query(value = """
            SELECT TRUE FROM oc_users WHERE email_address = :email
            """, nativeQuery = true)
    public Boolean emailExisting(String email);
}
