package com.mmmfingers;

import android.graphics.Bitmap;


class Obstacle extends AnimatedSpritesObject {

    public Obstacle(Bitmap imageThatHasSprites, int numberOfSprites, int rowLength) {
        super(imageThatHasSprites, numberOfSprites, rowLength);
    }

    @Override
    protected void doAnimate() {
        rotateImage(3);
    }
}
