package com.example.service;

import com.example.bean.User;

public interface TokenService {
    String getToken(User user);
    Boolean parseToken(String token);
}
