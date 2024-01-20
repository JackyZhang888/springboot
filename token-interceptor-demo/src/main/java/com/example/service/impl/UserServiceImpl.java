package com.example.service.impl;

import com.example.annotation.PassToken;
import com.example.bean.User;
import com.example.service.UserService;
import com.example.dao.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public void addUser(User user) {
        userMapper.insertUser(user);
    }

    @Override
    public List<User> getUsers() {
        List<User> users = userMapper.findAllUsers();
        return users;
    }


}

