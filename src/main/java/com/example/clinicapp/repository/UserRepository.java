package com.example.clinicapp.repository;

import com.example.clinicapp.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User, Long>{

    Optional<User> findByUsername(String username);

}
