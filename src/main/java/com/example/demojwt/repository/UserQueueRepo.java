package com.example.demojwt.repository;

import com.example.demojwt.entity.UserQueue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserQueueRepo extends JpaRepository<UserQueue,Long> {
}
