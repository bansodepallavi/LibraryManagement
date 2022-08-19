package com.example.demojwt.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourseNotFoundException extends RuntimeException{
    public long bookID;
    public String username;
    public ResourseNotFoundException(long bookID){
        super(String.format("book with id %s not found",bookID));
        this.bookID=bookID;
    }

    public ResourseNotFoundException(String username){
        super(String.format("user with username %s not found",username));
        this.username=username;
    }
}
