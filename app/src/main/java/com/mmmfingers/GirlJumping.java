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

    private WalkingDirection girlWalkingDirection;

    public GirlJumping(Bitmap imageThatHasSprites, int numberOfSprites, int rowLength) {
        super(imageThatHasSprites, numberOfSprites, rowLength);
    }

    public WalkingDirection getGirlWalkingDirection() {
        return girlWalkingDirection;
    }

    public void setGirlWalkingDirection(WalkingDirection girlWalkingDirection) {
        this.girlWalkingDirection = girlWalkingDirection;
    }
}
