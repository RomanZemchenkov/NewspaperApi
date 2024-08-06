package com.roman.web.handler.category;

import com.roman.service.validate.exception.CategoryDoesntExistException;
import com.roman.service.validate.exception.TitleExistException;
import com.roman.service.validate.exception.TitlePatternException;
import com.roman.web.handler.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.roman.web.controller.category")
public class CategoryControllerExceptionHandler {

    @ExceptionHandler({TitleExistException.class, TitlePatternException.class})
    public ResponseEntity<ErrorResponse> createCategoryHandleException(RuntimeException e){
        ErrorResponse response = new ErrorResponse(e.getMessage(), HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(response,HttpStatus.CONFLICT);
    }

    @ExceptionHandler({CategoryDoesntExistException.class})
    public ResponseEntity<ErrorResponse> findNewsHandleException(RuntimeException e){
        ErrorResponse response = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }
}
