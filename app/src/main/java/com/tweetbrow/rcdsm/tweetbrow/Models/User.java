package com.tweetbrow.rcdsm.tweetbrow.Models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by rcdsm on 09/04/15.
 */
public class User extends RealmObject {

    @PrimaryKey
    private String      email;
    private String      token;
    private String      login;
    private String      succes;
    private String      pseudo;

    private static User instance;

    public static User getInstance(){
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    public User(){
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

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
}
