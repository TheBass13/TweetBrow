package com.tweetbrow.rcdsm.tweetbrow;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by rcdsm on 19/05/15.
 */
public class ViewFragment extends Fragment {

    private TextView login;
    private TextView pseudo;
    private TextView message;
    private TextView date;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_fragment, container, false);
        /**
         * Inflate the layout for this fragment
         */
        Bundle bundle = this.getArguments();
        String logins = bundle.getString("message");
        Log.e("Postionnnn ojf", logins);


        login = (TextView) view.findViewById(R.id.pseudoView);
        pseudo = (TextView) view.findViewById(R.id.loginView);
        message = (TextView) view.findViewById(R.id.messageView);
        date = (TextView) view.findViewById(R.id.dateView);

       message.setText(logins);

        /*login.setText("Toto");
        pseudo.setText("@Le Saligo");
        message.setText("kikou ceci est un test");
        date.setText("10:55");*/

        return view;
    }
}
