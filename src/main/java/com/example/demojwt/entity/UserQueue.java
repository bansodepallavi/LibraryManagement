package com.example.demojwt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class UserQueue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    long bookID;

    @Column
    String username;

    public UserQueue(long bookID,String username){
        this.bookID=bookID;
        this.username=username;
    }
}
