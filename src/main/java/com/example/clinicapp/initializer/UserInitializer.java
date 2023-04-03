package com.example.clinicapp.initializer;

import com.example.clinicapp.config.ApplicationConfig;
import com.example.clinicapp.entity.Permission;
import com.example.clinicapp.entity.Role;
import com.example.clinicapp.entity.User;
import com.example.clinicapp.repository.PermissionRepository;
import com.example.clinicapp.repository.RoleRepository;
import com.example.clinicapp.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Component
public class UserInitializer {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;

    private final ApplicationConfig applicationConfig;

    @PostConstruct
    private void createBaseParameters(){

        List<Role> roles = roleRepository.findAll();
        Role role;
        if(roles.isEmpty()){
            role = new Role();
            role.setName("ADMIN");
            role.setDescription("System Admin");
            roleRepository.save(role);
        }else{
            role = roles.get(0);
        }

        List<Permission> permissions = permissionRepository.findAll();
        Permission permission;
        if (permissions.isEmpty()) {
            permission = new Permission();
            permission.setName("Role_Login");
            permission.setDescription("system login authorization");
            permissionRepository.save(permission);
        }else{
            permission = permissions.get(0);
        }

        List<User> users = userRepository.findAll();
        if(users.isEmpty()){
            User user = new User();
            user.setRole(role);
            user.setPermissions(permissions);
            user.setUsername("admin");
            user.setNameSurname("System Admin");
            user.setPassword(applicationConfig.passwordEncoder().encode("123456"));
            user.setOpenPassword("123456");
            userRepository.save(user);
        }

    }

}
