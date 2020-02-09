package com.example.threeseasons.maingame;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.example.threeseasons.R;

import java.util.Locale;

/** */
public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Switch for turning on and off the background music.
     */
    private Switch backgroundMusic;
    /**
     * Switch for choosing languages.
     */
    private Switch language;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("on create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        backgroundMusic = findViewById(R.id.bgm_control);
        language = findViewById(R.id.language_control);
        loadLanguage();
        setMusicSwitch();

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bgm_control:
                if (backgroundMusic.isChecked()){
                    MainActivity.getMusicService().resumeMusic();
                } else{
                    MainActivity.getMusicService().pauseMusic();
                }
                break;
            case R.id.language_control: //Create separate string.xml for each language
                if (language.isChecked()){
                    language.setChecked(true);
                    changeToLanguage("en");
                    recreate();
                } else{
                    language.setChecked(false);
                    changeToLanguage("zh");
                    recreate();
                }
                break;
            case R.id.back_to_menu:
                Intent backIntent = new Intent(getApplicationContext(), MainMenuActivity.class);
                startActivity(backIntent);
                break;
        }
    }

    /**
     * Set displaying language according to the preference.
     */
    public void loadLanguage(){
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String languagePref = prefs.getString("My_Lang", "");
        changeToLanguage(languagePref);
        if (languagePref.equals("zh")){
            language.setChecked(false);
        } else {
            language.setChecked(true);
        }
    }

    /**
     * Change language preference.
     *
     * @param language the language requested by the player.
     */
    private void changeToLanguage(String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", language);
        editor.apply();
    }

    /**
     * Set Switch backgroundMusic's state.
     */
    void setMusicSwitch() {
        if (MainActivity.getMusicService().isPlaying()) {
            backgroundMusic.setChecked(true);
        } else {
            backgroundMusic.setChecked(false);
        }
    }

}
