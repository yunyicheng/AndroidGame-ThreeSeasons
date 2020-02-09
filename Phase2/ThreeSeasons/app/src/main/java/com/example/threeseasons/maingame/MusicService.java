package com.example.threeseasons.maingame;

import android.app.Service;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.content.Intent;
import android.widget.Toast;

import com.example.threeseasons.R;

public class MusicService extends Service implements MediaPlayer.OnErrorListener {

    /**
     * The programing interface that the client use to interact with MusicService.
     */
    private final IBinder musicBinder = new ServiceBinder();
    /**
     * The MediaPlayer we use to play music in android framework.
     */
    private MediaPlayer mediaPlayer;
    /**
     * The position of time where the background music this currently at.
     */
    private int musicPosition;

    private boolean isPlaying;

    /**
     * Define inner class ServiceBinder.
     */
    class ServiceBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return musicBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        isPlaying = true;
        mediaPlayer = MediaPlayer.create(this, R.raw.entry_music);
        mediaPlayer.setOnErrorListener(this);

        if (mediaPlayer != null) {
            mediaPlayer.setLooping(true); // play background music continuously.
            mediaPlayer.setVolume(100, 100); // set left and right volume.


            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {

                public boolean onError(MediaPlayer mp, int what, int
                        extra) {
                    return true;
                }
            });
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
        return START_NOT_STICKY;
    }

    /**
     * Pause the music and remember where it pauses for possible resume later.
     */
    public void pauseMusic() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                musicPosition = mediaPlayer.getCurrentPosition();
            }
        }
        isPlaying = false;
    }

    /**
     * Resume music from where it paused.
     */
    public void resumeMusic() {
        if (mediaPlayer != null) {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.seekTo(musicPosition);
                mediaPlayer.start();
            }
        }
        isPlaying = true;
    }

    /**
     * When activity is finished, shut down the service, stop playing and release memory.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
            try {
                mediaPlayer.stop();
                mediaPlayer.release();
            } finally {
                mediaPlayer = null;
            }

    }

    /**
     * Raise error when music player failed.
     */
    public boolean onError(MediaPlayer mp, int what, int extra) {

        Toast.makeText(this, "Music player failed", Toast.LENGTH_SHORT).show();
        if (mediaPlayer != null) {
            try {
                mediaPlayer.stop();
                mediaPlayer.release();
            } finally {
                mediaPlayer = null;
            }
        }
        return false;
    }

    /**
     * Check if background music is playing.
     *
     * @return true if background music is playing.
     */
    public boolean isPlaying() {
        return isPlaying;
    }
}