package com.example.threeseasons.winter;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.threeseasons.R;
import com.example.threeseasons.data.User;

/**
 * The game rule activity called before starting game Winter.
 */
public class WinterRuleActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * User object that stores the user information.
     */
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winter_rule);
        user = (User) getIntent().getSerializableExtra("user");
    }

    @Override
    public void onClick(View v) {
        Intent winterIntent = new Intent(getApplicationContext(), WinterActivity.class);
        winterIntent.putExtra("user", user);
        winterIntent.putExtra("summerScore",
                getIntent().getSerializableExtra("summerScore"));
        winterIntent.putExtra("summerJade",
                getIntent().getSerializableExtra("summerJade"));
        winterIntent.putExtra("autumnScore",
                getIntent().getSerializableExtra("autumnScore"));
        winterIntent.putExtra("autumnJade",
                getIntent().getSerializableExtra("autumnJade"));
        startActivity(winterIntent);
    }
}
