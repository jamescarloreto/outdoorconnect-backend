package com.outdoor.connect.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.outdoor.connect.model.Role;

/**
 * 
 * @author James Carl Oreto
 * 
 */

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleName(String roleName);

}
