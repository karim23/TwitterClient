package com.example.twitterclient.Database;

/**
 * Created by Eng-Karim on 7/4/2016.
 */
public class Follower {
    private int id;
    private Long userId;
    private String fullName;
    private String userScreen;
    private String userImgUrl;
    private String userBunnerUrl;
    private String userBio;


    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USER_ID = "userId";
    public static final String COLUMN_FULL_NAME = "fullName";
    public static final String COLUMN_USER_SCREEN = "userScreen";
    public static final String COLUMN_USER_IMG_URL = "userImgUrl";
    public static final String COLUMN_USER_BUNNER_URL = "userBunnerUrl";
    public static final String COLUMN_BIO = "userBio";


    public String[] getFields() {
        return new String[]{COLUMN_USER_ID, COLUMN_FULL_NAME, COLUMN_USER_SCREEN, COLUMN_USER_IMG_URL, COLUMN_USER_BUNNER_URL, COLUMN_BIO
        };
    }

    public String[] getValues() {
        return new String[]{"" + userId, fullName, userScreen, userImgUrl, userBunnerUrl, userBio};
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
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

    public String getUserBio() {
        return userBio;
    }

    public void setUserBio(String userBio) {
        this.userBio = userBio;
    }
}
