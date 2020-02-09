package com.example.threeseasons.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    /*
     * We learned how to create database with android SQLiteDatabase from:
     * https://blog.csdn.net/dmk877/article/details/44876805
     * https://github.com/baileywwang/CSC207-Course-Project/blob/master/Phase2/GameCentre/app/src/main/java/fall2018/csc2017/GameCentre/data/SQLDatabase.java
     * https://stackoverflow.com/questions/21645838/implementing-a-class-that-extends-sqliteopenhelper
     * We acknowledge all the efforts the original author has made.
     */

    /**
     * Name of the database.
     */
    private static final String DB_NAME = "gameDatabase";
    /**
     * Name of the data table.
     */
    private static final String DATA_TABLE = "dataTable";
    /**
     * Name of the user table.
     */
    private static final String USER_TABLE = "userTable";
    /**
     * Username of per user.
     */
    private static final String KEY_USERNAME = "username";
    /**
     * Game part of the game
     */
    private static final String KEY_GAME_PART = "gamePart";
    /**
     * Score for per user per game part.
     */
    private static final String KEY_SCORE = "score";
    /**
     * Name of the file.
     */
    private static final String KEY_FILE_ADDRESS = "fileAddress";


    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application
     * assets and resources.
     *
     * @param context the context of the activity
     */
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DATA_TABLE = "CREATE TABLE " + DATA_TABLE + "("
                + KEY_USERNAME + " TEXT," + KEY_GAME_PART + " TEXT," + KEY_SCORE + " INTEGER,"
                + KEY_FILE_ADDRESS + " TEXT, PRIMARY KEY(" + KEY_USERNAME + ", " + KEY_GAME_PART + "))";
        String CREATE_USER_TABLE = "CREATE TABLE " + USER_TABLE + "("
                + KEY_USERNAME + " TEXT PRIMARY KEY," + KEY_FILE_ADDRESS + " TEXT)";

        db.execSQL(CREATE_DATA_TABLE);
        db.execSQL(CREATE_USER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DATA_TABLE);

        onCreate(db);
    }

    /**
     * Return true if the username exists in the userTable
     *
     * @param username the username of the user
     * @return whether the user exists.
     */
    public boolean userExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + USER_TABLE + " WHERE " + KEY_USERNAME +
                " =?", new String[]{username});
        if (cursor.getCount() > 0) {
            cursor.close();
            db.close();
            Log.d("userExists", "userExists: True");
            return true;
        } else {
            cursor.close();
            db.close();
            Log.d("userExists", "userExists: False");
            return false;
        }
    }

    /**
     * Return true if the username with the specified gamePart exists in the data table.
     *
     * @param username the username of the user
     * @param gamePart the part of the game
     * @return true if data exists.
     */
    public boolean dataExists(String username, String gamePart) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DATA_TABLE + " WHERE " + KEY_USERNAME +
                " =? AND " + KEY_GAME_PART + " =?", new String[]{username, gamePart});
        if (cursor.getCount() > 0) {
            cursor.close();
            db.close();
            Log.d("dataExists", "dataExists: True");
            return true;
        } else {
            cursor.close();
            db.close();
            Log.d("dataExists", "dataExists: False");
            return false;
        }
    }

    /**
     * For sign up. Add user to user Table when signing up.
     * If user exists, there will be error because username is the primary key,
     * but the program won't stop, error message only shown in log.d
     *
     * @param user the user object that stores user information.
     */
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, user.getUsername());
        values.put(KEY_FILE_ADDRESS, user.getUsername() + "_user.ser");

        db.insert(USER_TABLE, null, values);

        Log.d("Add user ", user.getUsername() + "File: " + user.getUsername() + "_user.ser");
    }

    /**
     * Add data file address as well as the whole data tuple into the data table.
     * Called when first entering a game which the user has never played.
     * dataExists() should be called first, if data doesn't exist, then call this method.
     * score is initialized to be 0 when data is added
     */
    public void addData(String username, String gamePart) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, username);
        values.put(KEY_GAME_PART, gamePart);
        values.put(KEY_SCORE, 0);
        values.put(KEY_FILE_ADDRESS, username + "_" + gamePart + "_data.ser");

        db.insert(DATA_TABLE, null, values);
        db.close();

        Log.d("Add data ", username + " and " + gamePart + "'s file is added to database");
    }

    /**
     * return user file address, the file contains the serialized user object
     * Call userExists() before calling this method, user must exist in the table in order to get
     * the file address.
     *
     * @param username the username of the user.
     * @return the file name of the current user.
     */
    public String getUserFile(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + USER_TABLE + " WHERE "
                + KEY_USERNAME + " =?", new String[]{username});
        if (cursor.moveToFirst()) {
            String result = cursor.getString(cursor.getColumnIndex(KEY_FILE_ADDRESS));
            cursor.close();
            db.close();
            Log.d("GetUser", result);
            return result;
        } else {
            cursor.close();
            db.close();
            Log.d("Get User", "Username Does Not Exist, Please Sign Up!");
            return "Username Does Not Exist, Please Sign Up!";
        }
    }

    /**
     * Update the database with user's newest best score.
     *
     * @param user     the user object that store the user information.
     * @param gamePart the part of the game.
     */
    public void updateScore(User user, String gamePart) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, user.getUsername());
        values.put(KEY_GAME_PART, gamePart);
        values.put(KEY_SCORE, user.getScore(gamePart));
        System.out.println(user.getScore(gamePart));
        values.put(KEY_FILE_ADDRESS, user.getFile(gamePart));
        db.update(DATA_TABLE, values, KEY_USERNAME + "=? AND " +
                KEY_GAME_PART + "=?", new String[]{user.getUsername(), gamePart});
        db.close();
        Log.d("updateScore", "updateScore: success?");
        System.out.println("Autumn");
    }

    /**
     * Get all the best scores of each game.
     *
     * @param game_type the type of the game.
     * @return the list of list, the inner list contains the game type, name of the user, score and its rank.
     */
    public List<List<String>> getScoreByGame(String game_type) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<List<String>> dataList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DATA_TABLE + " WHERE " + KEY_GAME_PART +
                        " =? AND " + KEY_SCORE + " <> 0 ORDER BY " + KEY_SCORE + " DESC",
                new String[]{game_type});
        if (cursor.moveToFirst()) {
            int rank = 1;
            do {
                String user = cursor.getString(cursor.getColumnIndex(KEY_USERNAME));
                String score = Integer.toString(cursor.getInt(cursor.getColumnIndex(KEY_SCORE)));
                List<String> data = new ArrayList<>();
                data.add(String.valueOf(rank));
                data.add(user);
                data.add(score);
                dataList.add(data);
                rank++;
            } while (cursor.moveToNext());
        }

        db.close();
        return dataList;
    }
}
