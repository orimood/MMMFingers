package com.mmmfingers;

import android.graphics.Bitmap;

import java.util.Random;


class Obstacle2 extends AnimatedSpritesObject {

    public Obstacle2(Bitmap imageThatHasSprites, int numberOfSprites, int rowLength) {
        super(imageThatHasSprites, numberOfSprites, rowLength);
    }



    // TODO: 11/10/2021 move to the animate
    /**
    public void update() {
        y = y + 5;
        if (y > GamePanel.HEIGHT) {
            y = - width;
            Random rnd = new Random();
            x = rnd.nextInt(1080 - (2 * width)) + width;
        }
        animation.update();
        rotateImage(3);
    }
*/
    @Override
    protected void doAnimate() {
        rotateImage(3);
        y = y + 10;
        if (y > GamePanel.HEIGHT) {
            y = - width;
            Random rnd = new Random();
            x = rnd.nextInt(1080 - (2 * width)) + width;
        }
    }
}
