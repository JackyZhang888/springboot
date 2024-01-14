package com.example.service;

import com.example.bean.User;

import java.util.List;

public interface UserService {
    void addUser(User user);
    List<User> getUsers();
}
