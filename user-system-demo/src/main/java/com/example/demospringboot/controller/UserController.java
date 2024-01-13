package com.example.demospringboot.controller;

import com.example.demospringboot.bean.User;
import com.example.demospringboot.dao.JobMapper;
import com.example.demospringboot.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserMapper userMapper;

    @Autowired
    JobMapper jobMapper;

    @GetMapping(value = {"/login"})
    public String loginPage() {
        // 返回login.html
        return "login";
    }

    @GetMapping("register")
    public String registerPage() {
        // 返回register.html
        return "register";
    }

    @PostMapping("register")
    public String Register(User user, Model model) {
        try {
            User userName = userMapper.findUserByName(user.getUsername());
            //没有用户可以进行注册
            if (userName == null) {
                if (user.getPassword().equals("") || user.getUsername().equals("")) {
                    model.addAttribute("tip", "请填写信息");
                    return "register";
                } else {
                    int ret = userMapper.insertUser(user);
                    if (ret > 0) {
                        model.addAttribute("tip", "注册成功,请返回登录页面进行登录");
                        // 设置发送邮件标志位为1
                        jobMapper.setSendmail(1);
                    }
                    return "register";
                }
            } else {
                model.addAttribute("tip", "用户已存在,请返回登录页面进行登录");
                return "register";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @PostMapping("login")
    public String login(User user, HttpSession session, Model model) {
        try {
            //先查找一下有没有该账号
            User userReturn = userMapper.findUserByName(user.getUsername());
            if (userReturn != null) {
                //如果有账号则判断账号密码是否正确
                if (userReturn.getPassword().equals(user.getPassword())) {
                    //添加到session保存起来
                    System.out.println(session);
                    session.setAttribute("loginUser", user);
                    //重定向到@GetMapping("success")
                    return "redirect:/success";
                } else {
                    //如果密码错误，则提示输入有误
                    model.addAttribute("msg", "账号或者密码有误");
                    return "login";
                }
            } else {
                model.addAttribute("msg", "账号或者密码有误");
                return "login";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @GetMapping("success")
    public String UserPage(HttpSession session, Model model) {
        User loginUser = (User)session.getAttribute("loginUser");
        if (loginUser != null) {
            model.addAttribute("user", loginUser.getUsername());
            // 如果是管理员，返回用户列表
            User user = userMapper.findUserByName(loginUser.getUsername());
            if ("admin".equals(userMapper.getUserRole(user.getId()))) {
                List<User> users = userMapper.findAllUsers();
                model.addAttribute("users", users);
            }
            // 返回success.html
            return "success";
        } else {
            model.addAttribute("msg", "请登录");
            return "login";
        }
    }
}
