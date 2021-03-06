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

import com.tweetbrow.rcdsm.tweetbrow.Manager.UserManager;


public class MainActivity extends ActionBarActivity {

    private UserManager userManag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        NewsFragment newsFrag = new NewsFragment();
        fragmentTransaction.replace(R.id.main_fragment, newsFrag);

        fragmentTransaction.commit();

        displayUser();
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

            userManag = new UserManager(getApplicationContext());
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
