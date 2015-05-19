package com.tweetbrow.rcdsm.tweetbrow;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by rcdsm on 18/05/15.
 */
public class LoginFragment extends Fragment {

    private Button connexion;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.connexion_fragment, container, false);
        /**
         * Inflate the layout for this fragment
         */
        connexion = (Button)view.findViewById(R.id.buttonSignIn);
        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((OnConnexionClickListener)(getActivity())).onConnexionClick();
            }
        });

        return view;
    }

    public interface OnConnexionClickListener {
        public void onConnexionClick();
    }
}

