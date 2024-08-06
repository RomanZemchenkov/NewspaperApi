package com.roman.service.validate.exception;

public class TitleExistException extends RuntimeException{

    public TitleExistException(String message){
        super(message);
    }
}
