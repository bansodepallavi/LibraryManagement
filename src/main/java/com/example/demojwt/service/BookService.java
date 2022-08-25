package com.example.demojwt.service;

import com.example.demojwt.entity.Book;
import com.example.demojwt.entity.IssueDetails;

import java.util.List;


public interface BookService {

    public Book addBook(Book book);

    public Book deleteBook(long bookID);

    public List<IssueDetails> issuedBookList();
}
