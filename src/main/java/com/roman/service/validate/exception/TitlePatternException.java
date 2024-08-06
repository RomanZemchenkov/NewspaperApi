package com.roman.service.validate.exception;

public class TitlePatternException extends RuntimeException{

    public TitlePatternException(){
        super("Название должно содержать хотя бы один символ.");
    }
}
