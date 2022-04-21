package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String createTime;

    @Column(nullable = false)
    private String content;

}
