package com.example.clinicapp.enums;


public enum ParameterType {

    APPOINTMENT_TYPE("Randevu Parametreleri"),
    PATIENT("Hasta Parametreleri");

    private final String parameter;

    ParameterType(String parameter){
        this.parameter = parameter;
    }

    public String getParameter(){
        return parameter;
    }
}
