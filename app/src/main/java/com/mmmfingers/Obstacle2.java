package com.mmmfingers;

import android.graphics.Bitmap;

import java.util.Random;


class Obstacle2 extends AnimatedSpritesObject {

    private Random rnd = new Random();
    private int rotateAngle;
    private int distance;

    public Obstacle2(Bitmap imageThatHasSprites, int numberOfSprites, int rowLength, int initialY, int rotateAngle, int distance) {
        super(imageThatHasSprites, numberOfSprites, rowLength);
        this.rotateAngle = rotateAngle;
        this.distance = distance;

        this.x = getNextX();
        this.y = initialY;
    }

    private int getNextX() {
        return rnd.nextInt(GamePanel.WIDTH) - Math.max(width, height) / 2;
    }

    // TODO: 11/10/2021 move to the animate

    /**
     * public void update() {
     * y = y + 5;
     * if (y > GamePanel.HEIGHT) {
     * y = - width;
     * Random rnd = new Random();
     * x = rnd.nextInt(1080 - (2 * width)) + width;
     * }
     * animation.update();
     * rotateImage(3);
     * }
     */
    @Override
    protected void doAnimate() {
        rotateImage(rotateAngle);
        y = y + 10;
        if (y > GamePanel.HEIGHT) {
            y = -distance;
            x = getNextX();
        }
    }
}
