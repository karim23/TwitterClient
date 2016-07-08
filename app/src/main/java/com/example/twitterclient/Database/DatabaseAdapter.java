package com.example.twitterclient.Database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.twitterclient.R;
import com.example.twitterclient.control.ConstVls;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eng-Karim on 7/4/2016.
 */
public class DatabaseAdapter {
    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "twitterclient.DB";
    public static final String TABLE_TWEETS = "tweets";
    public static final String TABLE_FOLLOWERS = "followers";
    public static final String TABLE_USERS = "users";
    private static final String TAG = "DatabaseAdapter";
    private static DatabaseAdapter adapter;
    private static DatabaseAdapter databaseAdapter;
    private final Context context;
    private final SqliteDatabaseHelper dbHelper;
    private SQLiteDatabase db;

    private DatabaseAdapter(Context context) {
        this.context = context;
        this.dbHelper = new SqliteDatabaseHelper(context);
    }

    public static DatabaseAdapter getInstance(Context c) {
        if (databaseAdapter == null) {
            databaseAdapter = new DatabaseAdapter(c);
        }
        return databaseAdapter;
    }


    public synchronized long insert(String table, String[] fields, String[] values, String whereCause) {

        long id = -1;
        Cursor c = null;
        try {
            Log.v(TAG, "insert data into " + table);
            db = this.dbHelper.getWritableDatabase();
            ContentValues vals = new ContentValues();
            for (int i = 0; i < fields.length; i++) {
                vals.put(fields[i], values[i]);
                Log.v("" + fields[i], "" + values[i]);
            }
            db.insert(table, null, vals);
            String query = " Select " + whereCause + " from " + table + " order by " + whereCause + " DESC limit 1";

            c = db.rawQuery(query, null);
            if (c.moveToFirst()) {
                id = c.getLong(0);
                Log.v("query gadawal", "" + id);
            }
        } catch (Exception e) {
            Log.v("Gaawal insert fun   ", "" + e.getMessage() + e.toString());
            Log.e(TAG, e.toString());
        }
        c.close();
        return id;
    }

    public synchronized Cursor select(String table, boolean close) {

        db = this.dbHelper.getReadableDatabase();

        Cursor c = db.query(table, null, null, null, null, null, null);
        Log.v("database", "select from: " + table);

        if (close) {
            this.dbHelper.close();
        }
        return c;

    }

    public Cursor select(String table, boolean close, String id, String field) {
        db = this.dbHelper.getWritableDatabase();
        Cursor c = db.query(table, null, field + "=?", new String[]{id}, null, null, null);
        Log.v("database", "select from: " + table);
        return c;
    }

    public Cursor select(String table, String column, String column2, String quiry) {
        Cursor c;
        db = this.dbHelper.getReadableDatabase();
        if (column2 == null) {
            String query = "SELECT " + table + ".* FROM " + table + " WHERE " + column + " LIKE " + "?";
            c = db.rawQuery(query, new String[]{"%" + quiry + "%"});
            Log.v("database", "select from: " + table);
        } else {
            String query = "SELECT " + table + ".* FROM " + table + " WHERE " + column + " LIKE " + "? or " + column2 + " LIKE " + "?";
            c = db.rawQuery(query, new String[]{"%" + quiry + "%", "%" + quiry + "%"});
            Log.v("database", "select from: " + table);
        }
        return c;
    }

    public void closeDataB() {
        dbHelper.close();
    }

    public List<Tweet> getTweetsByUserId(Long userId) {
        Log.v(TABLE_TWEETS, " " + context.getString(R.string.table_loaded));
        List<Tweet> tweets = new ArrayList<>();
        Cursor c = this.select(TABLE_TWEETS, false , "" + userId, "userId");
        tweets = setTweetsData(c);
        c.close();
        return tweets;
    }

    private List<Tweet> setTweetsData(Cursor c) {
        List<Tweet> tweets = new ArrayList<Tweet>();
        if (c.moveToFirst()) {
            do {
                Tweet temp = new Tweet();
                temp.setId(c.getInt(c.getColumnIndex(Tweet.COLUMN_ID)));
                temp.setUserId(c.getInt(c.getColumnIndex(Tweet.COLUMN_USER_ID)));
                temp.setUpdateTime((long) c.getInt(c.getColumnIndex(Tweet.COLUMN_UPDATE_TIME)));
                temp.setTweetText(c.getString(c.getColumnIndex(Tweet.COLUMN_TWEET_TEXT)));
                temp.setUserScreen(c.getString(c.getColumnIndex(Tweet.COLUMN_USER_SCREEN)));
                temp.setTweetName(c.getString(c.getColumnIndex(Tweet.COLUMN_TWEET_NAME)));
                temp.setUserImg(c.getString(c.getColumnIndex(Tweet.COLUMN_USER_IMG)));
                temp.setTweetMediaURL(c.getString(c.getColumnIndex(Tweet.COLUMN_TWEET_MEDIA_URL)));
                tweets.add(temp);
            } while (c.moveToNext());
        }
        return tweets;
    }

    public List<User> getUserByUserId(int userId) {
        Log.v(TABLE_USERS, " " + context.getString(R.string.table_loaded));
        List<User> users = new ArrayList<>();
        Cursor c = this.select(TABLE_USERS, false, "" + userId, User.COLUMN_USER_ID);
        users = setUserData(c);
        c.close();
        return users;
    }

    private List<User> setUserData(Cursor c) {
        List<User> users = new ArrayList<User>();
        if (c.moveToFirst()) {
            do {
                User temp = new User();
                temp.setId(c.getInt(c.getColumnIndex(User.COLUMN_ID)));
                temp.setUserId(c.getInt(c.getColumnIndex(User.COLUMN_USER_ID)));
                temp.setFullName(c.getString(c.getColumnIndex(User.COLUMN_FULL_NAME)));
                temp.setUserScreen(c.getString(c.getColumnIndex(User.COLUMN_USER_SCREEN)));
                temp.setUserImgUrl(c.getString(c.getColumnIndex(User.COLUMN_USER_IMG_URL)));
                temp.setUserBunnerUrl(c.getString(c.getColumnIndex(User.COLUMN_USER_BUNNER_URL)));
                temp.setTokenKey(c.getString(c.getColumnIndex(User.COLUMN_TOKEN_KEY)));
                temp.setTokenSecret(c.getString(c.getColumnIndex(User.COLUMN_TOKEN_SECRET)));
                users.add(temp);
            } while (c.moveToNext());
        }
        return users;
    }

    private List<Follower> setFollowerData(Cursor c) {
        List<Follower> followers = new ArrayList<Follower>();
        if (c.moveToFirst()) {
            do {
                Follower temp = new Follower();
                temp.setId(c.getInt(c.getColumnIndex(Follower.COLUMN_ID)));
                temp.setUserId(c.getLong(c.getColumnIndex(Follower.COLUMN_USER_ID)));
                temp.setFullName(c.getString(c.getColumnIndex(Follower.COLUMN_FULL_NAME)));
                temp.setUserScreen(c.getString(c.getColumnIndex(Follower.COLUMN_USER_SCREEN)));
                temp.setUserImgUrl(c.getString(c.getColumnIndex(Follower.COLUMN_USER_IMG_URL)));
                temp.setUserBunnerUrl(c.getString(c.getColumnIndex(Follower.COLUMN_USER_BUNNER_URL)));
                temp.setUserBio(c.getString(c.getColumnIndex(Follower.COLUMN_BIO)));
                followers.add(temp);
            } while (c.moveToNext());
        }
        return followers;
    }

    public List<Follower> getFollowersByUserId(Long userId) {
        List<Follower> followersList = new ArrayList<>();
        Cursor c = select(TABLE_FOLLOWERS, false);
        followersList = setFollowerData(c);
        return followersList;
    }

    public void addFollowers(List<Follower> followers) {
        for (int i = 0; i < followers.size(); i++) {
            insert(TABLE_FOLLOWERS, followers.get(i).getFields(), followers.get(i).getValues(),
                    Follower.COLUMN_ID);
        }
        context.sendBroadcast(new Intent(ConstVls.FOLLOWERS_INTNT_FILTER));


    }

    public void addUser(User user) {
        insert(TABLE_USERS, user.getFields(), user.getValues(),
                User.COLUMN_ID);
    }

    public void addTweets(List<Tweet> tweets) {
        for (int i = 0; i < tweets.size(); i++) {
            insert(TABLE_TWEETS, tweets.get(i).getFields(), tweets.get(i).getValues(),
                    Follower.COLUMN_ID);
        }
        context.sendBroadcast(new Intent(ConstVls.TIMELINE_INTNT_FILTER));

    }


    public static class SqliteDatabaseHelper extends SQLiteOpenHelper {

        /**
         * database creation string
         */

        private static final String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "(" + User.COLUMN_ID
                + " INTEGER NOT NULL PRIMARY KEY, " + User.COLUMN_USER_ID + " LONG, " + User.COLUMN_FULL_NAME + " TEXT, "
                + User.COLUMN_USER_SCREEN + " TEXT, " + User.COLUMN_USER_IMG_URL + " TEXT, " + User.COLUMN_USER_BUNNER_URL + " TEXT, " + User.COLUMN_TOKEN_KEY
                + " TEXT, " + User.COLUMN_TOKEN_SECRET + " TEXT " + ");";

        private static final String CREATE_FOLLWERS_TABLE = "CREATE TABLE " + TABLE_FOLLOWERS + "(" + Follower.COLUMN_ID
                + " INTEGER NOT NULL PRIMARY KEY, " + Follower.COLUMN_USER_ID + " LONG UNIQUE, " + Follower.COLUMN_FULL_NAME + " TEXT, "
                + Follower.COLUMN_USER_SCREEN + " TEXT, " + Follower.COLUMN_USER_IMG_URL + " TEXT, " + Follower.COLUMN_USER_BUNNER_URL
                + " TEXT, " + Follower.COLUMN_BIO + " TEXT " + ");";

        private static final String CREATE_TWEETS_TABLE = "CREATE TABLE " + TABLE_TWEETS + "(" + Tweet.COLUMN_ID
                + " INTEGER NOT NULL  PRIMARY KEY UNIQUE, " + Tweet.COLUMN_USER_ID + " LONG , " + Tweet.COLUMN_UPDATE_TIME + " INTEGER ,"
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
