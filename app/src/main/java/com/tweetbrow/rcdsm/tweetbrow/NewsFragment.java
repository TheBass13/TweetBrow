package com.tweetbrow.rcdsm.tweetbrow;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tweetbrow.rcdsm.tweetbrow.Adapter.TweetAdapter;
import com.tweetbrow.rcdsm.tweetbrow.Manager.TweetManager;
import com.tweetbrow.rcdsm.tweetbrow.Manager.UserManager;
import com.tweetbrow.rcdsm.tweetbrow.Models.Tweet;

import java.util.ArrayList;

/**
 * Created by rcdsm on 18/05/15.
 */
public class NewsFragment extends Fragment implements AdapterView.OnItemClickListener{

    private UserManager userManag;
    private ArrayList<Tweet> tweets;
    private ListView tweetList;
    private TweetAdapter adapter;
    private TweetManager tweet;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       final View view = inflater.inflate(R.layout.news_layout, container, false);
        /**
         * Inflate the layout for this fragment
         */
        tweetList = (ListView)view.findViewById(R.id.newsList);
        tweet = new TweetManager(view.getContext());

        ClientAPI.getInstance().takeTweet(new ClientAPI.APIListener() {
            @Override
            public void callback() {
                tweets = new ArrayList<Tweet>();
                adapter = new TweetAdapter(view.getContext(), tweets);
                tweetList.setAdapter(adapter);
                tweets.clear();
                tweets.addAll(tweet.allListNote());
            }
        });

        tweetList.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        removeItemFromList(position);
    }

    public void removeItemFromList(int position){
        final int deletePosition = position;

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        ViewFragment viewTweet = new ViewFragment();
        fragmentTransaction.replace(R.id.main_fragment, viewTweet);

        fragmentTransaction.commit();

    }

}

