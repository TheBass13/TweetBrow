package com.tweetbrow.rcdsm.tweetbrow.Manager;

import android.content.Context;
import android.util.Log;

import com.tweetbrow.rcdsm.tweetbrow.ClientAPI;
import com.tweetbrow.rcdsm.tweetbrow.Models.Tweet;
import com.tweetbrow.rcdsm.tweetbrow.Models.User;

import java.util.ArrayList;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by rcdsm on 19/05/15.
 */
public class TweetManager {

    protected Realm realm;

    public TweetManager(Context context){
        realm = Realm.getInstance(context);

        if(!isPopulated()){
            Log.d("NoteManager", "Created");
        }else{
            Log.d("NoteManager","Display");
        }
    }

    public ArrayList<Tweet> allListNote(){
        ArrayList<Tweet> items = new ArrayList<Tweet>();
        RealmResults<Tweet> results = realm.where(Tweet.class).findAllSorted("date_create",RealmResults.SORT_ORDER_DESCENDING);
        for(Tweet tweet : results){
            items.add(tweet);
        }

        ClientAPI.getInstance().takeTweet(new ClientAPI.APIListener() {
            @Override
            public void callback() {
            }
        });

        return items;
    }

    //Verifie si il y a un resultat dans realm
    public boolean isPopulated(){
        return realm.where(Tweet.class).findAll().size()>0;
    }

    public void clear(){
        realm.beginTransaction();
        RealmResults<Tweet> results = realm.where(Tweet.class).findAll();
        results.clear();
        realm.commitTransaction();
    }

    public void clearOneItem(String id){
        realm.beginTransaction();
        Tweet results = realm.where(Tweet.class).equalTo("id", id).findFirst();
        results.removeFromRealm();
        realm.commitTransaction();

        ClientAPI.getInstance().deleteTweet(id, User.getInstance().getToken(), new ClientAPI.APIListener() {
            @Override
            public void callback() {
            }
        });
    }

    public void addTweet(Tweet valueNote){
        realm.beginTransaction();
        Tweet note = realm.createObject(Tweet.class);
        note.setId(valueNote.getId());
        note.setDate_create(new Date());
        note.setMessage(valueNote.getMessage());
        realm.commitTransaction();
    }
}


