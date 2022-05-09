package com.mmmfingers;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.mmmfingers.sceneBased.Scene;

import java.util.ArrayList;
import java.util.List;

public class EndScene implements Scene {

    private Background background;
    private Button gotoGame;
    private final GamePanel gamePanel;

    public EndScene(GamePanel gamePanel) {
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
        canvas.drawColor(Color.RED);
    }

    @Override
    public void terminate(){

    }

    @Override
    public void receiveTouch(int action, int xPosition, int yPosition){
//        gamePanel.goToStartScreen();
    }

    @Override
    public void resetAll(){

    }

    @Override
    public void activate() {
        gamePanel.setVisibility(View.INVISIBLE);
        gotoGame.setVisibility(View.VISIBLE);
        gotoGame.bringToFront();
        gamePanel.invalidate();
        gamePanel.getParentView().invalidate();
        gamePanel.getParentView().bringToFront();
    }

    @Override
    public void deactivate() {
        gotoGame.setVisibility(View.INVISIBLE);
        gamePanel.invalidate();
        gamePanel.getParentView().invalidate();
    }
}