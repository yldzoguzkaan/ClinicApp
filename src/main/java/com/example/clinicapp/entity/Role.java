package com.example.clinicapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "Role")
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseEntity{

    private String name;
    private String description;


}
