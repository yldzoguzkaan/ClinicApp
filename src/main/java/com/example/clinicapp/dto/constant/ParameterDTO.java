package com.example.clinicapp.dto.constant;

import com.example.clinicapp.enums.ParameterType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParameterDTO {

    private Long id;
    private ParameterType parameterType;
    private String label;
    private String description;


}
