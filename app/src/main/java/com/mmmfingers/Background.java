package com.mmmfingers;

import android.graphics.Bitmap;

/**
 * @author Ori Sinvani.
 * @version version 1.50
 * MMM Fingers Project
 * Modi-in, YACHAD high-school.
 *
 * *************************************************************
 * this class extends the AnimatedSpritesObject class
 * and adds:
 * scrolling background for the game
 * *************************************************************
 */

public class Background extends AnimatedSpritesObject {


    public Background(Bitmap imageThatHasSprites, int numberOfSprites, int rowLength) {
        super(imageThatHasSprites, numberOfSprites, rowLength);
    }

    @Override
    public void update() {
    }
}//end class
