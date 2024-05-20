package com.application.ProgramX.service.exceptions;

public class LessThanZeroException extends RuntimeException{

    public LessThanZeroException(String field, int value){
        super(String.format("%s is equal to %d which is less than 0!",field,value));
    }
    public LessThanZeroException(String field, float value){
        super(String.format("%s is equal to %.2f which is less than 0!",field,value));
    }
    public static void throwIfLessThan0(String field, int value){
        if(value < 0)
            throw new LessThanZeroException(field,value);
    }
    public static void throwIfLessThan0(String field, float value) {
        if(value < 0)
            throw new LessThanZeroException(field,value);
    }


}
