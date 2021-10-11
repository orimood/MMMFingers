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

    /**
     * So to create an animated object
     * first we pass the bit map image (the image that has the sprites) - imageThatHasSprites
     * the dimensions of each sprite we get from the bitmap image
     * numberOfSprites -  number Of Sprites in the all image
     * rowLength - number of sprites in each row
     * so now we can cut each sprite from the image and save it in an array of bitmap (spritesArray)
     * and pass this array to the animation class
     *
     * @param imageThatHasSprites
     * @param numberOfSprites
     * @param rowLength
     */
    public Background(Bitmap imageThatHasSprites, int numberOfSprites, int rowLength) {
        super(imageThatHasSprites, numberOfSprites, rowLength);
    }
}//end class
