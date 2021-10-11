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

public class Troll extends AnimatedSpritesObject {

    // start time pointer, for many uses
    private long startTime;

    private WalkingDirection walkingDirection;

    public Troll(Bitmap imageThatHasSprites, int numberOfSprites, int rowLength) {
        super(imageThatHasSprites, numberOfSprites, rowLength);

        //Now we initiate the timer so we can use in the update method
        startTime = System.nanoTime();

    }

    @Override
    public void update() {
        // here is the timer of our player millis
        long elapsed = (System.nanoTime() - startTime) / 1000000;

        // Now when our timer gets past xxx millis we want the to do something
        if (elapsed > 100) {
            // here is our code
            if (walkingDirection == WalkingDirection.RIGHT) {
                x = x + 10;
                if (x > GamePanel.WIDTH + width) {
                    x = -width;
                }
            }
            if (walkingDirection == WalkingDirection.LEFT)
            {
                x = x - 10;
                if (x < -width) {
                    x = GamePanel.WIDTH + width;
                }
            }
            if (walkingDirection == WalkingDirection.DOWN)
            {
                y = y + 10;
                if (y >GamePanel.HEIGHT + height) {
                    y = -height;
                }
            }
            if (walkingDirection == WalkingDirection.UP)
            {
                y = y - 10;
                if (y < -height) {
                    y = GamePanel.HEIGHT + height;
                }
            }
            if (walkingDirection == WalkingDirection.UP_LEFT)
            {
                y = y - 8;
                x = x - 8;
                if (y < -height) {
                    y = GamePanel.HEIGHT + height;
                }
                if (x < -width) {
                    x = GamePanel.WIDTH + width;
                }
            }
            if (walkingDirection == WalkingDirection.UP_RIGHT)
            {
                y = y - 8;
                x = x + 8;
                if (y < -height) {
                    y = GamePanel.HEIGHT + height;
                }
                if (x > GamePanel.WIDTH + width) {
                    x = -width;
                }
            }
            if (walkingDirection == WalkingDirection.DOWN_LEFT)
            {
                y = y + 8;
                x = x - 8;
                if (y >GamePanel.HEIGHT + height) {
                    y = -height;
                }
                if (x < -width) {
                    x = GamePanel.WIDTH + width;
                }
            }
            if (walkingDirection == WalkingDirection.DOWN_RIGHT)
            {
                y = y + 8;
                x = x + 8;
                if (x > GamePanel.WIDTH + width) {
                    x = -width;
                }
               if (y >GamePanel.HEIGHT + height) {
                    y = -height;
                }
            }
            // set back start time
            startTime = System.nanoTime();
        }
        // update the animation
        animation.update();
    }

    public WalkingDirection getTrollWalkingDirection() {
        return walkingDirection;
    }

    public void setTrollWalkingDirection(WalkingDirection walkingDirection) {
        this.walkingDirection = walkingDirection;
    }
}
