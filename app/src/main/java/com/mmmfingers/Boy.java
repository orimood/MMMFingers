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

public class Boy extends AnimatedSpritesObject {
    public Boy(Bitmap imageThatHasSprites, int numberOfSprites, int rowLength) {
        super(imageThatHasSprites, numberOfSprites, rowLength);

    }

    @Override
    public void update() {
        // here is our code
        animation.update();

    }
}
