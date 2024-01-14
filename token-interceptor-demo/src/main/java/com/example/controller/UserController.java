package com.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import com.alibaba.fastjson.JSONObject;
import com.example.bean.User;
import com.example.service.UserService;

@RestController
@RequestMapping
@Slf4j
public class UserController {
  //  @Autowired
   // TokenService tokenService;

    @Autowired
    UserService userService;

    // 创建线程安全的Map，模拟users信息的存储
    static Map<Long, User> users = Collections.synchronizedMap(new HashMap<Long, User>());

    /**
     * 处理"/users/"的POST请求，用来创建User，并返回token值
     * put http://localhost:8080/users
     * @param user
     * @return
     */
    @PostMapping("/users")
    public JSONObject postUser(@RequestBody User user) {
        // @RequestBody注解用来绑定通过http请求中application/json类型上传的数据
        JSONObject jsonObject=new JSONObject();
//        jsonObject.put("token", token);
        jsonObject.put("user", user);
        userService.addUser(user);
        return jsonObject;
    }

    /**
     * 处理"/users/"的GET请求，用来获取用户列表
     * get http://localhost:8080/users
     * @return
     */
    @GetMapping("/users")
    public List<User> getUserList() {
        List<User> users = userService.getUsers();
        return users;
    }
}
