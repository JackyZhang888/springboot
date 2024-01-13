package com.example.demospringboot.dao;

import com.example.demospringboot.bean.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Cacheable
@CachePut
@CacheConfig(cacheNames = "users")
public interface UserMapper {
    User findUserById(@Param("id") int id);
    User findUserByName(@Param("username") String username);
    String getUserRole(@Param("id") int id);
    List<User> findAllUsers();
    @CachePut // 更新缓存
    void deleteUserById(@Param("id") int id);
    @CachePut
    void deleteAllUsers();
    @CachePut
    int insertUser(@Param("user") User user);
    @CachePut
    void updateUserPassword(@Param("user") User user);
}


