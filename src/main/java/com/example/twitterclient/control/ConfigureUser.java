package com.example.twitterclient.control;

import android.content.Context;
import android.content.SharedPreferences;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by Eng-Karim on 7/3/2016.
 */
public class ConfigureUser {
    private String userToken;
    private String userSecret;
    private Twitter TwitterObj;
    private SharedPreferences userPref;

    public ConfigureUser(String userName, Context context) {
        // get user preferences
        userPref = context.getSharedPreferences(userName, Context.MODE_PRIVATE);
        userToken = userPref.getString(ConstVls.USER_TOKEN, null);
        userSecret = userPref.getString(ConstVls.USER_SECRET, null);
    }

    public Long getSharedPrefUserId() {
        return userPref.getLong(ConstVls.USER_ID, 0);

    }
    public String getSharedPrefUserScreen() {
        return userPref.getString(ConstVls.SCREEN_NAME, null);

    }

    public Twitter getUserTwitterObj() {
        // create new configuration

        Configuration twitConf = new ConfigurationBuilder().setOAuthConsumerKey(ConstVls.TWIT_KEY)
                .setOAuthConsumerSecret(ConstVls.TWIT_SECRET).setOAuthAccessToken(userToken)
                .setOAuthAccessTokenSecret(userSecret).build();
        // instantiate new twitter

        TwitterObj = new TwitterFactory(twitConf).getInstance();

        return TwitterObj;

    }}
