package com.example.twitterclient.control;

import android.content.Context;

import com.example.twitterclient.Database.DatabaseAdapter;
import com.example.twitterclient.Database.Tweet;

import java.util.List;

/**
 * Created by Eng-Karim on 7/6/2016.
 */

public class TweetsControl {
    private final DatabaseAdapter da;

    public TweetsControl(Context c) {
        da = DatabaseAdapter.getInstance(c);
    }

    public List<Tweet> getTweetsByUserId(int followerId) {
        return da.getTweetsByUserId(followerId);
    }

    public void addTweets(List<Tweet> tweets) {
        da.addTweets(tweets);
    }
}
