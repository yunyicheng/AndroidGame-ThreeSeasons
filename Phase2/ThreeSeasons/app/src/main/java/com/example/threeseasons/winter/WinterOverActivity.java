package com.example.threeseasons.winter;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.threeseasons.R;
import com.example.threeseasons.maingame.MainGameOver;

/**
 The activity called when game Winter is over.
 */
public class WinterOverActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.winter_gameover);

        setTexts();
    }

    /**
     * Set text for all the TextViews displaying on the screen.
     */
    void setTexts(){
        TextView score = findViewById(R.id.score_sta);
        TextView time = findViewById(R.id.finaltime2_sta);
        TextView moves = findViewById(R.id.numMove_sta);
        Bundle b = getIntent().getExtras();
        int s = b.getInt("Score");
        int t = b.getInt("Time");
        int m = b.getInt("Moves");

        String scoreText = "" + s;
        String timeText = "" + t + "''";
        String movesText = "" + m;
        score.setText(scoreText);
        time.setText(timeText);
        moves.setText(movesText);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), MainGameOver.class);
        intent.putExtra("user", getIntent().getSerializableExtra("user"));
        intent.putExtra("summerScore",
                getIntent().getSerializableExtra("summerScore"));
        intent.putExtra("summerJade",
                getIntent().getSerializableExtra("summerJade"));
        intent.putExtra("autumnScore",
                getIntent().getSerializableExtra("autumnScore"));
        intent.putExtra("autumnJade",
                getIntent().getSerializableExtra("autumnJade"));
        intent.putExtra("winterScore",
                getIntent().getSerializableExtra("winterScore"));
        intent.putExtra("winterJade",
                getIntent().getSerializableExtra("winterJade"));
        startActivity(intent);
    }
}
