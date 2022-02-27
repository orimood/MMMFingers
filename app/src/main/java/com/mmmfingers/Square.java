package com.mmmfingers;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import java.util.Random;


class Square extends AnimatedSpritesObject {



    private Random rnd = new Random();
    private int distance;

    public Square(Bitmap imageThatHasSprites, int numberOfSprites, int rowLength) {
        super(imageThatHasSprites, numberOfSprites, rowLength);
        this.distance = distance;

        this.x = getNextX();
        this.y = 0;
    }

    @Override
    public void update() {
        if (y > GamePanel.getHEIGHT()) {
            y = -distance -200;
            x = getNextX();
        }

        y = y + Constants.OBSTACLE_DROPPING_RATE;


        // update the animation
        animation.update();

    }


    private int getNextX() {
        return rnd.nextInt(400);
    }
/*

    @Override
    protected void doAnimate() {
        rotateImage(rotation);
    }
*/



}
