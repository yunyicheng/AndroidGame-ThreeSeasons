package com.example.threeseasons.summer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.threeseasons.R;

/**
 * The customization activity for choose which mode to play
 */
public class CustomizationActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customization);
    }

    /**
     * Choose the character mode to play with and start the game
     */
    public void onClick(View v) {
        String character;

        switch (v.getId()) {
            case R.id.man:
                character = "MAN";
                break;

            case R.id.shadow:
                character = "SHADOW";
                break;

            default:
                character = "MAN";
        }

        Intent intent = new Intent(getApplicationContext(), SummerActivity.class);
        Bundle characterBundle = new Bundle();
        characterBundle.putString("mode", character);
        intent.putExtras(characterBundle);
        intent.putExtra("user", getIntent().getSerializableExtra("user"));
        startActivity(intent);
        finish();
    }
}
