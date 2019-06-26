package com.mmall.model.po;

import com.mmall.model.vo.UserVO;
import com.mmall.util.MD5Util;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "mmall_user")
public class User {
    @Id
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "question")
    private String question;

    @Column(name = "answer")
    private String answer;

    @Column(name = "role")
    private Integer role;

    @Column(name = "create_time")
    private Integer createTime;

    @Column(name = "update_time")
    private Integer updateTime;

    public User(UserVO userVO) {
        this.username = userVO.getUsername();
        // MD5加密
        this.password = MD5Util.MD5EncodeUtf8(userVO.getPassword());
        this.email = userVO.getEmail();
        this.phone = userVO.getPhone();
        this.question = userVO.getQuestion();
        this.answer = userVO.getAnswer();
        this.role = userVO.getRole();
        this.createTime = userVO.getCreateTime();
        this.updateTime = userVO.getUpdateTime();
    }

}