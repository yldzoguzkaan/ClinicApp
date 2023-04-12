package com.example.clinicapp.repository;

import com.example.clinicapp.entity.Parameter;
import com.example.clinicapp.enums.ParameterType;

import java.util.List;

public interface ParameterRepository extends BaseRepository<Parameter, Long> {

    List<Parameter> findAllByParameterTypeAndIsActiveTrueAndIsDeleteFalse(ParameterType type);
}
