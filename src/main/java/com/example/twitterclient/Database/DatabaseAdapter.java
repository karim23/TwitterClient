package com.example.twitterclient.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.twitterclient.control.ConstVls;

/**
 * Created by Eng-Karim on 7/4/2016.
 */
public class DatabaseAdapter {
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "twitterclient.DB";
    public static final String TABLE_TWEETS = "tweets";
    public static final String TABLE_FOLLOWERS = "followers";
    public static final String TABLE_USERS = "users";



    public static class SqliteDatabaseHelper extends SQLiteOpenHelper {

        /**
         * database creation string
         */

        private static final String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "(" + User.COLUMN_ID
                + " INTEGER NOT NULL PRIMARY KEY, " + User.COLUMN_USER_ID + " INTEGER, " + User.COLUMN_FULL_NAME + " TEXT, "
                + User.COLUMN_USER_SCREEN + " TEXT, " + User.COLUMN_USER_IMG_URL + " TEXT, " + User.COLUMN_USER_BUNNER_URL + " TEXT, " + User.COLUMN_TOKEN_KEY
                + " TEXT, " + User.COLUMN_TOKEN_SECRET + " TEXT, " + ");";

        private static final String CREATE_FOLLWERS_TABLE = "CREATE TABLE " + TABLE_FOLLOWERS + "(" + Follower.COLUMN_ID
                + " INTEGER NOT NULL PRIMARY KEY, " + Follower.COLUMN_USER_ID + " INTEGER, " + Follower.COLUMN_FULL_NAME + " TEXT, "
                + Follower.COLUMN_USER_SCREEN + " TEXT, " + Follower.COLUMN_USER_IMG_URL + " TEXT, " + Follower.COLUMN_USER_BUNNER_URL
                + " TEXT, " + Follower.COLUMN_BIO + " TEXT, " + ");";

        private static final String CREATE_TWEETS_TABLE = "CREATE TABLE " + TABLE_TWEETS + "(" + Tweet.COLUMN_ID
                + " INTEGER NOT NULL  PRIMARY KEY AUTOINCREMENT, " + Tweet.COLUMN_USER_ID + " INTEGER UNIQUE, " + Tweet.COLUMN_UPDATE_TIME + " INTEGER ,"
                + Tweet.COLUMN_TWEET_TEXT + " TEXT ," + Tweet.COLUMN_USER_SCREEN + " TEXT," + Tweet.COLUMN_TWEET_NAME + " TEXT ," + Tweet.COLUMN_USER_IMG + " TEXT ,"
                + Tweet.COLUMN_TWEET_MEDIA_URL + " TEXT);";


        public SqliteDatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(ConstVls.SQLITE_LOG, "on Create Called");
            db.execSQL(CREATE_USERS_TABLE);
            db.execSQL(CREATE_FOLLWERS_TABLE);
            db.execSQL(CREATE_TWEETS_TABLE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d(ConstVls.SQLITE_LOG, "On upgared called");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOLLOWERS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TWEETS);
            // db.execSQL("VACUUM");
            onCreate(db);
        }

    }
}
