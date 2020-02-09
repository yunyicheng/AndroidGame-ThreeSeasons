package com.example.threeseasons.autumn;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.threeseasons.R;
import com.example.threeseasons.data.User;

public class AutumnRuleActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * User object that stores the user information.
     */
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autumn_rule);
        user = (User) getIntent().getSerializableExtra("user");
    }

    @Override
    public void onClick(View v) {
        Intent autumnIntent = new Intent(getApplicationContext(), AutumnActivity.class);
        autumnIntent.putExtra("user", user);
        autumnIntent.putExtra("summerScore",
                getIntent().getSerializableExtra("summerScore"));
        autumnIntent.putExtra("summerJade",
                getIntent().getSerializableExtra("summerJade"));
        startActivity(autumnIntent);
        finish();

    }
}
