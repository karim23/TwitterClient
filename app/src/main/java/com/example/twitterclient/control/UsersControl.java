package com.example.twitterclient.control;

import android.content.Context;

import com.example.twitterclient.Database.DatabaseAdapter;
import com.example.twitterclient.Database.Tweet;
import com.example.twitterclient.Database.User;

import java.util.List;

/**
 * Created by Eng-Karim on 7/6/2016.
 */

public class UsersControl {
    private final DatabaseAdapter da;

    public UsersControl(Context c) {
        da = DatabaseAdapter.getInstance(c);
    }

    public List<User> getUserByUser(int userId) {
        return da.getUserByUserId(userId);
    }

    public void addUser(User user) {
        da.addUser(user);
    }
}
