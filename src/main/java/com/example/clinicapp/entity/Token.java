package com.example.clinicapp.entity;

import com.example.clinicapp.enums.TokenType;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name="Token")
@EqualsAndHashCode(callSuper = true)
public class Token extends BaseEntity {

    @Column(unique = true)
    public String token;

    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;
    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;
}
