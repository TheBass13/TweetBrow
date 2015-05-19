package com.tweetbrow.rcdsm.tweetbrow.Models;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by rcdsm on 19/05/15.
 */
public class Tweet extends RealmObject{

    @PrimaryKey
    private long        id;

    private String      message;
    private int         author;

    private Date        date_create;

    public long getId(){
        return id;
    }
    public void setId(long valueId){
        this.id = valueId;
    }

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate_create() {
        return date_create;
    }

    public void setDate_create(Date date_create) {
        this.date_create = date_create;
    }

}

