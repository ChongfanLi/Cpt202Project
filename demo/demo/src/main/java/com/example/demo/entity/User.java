package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private String passWord;

    @Column(nullable = true, unique = true)
    private String nickName;

    @Column(nullable = false)
    private String regTime;

    public User(String userName, String passWord, String nickName, String regTime) {
        this.userName = userName;
        this.passWord = passWord;
        this.nickName = nickName;
        this.regTime = regTime;
    }

    public User() {
    }

}
