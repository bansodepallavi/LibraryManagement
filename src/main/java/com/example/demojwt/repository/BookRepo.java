package com.example.demojwt.repository;

import com.example.demojwt.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepo extends JpaRepository<Book,Long> {
    Book findByBookID(long bookID);
}
