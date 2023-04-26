package com.example.clinicapp.controller;

import com.example.clinicapp.dto.constant.ParameterDTO;
import com.example.clinicapp.enums.ParameterType;
import com.example.clinicapp.service.ParameterService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/params")
@RequiredArgsConstructor
public class ParameterController {

    private final ParameterService parameterService;

    /*@GetMapping("/getByType/{type}")
    public List<ParameterDTO> getParameters(@PathVariable ParameterType type) {
        return parameterService.getParametersByType(type);
    }*/

    @GetMapping("/getByType")
    public List<ParameterDTO> getParameters(@RequestParam ParameterType type) {
        return parameterService.getParametersByType(type);
    }

    @PostMapping("/save")
    public ParameterDTO saveParameter(@RequestBody ParameterDTO parameterDTO){
        return parameterService.saveParamater(parameterDTO);
    }

}
