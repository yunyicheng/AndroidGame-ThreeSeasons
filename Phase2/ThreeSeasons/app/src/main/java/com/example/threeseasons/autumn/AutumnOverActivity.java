package com.example.threeseasons.autumn;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.threeseasons.R;
import com.example.threeseasons.data.User;
import com.example.threeseasons.winter.WinterRuleActivity;


public class AutumnOverActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * TextView for the score.
     */
    TextView score;
    /**
     * TextView for the max number of streaks.
     */
    TextView maxStreak;
    /**
     * TextView for the number of jades.
     */
    TextView jade;
    /**
     * Current user
     */
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autumn_gameover);

        score = findViewById(R.id.finalscore_sta);
        maxStreak = findViewById(R.id.finalMaxStreak_sta);
        jade = findViewById(R.id.numJade_sta);

        String scoreSta = (String) getIntent().getSerializableExtra("score");
        String maxStreakSta = (String) getIntent().getSerializableExtra("maxStreak");
        System.out.println(maxStreakSta);
        String jadeSta = (String) getIntent().getSerializableExtra("jade");
        user = (User) getIntent().getSerializableExtra("user");

        score.setText(scoreSta);
        maxStreak.setText(maxStreakSta);
        jade.setText(jadeSta);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), WinterRuleActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("summerScore",
                getIntent().getSerializableExtra("summerScore"));
        intent.putExtra("summerJade",
                getIntent().getSerializableExtra("summerJade"));
        intent.putExtra("autumnScore",
                getIntent().getSerializableExtra("autumnScore"));
        intent.putExtra("autumnJade",
                getIntent().getSerializableExtra("autumnJade"));
        startActivity(intent);
    }
}
