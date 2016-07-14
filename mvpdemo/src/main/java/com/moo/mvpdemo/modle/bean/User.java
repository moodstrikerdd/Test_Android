package com.moo.mvpdemo.modle.bean;

/**
 * Created by Administrator on 2016/7/14.
 */
public class User {
    private String userName;
    private String userTel;

    public User() {
    }

    public User(String userName, String userTel) {
        this.userName = userName;
        this.userTel = userTel;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }
}
