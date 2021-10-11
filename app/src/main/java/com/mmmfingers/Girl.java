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

public class Girl extends AnimatedSpritesObject {

    // start time pointer, for many uses
    private long startTime;

    private WalkingDirection girlWalkingDirection;

    public Girl(Bitmap imageThatHasSprites, int numberOfSprites, int rowLength) {
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
            if (girlWalkingDirection == WalkingDirection.RIGHT) {
                x = x + 10;
                if (x > GamePanel.WIDTH + width) {
                    x = -width;
                }
            }
            else
            {
                x = x - 10;
                if (x < -width) {
                    x = GamePanel.WIDTH + width;
                }
            }
            // set back start time
            startTime = System.nanoTime();
        }
        // update the animation
        animation.update();
    }

    public WalkingDirection getGirlWalkingDirection() {
        return girlWalkingDirection;
    }

    public void setGirlWalkingDirection(WalkingDirection girlWalkingDirection) {
        this.girlWalkingDirection = girlWalkingDirection;
    }
}
