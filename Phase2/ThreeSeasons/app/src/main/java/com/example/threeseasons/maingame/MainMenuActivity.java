package com.example.threeseasons.maingame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.threeseasons.R;
import com.example.threeseasons.data.DatabaseHelper;
import com.example.threeseasons.data.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Locale;

public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Database that stores user information.
     */
    private DatabaseHelper database;
    /**
     * User object that store the user information.
     */
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in:
                Intent signInIntent = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(signInIntent);
                break;
            case R.id.sign_up:
                Intent signUpIntent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(signUpIntent);
                break;
            case R.id.quick_start:
                checkGuestUser();
                Intent quickStartIntent = new Intent(getApplicationContext(), NewGameActivity.class);
                quickStartIntent.putExtra("user", user);
                startActivity(quickStartIntent);
                break;
            case R.id.settings:
                Intent settingIntent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(settingIntent);
                break;
        }
    }

    /**
     * Sign in as the guest user in quick start mode.
     */
    void checkGuestUser() {
        database = new DatabaseHelper(this);
        if (!database.userExists("GUEST")) {
            user = new User("GUEST", "GUEST");
            database.addUser(user);
            saveUserToFile();
        } else {
            loadUserFromFile();
        }
    }


    /**
     * Save the guest user to the corresponding file in the database.
     */
    public void saveUserToFile() {
        String fileName = database.getUserFile(user.getUsername());
        try {
            ObjectOutputStream outputStream =
                    new ObjectOutputStream(this.openFileOutput(fileName, Context.MODE_PRIVATE));

            outputStream.writeObject(user);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Load the guest user from the corresponding file in the database.
     */
    private void loadUserFromFile() {

        try {
            InputStream inputStream = this.openFileInput(database.getUserFile("GUEST"));
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                this.user = (User) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }

}

