package com.mmmfingers;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import java.util.Random;


class Square extends AnimatedSpritesObject {


    private int rotation;
    private Random rnd = new Random();
    private int distance;

    public Square(Bitmap imageThatHasSprites, int numberOfSprites, int rowLength, int rotation) {
        super(imageThatHasSprites, numberOfSprites, rowLength);
        this.rotation = rotation;
        this.distance = distance;

        this.x = getNextX();
        this.y = 0;
    }

    @Override
    public void update() {
        if (y > GamePanel.HEIGHT) {
            y = -distance -200;
            x = getNextX();
        }

        y = y + Constants.OBSTACLE_DROPPING_RATE;


        // update the animation
        animation.update();
        this.rotateImage(rotation);
    }


        public void rotateImage(float angle) {
        Matrix matrix = new Matrix();
        Bitmap image = animation.getImage();

        // rotate
        matrix.postTranslate(-this.getWidth() / 2.0f, -this.getHeight() / 2.0f);
        matrix.postRotate(angle % 360.0f);
        matrix.postTranslate(this.getWidth() / 2.0f, image.getHeight() / 2.0f);

        // placement
        matrix.postTranslate(x, y);

    }

    private int getNextX() {
        return rnd.nextInt(400);
    }
/*

    @Override
    protected void doAnimate() {
        rotateImage(rotation);
    }
*/



}
