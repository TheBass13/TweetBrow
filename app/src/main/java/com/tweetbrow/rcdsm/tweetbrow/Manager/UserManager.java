package com.tweetbrow.rcdsm.tweetbrow.Manager;

import android.content.Context;
import android.util.Log;

import com.tweetbrow.rcdsm.tweetbrow.Models.User;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by rcdsm on 19/05/15.
 */
public class UserManager {

    protected Realm realm;

    public UserManager(Context context){
        realm = Realm.getInstance(context);
    }

    public void foundUser(){
        RealmResults<User> results = realm.where(User.class).findAll();
        for(User user : results){

            Log.e("YOOOOOOO",user.toString());
            User.getInstance().setPseudo(user.getPseudo());
            User.getInstance().setLogin(user.getLogin());
        }
    }

    public void userConnected(User valueUser){
        realm.beginTransaction();
        User user = realm.createObject(User.class);
        user.setEmail(valueUser.getEmail());
        user.setLogin(valueUser.getLogin());
        user.setPseudo(valueUser.getPseudo());
        realm.commitTransaction();
    }

    public void clear(){
        realm.beginTransaction();
        RealmResults<User> results = realm.where(User.class).findAll();
        results.clear();
        realm.commitTransaction();
    }
}
