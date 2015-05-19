package com.tweetbrow.rcdsm.tweetbrow;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.tweetbrow.rcdsm.tweetbrow.Adapter.TweetAdapter;
import com.tweetbrow.rcdsm.tweetbrow.Manager.TweetManager;
import com.tweetbrow.rcdsm.tweetbrow.Manager.UserManager;
import com.tweetbrow.rcdsm.tweetbrow.Models.Tweet;
import com.tweetbrow.rcdsm.tweetbrow.Models.User;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity{

    private UserManager userManag;
    private ArrayList<Tweet> tweets;
    private ListView tweetList;
    private TweetAdapter adapter;
    private TweetManager tweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_layout);

        tweetList = (ListView)findViewById(R.id.newsList);
        tweet = new TweetManager(this);

        ClientAPI.getInstance().takeTweet(User.getInstance().getToken(),new ClientAPI.APIListener() {
            @Override
            public void callback() {
                tweets = new ArrayList<Tweet>();
                adapter = new TweetAdapter(getApplicationContext(), tweets);
                tweetList.setAdapter(adapter);
                tweets.clear();
                tweets.addAll(tweet.allListNote());
            }
        });
        displayUser();
    }

    @Override
    protected void onResume() {
        super.onResume();
        tweets = new ArrayList<Tweet>();
        adapter = new TweetAdapter(this,tweets);
        tweetList.setAdapter(adapter);
        tweets.clear();
        tweets.addAll(tweet.allListNote());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.add_tweet) {

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            AddTweetFragment addTweet = new AddTweetFragment();
            fragmentTransaction.replace(R.id.main_fragment, addTweet);

            fragmentTransaction.commit();

            return true;
        }
        if(id==R.id.action_deconnexion){

            SharedPreferences preferences = getApplicationContext().getSharedPreferences("Login", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();

            userManag = new UserManager(this);
            userManag.clear();

            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void displayUser(){
         userManag = new UserManager(this);
         userManag.foundUser();
    }
}
