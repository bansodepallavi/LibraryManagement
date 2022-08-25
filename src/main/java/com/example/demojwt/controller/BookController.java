package com.example.demojwt.controller;

import com.example.demojwt.entity.Book;
import com.example.demojwt.entity.IssueDetails;
import com.example.demojwt.exception.ApiResponse;
import com.example.demojwt.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {
    @Autowired(required = true)
    private BookService bookService;

    @PreAuthorize("hasRole('ADMIN')")
    //@RolesAllowed("ADMIN")
    @PostMapping("/admin/addBook")
    @ResponseBody
    public ResponseEntity<ApiResponse> addBook(@RequestBody Book book){
            Book b=this.bookService.addBook(book);
            return new ResponseEntity<>(new ApiResponse("book added successfully....", true)
                ,HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    //@RolesAllowed("ADMIN")
    @DeleteMapping("/admin/deleteBook/{bookID}")
    public ResponseEntity<ApiResponse> deleteBook(@PathVariable long bookID){
            this.bookService.deleteBook(bookID);
            return new ResponseEntity<>(new ApiResponse("book deleted successfully....", true)
                    ,HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/issuedBooks")
    public ResponseEntity<List<IssueDetails>> issuedBookList(){
        List<IssueDetails> list=this.bookService.issuedBookList();
        return ResponseEntity.ok(list);
    }
}
