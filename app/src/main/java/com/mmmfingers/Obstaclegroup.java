package com.mmmfingers;


import android.graphics.Bitmap;

import java.util.Random;


class Obstaclegroup extends AnimatedSpritesObject {

    private Random rnd = new Random();
    private int rotateAngle;
    private int distance;
    private Square obst1;
    private Teeth obst2;
    private Square obst3;



    public Obstaclegroup(Bitmap imageThatHasSprites, int numberOfSprites, int rowLength, int initialY, int rotateAngle, int distance) {
        super(imageThatHasSprites, numberOfSprites, rowLength);
        this.rotateAngle = rotateAngle;
        this.distance = distance;

        this.x = getNextX();
        this.y = initialY;
    }

    private int getNextX() {
        return rnd.nextInt(GamePanel.getWIDTH()) - Math.max(width, height) / 2;
    }
/*
    @Override
    protected void doAnimate() {
        //rotateImage(rotateAngle);
        y = y + 10;
        if (y > GamePanel.HEIGHT) {
            y = -distance;
            x = getNextX();
        }

    }*/
}