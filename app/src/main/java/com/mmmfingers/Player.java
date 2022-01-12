package com.mmmfingers;

import android.graphics.Bitmap;

/**
 * Study Android:
 * MODI'IN, YACHAD high-school.
 *
 * @author Ori Sinvani.
 * @version version 2.00
 * @since version 2.00
 */

public class Player extends AnimatedSpritesObject {

    private WalkingDirection girlWalkingDirection;

    public Player(Bitmap imageThatHasSprites, int numberOfSprites, int rowLength) {
        super(imageThatHasSprites, numberOfSprites, rowLength);
    }
    public int getRadius(){
        return this.getWidth() / 2;
    }
    public int getCenterX(){
        return this.getX() + (this.getWidth() / 2);
    }
    public int getCenterY(){
        return this.getY() + (this.getHeight() / 2);
    }

/*
    @Override
    protected void doAnimate() {
        // here is our code
        if (girlWalkingDirection == WalkingDirection.RIGHT) {
            x = x + 10;
            if (x > GamePanel.WIDTH + width) {
                x = -width;
            }
        } else {
            x = x - 10;
            if (x < -width) {
                x = GamePanel.WIDTH + width;
            }
        }
    }*/

    public WalkingDirection getGirlWalkingDirection() {
        return girlWalkingDirection;
    }

    public void setGirlWalkingDirection(WalkingDirection girlWalkingDirection) {
        this.girlWalkingDirection = girlWalkingDirection;
    }
}
