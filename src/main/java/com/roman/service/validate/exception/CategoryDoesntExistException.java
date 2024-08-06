package com.roman.service.validate.exception;

public class CategoryDoesntExistException extends RuntimeException{

    public CategoryDoesntExistException(Long id){
        super("Категория с id %d не существует.".formatted(id));
    }
}
