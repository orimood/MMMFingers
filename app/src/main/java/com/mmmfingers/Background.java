package com.mmmfingers;

import android.graphics.Bitmap;

/**
 * Study Android:
 * MODI'IN, YACHAD high-school.
 *
 * @author Created by Eli Guriel on 20,September,2018.
 * @author Ori Sinvani.
 * @version version 2.00
 * @since version 2.00
 */

public class Background extends AnimatedSpritesObject {


    public Background(Bitmap imageThatHasSprites, int numberOfSprites, int rowLength) {
        super(imageThatHasSprites, numberOfSprites, rowLength);
    }

    @Override
    public void update() {
        y = y + 10;
        if (y >= height)
            y = -height;
    }
}//end class
