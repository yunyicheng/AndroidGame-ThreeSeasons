
package com.example.threeseasons.summer;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.threeseasons.data.DatabaseHelper;
import com.example.threeseasons.data.User;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * The main activity for the game
 */
public class SummerActivity extends AppCompatActivity implements SummerView {

    /**
     * The game view
     */
    SummerGameView firstView;

    /**
     * The presenter for this game
     */
    SummerPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);

        //Decide which mode to start the game
        Bundle mode = getIntent().getExtras();
        String character = mode.getString("mode");

        DatabaseHelper database = new DatabaseHelper(this);
        User user = (User) getIntent().getSerializableExtra("user");
        SummerManager summerManager = new SummerManager(database,user);
        this.presenter = new SummerPresenter(this, summerManager);
        this.firstView = new SummerGameView(this, presenter, character, user);

        setContentView(firstView);
    }

    @Override
    protected void onRestart(){
        super.onRestart();

        //Decide which mode to start the game
        Bundle mode = getIntent().getExtras();
        String character = mode.getString("mode");

        DatabaseHelper database = new DatabaseHelper(this);
        User user = (User) getIntent().getSerializableExtra("user");
        SummerManager summerManager = new SummerManager(database,user);
        this.presenter = new SummerPresenter(this, summerManager);
        this.firstView = new SummerGameView(this, presenter, character, user);

        setContentView(firstView);
    }


    /**
     * Store and pass statistics of this game and jump to the statistics page on the screen
     */
    @Override
    public void gameOver() {

        Intent intent = new Intent(this, SummerGameOver.class);
        Bundle statistics = new Bundle();

        intent.putExtra("user", presenter.getUser());
        intent.putExtra("summerScore", presenter.getData().getScore());
        intent.putExtra("summerJade", presenter.getData().getNumberJadesEarned());
        int score = presenter.getData().getScore();
        long duration = presenter.getData().getDuration();
        int distance = presenter.getData().getDistance();

        statistics.putInt("score", score);
        statistics.putLong("time", duration);
        statistics.putInt("distance", distance);
        intent.putExtras(statistics);
        startActivity(intent);
        finish();
    }

    /**
     * Save the user to file
     */
    @Override
    public void saveUserToFile(String fileName) {
        try {
            ObjectOutputStream outputStream =
                    new ObjectOutputStream(openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStream.writeObject(presenter.getUser());

            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}