package com.roman.web.handler.news;

import com.roman.service.validate.exception.CategoryDoesntExistException;
import com.roman.service.validate.exception.NewsDoesntExistException;
import com.roman.service.validate.exception.TitleExistException;
import com.roman.service.validate.exception.TitlePatternException;
import com.roman.web.handler.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.roman.web.controller.news")
public class NewsControllerExceptionHandler {

    @ExceptionHandler({TitlePatternException.class, TitleExistException.class})
    public ResponseEntity<ErrorResponse> createNewsHandleException(RuntimeException e){
        ErrorResponse response = new ErrorResponse(e.getMessage(), HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(409));
    }

    @ExceptionHandler({NewsDoesntExistException.class,CategoryDoesntExistException.class})
    public ResponseEntity<ErrorResponse> findNewsHandleException(RuntimeException e){
        ErrorResponse response = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(404));
    }

}
