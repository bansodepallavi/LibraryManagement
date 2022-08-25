package com.example.demojwt.repository;

import com.example.demojwt.entity.UserQueue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserQueueRepo extends JpaRepository<UserQueue,Long> {
    List<UserQueue> findUserQueueByBookID(long bookID);
}
