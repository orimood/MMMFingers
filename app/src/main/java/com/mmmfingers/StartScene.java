package com.mmmfingers;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;

import com.mmmfingers.sceneBased.Scene;

public class StartScene implements Scene {

    private Background background;

    private final GamePanel gamePanel;
    private Button gotoGame;

    public StartScene(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        gotoGame = gamePanel.getParentView().findViewById(R.id.nextbutton);
    }

    @Override
    public void initialize(View view){
//        background = new Background(BitmapFactory.decodeResource(view.getResources(), R.drawable.endscreen),
//                1, 1);


    }

    @Override
    public void update(){

    }

    @Override
    public void draw(Canvas canvas){
//        background.draw(canvas);
        canvas.drawColor(Color.argb(50, 0, 102, 102));
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
        gotoGame.setVisibility(View.VISIBLE);
        gamePanel.getParentView().invalidate();
    }

    @Override
    public void deactivate() {
        gotoGame.setVisibility(View.INVISIBLE);
        gamePanel.getParentView().invalidate();
    }
}