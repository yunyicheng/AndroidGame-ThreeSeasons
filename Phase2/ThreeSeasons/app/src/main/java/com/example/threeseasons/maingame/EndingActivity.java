package com.example.threeseasons.maingame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.threeseasons.R;
import com.example.threeseasons.data.User;

public class EndingActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ending);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.say_hello:
                Intent helloIntent = new Intent(getApplicationContext(), NewSpringHelloActivity.class);
                helloIntent.putExtra("user", (User) getIntent().getSerializableExtra("user"));
                helloIntent.putExtra("jades", (int) getIntent().getSerializableExtra("jades"));
                startActivity(helloIntent);
                break;
            case R.id.ask_spring:
                Intent askIntent = new Intent(getApplicationContext(), NewSpringAskActivity.class);
                askIntent.putExtra("user", (User) getIntent().getSerializableExtra("user"));
                askIntent.putExtra("jades", (int) getIntent().getSerializableExtra("jades"));
                startActivity(askIntent);
                break;
        }
    }
}
