package com.application.ProgramX.domain;

public class DomainHelper {
    public static void  checkNotLessThan0AndThrowErrorInCase(String typeName, float value){
        if(value <0.0f)
            throw new RuntimeException(typeName + "cannot be less than 0 !");
    }
}
