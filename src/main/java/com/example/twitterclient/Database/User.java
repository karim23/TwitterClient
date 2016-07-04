package com.example.twitterclient.Database;

/**
 * Created by Eng-Karim on 7/4/2016.
 */
public class User {
    private int id;
    private int userId;
    private String fullName;
    private String userScreen;
    private String userImgUrl;
    private String userBunnerUrl;
    private String tokenKey;
    private String tokenSecret;

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USER_ID = "userId";
    public static final String COLUMN_FULL_NAME = "fullName";
    public static final String COLUMN_USER_SCREEN = "userScreen";
    public static final String COLUMN_USER_IMG_URL = "userImgUrl";
    public static final String COLUMN_USER_BUNNER_URL = "userBunnerUrl";
    public static final String COLUMN_TOKEN_KEY = "tokenKey";
    public static final String COLUMN_TOKEN_SECRET = "tokenSecret";

    public String[] getFields() {
        return new String[]{COLUMN_USER_ID, COLUMN_FULL_NAME, COLUMN_USER_SCREEN, COLUMN_USER_IMG_URL, COLUMN_USER_BUNNER_URL, COLUMN_TOKEN_KEY, COLUMN_TOKEN_SECRET
        };
    }

    public String[] getValues() {
        return new String[]{"" + userId, fullName ,userScreen, userImgUrl, userBunnerUrl, tokenKey, tokenSecret };
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserScreen() {
        return userScreen;
    }

    public void setUserScreen(String userScreen) {
        this.userScreen = userScreen;
    }

    public String getUserImgUrl() {
        return userImgUrl;
    }

    public void setUserImgUrl(String userImgUrl) {
        this.userImgUrl = userImgUrl;
    }

    public String getUserBunnerUrl() {
        return userBunnerUrl;
    }

    public void setUserBunnerUrl(String userBunnerUrl) {
        this.userBunnerUrl = userBunnerUrl;
    }

    public String getTokenKey() {
        return tokenKey;
    }

    public void setTokenKey(String tokenKey) {
        this.tokenKey = tokenKey;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }

    public void setTokenSecret(String tokenSecret) {
        this.tokenSecret = tokenSecret;
    }
}
