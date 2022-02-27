package com.mmmfingers;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import java.util.Random;


class Teeth extends AnimatedSpritesObject {

    private Random rnd = new Random();
    private int rotateAngle;
    private int distance;

    public Teeth(Bitmap imageThatHasSprites, int numberOfSprites, int rowLength, int initialY, int rotateAngle, int distance) {
        super(imageThatHasSprites, numberOfSprites, rowLength);
        this.rotateAngle = rotateAngle;
        this.distance = distance;

        this.x = getNextX();
        this.y = initialY;
    }

    @Override
    public void update() {

        /*long newUpdateTime = System.nanoTime();
        // here is the timer of our Player millis
        long elapsed = (newUpdateTime - lastUpdateTime) / 1000000;

        // Now when our timer gets past xxx millis we want the to do something
        if (elapsed >= 40) {
            // here is our code
            doAnimate();

            lastUpdateTime = newUpdateTime;
        }*/


        if (y > GamePanel.getHEIGHT()) {
            y = -distance;
            x = getNextX();
        }

        y = y + Constants.OBSTACLE_DROPPING_RATE;

        // update the animation
        animation.update();
    }//end update

    private int getNextX() {
        return rnd.nextInt(GamePanel.getWIDTH()) - Math.max(width, height) / 2;
    }


/*    protected void doAnimate() {
        rotateImage(rotateAngle);

        //drops the obstacle a certain amount of pixels every frame
        y = y + Constants.OBSTACLE_DROPPING_RATE;
        if (y > GamePanel.HEIGHT) {
            y = -distance;
            x = getNextX();
        }
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

    }*/
}
