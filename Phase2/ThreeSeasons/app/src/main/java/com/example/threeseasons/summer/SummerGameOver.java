package com.example.threeseasons.summer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.threeseasons.autumn.AutumnActivity;
import com.example.threeseasons.R;
import com.example.threeseasons.autumn.AutumnRuleActivity;

/**
 * The game over activity
 */
public class SummerGameOver extends AppCompatActivity {

    /**
     * TextView for displaying score
     */
    TextView scoreView;

    /**
     * TextView for displaying duration
     */
    TextView durationView;

    /**
     * TextView for displaying distance
     */
    TextView distanceView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_game_over);

        scoreView = findViewById(R.id.score_game1_sta);
        durationView = findViewById(R.id.duration_sta);
        distanceView = findViewById(R.id.distance_sta);

        // Get the statistics for displaying
        Bundle statistics = getIntent().getExtras();
        int score = statistics.getInt("score");
        long time = statistics.getLong("time");
        int distance = statistics.getInt("distance");

        scoreView.setText("" + score);
        durationView.setText("" + time + "''");
        distanceView.setText("" + distance);

        Button button1 = findViewById(R.id.next_level);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(SummerGameOver.this, AutumnRuleActivity.class);
                intent.putExtra("user", getIntent().getSerializableExtra("user"));
                intent.putExtra("summerScore",
                        getIntent().getSerializableExtra("summerScore"));
                intent.putExtra("summerJade",
                        getIntent().getSerializableExtra("summerJade"));
                startActivity(intent);
                finish();
            }
        });
    }
}
