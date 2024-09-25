package com.outdoor.connect.bean;

import java.io.Serializable;
import java.util.List;

import com.outdoor.connect.model.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author James Carl Oreto
 * 
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserBean implements Serializable {

    private int id;

    private String username;

    private String password;

    private String emailAddress;

    private List<Role> roles;

    private String role;
}
