package com.mmmfingers;

/**
 * @author Ori Sinvani.
 * @version version 2.00
 * @since version 2.00
 * Study Android,
 * Modi'in, Yachad high-school.
 * <p>
 * This is the Main Activity of our game
 */


import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
public class MainActivity extends AppCompatActivity {

    private static int WIDTH;
    private static int HEIGHT;
    MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();

    GamePanel gamePanel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //in this lesson  we will set the content view and we will create a new class
        setContentView(R.layout.activity_main);

        //turn title off
        // requestWindowFeature(Window.FEATURE_NO_TITLE);

        // set screen landscape view
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);


        //set to full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // get device screen size for game panel use
        Display display = getWindowManager().getDefaultDisplay();
        // display size in pixels
        Point size = new Point();
        display.getSize(size);
        WIDTH = size.x;
        HEIGHT = size.y;

        // hideSystemUI();

        gamePanel = new GamePanel(this, WIDTH, HEIGHT);
        setContentView(gamePanel);

        IntentFilter filter1 = new IntentFilter((Intent.ACTION_BATTERY_LOW));
        registerReceiver(myBroadcastReceiver, filter1);

    }

    @Override
    public void onStop(){
        super.onStop();
        unregisterReceiver(myBroadcastReceiver);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // switch for selecting the actions of the right option selected
        switch (item.getItemId()) {
            case R.id.item1:
                // Set to Opening Scene
                gamePanel.addScene(GameScene.SCENE_NAME);

                return true;
            case R.id.item2:
                // Set to Settings PopUp


                return true;
            case R.id.item3:
                // Exit Game
                // here we implement alertDialog
                // if your main activity has other name write it in place of MainActivity
                AlertDialog.Builder builder  = new AlertDialog.Builder(MainActivity.this);

                // set the message of the alert dialog
                builder.setMessage("Are you sure you want to exit ?")
                        // set the yes button
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // finish and exit the application
                                finish();
                            }
                            // set the no button
                            // do nothing
                        }).setNegativeButton("Cancel", null);
                // create the alert dialog
                AlertDialog alertDialog = builder.create();

                // show it, start the alert dialog
                alertDialog.show();




                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * this methods used for full screen enable and disable
     */
    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        ((View) decorView).setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
    // except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

}
