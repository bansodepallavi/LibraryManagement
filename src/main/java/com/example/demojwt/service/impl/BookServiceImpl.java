package com.example.demojwt.service.impl;

import com.example.demojwt.entity.Book;
import com.example.demojwt.exception.InvalidFieldException;
import com.example.demojwt.exception.ResourseNotFoundException;
import com.example.demojwt.repository.BookRepo;
import com.example.demojwt.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepo bookRepo;

    @Override
    public Book addBook(Book book) {
        validateBookFields(book);
        if(bookRepo.existsById(book.getBookID()))
            throw new InvalidFieldException("Book already exists with this bookID");
        book.available = true;
        bookRepo.save(book);
        return book;
    }

    @Override
    public Book deleteBook(long bookID) {
        if(bookID <= 0)
            throw new InvalidFieldException("Enter valid book id");
        Book book = bookRepo.findById(bookID)
                .orElseThrow(() -> new ResourseNotFoundException(bookID));

        if(!book.isAvailable())
             throw new InvalidFieldException("Can't delete book...book is issued by someone");
        if (bookRepo.existsById(bookID)) {
            Book b = bookRepo.findById(bookID).get();
            bookRepo.deleteById(bookID);
            return b;
        }
        return book;
    }
    @Override
    public List<Book> viewAllBooks() {
        return bookRepo.findAll();
    }

    private void validateBookFields(Book book) {
        if(book.getBookID() <= 0)
            throw new InvalidFieldException("Enter valid Book id");
        else if(book.getTitle().length() < 3)
            throw new InvalidFieldException("Enter valid book title");
        else if(book.getAuthor().length() < 3)
            throw new InvalidFieldException("Enter valid book author");
    }
}
