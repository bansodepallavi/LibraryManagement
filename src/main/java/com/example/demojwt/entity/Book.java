package com.example.demojwt.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.Id;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book {
    @Id
    private long bookID;

    @NotNull
    public String title;

    @NotNull
    public String author;

    public boolean available;

}
