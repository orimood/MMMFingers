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
public class GirlJumping extends AnimatedSpritesObject {

    // start time pointer, for many uses
    private long startTime;

    private WalkingDirection girlWalkingDirection;

    public GirlJumping(Bitmap imageThatHasSprites, int numberOfSprites, int rowLength) {
        super(imageThatHasSprites, numberOfSprites, rowLength);

        //Now we initiate the timer so we can use in the update method
        startTime = System.nanoTime();

    }

    @Override
    public void update() {
        animation.update();
    }

    public WalkingDirection getGirlWalkingDirection() {
        return girlWalkingDirection;
    }

    public void setGirlWalkingDirection(WalkingDirection girlWalkingDirection) {
        this.girlWalkingDirection = girlWalkingDirection;
    }
}
