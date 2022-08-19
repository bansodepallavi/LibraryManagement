package com.example.demojwt.controller;

import com.example.demojwt.entity.Book;
import com.example.demojwt.entity.Cart;
import com.example.demojwt.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class StudentController {
    @Autowired
    private StudentService studentService;

    @PostMapping("user/issuebook/{email}/{bookID}")
    @ResponseBody
    public ResponseEntity<Book> issueBook(@PathVariable String email, @PathVariable long bookID){
            Book book=studentService.issueBook(email,bookID);
            return ResponseEntity.of(Optional.of(book));
    }

    @PostMapping("user/returnbook/{email}/{bookID}")
    @ResponseBody
    public ResponseEntity<Book> returnBook(@PathVariable String email, @PathVariable long bookID){
        Book book=studentService.returnBook(email,bookID);
        return ResponseEntity.of(Optional.of(book));
    }

    @GetMapping("/getBooks")
    private ResponseEntity<List<Book>> viewAllBooks(){
        List<Book> list=this.studentService.viewAllBooks();
        return ResponseEntity.ok(list);
    }
}
