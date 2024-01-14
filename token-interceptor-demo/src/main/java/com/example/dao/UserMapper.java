package com.example.dao;

import com.example.bean.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    List<User> findAllUsers();
    int insertUser(@Param("user") User user);
}


