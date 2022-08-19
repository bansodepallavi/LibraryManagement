package com.example.demojwt.service;

import com.example.demojwt.entity.Book;

import java.util.List;

public interface StudentService {
    public Book issueBook(String email,long bookID);

    public Book returnBook(String email,long bookID);

    List<Book> viewAllBooks();
}
