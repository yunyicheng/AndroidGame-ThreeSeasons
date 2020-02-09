package com.example.threeseasons.summer;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.threeseasons.R;
import com.example.threeseasons.data.User;

public class SummerRuleActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * User object that stores the user information.
     */
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summer_rule);
        user = (User) getIntent().getSerializableExtra("user");
    }

    @Override
    public void onClick(View v) {
        Intent customizationIntent = new Intent(getApplicationContext(), CustomizationActivity.class);
        customizationIntent.putExtra("user", user);
        startActivity(customizationIntent);

    }
}
