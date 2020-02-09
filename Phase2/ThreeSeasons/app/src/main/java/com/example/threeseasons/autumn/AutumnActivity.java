package com.example.threeseasons.autumn;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.threeseasons.R;
import com.example.threeseasons.data.DatabaseHelper;
import com.example.threeseasons.data.User;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class AutumnActivity extends AppCompatActivity implements AutumnView {
    /**
     * Controller for this activity.
     */
    private AutumnPresenter autumnPresenter;
    /**
     * TextView for the command.
     */
    private TextView commandDisplay;
    /**
     * TextView for the score.
     */
    private TextView scoreDisplay;
    /**
     * TextView for the time.
     */
    private TextView timeDisplay;
    /**
     * TextView for the number of failures.
     */
    private TextView failureDisplay;
    /**
     * TextView for the number of streaks.
     */
    private TextView streakDisplay;
    /**
     * TextView for the max number of streaks.
     */
    private TextView maxStreakDisplay;
    /**
     * TextView for the number of jades.
     */
    private TextView jadeDisplay;
    /**
     * ImageView for the frame of the command.
     */
    private ImageView frame;
    /**
     * ImageView for the jade.
     */
    private ImageButton jade;
    /**
     * Buttons to be manipulated and displayed.
     */
    private ImageButton buttonRed, buttonYellow, buttonGreen;
    /**
     * 5-s count-down timer used in each round.
     */
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autumn);
        setupPresenter();
        setupView();
        setupOnClickListener();
        setupCountDownTimer();
        setCommandView();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setContentView(R.layout.activity_autumn);
        setupPresenter();
        setupView();
        setupOnClickListener();
        setupCountDownTimer();
        setCommandView();
    }

    /**
     * Create and set up presenter.
     */
    private void setupPresenter() {
        DatabaseHelper database = new DatabaseHelper(this);
        User user = (User) getIntent().getSerializableExtra("user");
        AutumnManager autumnManager = new AutumnManager(database, user);
        autumnPresenter = new AutumnPresenter(this, autumnManager);
    }

    /**
     * Create and set up buttons, TextView and ImageView for the game.
     */
    private void setupView() {
        commandDisplay = findViewById(R.id.command);
        frame = findViewById(R.id.frame);
        timeDisplay = findViewById(R.id.timer);
        failureDisplay = findViewById(R.id.failures_sta);
        scoreDisplay = findViewById(R.id.score_sta);
        streakDisplay = findViewById(R.id.streak_sta);
        maxStreakDisplay = findViewById(R.id.maxStreak_sta);
        jadeDisplay = findViewById(R.id.jade_sta);
        buttonRed = findViewById(R.id.red);
        buttonYellow = findViewById(R.id.yellow);
        buttonGreen = findViewById(R.id.green);
        jade = findViewById(R.id.jade_pendant);
        scoreDisplay.setText(String.valueOf(autumnPresenter.getScore()));
        failureDisplay.setText(autumnPresenter.getFailure());
        streakDisplay.setText(autumnPresenter.getStreak());
        maxStreakDisplay.setText(autumnPresenter.getMaxStreak());
        jadeDisplay.setText(String.valueOf(autumnPresenter.getJade()));
    }

    /**
     * Set up setOnClickListener for ImageButtons.
     */
    private void setupOnClickListener() {
        buttonRed.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        autumnPresenter.checkAnswer(Color.RED);
                        autumnPresenter.generateJade();
                        if (autumnPresenter.getGameRunning()) {
                            newRound();
                        }
                    }
                });
        buttonYellow.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        autumnPresenter.checkAnswer(Color.YELLOW);
                        autumnPresenter.generateJade();
                        if (autumnPresenter.getGameRunning()) {
                            newRound();
                        }
                    }
                });
        buttonGreen.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        autumnPresenter.checkAnswer(Color.GREEN);
                        autumnPresenter.generateJade();
                        if (autumnPresenter.getGameRunning()) {
                            newRound();
                        }
                    }
                });
        jade.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        autumnPresenter.updateJade();
                        jadeDisplay.setText(String.valueOf(autumnPresenter.getJade()));
                        jade.animate().alpha(0).setDuration(1000).withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                jade.setVisibility(View.GONE);
                            }
                        });

                    }
                });
    }

    /**
     * End the game to show the score board for this turn, set gameRunning to false and stop the
     * count-down timer.
     */
    public void endGame() {
        countDownTimer.cancel();
        autumnPresenter.setGameRunning(false);
        final Intent intent = new Intent(AutumnActivity.this, AutumnOverActivity.class);
        Bundle b = new Bundle();
        b.putString("score", String.valueOf(autumnPresenter.getScore()));
        b.putString("maxStreak", autumnPresenter.getMaxStreak());
        b.putString("jade", String.valueOf(autumnPresenter.getJade()));
        intent.putExtras(b);
        intent.putExtra("user", autumnPresenter.getUser());
        intent.putExtra("summerScore",
                getIntent().getSerializableExtra("summerScore"));
        intent.putExtra("summerJade",
                getIntent().getSerializableExtra("summerJade"));
        intent.putExtra("autumnScore", autumnPresenter.getScore());
        intent.putExtra("autumnJade", autumnPresenter.getJade());
        String finalScore = String.valueOf(5);
        failureDisplay.setText(finalScore);

        Runnable r =
                new Runnable() {
                    @Override
                    public void run() {
                        startActivity(intent);
                        finish();
                    }
                };

        Handler h = new Handler();
        h.postDelayed(r, 1000);
    }

    /**
     * Set a new command with new color, shape and word.
     */
    private void setCommandView() {
        Command command = autumnPresenter.newCommand();
        commandDisplay.setText(command.getWord());
        commandDisplay.setTextColor(command.getColor());
        if (command.getShape() == 0) frame.setBackgroundResource(R.drawable.round);
        else frame.setBackgroundResource(R.drawable.square);
    }

    /**
     * Count down timer, setup initial time based on the record in manager
     */
    private void setupCountDownTimer() {
        countDownTimer =
                new CountDownTimer(5000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        String timeText = autumnPresenter.getTime() + "\"";
                        timeDisplay.setText(timeText);
                    }

                    public void onFinish() {
                        autumnPresenter.timeUp();
                        if (autumnPresenter.getGameRunning()) {
                            newRound();
                        }
                    }
                }.start();
    }

    /**
     * Pop a jade on the screen.
     */
    public void popJade() {
        jade.setVisibility(View.VISIBLE);
        jade.animate().alpha(1).setDuration(1000);
    }

    /**
     * Start a new round by restarting the count down timer, displaying the data and setting a new
     * command.
     */
    void newRound() {
        countDownTimer.cancel();
        scoreDisplay.setText(String.valueOf(autumnPresenter.getScore()));
        failureDisplay.setText(autumnPresenter.getFailure());
        streakDisplay.setText(autumnPresenter.getStreak());
        maxStreakDisplay.setText(autumnPresenter.getMaxStreak());
        setCommandView();
        countDownTimer.start();
    }

    /**
     * Save current user to the corresponding file in the database.
     *
     * @param fileName the name of the file.
     */
    public void saveUserToFile(String fileName) {
        try {
            ObjectOutputStream outputStream =
                    new ObjectOutputStream(openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStream.writeObject(autumnPresenter.getUser());

            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
