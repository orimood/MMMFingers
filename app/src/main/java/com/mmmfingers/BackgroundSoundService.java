package com.mmmfingers;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;

/**
 * @author Ori Sinvani.
 * @version version 1.50
 * MMM Fingers Project
 * Modi-in, YACHAD high-school.
 *
 * *****************************************************************
 * <p>
 * Class description:
 * The BackgroundSoundService class will help
 * us to play music
 * android tool service
 * *************************************************************
 */

public class BackgroundSoundService extends Service {
    //what plays the music
    MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    // when media player is created
    @Override
    public void onCreate() {
        super.onCreate();

        //go to the shared preferences for the app (to check if mute switch is enabled)
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean isMute = sharedPref.getBoolean("mute", false);

        //if not mute, start playing music in loop
        if (!isMute) {
            mediaPlayer = MediaPlayer.create(this, R.raw.sound);
            mediaPlayer.setLooping(true); // Set looping
            mediaPlayer.setVolume(700, 700);
        }
    }


    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
        return startId;
    }

    //stops the service
    @Override
    public void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    @Override
    public void onLowMemory() {
    }
}