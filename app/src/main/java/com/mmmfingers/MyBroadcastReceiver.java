package com.mmmfingers;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
/**
 * @author Ori Sinvani.
 * @version version 1.50
 * MMM Fingers Project
 * Modi-in, YACHAD high-school.
 *
 * *************************************************************
 * this class is the fragment that hold our GameScene, which is where the game runs
 * *************************************************************
 */

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // if battery low show long toast message
        if(Intent.ACTION_BATTERY_LOW.equals(intent.getAction())){
            Toast.makeText(context, "Low Battery", Toast.LENGTH_LONG).show();
        }

    }
}
