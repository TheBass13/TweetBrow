package com.tweetbrow.rcdsm.tweetbrow;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;

import com.androidquery.AQuery;

/**
 * Created by rcdsm on 18/05/15.
 */
public class LoginActivity extends ActionBarActivity implements LoginFragment.OnConnexionClickListener  {

    private AQuery aq;
    private EditText login;
    private EditText password;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        ClientAPI.createInstance(getApplicationContext());
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("Login", Context.MODE_PRIVATE);

        if(preferences.contains("Token")){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        LoginFragment loginFrag = new LoginFragment();
        fragmentTransaction.replace(R.id.main_fragment, loginFrag);

        fragmentTransaction.commit();
    }

    @Override
    public void onConnexionClick() {

        login = (EditText)findViewById(R.id.editLogin);
        password = (EditText)findViewById(R.id.editPassword);

        ClientAPI.getInstance().connect(login.getText().toString(), password.getText().toString(), new ClientAPI.APIListener() {
            @Override
            public void callback() {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}



