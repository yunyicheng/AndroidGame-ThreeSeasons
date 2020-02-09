package com.example.threeseasons.maingame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.threeseasons.R;
import com.example.threeseasons.data.User;

public class NoEndingActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_ending);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.try_again:
                Intent BackIntent = new Intent(getApplicationContext(), NewGameActivity.class);
                BackIntent.putExtra("user", (User) getIntent().getSerializableExtra("user"));
                startActivity(BackIntent);
                break;
        }
    }
}
