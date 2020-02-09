package com.example.threeseasons.maingame;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.threeseasons.R;
import com.example.threeseasons.data.DatabaseHelper;
import com.example.threeseasons.data.User;

public class NewGameActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * Database that stores user information.
     */
    private DatabaseHelper database;
    /**
     * User object that store the user information.
     */
    private User user;
    /**
     * Constant string representing the name of the game.
     */
    private static final String GAME_NAME = "Three Seasons";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        this.database = new DatabaseHelper(this);
        user = (User) getIntent().getSerializableExtra("user");
        setupData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.newGame:
                Intent storyIntent = new Intent(getApplicationContext(), BackgroundStoryActivity.class);
                storyIntent.putExtra("user", user);
                startActivity(storyIntent);
                break;
            case R.id.viewLocalRecords:
                Intent viewRecord = new Intent(getApplicationContext(), ChooseRecordActivity.class);
                viewRecord.putExtra("scoreBoardType", "byGame");
                viewRecord.putExtra("user", user);
                startActivity(viewRecord);
                break;
            case R.id.viewUserRecords:
                Intent viewUserRecord = new Intent(getApplicationContext(), ChooseRecordActivity.class);
                viewUserRecord.putExtra("scoreBoardType", "byUser");
                viewUserRecord.putExtra("user", user);
                startActivity(viewUserRecord);
                break;
            case R.id.switchAccount:
                Intent signInIntent = new Intent(getApplicationContext(), SignInActivity.class);
                startActivity(signInIntent);
        }
    }

    /**
     * Initiate user's data if it does not exist in the database.
     */
    void setupData() {
        if (!database.dataExists(user.getUsername(), GAME_NAME))
            database.addData(user.getUsername(), GAME_NAME);
    }

}
