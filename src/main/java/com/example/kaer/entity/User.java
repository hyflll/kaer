package com.example.kaer.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
@Data
@ToString
@EqualsAndHashCode
public class User implements Serializable {
    private Integer uid;
    private String username;
    private String password;
    private String phone;
    private String email;
    private String salt;
    private Integer isDelete;
    private Integer gender;
    private String avatar;
    private String code;
}
