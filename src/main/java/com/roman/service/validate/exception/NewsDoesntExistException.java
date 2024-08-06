package com.roman.service.validate.exception;

public class NewsDoesntExistException extends RuntimeException{

    public NewsDoesntExistException(Long id){
        super("Новость с id %d не существует.".formatted(id));
    }
}
