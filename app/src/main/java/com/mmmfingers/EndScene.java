package com.mmmfingers;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import com.mmmfingers.sceneBased.Scene;

import java.util.ArrayList;
import java.util.List;

public class EndScene implements Scene {

    private Background background;

    private final GameLogic gameLogic;

    public EndScene(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
    }




    public void initialize(View view){
        background = new Background(BitmapFactory.decodeResource(view.getResources(), R.drawable.endscreen),
                1, 1);

    }

    public void update(){

    }

    public void draw(Canvas canvas){

    }

    public void terminate(){

    }

    public void receiveTouch(int action, int xPosition, int yPosition){

    }

    public void resetAll(){

    }
}