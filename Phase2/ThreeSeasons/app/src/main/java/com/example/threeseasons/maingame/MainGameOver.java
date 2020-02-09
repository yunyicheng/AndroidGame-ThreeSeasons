package com.example.threeseasons.maingame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.threeseasons.R;
import com.example.threeseasons.data.DatabaseHelper;
import com.example.threeseasons.data.User;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class MainGameOver extends AppCompatActivity implements View.OnClickListener {

    /**
     * The number of jades that the player needs to trigger the ending.
     */
    private static final int JADE_TRIGGER = 10;

    /**
     * Database that stores user information.
     */
    private DatabaseHelper database;
    /**
     * User object that stores the user information.
     */
    private User user;
    /**
     * TextView for displaying the number of jade pendants collected in game Summer.
     */
    TextView summerJade;
    /**
     * TextView for displaying the score earned in game Summer.
     */
    TextView summerScore;
    /**
     * TextView for displaying the number of jade pendants collected in game Autumn.
     */
    TextView autumnJade;
    /**
     * TextView for displaying the score earned in game Autumn.
     */
    TextView autumnScore;
    /**
     * TextView for displaying the number of jade pendants collected in game Winter.
     */
    TextView winterJade;
    /**
     * TextView for displaying the score earned in game Winter.
     */
    TextView winterScore;
    /**
     * TextView for displaying the total number of jade pendants collected in three parts of the game.
     */
    TextView threeSeasonsJade;
    /**
     * TextView for displaying the total score earned in three parts of the game.
     */
    TextView threeSeasonsScore;
    /**
     * Constant string representing the name of the game.
     */
    private static final String GAME_NAME = "Three Seasons";
    /**
     * Number of jade pendants collected in game Summer.
     */
    int summerJadeSta;
    /**
     * Score earned in game Summer.
     */
    int summerScoreSta;
    /**
     * Number of jade pendants collected in game Autumn.
     */
    int autumnJadeSta;
    /**
     * Score earned in game Autumn.
     */
    int autumnScoreSta;
    /**
     * Number of jade pendants collected in game Winter.
     */
    int winterJadeSta;
    /**
     * Score earned in game Winter.
     */
    int winterScoreSta;
    /**
     * Total number of jade pendants collected in three parts of the game.
     */
    int threeSeasonsJadeSta;
    /**
     * Total score earned in three parts of the game.
     */
    private int threeSeasonsScoreSta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_game_over);

        this.database = new DatabaseHelper(this);
        this.user = (User) getIntent().getSerializableExtra("user");

        findViews();

        getData();

        userUpdateScore();

        userUpdateJade();

        saveUserToFile();

        displayData();

    }

    /**
     * Find all the TextViews needed to display user's statistics
     */
    void findViews() {
        summerJade = findViewById(R.id.summerJade);
        summerScore = findViewById(R.id.summerScore);
        autumnJade = findViewById(R.id.autumnJade);
        autumnScore = findViewById(R.id.autumnScore);
        winterJade = findViewById(R.id.winterJade);
        winterScore = findViewById(R.id.winterScore);
        threeSeasonsJade = findViewById(R.id.threeSeasonsJade);
        threeSeasonsScore = findViewById(R.id.threeSeasonsScore);
    }

    /**
     * Get scores and numbers of jade pendants gained in each game and calculate the total amount.
     */
    void getData() {
        summerJadeSta = (int) getIntent().getSerializableExtra("summerJade");
        summerScoreSta = (int) getIntent().getSerializableExtra("summerScore");
        autumnJadeSta = (int) getIntent().getSerializableExtra("autumnJade");
        autumnScoreSta = (int) getIntent().getSerializableExtra("autumnScore");
        winterJadeSta = (int) getIntent().getSerializableExtra("winterJade");
        winterScoreSta = (int) getIntent().getSerializableExtra("winterScore");
        threeSeasonsJadeSta = summerJadeSta + autumnJadeSta + winterJadeSta;
        threeSeasonsScoreSta = summerScoreSta + autumnScoreSta + winterScoreSta;
    }

    /**
     * Display data of scores and numbers of jade pendants gained game.
     */
    void displayData() {
        summerJade.setText(String.valueOf(summerJadeSta));
        summerScore.setText(String.valueOf(summerScoreSta));
        autumnJade.setText(String.valueOf(autumnJadeSta));
        autumnScore.setText(String.valueOf(autumnScoreSta));
        winterJade.setText(String.valueOf(winterJadeSta));
        winterScore.setText(String.valueOf(winterScoreSta));
        threeSeasonsJade.setText(String.valueOf(threeSeasonsJadeSta));
        threeSeasonsScore.setText(String.valueOf(threeSeasonsScoreSta));

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
     * Update the total number of jades in user.
     */
    void userUpdateJade() {
        user.updateJade(threeSeasonsJadeSta);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_to_menu:
                Intent BackIntent = new Intent(getApplicationContext(), NewGameActivity.class);
                BackIntent.putExtra("user", user);
                startActivity(BackIntent);
                break;
            case R.id.exchangeForScore:
                Intent intent = new Intent(getApplicationContext(), BoostsActivity.class);
                intent.putExtra("user", user);
                intent.putExtra("score", threeSeasonsScoreSta);
                startActivity(intent);
                break;
            case R.id.ending:
                if (threeSeasonsJadeSta < JADE_TRIGGER) {
                    Intent noEndingIntent = new Intent(getApplicationContext(), NoEndingActivity.class);
                    noEndingIntent.putExtra("user", user);
                    startActivity(noEndingIntent);
                } else {       // trigger the ending
                    Intent endingIntent = new Intent(getApplicationContext(), EndingActivity.class);
                    endingIntent.putExtra("user", user);
                    endingIntent.putExtra("jades", threeSeasonsJadeSta);
                    startActivity(endingIntent);
                }
                break;
        }
    }
}
