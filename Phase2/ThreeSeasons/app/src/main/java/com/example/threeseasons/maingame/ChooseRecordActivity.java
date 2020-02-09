package com.example.threeseasons.maingame;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.threeseasons.R;
import com.example.threeseasons.data.User;

public class ChooseRecordActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * User object that stores the user information.
     */
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooserecord);
        user = (User) getIntent().getSerializableExtra("user");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.threeSeasons:
                Intent threeSeasons = new Intent(getApplicationContext(), ScoreBoardActivity.class);
                threeSeasons.putExtra("user", user);
                threeSeasons.putExtra("scoreBoardType",
                        (String) getIntent().getSerializableExtra("scoreBoardType"));
                threeSeasons.putExtra("gameType", "Three Seasons");
                startActivity(threeSeasons);
                break;
            case R.id.summer:
                Intent summer = new Intent(getApplicationContext(), ScoreBoardActivity.class);
                summer.putExtra("user", user);
                summer.putExtra("scoreBoardType",
                        (String) getIntent().getSerializableExtra("scoreBoardType"));
                summer.putExtra("gameType", "Summer");
                startActivity(summer);
                break;
            case R.id.autumn:
                Intent autumn = new Intent(getApplicationContext(), ScoreBoardActivity.class);
                autumn.putExtra("user", user);
                autumn.putExtra("scoreBoardType",
                        (String) getIntent().getSerializableExtra("scoreBoardType"));
                autumn.putExtra("gameType", "Autumn");
                startActivity(autumn);
                break;
            case R.id.winter:
                Intent winter = new Intent(getApplicationContext(), ScoreBoardActivity.class);
                winter.putExtra("user", user);
                winter.putExtra("scoreBoardType",
                        (String) getIntent().getSerializableExtra("scoreBoardType"));
                winter.putExtra("gameType", "Winter");
                startActivity(winter);
                break;
        }
    }
}
