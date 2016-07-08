package com.example.twitterclient.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.twitterclient.Database.Tweet;
import com.example.twitterclient.R;
import com.example.twitterclient.adapters.TweetsListAdapter;
import com.example.twitterclient.control.ConfigureUser;
import com.example.twitterclient.control.ConstVls;
import com.example.twitterclient.control.TimelineControl;

import java.util.List;

import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class FollowerTimeline extends AppCompatActivity {

    private Context context;
    private SwipeRefreshLayout swipeLayout;
    private TimelineControl timelineControl;
    private ConfigureUser configureUser;
    private Twitter configureUserObj;
    private List<Tweet> tweetsList;
    private ListView tweetsListLv;
    private TimelineUpdateReciever timelineUpdateReciever;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.followers_timeline);
        this.context = getApplicationContext();
        if (getIntent() != null && getIntent().hasExtra(ConstVls.FOLLOWER_ID)) {
            userId = getIntent().getExtras().getLong(ConstVls.FOLLOWER_ID, -1);
        }
        timelineUpdateReciever = new TimelineUpdateReciever();
        tweetsListLv = (ListView) findViewById(R.id.followerTimeline_tweetsListLv);
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.followerTimeline_swipToRefreshLo);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                new GetFollowerTimeline().execute(configureUserObj);
            }
        });
        timelineControl = new TimelineControl(context);
        configureUser = new ConfigureUser(ConstVls.SHARED_PREF_NAME, getApplicationContext());
        this.configureUserObj = configureUser.getUserTwitterObj();
        List<Tweet> cachedtweets = timelineControl.getTweetsByUserId(userId);
        tweetsListLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Start the follower timeline activity
                Intent intent = new Intent(context, FollowerTimeline.class);
                intent.putExtra(ConstVls.FOLLOWER_ID, tweetsList.get(position).getUserId());
                startActivity(intent);
            }
        });
        if (!cachedtweets.isEmpty()) {
            TweetsListAdapter tweetsListAdapter = new TweetsListAdapter(context, R.layout.tweets_list_row, cachedtweets);
            tweetsListLv.setAdapter(tweetsListAdapter);
        }
        registerReceiver(timelineUpdateReciever, new IntentFilter(ConstVls.TIMELINE_INTNT_FILTER));
        new GetFollowerTimeline().execute(configureUserObj);

//        followersControl.getFollewersList(followers);

//        followersControl.addFollowers(followers);
        // FollowersListAdapater followersListAdapater = new FollowersListAdapater(context, R.layout.followers_list_row, );

    }


    public class GetFollowerTimeline extends AsyncTask<Twitter, Void, Void> {
        private ResponseList<twitter4j.Status> timelineList;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            swipeLayout.setRefreshing(true);

        }

        @Override
        protected Void doInBackground(Twitter... twitters) {
            try {
                timelineList = twitters[0].getUserTimeline(userId, new Paging(1, ConstVls.NUMBER_OF_TWEETS));
            } catch (TwitterException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            tweetsList = timelineControl.getTweetsList(timelineList);
            timelineControl.addTweets(tweetsList);
            swipeLayout.setRefreshing(false);


        }
    }

    public class TimelineUpdateReciever extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            List<Tweet> tweetList = timelineControl.getTweetsByUserId(userId);
            TweetsListAdapter tweetsListAdapter = new TweetsListAdapter(context, R.layout.tweets_list_row, tweetsList);
            tweetsListLv.setAdapter(tweetsListAdapter);

        }

    }


}
