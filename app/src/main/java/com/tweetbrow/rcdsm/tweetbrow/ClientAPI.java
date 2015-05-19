package com.tweetbrow.rcdsm.tweetbrow;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.tweetbrow.rcdsm.tweetbrow.Models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by rcdsm on 27/04/15.
 */
public class ClientAPI {

    private Context context;
    private AQuery aq;
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

        aq.ajax(url, params, JSONObject.class, new AjaxCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {

                if (json != null) {
                    User user = User.getInstance();
                    user.setEmail(login);
                    try {
                        user.setToken(json.getJSONObject("data").getString("token"));

                        if (preferences.contains("Token") == false) {
                            editor = preferences.edit();
                            editor.putString("Token", json.getJSONObject("data").getString("token"));
                            editor.commit();
                        }

                        Log.e("Token", user.getToken());
                        user.setSucces(json.getString("reponse"));
                        Log.e("Note", json.getString("reponse").toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //successful ajax call, show status code and json content
                    //Toast.makeText(aq.getContext(), status.getCode() + ":" + json.toString(), Toast.LENGTH_LONG).show();
                    if (user.isSucces().equals("success")) {
                        Toast.makeText(aq.getContext(), "Welcom : " + login, Toast.LENGTH_LONG).show();
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

        public void subscrib(final String email,final String password,final String confirmPassword,APIListener listener){

        //On crée un JSOnObject et on recupère les informations email/password
        JSONObject requete = new JSONObject();
        //Puis on crée un deuxième JSONObject user ou l'on va mettre les informations recuperé précedement
        JSONObject user = new JSONObject();

        final APIListener _listener = listener;

        Log.i("Note", requete.toString());

        try {
            user.putOpt("email",email);
            user.putOpt("password",password);

            requete.putOpt("user", user);
            Log.i("Note", requete.toString());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        aq = new AQuery(context);
        String url = "http://notes.lloyd66.fr/api/v1/user/";
        aq.post(url, requete, JSONObject.class, new AjaxCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {

                User user = User.getInstance();
                user.setEmail(email);
                user.setStatus(status.getCode());
                try {
                    user.setSucces(json.getString("reponse"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //successful ajax call, show status code and json content
                //Toast.makeText(aq.getContext(), status.getCode() + ":" + json.toString(), Toast.LENGTH_LONG).show();
                if (confirmPassword.equals(password)) {
                    if (user.isSucces().equals("success")) {
                        connect(email, password, _listener);
                    } else {
                        Toast.makeText(aq.getContext(), "User already exist !", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(aq.getContext(), "The confirm password is wrong !", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public interface APIListener{
        public void callback();
    }
}