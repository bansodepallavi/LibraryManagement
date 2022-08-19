package com.example.demojwt.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InvalidFieldException extends RuntimeException{
    String message;

    public InvalidFieldException(String message){
        this.message=message;
    }
}
