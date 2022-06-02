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

}
