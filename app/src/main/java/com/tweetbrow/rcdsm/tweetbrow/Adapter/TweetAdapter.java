package com.tweetbrow.rcdsm.tweetbrow.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tweetbrow.rcdsm.tweetbrow.Models.Tweet;
import com.tweetbrow.rcdsm.tweetbrow.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by rcdsm on 19/05/15.
 */
public class TweetAdapter extends BaseAdapter {

    Context context;
    ArrayList<Tweet> tweets;

    LayoutInflater inflater;
    SimpleDateFormat format;

    public TweetAdapter(Context context, ArrayList<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
        inflater = LayoutInflater.from(context);
        format = new SimpleDateFormat("MMMM dd, yyyy hh:mm aa");
    }

    @Override
    public int getCount() {
        return tweets.size();
    }

    @Override
    public Object getItem(int position) {
        return tweets.size();
    }

    @Override
    public long getItemId(int position) {
        return tweets.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView==null) {
            convertView = inflater.inflate(R.layout.tweet_item, null);
            holder = new ViewHolder();
            holder.login = (TextView) convertView.findViewById(R.id.loginTweet);
            holder.pseudo = (TextView) convertView.findViewById(R.id.pseudoTweet);
            holder.message = (TextView) convertView.findViewById(R.id.messageTweet);
            holder.date = (TextView)convertView.findViewById(R.id.dateTweet);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.login.setText("TOTO");
        holder.pseudo.setText("TATA");
        holder.message.setText(tweets.get(position).getMessage());
        holder.date.setText("Last Edited : "+format.format(tweets.get(position).getDate_create()));

        return convertView;
    }

    class ViewHolder {
        public TextView login;
        public TextView pseudo;
        public TextView message;
        public TextView date;
    }
}

