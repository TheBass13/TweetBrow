package com.tweetbrow.rcdsm.tweetbrow.Models;

/**
 * Created by rcdsm on 09/04/15.
 */
public class User {

    private String email;
    private String token;
    private String     succes;
    private int         status;

    private static User instance;

    public static User getInstance(){
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    public User(){
    }

    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
    }

    private String reasons;

    public String isSucces() {
        return succes;
    }

    public void setSucces(String succes) {
        this.succes = succes;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
