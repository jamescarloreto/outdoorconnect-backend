package com.outdoor.connect.dto.response;

import java.util.ArrayList;

import com.outdoor.connect.model.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserResponseDto {

    private String username;

    private String password;

    private String emailAddress;

    private ArrayList<Role> roles;
}
