package com.mmmfingers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class EndScene implements Scene {

    public static String SCENE_NAME = "END_SCENE";

    private Background background;
    private Button gotoGame;
    private final GamePanel gamePanel;

    public EndScene(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

//        gotoGame = gamePanel.getParentView().findViewById(R.id.nextbutton);
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
    public void receiveTouch(MotionEvent touch){
//        gamePanel.goToStartScreen();
    }

    @Override
    public void resetAll(){

    }

}