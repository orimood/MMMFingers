package com.mmmfingers;

import android.graphics.Bitmap;


class Square extends AnimatedSpritesObject {


    private int rotation;

    public Square(Bitmap imageThatHasSprites, int numberOfSprites, int rowLength, int rotation) {
        super(imageThatHasSprites, numberOfSprites, rowLength);
        this.rotation = rotation;
    }

/*

    @Override
    protected void doAnimate() {
        rotateImage(rotation);
    }
*/



}
