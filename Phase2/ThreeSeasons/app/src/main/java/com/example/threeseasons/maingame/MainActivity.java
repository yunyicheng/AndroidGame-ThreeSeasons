package com.example.threeseasons.maingame;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.threeseasons.R;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * The background music service of this game.
     */
    private static MusicService musicService;
    /**
     * The implementation of ServiceConnection which monitors the connection to the music service.
     */
    private ServiceConnection serviceConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName name, IBinder binder) {
            musicService = ((MusicService.ServiceBinder) binder).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            musicService = null;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_entry);

        loadLanguage();
        doBindService();
        Intent music = new Intent();
        music.setClass(this, MusicService.class);
        startService(music);
    }

    @Override
    public void onClick(View v) {
        Intent menuIntent = new Intent(getApplicationContext(), MainMenuActivity.class);
        startActivity(menuIntent);
    }

    /**
     * Bind to music service.
     */
    void doBindService() {
        bindService(new Intent(this, MusicService.class),
                serviceConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * Return the music service of this game
     *
     * @return the music service
     */
    static MusicService getMusicService() {
        return musicService;
    }

    /**
     * Set displaying language according to the preference.
     */
    public void loadLanguage() {
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String languagePref = prefs.getString("My_Lang", "");
        changeToLanguage(languagePref);
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
}