package com.mmmfingers;

import android.graphics.Bitmap;


class Obstacle2 extends AnimatedSpritesObject {

    public Obstacle2(Bitmap imageThatHasSprites, int numberOfSprites, int rowLength) {
        super(imageThatHasSprites, numberOfSprites, rowLength);
    }


    @Override
    protected void doAnimate() {
        rotateImage(3);
    }
}
