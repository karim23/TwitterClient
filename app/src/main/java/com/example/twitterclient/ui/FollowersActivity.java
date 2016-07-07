package com.example.twitterclient.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.twitterclient.Database.Follower;
import com.example.twitterclient.R;
import com.example.twitterclient.adapters.FollowersListAdapater;
import com.example.twitterclient.control.ConfigureUser;
import com.example.twitterclient.control.ConstVls;
import com.example.twitterclient.control.FollowersControl;

import java.util.List;

import twitter4j.PagableResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

public class FollowersActivity extends AppCompatActivity {

    private ListView followersListLv;
    private Context context;
    private Twitter configureUserObj;
    private PagableResponseList<User> followers;
    private ConfigureUser configureUser;
    private FollowersControl followersControl;
    private List<Follower> followersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);
        this.context = getApplicationContext();
        FollwersUpdateReceiver follwersUpdateReceiver =new FollwersUpdateReceiver();
        followersListLv = (ListView) findViewById(R.id.followerActivity_followersListLv);
        followersControl = new FollowersControl(context);
        configureUser = new ConfigureUser(ConstVls.SHARED_PREF_NAME, getApplicationContext());
        this.configureUserObj = configureUser.getUserTwitterObj();
        registerReceiver(follwersUpdateReceiver, new IntentFilter(ConstVls.FOLLOWERS_INTNT_FILTER));
        new GetUserFollowers().execute(configureUserObj);

//        followersControl.getFollewersList(followers);

//        followersControl.addFollowers(followers);
        // FollowersListAdapater followersListAdapater = new FollowersListAdapater(context, R.layout.followers_list_row, );

    }

    public class GetUserFollowers extends AsyncTask<Twitter, Void, Void> {

        @Override
        protected Void doInBackground(Twitter... twitters) {
            try {
                followers = twitters[0].getFollowersList(configureUser.getSharedPrefUserId(), -1);
            } catch (TwitterException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            followersList = followersControl.getFollewersList(followers);
            followersControl.addFollowers(followersList);

        }
    }

    class FollwersUpdateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            List<Follower> followersList = followersControl.getFollowersByUserId(configureUser.getSharedPrefUserId());
            FollowersListAdapater followersListAdapater = new FollowersListAdapater(context, R.layout.followers_list_row, followersList);
            followersListLv.setAdapter(followersListAdapater);
            ;
        }

    }
}
