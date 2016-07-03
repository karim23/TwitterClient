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
        userToken = userPref.getString("user_token", null);
        userSecret = userPref.getString("user_secret", null);
    }

    public Long getSharedPrefUserId() {
        return userPref.getLong("userID", 0);

    }
    public String getSharedPrefUserScreen() {
        return userPref.getString("screenName", null);

    }

    public Twitter getUserTwitterObj() {
        // create new configuration

        Configuration twitConf = new ConfigurationBuilder().setOAuthConsumerKey(ConstVls.TWIT_KEY)
                .setOAuthConsumerSecret(ConstVls.TWIT_SECRET).setOAuthAccessToken(userToken)
                .setOAuthAccessTokenSecret(userSecret).build();
        // instantiate new twitter

        TwitterObj = new TwitterFactory(twitConf).getInstance();

        return TwitterObj;

    }
}
