package com.example.clinicapp.service;

import com.example.clinicapp.dto.constant.ParameterDTO;
import com.example.clinicapp.entity.Parameter;
import com.example.clinicapp.enums.ParameterType;
import com.example.clinicapp.repository.ParameterRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParameterService {

    private final ParameterRepository parameterRepository;
    private final ModelMapper mapper;

    public ParameterService(ParameterRepository parameterRepository, ModelMapper mapper){
        this.parameterRepository = parameterRepository;
        this.mapper = mapper;
    }

    public List<ParameterDTO> getParametersByType(ParameterType type){
        var parameters = parameterRepository.findAllByParameterTypeAndIsActiveTrueAndIsDeleteFalse(type);
        List<ParameterDTO> dtoList = parameters.stream()
                .map(parameter -> mapper.map(parameter,ParameterDTO.class))
                .collect(Collectors.toList());
        return dtoList;
    }

    public ParameterDTO saveParamater(ParameterDTO parameterDTO){
        return mapper.map(parameterRepository.save(mapper.map(parameterDTO,Parameter.class)), ParameterDTO.class);
    }

}
