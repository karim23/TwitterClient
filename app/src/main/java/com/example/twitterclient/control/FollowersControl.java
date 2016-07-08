package com.example.twitterclient.control;

import android.content.Context;

import com.example.twitterclient.Database.DatabaseAdapter;
import com.example.twitterclient.Database.Follower;
import com.example.twitterclient.Database.Tweet;

import java.util.ArrayList;
import java.util.List;

import twitter4j.PagableResponseList;
import twitter4j.User;

/**
 * Created by Eng-Karim on 7/6/2016.
 */

public class FollowersControl {
    private final DatabaseAdapter da;

    public FollowersControl(Context c) {
        da = DatabaseAdapter.getInstance(c);
    }

    public List<Follower> getFollowersByUserId(Long userId) {
        return da.getFollowersByUserId(userId);
    }

    public void addFollowers(List<Follower> followers) {
        da.addFollowers(followers);
    }

    public List<Follower> getFollewersList(PagableResponseList<User> followersParse) {
        List<Follower> followers = new ArrayList<>();
        for (int i = 0; i < followersParse.size(); i++) {
            User item = followersParse.get(i);
            Follower temp = new Follower();
            temp.setUserId(item.getId());
            temp.setFullName(item.getName());
            temp.setUserBunnerUrl(item.getProfileBannerURL());
            temp.setUserScreen(item.getScreenName());
            temp.setUserBio(item.getDescription());
            temp.setUserImgUrl(item.getProfileImageURL());
            followers.add(temp);
        }
        return followers;
    }
}
