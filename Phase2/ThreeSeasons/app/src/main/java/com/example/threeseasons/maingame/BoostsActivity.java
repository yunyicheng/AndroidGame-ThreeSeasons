package com.example.threeseasons.maingame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.threeseasons.R;
import com.example.threeseasons.data.DatabaseHelper;
import com.example.threeseasons.data.User;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class BoostsActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Database that stores user information.
     */
    private DatabaseHelper database;
    /**
     * User object that stores the user information.
     */
    private User user;
    /**
     * Total score earned in three parts of the game.
     */
    private int threeSeasonsScoreSta;
    /**
     * Total number of jades of this user.
     */
    private int jadeSta;
    /**
     * TextView for displaying total score.
     */
    TextView score;
    /**
     * TextView for displaying the number of jade pendants of this user.
     */
    TextView jade;
    /**
     * TextView for displaying the highest score of this user.
     */
    TextView highestScoreDisplay;
    /**
     * Constant string representing the name of the game.
     */
    private static final String GAME_NAME = "Three Seasons";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boosts);

        this.database = new DatabaseHelper(this);
        this.user = (User) getIntent().getSerializableExtra("user");

        getData();

        findViews();

        displayData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.exchange:
                if (jadeSta > 0) {
                    threeSeasonsScoreSta += 10;
                    jadeSta -= 1;
                } else {
                    Toast.makeText(BoostsActivity.this,
                            getResources().getString(R.string.jade_error),
                            Toast.LENGTH_SHORT).show();
                }

                displayData();

                checkHighestScore();

                userUpdateScore();

                userUpdateJade();

                saveUserToFile();
                break;
            case R.id.back_to_menu2:
                Intent BackIntent = new Intent(getApplicationContext(), NewGameActivity.class);
                BackIntent.putExtra("user", user);
                startActivity(BackIntent);
                break;
        }
    }

    /**
     * Find all the TextViews needed to display user's statistics
     */
    void findViews() {
        score = findViewById(R.id.currentScoreSta);
        jade = findViewById(R.id.currentJadeSta);
        highestScoreDisplay = findViewById(R.id.highestScore);
    }

    /**
     * Get score and number of jade pendants of this user.
     */
    void getData() {
        this.threeSeasonsScoreSta = (int) getIntent().getSerializableExtra("score");
        this.jadeSta = user.getJade();
    }

    /**
     * Update the total score both in user's own record and in the database.
     */
    void userUpdateScore() {
        boolean updated = user.updateScore(GAME_NAME, threeSeasonsScoreSta);
        if (updated) {
            database.updateScore(user, GAME_NAME);
        }
    }

    /**
     * Display data of score and number of jade pendants of this user.
     */
    void displayData() {
        score.setText(String.valueOf(threeSeasonsScoreSta));
        jade.setText(String.valueOf(jadeSta));
    }

    /**
     * Save user to the corresponding file in the database.
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
     * Update the total number of jades in user.
     */
    void userUpdateJade() {
        user.updateJade(-1);
    }

    /**
     * Check whether the new score is the highest score for this user, if true, display text
     * "Highest score"
     */
    void checkHighestScore() {
        if (user.highestScore(GAME_NAME, threeSeasonsScoreSta)) {
            String highestScore = "Highest score";
            highestScoreDisplay.setText(highestScore);
        }
    }
}
