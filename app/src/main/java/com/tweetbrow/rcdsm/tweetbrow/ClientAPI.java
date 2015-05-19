package com.tweetbrow.rcdsm.tweetbrow;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.tweetbrow.rcdsm.tweetbrow.Manager.UserManager;
import com.tweetbrow.rcdsm.tweetbrow.Models.Tweet;
import com.tweetbrow.rcdsm.tweetbrow.Models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import io.realm.Realm;


/**
 * Created by rcdsm on 27/04/15.
 */
public class ClientAPI {

    private Context context;
    private AQuery aq;
    protected Realm realm;
    private static ClientAPI instance;
    private SharedPreferences.Editor editor;
    private SharedPreferences preferences;

    public static void createInstance(Context context){
        instance = new ClientAPI(context);
    }

    public static ClientAPI getInstance(){
      return instance;
    }

    public ClientAPI(Context appContext){
        this.context = appContext;
    }

    public void connect(final String login,final String password,APIListener listener){

         preferences = context.getSharedPreferences("Login", Context.MODE_PRIVATE);
        //On crée un JSOnObject et on recupère les informations login/password

        final APIListener _listener = listener;
        aq = new AQuery(context);

        String url = "http://172.31.1.120:8888/tweetbrow/connect";

        Map<String, String> params = new HashMap<String, String>();
        params.put("login", login);
        params.put("password", password);
        params.put("email",login);

        aq.ajax(url, params, JSONObject.class, new AjaxCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {

                if (json != null) {
                    User user = User.getInstance();
                    UserManager userManager = new UserManager(context);
                    try {
                        user.setToken(json.getJSONObject("data").getString("token"));
                        user.setLogin(json.getJSONObject("data").getString("login"));
                        user.setEmail(json.getJSONObject("data").getString("email"));
                        user.setPseudo(json.getJSONObject("data").getString("pseudo"));
                        userManager.userConnected(user);

                        if (preferences.contains("Token") == false) {
                            editor = preferences.edit();
                            editor.putString("Token", json.getJSONObject("data").getString("token"));
                            editor.commit();
                        }

                        Log.e("Login", user.getLogin());
                        Log.e("Email", user.getEmail());
                        Log.e("Pseudo", user.getPseudo());
                        Log.e("Token", user.getToken());

                        user.setSucces(json.getString("reponse"));
                        Log.e("Note", json.getString("reponse").toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //successful ajax call, show status code and json content
                    //Toast.makeText(aq.getContext(), status.getCode() + ":" + json.toString(), Toast.LENGTH_LONG).show();
                    if (user.isSucces().equals("success")) {
                        Toast.makeText(aq.getContext(), "Welcom : " + user.getPseudo(), Toast.LENGTH_LONG).show();
                        _listener.callback();
                    } else {
                        Toast.makeText(aq.getContext(), "Email or Password is wrong !", Toast.LENGTH_LONG).show();
                    }

                } else {
                    //ajax error, show error code
                    Toast.makeText(aq.getContext(), "Error:" + status.getCode(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void deleteTweet(String tweetID,String token,APIListener listener){

        final APIListener _listener = listener;
        preferences = context.getSharedPreferences("Login", Context.MODE_PRIVATE);
        JSONObject tweet = new JSONObject();

        Map<String, String> params = new HashMap<String, String>();
        params.put("token", token);
        params.put("tweet_id", tweetID);

        aq = new AQuery(context);
        String url = "http://172.31.1.120:8888/tweetbrow/tweet/delete";
        aq.ajax(url, params, JSONObject.class, new AjaxCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                User user = User.getInstance();
                try {
                    user.setSucces(json.getString("reponse"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (user.isSucces().equals("success")) {
                    Log.e("TAG", "Delete tweet : ");
                    _listener.callback();
                }
            }
        });
    }

    public void takeTweet(APIListener listener){

        final ArrayList<Tweet> listTweet = new ArrayList<Tweet>();
        final APIListener _listener = listener;
        preferences = context.getSharedPreferences("Login", Context.MODE_PRIVATE);
        realm = Realm.getInstance(context);

        aq = new AQuery(context);
        String url = "http://172.31.1.120:8888/tweetbrow/timeline";

        Map<String, String> params = new HashMap<String, String>();
        System.out.println(User.getInstance().getToken());
        params.put("token", User.getInstance().getToken());

        aq.ajax(url, params, JSONObject.class, new AjaxCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                try {
                    Log.e("LAWL",json.toString());
                    JSONArray jArray  = json.getJSONArray("data");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

                    realm.beginTransaction();

                    for(int i=0;i<jArray.length();i++)
                    {
                        Date date_create = dateFormat.parse(jArray.getJSONObject(i).getString("date_create"));

                        jArray.getJSONObject(i).put("date_create",date_create.getTime());

                        Log.e("LAWL",jArray.toString());

                        realm.createOrUpdateObjectFromJson(Tweet.class, jArray.getJSONObject(i));
                    }
                    realm.commitTransaction();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                _listener.callback();

            }
        });

    }

    public interface APIListener{
        public void callback();
    }
}