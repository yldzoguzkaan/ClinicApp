package com.example.clinicapp.entity;

import com.example.clinicapp.enums.ParameterType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "Parameter")
@EqualsAndHashCode(callSuper = true)
public class Parameter extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private ParameterType parameterType;

    private String label;

    private String description;

}
