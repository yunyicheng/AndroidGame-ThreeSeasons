package com.example.threeseasons.maingame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.threeseasons.R;
import com.example.threeseasons.data.User;

public class HiddenEndingActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden_ending);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_menu:
                int jadesNumber = (int) getIntent().getSerializableExtra("jades");
                User user = (User) getIntent().getSerializableExtra("user");
                Intent backIntent = new Intent(getApplicationContext(), NewGameActivity.class);
                backIntent.putExtra("user", user);
                backIntent.putExtra("jades", jadesNumber);
                startActivity(backIntent);
                break;
        }
    }
}
