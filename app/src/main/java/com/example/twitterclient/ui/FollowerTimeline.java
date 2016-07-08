package com.example.twitterclient.ui;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.twitterclient.Database.Tweet;
import com.example.twitterclient.R;
import com.example.twitterclient.adapters.TweetsListAdapter;
import com.example.twitterclient.control.ConfigureUser;
import com.example.twitterclient.control.ConstVls;
import com.example.twitterclient.control.TimelineControl;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

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
    ProgressDialog progressDialog;
    private ImageView profileIm;
    private LinearLayout topBackground;
    private Button userNameBtn;
    private User status;
    private String userName;
    private String userScreen;
    private String userBunnnerImg;
    private String userImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getApplicationContext();

        setContentView(R.layout.followers_timeline);

        if (getIntent() != null && getIntent().hasExtra(ConstVls.FOLLOWER_ID)) {
            Bundle extras = getIntent().getExtras();
            userId = extras.getLong(ConstVls.FOLLOWER_ID, -1);
            userName = extras.getString(ConstVls.USER_FULL_NAME, "");
            userScreen = extras.getString(ConstVls.SCREEN_NAME, "");
            userBunnnerImg = extras.getString(ConstVls.USER_PROFILE_BANNER_URL, "");
            userImg = extras.getString(ConstVls.USER_PROFILE_IMG_URL, "");


        }

        timelineUpdateReciever = new TimelineUpdateReciever();
        tweetsListLv = (ListView) findViewById(R.id.followerTimeline_tweetsListLv);
        profileIm = (ImageView) findViewById(R.id.followerTimeline_userProfile);
        topBackground = (LinearLayout) findViewById(R.id.followerTimeline_topBackGround);
        userNameBtn = (Button) findViewById(R.id.followerTimeline_userName);
        userNameBtn.setText(userName + "\n@" + userScreen);
        if (userBunnnerImg != null && userBunnnerImg != "") {
            Picasso.with(context).load(userBunnnerImg).into(new Target() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    topBackground.setBackground(new BitmapDrawable(bitmap));
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }

            });
        }
        if (userBunnnerImg != null && userBunnnerImg != "") {
            Picasso.with(context).load(userImg).into(profileIm);
        }
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
            registerReceiver(timelineUpdateReciever, new IntentFilter(ConstVls.TIMELINE_INTNT_FILTER));
            swipeLayout.setRefreshing(true);
        }

        @Override
        protected Void doInBackground(Twitter... twitters) {
            try {
                timelineList = twitters[0].getUserTimeline(userId, new Paging(1, ConstVls.NUMBER_OF_TWEETS));
                status = twitters[0].showUser(userId);


            } catch (TwitterException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (timelineList != null) {
                tweetsList = timelineControl.getTweetsList(timelineList);
                timelineControl.addTweets(tweetsList);
                swipeLayout.setRefreshing(false);
            }

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
