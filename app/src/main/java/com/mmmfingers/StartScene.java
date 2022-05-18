package com.mmmfingers;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.mmmfingers.sceneBased.Button;
import com.mmmfingers.sceneBased.Scene;
import com.mmmfingers.sceneBased.SceneManager;

public class StartScene extends AppCompatActivity implements Scene {

    AppCompatActivity activity = new AppCompatActivity();

    private Background background;

    private  GamePanel gamePanel;
    private Button gotoGame;

    public StartScene(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        gotoGame = new Button((BitmapFactory.decodeResource(gamePanel.getResources(), R.drawable.start_btn)), 1,1);
        gotoGame.setX(Constants.ORIGINAL_SCREEN_WIDTH/2 - gotoGame.getWidth()/2);
        gotoGame.setY(Constants.ORIGINAL_SCREEN_HEIGHT/2 - gotoGame.getHeight());
        gotoGame.setButtonTouchListener(new Button.OnButtonTouchListener() {
            @Override
            public void onTouchDown() {

            }

            @Override
            public void onTouchUp() {
                gamePanel.addScene(GameScene.SCENE_NAME);

            }
        });


    }

    @Override
    public void initialize(View view){


    }

    @Override
    public void update(){
        gotoGame.update();
    }

    @Override
    public void draw(Canvas canvas){
//        background.draw(canvas);

        gotoGame.draw(canvas);
    }

    @Override
    public void terminate(){

    }

    @Override
    public void receiveTouch(int action, int xPosition, int yPosition){
        gamePanel.nextScene();
    }

    @Override
    public void resetAll(){

    }

    @Override
    public void activate() {
        gamePanel.getParentView().invalidate();
    }

    @Override
    public void deactivate() {
        gamePanel.getParentView().invalidate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = activity.getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // switch for selecting the actions of the right option selected
        switch (item.getItemId()) {
            case R.id.item1:
                // start the right activity
                return true;
            case R.id.item2:
                // start the right activity
                return true;
            case R.id.item3:
                // start the right activity
                return true;
        }
        return onOptionsItemSelected(item);
    }
}