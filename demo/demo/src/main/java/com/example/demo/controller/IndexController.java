package com.example.demo.controller;

import com.example.demo.entity.Comment;
import com.example.demo.entity.User;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class IndexController {

    @Resource
    private UserRepository userRepository;

    @Resource
    private CommentRepository commentRepository;

    @RequestMapping("/")
    public ModelAndView index(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null){
            return new ModelAndView("login");
        }

        return new ModelAndView("index");
    }

    @RequestMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }


    @RequestMapping("/logout")
    public ModelAndView logout(HttpSession session) {
        session.removeAttribute("user");
        return new ModelAndView("login");
    }

    @RequestMapping("/register")
    public ModelAndView register() {
        return new ModelAndView("register");
    }

    @RequestMapping(value = "/userInfo", method = RequestMethod.POST)
    @ResponseBody
    public User userInfo(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return user;
    }


    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    public String reg(@ModelAttribute User user) {
        String userName = user.getUserName();

        User user1 = userRepository.findByUserName(userName);

        if (user1 != null){
            return "0";
        }

        Date data = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(data);
        user.setRegTime(formattedDate);
        userRepository.save(user);
        return "1";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@ModelAttribute User user, HttpSession session) {

        String userName = user.getUserName();
        String password = user.getPassWord();

        User user1 = userRepository.findByUserName(userName);

        if (user1 == null){
            return "0";
        }else if (user1.getPassWord().equals(password)) {
            session.setAttribute("user",user1);
            return "1";
        } else {
            return "2";
        }

    }

    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public String comment(@ModelAttribute Comment comment, HttpSession session) {
        Date data = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(data);
        comment.setCreateTime(formattedDate);

        User user = (User) session.getAttribute("user");

        if (user == null){
            return "0";
        }
        comment.setNickName(user.getNickName());
        comment.setUserName(user.getUserName());

        commentRepository.save(comment);
        return  "1";
    }


    @RequestMapping("/list")
    @ResponseBody
    public Page<Comment> list(Integer pageNum){

        if (pageNum == null){
            pageNum = 1;
        }
        Sort sort =  Sort.by(Sort.Order.desc("createTime"));
        Pageable pageable = PageRequest.of(pageNum - 1, 10, sort);
        Page<Comment> page = commentRepository.findAll(pageable);
        return page;
    }
}
