package com.example.demojwt.service;

import com.example.demojwt.entity.Book;
import java.util.List;


public interface BookService {

    public Book addBook(Book book);

    public Book deleteBook(long bookID);

    public List<Book> viewAllBooks();
}
