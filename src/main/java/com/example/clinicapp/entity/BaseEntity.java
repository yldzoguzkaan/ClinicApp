package com.example.clinicapp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.UUID;

@Data
@MappedSuperclass
@EqualsAndHashCode(of = "id")
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uid = UUID.randomUUID().toString();
    private Boolean isDelete = false;
    private Boolean isActive = true;

}
