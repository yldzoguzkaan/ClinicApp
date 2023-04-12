package com.example.clinicapp.service;

import com.example.clinicapp.dto.constant.ParameterDTO;
import com.example.clinicapp.entity.Parameter;
import com.example.clinicapp.enums.ParameterType;
import com.example.clinicapp.repository.ParameterRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParameterService {

    private final ParameterRepository parameterRepository;

    public ParameterService(ParameterRepository parameterRepository){
        this.parameterRepository = parameterRepository;
    }

    public List<ParameterDTO> getParametersByType(ParameterType type){
        var parameters = parameterRepository.findAllByParameterTypeAndIsActiveTrueAndIsDeleteFalse(type);
        if (parameters.isEmpty()) {
            return new ArrayList<>();
        }
        List<ParameterDTO> dtoList = new ArrayList<>();
        parameters.forEach(parameter -> {
            var parameterDto = ParameterDTO.builder()
                    .id(parameter.getId())
                    .label(parameter.getLabel())
                    .description(parameter.getDescription())
                    .type(parameter.getParameterType())
                    .build();
            dtoList.add(parameterDto);
        });
        return dtoList;
    }

    public ParameterDTO saveParamater(ParameterDTO parameterDTO){//mapper eklenebilir
        Parameter p = new Parameter();
        p.setParameterType(parameterDTO.getType());
        p.setLabel(parameterDTO.getLabel());
        p.setDescription(parameterDTO.getDescription());
        p = parameterRepository.save(p);
        return new ParameterDTO(p.getId(),p.getParameterType(),p.getLabel(),p.getDescription());
    }

}
