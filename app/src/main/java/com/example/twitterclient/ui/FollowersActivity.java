package com.example.twitterclient.ui;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.twitterclient.Database.Follower;
import com.example.twitterclient.Database.Tweet;
import com.example.twitterclient.R;
import com.example.twitterclient.adapters.FollowersListAdapater;
import com.example.twitterclient.control.ConfigureUser;
import com.example.twitterclient.control.ConnectionControl;
import com.example.twitterclient.control.ConstVls;
import com.example.twitterclient.control.FollowersControl;
import com.example.twitterclient.control.TimelineControl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

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
    private List<Follower> followersList;
    private SwipeRefreshLayout swipeLayout;
    private FollowersControl followerControl;
    private ProgressDialog progressDialog;
    private TimelineUpdateReceiver follwersUpdateReceiver;
    private ConnectionControl connectionControl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.followers);
        this.context = getApplicationContext();
        connectionControl = new ConnectionControl(context);
        follwersUpdateReceiver = new TimelineUpdateReceiver();
        followersListLv = (ListView) findViewById(R.id.followerActivity_followersListLv);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.channelFrag_swipToRefreshLo);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                new GetUserFollowers().execute(configureUserObj);
            }
        });
        followersList = new ArrayList<>();
        followerControl = new FollowersControl(context);
        configureUser = new ConfigureUser(ConstVls.SHARED_PREF_NAME, getApplicationContext());
        this.configureUserObj = configureUser.getUserTwitterObj();
        final List<Follower> cachedFollowersList = followerControl.getFollowersByUserId(configureUser.getSharedPrefUserId());
        followersListLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Start the follower timeline activity
                if (!followersList.isEmpty() || !cachedFollowersList.isEmpty()) {
                    Intent intent = new Intent(context, FollowerTimeline.class);
                    intent.putExtra(ConstVls.FOLLOWER_ID, followersList.get(position).getUserId());
                    intent.putExtra(ConstVls.USER_FULL_NAME, followersList.get(position).getFullName());
                    intent.putExtra(ConstVls.SCREEN_NAME, followersList.get(position).getUserScreen());
                    intent.putExtra(ConstVls.USER_PROFILE_BANNER_URL, followersList.get(position).getUserBunnerUrl());
                    intent.putExtra(ConstVls.USER_PROFILE_IMG_URL, followersList.get(position).getUserImgUrl());
                    startActivity(intent);
                }
            }
        });
        if (!cachedFollowersList.isEmpty()) {
            FollowersListAdapater followersListAdapater = new FollowersListAdapater(context, R.layout.followers_list_row, cachedFollowersList);
            followersListLv.setAdapter(followersListAdapater);
        }
        if (connectionControl.checkInternetConnection())
            new GetUserFollowers().execute(configureUserObj);
        else
            connectionControl.showNoInternetConnectionMessage();
//        followersControl.getFollewersList(followers);

//        followersControl.addFollowers(followers);
        // FollowersListAdapater followersListAdapater = new FollowersListAdapater(context, R.layout.followers_list_row, );

    }

    public class GetUserFollowers extends AsyncTask<Twitter, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            swipeLayout.setRefreshing(true);
            registerReceiver(follwersUpdateReceiver, new IntentFilter(ConstVls.FOLLOWERS_INTNT_FILTER));

        }

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
            if (followers != null) {
                followersList = followerControl.getFollewersList(followers);
                followerControl.addFollowers(followersList);
            }
            swipeLayout.setRefreshing(false);


        }
    }

    class TimelineUpdateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            List<Follower> followersList = followerControl.getFollowersByUserId(configureUser.getSharedPrefUserId());
            FollowersListAdapater followersListAdapater = new FollowersListAdapater(context, R.layout.followers_list_row, followersList);
            followersListLv.setAdapter(followersListAdapater);
            ;
        }

    }
}
