package com.tweetbrow.rcdsm.tweetbrow;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by rcdsm on 18/05/15.
 */
public class NewsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_layout, container, false);
        /**
         * Inflate the layout for this fragment
         */

        return view;
    }

}

