package com.tweetbrow.rcdsm.tweetbrow;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.tweetbrow.rcdsm.tweetbrow.Models.User;

/**
 * Created by rcdsm on 19/05/15.
 */
public class AddTweetFragment extends Fragment {

    private TextView login;
    private TextView pseudo;
    private TextView numberChar;
    private EditText tweet;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_tweet_fragment, container, false);
        /**
         * Inflate the layout for this fragment
         */

        login = (TextView)view.findViewById(R.id.loginUser);
        pseudo = (TextView)view.findViewById(R.id.pseudoUser);
        numberChar = (TextView)view.findViewById(R.id.numberChar);
        tweet = (EditText)view.findViewById(R.id.editNews);

        final TextWatcher txwatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                numberChar.setText(String.valueOf(140-s.length()));
            }

            public void afterTextChanged(Editable s) {
            }
        };

        tweet.addTextChangedListener(txwatcher);

        login.setText(User.getInstance().getLogin());
        pseudo.setText(User.getInstance().getPseudo());

        System.out.println(User.getInstance().getLogin());

        return view;
    }

}
