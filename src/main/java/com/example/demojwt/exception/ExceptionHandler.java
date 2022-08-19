package com.example.demojwt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(ResourseNotFoundException.class)
    public ResponseEntity<ApiResponse> userNotFoundExceptionHandler(ResourseNotFoundException resourse){
        String message=resourse.getMessage();
        ApiResponse apiResponse=new ApiResponse(message,false);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(InvalidFieldException.class)
    public ResponseEntity<String> invalidFieldException(InvalidFieldException Ifex){
        //String messageIfex.getMessage();
        return new ResponseEntity<String>(Ifex.getMessage(),HttpStatus.BAD_REQUEST);
    }
}
