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

    private boolean isRotate;

    public Boy(Bitmap imageThatHasSprites, int numberOfSprites, int rowLength) {
        super(imageThatHasSprites, numberOfSprites, rowLength);
    }

    public void rotate() {
        if (isRotate) {
            return;
        }
        setRotationAngle(0);
        isRotate = true;
    }

    @Override
    protected void doAnimate() {
        if (isRotate) {
            rotateImage(9);
        }

        if (getRotationAngle() == 0) {
            isRotate = false;
        }
    }
}
