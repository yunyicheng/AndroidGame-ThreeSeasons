package com.example.threeseasons.maingame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.threeseasons.R;
import com.example.threeseasons.data.User;

public class NewSpringAskActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * The number of jades that the player needs to have to trigger the hidden ending.
     */
    private static final int TRIGGER_HIDDEN = 16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_spring_ask);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.next_ask:
                int jadesNumber = (int) getIntent().getSerializableExtra("jades");
                User user = (User) getIntent().getSerializableExtra("user");
                if (jadesNumber < TRIGGER_HIDDEN) {        // not trigger the hidden ending
                    Intent backIntent = new Intent(getApplicationContext(), NewGameActivity.class);
                    backIntent.putExtra("user", user);
                    backIntent.putExtra("jades", jadesNumber);
                    startActivity(backIntent);
                    finish();
                } else {
                    Intent hiddenIntent = new Intent(getApplicationContext(), HiddenEndingActivity.class);
                    hiddenIntent.putExtra("user", user);
                    hiddenIntent.putExtra("jades", jadesNumber);
                    startActivity(hiddenIntent);
                    finish();
                }
                break;
        }
    }

}