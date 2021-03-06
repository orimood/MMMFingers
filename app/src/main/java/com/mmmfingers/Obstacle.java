package com.mmmfingers;

import android.graphics.Bitmap;

import java.util.Random;
/**
 * @author Ori Sinvani.
 * @version version 1.50
 * MMM Fingers Project
 * Modi-in, YACHAD high-school.
 *
 * *************************************************************
 * this class is the main class of the game obstacles
 * extending animatedspritesobject so we can get methods
 * *************************************************************
 */

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

        // fall all the time
        if (y > GamePanel.getHEIGHT()) {
            y = -distance;
            x = getNextX();
        }

        y = y + Constants.OBSTACLE_DROPPING_RATE;

        //if movex is implemented, so only for the squares
        if (moveX) {

            //if to close to border, switch directions
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
