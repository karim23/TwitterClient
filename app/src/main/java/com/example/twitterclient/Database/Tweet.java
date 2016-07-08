package com.example.twitterclient.Database;

/**
 * Created by Eng-Karim on 7/4/2016.
 */
public class Tweet {
    private int id;
    private int userId;
    private Long updateTime;
    private String tweetText;
    private String userScreen;
    private String tweetName;
    private String userImg;
    private String tweetMediaURL;

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USER_ID = "userId";
    public static final String COLUMN_UPDATE_TIME = "updateTime";
    public static final String COLUMN_TWEET_TEXT = "tweetText";
    public static final String COLUMN_USER_SCREEN = "userScreen";
    public static final String COLUMN_TWEET_NAME = "tweetName";
    public static final String COLUMN_USER_IMG = "userImg";
    public static final String COLUMN_TWEET_MEDIA_URL = "tweetMediaURL";

    public String[] getFields() {
        return new String[]{COLUMN_USER_ID, COLUMN_UPDATE_TIME, COLUMN_TWEET_TEXT, COLUMN_USER_SCREEN, COLUMN_TWEET_NAME, COLUMN_USER_IMG, COLUMN_TWEET_MEDIA_URL
        };
    }

    public String[] getValues() {
        return new String[]{"" + userId, updateTime + "", tweetText, userScreen, tweetName, userImg, tweetMediaURL};
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public String getTweetText() {
        return tweetText;
    }

    public void setTweetText(String tweetText) {
        this.tweetText = tweetText;
    }

    public String getUserScreen() {
        return userScreen;
    }

    public void setUserScreen(String userScreen) {
        this.userScreen = userScreen;
    }

    public String getTweetName() {
        return tweetName;
    }

    public void setTweetName(String tweetName) {
        this.tweetName = tweetName;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getTweetMediaURL() {
        return tweetMediaURL;
    }

    public void setTweetMediaURL(String tweetMediaURL) {
        this.tweetMediaURL = tweetMediaURL;
    }
}
