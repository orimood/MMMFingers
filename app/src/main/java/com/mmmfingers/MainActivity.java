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
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.mmmfingers.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private MyBroadcastReceiver broadcastReceiver = new MyBroadcastReceiver();

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get device screen size for game panel use
        Display display = getWindowManager().getDefaultDisplay();
        // display size in pixels
        Point size = new Point();
        display.getSize(size);

        Constants.SCREEN_WIDTH = size.x;
        Constants.SCREEN_HEIGHT = size.y;

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        appBarConfiguration = new AppBarConfiguration.Builder(R.id.StartFragment, R.id.GameFragment, R.id.EndFragment, R.id.SettingsFragment, R.id.ScoreFragment).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller,
                                             @NonNull NavDestination destination,
                                             @Nullable Bundle arguments) {
                if (destination.getId() == R.id.GameFragment) {
                    playBackgroundSound();
                    binding.toolbar.setVisibility(View.GONE);
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS |
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);
                } else {
                    stopBackgroundSound();
                    binding.toolbar.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // select the actions of the right option selected
        if (id == R.id.action_start) {
            // Go to start screen
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.StartFragment);
            return true;
        } else if (id == R.id.action_settings) {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.SettingsFragment);
            return true;
        } else if (id == R.id.action_score) {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.ScoreFragment);
            return true;
        } else if (id == R.id.action_exit) {
            // Exit Game
            // here we implement alertDialog
            // if your main activity has other name write it in place of MainActivity
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

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

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void playBackgroundSound() {
        Intent intent = new Intent(MainActivity.this, BackgroundSoundService.class);
        startService(intent);
    }

    public void stopBackgroundSound() {
        Intent intent = new Intent(MainActivity.this, BackgroundSoundService.class);
        stopService(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter filter1 = new IntentFilter(Intent.ACTION_BATTERY_LOW);
        registerReceiver(broadcastReceiver, filter1);
    }

    @Override
    protected void onStop() {
        unregisterReceiver(broadcastReceiver);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
