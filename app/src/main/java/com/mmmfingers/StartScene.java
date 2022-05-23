package com.mmmfingers;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;

public class StartScene implements Scene {


    public static float scaleFactorXMul = 1.0f;
    public static float scaleFactorYMul = 1.0f;
    public static String SCENE_NAME = "START_SCENE";

    private Background background;

    private  GamePanel gamePanel;
    private Button startGame;

    public StartScene(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        startGame = new Button((BitmapFactory.decodeResource(gamePanel.getResources(), R.drawable.start_btn)), 1,1);
        startGame.setX(Constants.ORIGINAL_SCREEN_WIDTH/2 - startGame.getWidth()/2);
        startGame.setY(Constants.ORIGINAL_SCREEN_HEIGHT/2 - startGame.getHeight());
        startGame.setButtonTouchListener(new Button.OnButtonTouchListener() {
            @Override
            public void onTouchDown() {
                gamePanel.addScene(GameScene.SCENE_NAME);            }

            @Override
            public void onTouchUp() {
                gamePanel.addScene(GameScene.SCENE_NAME);

            }
        });


    }



    @Override
    public void update(){
        startGame.update();
    }

    @Override
    public void draw(Canvas canvas){
//        background.draw(canvas);

        startGame.draw(canvas);
    }

    @Override
    public void terminate(){

    }

    @Override
    public void receiveTouch(MotionEvent touch){

        int action = touch.getActionMasked();
        int index = touch.getActionIndex();
        int xPosition, yPosition;


        // adjust x and y the screen resolution by dividing by the factors
        xPosition = (int) (touch.getX(index) / scaleFactorXMul);
        yPosition = (int) (touch.getY(index) / scaleFactorYMul);
        gamePanel.addScene(GameScene.SCENE_NAME);

    }

    @Override
    public void resetAll(){

    }

}