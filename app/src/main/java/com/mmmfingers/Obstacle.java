package com.mmmfingers;

import android.graphics.Bitmap;

import java.util.Random;


class Obstacle extends AnimatedSpritesObject {

    private Random rnd = new Random();
    private int distance;

    private boolean moveX;
    private int currentMoveX = -10;

    public Obstacle(Bitmap imageThatHasSprites, int numberOfSprites, int rowLength, int initialY, int distance, boolean moveX) {
        super(imageThatHasSprites, numberOfSprites, rowLength);

        this.distance = distance;
        this.moveX = moveX;

        this.x = getNextX();
        this.y = initialY;
    }

    @Override
    public void update() {
        if (y > GamePanel.getHEIGHT()) {
            y = -distance;
            x = getNextX();
        }

        y = y + Constants.OBSTACLE_DROPPING_RATE;

        if (moveX) {
            if (getX() < -30) {
                currentMoveX = -currentMoveX;
            }

            if (getX() > 700) {
                currentMoveX = -currentMoveX;
            }

            setX(getX() + currentMoveX);
        }

        // update the animation
        animation.update();
    }//end update

    private int getNextX() {
        return rnd.nextInt(700);
    }

}
