package com.example.action.dao;

import com.example.action.entity.UserAction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserActionRepository extends JpaRepository<UserAction, Integer> {

    List<UserAction> findByUserUuid(String userUuid);
}
