package com.example.twitterclient.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.twitterclient.R;
import com.example.twitterclient.control.ConfigureUser;
import com.example.twitterclient.control.ConnectionControl;
import com.example.twitterclient.control.ConstVls;
import com.example.twitterclient.ui.FollowersActivity;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;


public class MainActivity extends AppCompatActivity {

    private Context context;
    private SharedPreferences preferences;
    private Twitter twitterInst;
    private static final String LOG_TAG = "MainActivity";
    private RequestToken requestToken;
    private String userFullName;
    private String userProfileImgURL;
    private String userProfileBannerURL;
    private AccessToken accToken;
    private Button loginBtn;
    private ProgressBar progressBar1Pb;
    private TextView loginHintTv;
    private TextView pleaseWaitTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        setContentView(R.layout.activity_main);
        loginBtn = (Button) findViewById(R.id.mainActivity_loginBtn);
        loginHintTv =(TextView)findViewById(R.id.mainActivity_loginHintTv);
        pleaseWaitTv =(TextView)findViewById(R.id.mainActivity_pleaseWaitTv);

        progressBar1Pb =(ProgressBar) findViewById(R.id.mainActivity_progressBar1Pb);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String authURL = requestToken.getAuthenticationURL();
                new openOAuth().execute(authURL);
            }
        });
        ConnectionControl connectionControl = new ConnectionControl(context);
        if (connectionControl.checkInternetConnection()) {
            checkAuoth();
        } else
            connectionControl.showNoInternetConnectionMessage();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Uri twitURI = intent.getData();
        if (twitURI != null && twitURI.toString().startsWith(ConstVls.TWIT_URL)) {
            // is verification - get the returned data
            final String oaVerifier = twitURI.getQueryParameter("oauth_verifier");
            // attempt to retrieve access token

            // try to get an access token using the returned data from the
            // verification page
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        accToken = twitterInst.getOAuthAccessToken(requestToken, oaVerifier);
                        // add the token and secret to shared prefs for future reference
                        preferences.edit().putString(ConstVls.USER_TOKEN, accToken.getToken())
                                .putString(ConstVls.USER_SECRET, accToken.getTokenSecret()).putLong(ConstVls.USER_ID, accToken.getUserId())
                                .putString(ConstVls.SCREEN_NAME, accToken.getScreenName()).commit();
                        // display the timeline

                        userFullName = twitterInst.showUser(preferences.getLong(ConstVls.USER_ID, 111111)).getName();
                        userProfileImgURL = twitterInst.showUser(preferences.getLong(ConstVls.USER_ID, 111111)).getProfileImageURL();
                        userProfileBannerURL = twitterInst.showUser(preferences.getLong(ConstVls.USER_ID, 111111)).getProfileBannerURL();

                        preferences.edit().putString(ConstVls.USER_FULL_NAME, userFullName)
                                .putString(ConstVls.USER_PROFILE_IMG_URL, userProfileImgURL)
                                .putString(ConstVls.USER_PROFILE_BANNER_URL, userProfileBannerURL).commit();

                        Intent intent1 = new Intent(context, FollowersActivity.class);
                        startActivity(intent1);
                    } catch (TwitterException e) {
                        Log.e(LOG_TAG, "Failed to get access token: " + e.getMessage());
                    }

                }
            });
            thread.start();

        }
    }

    private void checkAuoth() {
        preferences = getSharedPreferences(ConstVls.SHARED_PREF_NAME, context.MODE_PRIVATE);
        if (preferences.getString(ConstVls.USER_TOKEN, null) == null) {
            twitterInst = new TwitterFactory().getInstance();
            new GetAuthenticationRequestToken().execute(ConstVls.TWIT_URL);
        } else {
            Intent followersActivity = new Intent(context, FollowersActivity.class);
            startActivity(followersActivity);
        }
    }

    public class GetAuthenticationRequestToken extends AsyncTask<String, Integer, Void> {


        @Override
        protected void onPreExecute() {

            loginBtn.setVisibility(View.GONE);
            loginHintTv.setVisibility(View.GONE);
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                // pass developer key and secret
                twitterInst.setOAuthConsumer(ConstVls.TWIT_KEY, ConstVls.TWIT_SECRET);
                // try to get request token
                requestToken = twitterInst.getOAuthRequestToken(params[0]);

            } catch (TwitterException e) {

                Log.e(LOG_TAG, "TE " + e.getMessage());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            progressBar1Pb.setVisibility(View.GONE);
            pleaseWaitTv.setVisibility(View.GONE);
            TextView textView = (TextView) findViewById(R.id.startTv_twitterLike);
            loginBtn.setVisibility(View.VISIBLE);
            loginHintTv.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) textView.getLayoutParams();
            lp.addRule(RelativeLayout.ABOVE, loginBtn.getId());
            super.onPostExecute(result);
        }

    }

    // Asynch Task to handle login connection
    public class openOAuth extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(params[0])));
            return null;
        }

    }
}
