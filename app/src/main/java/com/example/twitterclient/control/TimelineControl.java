package com.example.twitterclient.control;

import android.content.Context;

import com.example.twitterclient.Database.DatabaseAdapter;
import com.example.twitterclient.Database.Tweet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import twitter4j.MediaEntity;
import twitter4j.PagableResponseList;
import twitter4j.ResponseList;
import twitter4j.Status;

/**
 * Created by Eng-Karim on 7/6/2016.
 */

public class TimelineControl {
    private final DatabaseAdapter da;

    public TimelineControl(Context c) {
        da = DatabaseAdapter.getInstance(c);
    }

    public List<Tweet> getTweetsByUserId(Long followerId) {
        return da.getTweetsByUserId(followerId);
    }

    public void addTweets(List<Tweet> tweets) {
        da.addTweets(tweets);
    }

    public List<Tweet> getTweetsList(ResponseList<Status> tweetss) {
        List<Tweet> tweets = new ArrayList<>();
        for (int i = 0; i < tweetss.size(); i++) {
            twitter4j.Status item = tweetss.get(i);
            Tweet temp = new Tweet();
            temp.setUserId((int) item.getId());
            temp.setTweetName(item.getUser().getName());
            // temp.setTweetMediaURL(item.getUserMentionEntities());
            for (MediaEntity entity : item.getMediaEntities()) {
                temp.setTweetMediaURL(entity.getMediaURL());
            }
            temp.setUserScreen(item.getUser().getScreenName());
            temp.setTweetText(item.getText());
            temp.setUserImg(item.getUser().getProfileImageURL());
            try {
                temp.setUpdateTime(DateToLong(item.getCreatedAt()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            tweets.add(temp);
        }
        return tweets;
    }

    public Long DateToLong(Date date) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
        formatter.setLenient(false);

        Date curDate = new Date();
        long curMillis = curDate.getTime();
        String curTime = formatter.format(curDate);

        String oldTime = "05.01.2011, 12:45";
        Date oldDate = formatter.parse(oldTime);
        long oldMillis = oldDate.getTime();
        return oldMillis;
    }

}
