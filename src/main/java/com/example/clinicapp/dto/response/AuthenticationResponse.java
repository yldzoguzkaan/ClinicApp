package com.example.clinicapp.dto.response;

import com.example.clinicapp.entity.Permission;
import com.example.clinicapp.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private String uid;
    private String nameSurname;
    private String username;
    private Role role;
    private List<Permission> permissions;
    private String token;
}
