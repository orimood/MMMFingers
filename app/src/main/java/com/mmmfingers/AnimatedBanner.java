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
public class AnimatedBanner extends AnimatedSpritesObject {
    public AnimatedBanner(Bitmap imageThatHasSprites, int numberOfSprites, int rowLength) {
        super(imageThatHasSprites, numberOfSprites, rowLength);

    }

    @Override
    public void update() {
        // here is our code
        if (animation.getPlayedOnce()) {
            animation.update();
        }
    }

}
