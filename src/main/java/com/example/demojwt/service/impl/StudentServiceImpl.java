package com.example.demojwt.service.impl;

import com.example.demojwt.entity.Book;
import com.example.demojwt.entity.Cart;
import com.example.demojwt.entity.User;
//import com.example.demojwt.entity.UserQueue;
import com.example.demojwt.entity.UserQueue;
import com.example.demojwt.exception.InvalidFieldException;
import com.example.demojwt.repository.BookRepo;
import com.example.demojwt.repository.CartRepo;
//import com.example.demojwt.repository.UserQueueRepo;
import com.example.demojwt.repository.UserQueueRepo;
import com.example.demojwt.repository.UserRepo;
import com.example.demojwt.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class StudentServiceImpl implements StudentService {
    @Autowired
    BookRepo bookRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    CartRepo cartRepo;

    @Autowired
    UserQueueRepo userQueueRepo;

    @Override
    public Book issueBook(String email,long bookID) {
        validateFields(email,bookID);
        User user=userRepo.findByEmail(email);
        Book book=bookRepo.findByBookID(bookID);
        checkFields(user,book);
        if(!book.isAvailable()) {
            saveUser(email,bookID);
            throw new InvalidFieldException("Book is already issued by someone...");
        }
        if(user.getCart() == null){
            Cart cart=new Cart();
            cart.setBookList(new HashSet<Book>());
            cart.getBookList().add(book);
            user.setCart(cart);
            book.available=false;
            cartRepo.save(cart);
        }
        else{
            Cart cart=user.getCart();
            cart.getBookList().add(book);
            user.setCart(cart);
            book.available=false;
            cartRepo.save(cart);
        }
        bookRepo.save(book);
        return book;
    }

    private void saveUser(String email, long bookID) {
        List<UserQueue> list=userQueueRepo.findAll();
        if(list.size() > 0){
            for(UserQueue userQueue:list){
                if(userQueue.getBookID() == bookID && userQueue.getUsername().equals(email))
                    throw new InvalidFieldException("Your is stored in queue");
            }
        }
        UserQueue userQueue=new UserQueue(bookID,email);
        userQueueRepo.save(userQueue);
    }

    @Override
    public Book returnBook(String email,long bookID) {
        validateFields(email,bookID);
        User user=userRepo.findByEmail(email);
        Book book=bookRepo.findByBookID(bookID);
        checkFields(user,book);
        Cart cart1=user.getCart();
        if(cart1 == null)
            throw new InvalidFieldException("Sorry you haven't issued any book....your cart is empty...");
        if(!cart1.getBookList().contains(book))
            throw new InvalidFieldException(book.getTitle()+" is not issued by you");
        if(book.isAvailable())
            throw new InvalidFieldException("Please enter issued book id...");
        if(user.getCart().getBookList().size() > 1){
            Cart cart=user.getCart();
            cart.getBookList().remove(book);
            user.setCart(cart);
            book.available=true;
            cartRepo.save(cart);
        }else{
            Cart cart=user.getCart();
            cart.getBookList().remove(book);
            cart.setBookList(null);
            user.setCart(null);
            book.available=true;
            cartRepo.deleteById(cart.getId());
        }
        bookRepo.save(book);
        checkUserQueue(bookID);
        return book;
    }

    private void checkUserQueue(long bookID) {
        List<UserQueue> list=userQueueRepo.findAll();
        if(list.size() == 0)
            return;
        for(UserQueue user:list){
            if(user.getBookID() == bookID){
                issueBook(user.getUsername(), user.getBookID());
                userQueueRepo.deleteById(user.getId());
            }
        }
    }

    @Override
    public List<Book> viewAllBooks() {
        return bookRepo.findAll();
    }

    private void checkFields(User user, Book book) {
        if(user == null)
            throw new InvalidFieldException("user does not exists...");
        if(book == null)
            throw new InvalidFieldException("book does not exists");
    }

    public void validateFields(String email,long bookID){
        String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";
        Pattern pattern=Pattern.compile(EMAIL_PATTERN);
        Matcher matcher=pattern.matcher(email);
        if(!matcher.matches())
            throw new InvalidFieldException("please enter valid email address");
        if(bookID <= 0)
            throw new InvalidFieldException("enter valid book id");
    }
}
