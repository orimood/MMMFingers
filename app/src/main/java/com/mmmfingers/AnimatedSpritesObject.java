package com.mmmfingers;

import android.graphics.Bitmap;
import android.graphics.Canvas;


/**
 * @author Ori Sinvani.
 * @version version 1.50
 * MMM Fingers Project
 * Modi-in, YACHAD high-school.
 *
 * *************************************************************
 * this class extends the GameObject class
 * and adds:
 * Animation class as variable object for the objects animations
 * *************************************************************
 */

public class AnimatedSpritesObject extends GameObject {

    // show flag - set default true, so we can see the image
    private boolean show = true;

    // this class uses the Animation class, so we make a reference to a new Animation
    protected Animation animation = new Animation();

    // set an array of bitmaps for our sprites
    private Bitmap[] spritesArray;

    // save bitmap  - this is used for rotate method
    final private Bitmap[] saveSpritesArray;

    private float rotationAngle;

    // bitmap image from where we cut our sprites
    private Bitmap spriteSheet;

    // start time pointer, for many uses
    private long lastUpdateTime;

    /**
     * So to create an animated object
     * first we pass the bit map image (the image that has the sprites) - imageThatHasSprites
     * the dimensions of each sprite we get from the bitmap image
     * numberOfSprites -  number Of Sprites in the all image
     * rowLength - number of sprites in each row
     * so now we can cut each sprite from the image and save it in an array of bitmap (spritesArray)
     * and pass this array to the animation class
     */
    public AnimatedSpritesObject(Bitmap imageThatHasSprites, int numberOfSprites, int rowLength) {

        /**
         * first calculate:
         * The dimensions of each sprite in the image - width and height -
         * from rowLength and numberOfSprites
         * so we can create a bitmap
         * for each sprite
         */
        imageThatHasSprites.setHasAlpha(true);

        height = imageThatHasSprites.getHeight() / (numberOfSprites / rowLength);
        width = imageThatHasSprites.getWidth() / rowLength;

        // set an array of bitmaps for our sprites
        spritesArray = new Bitmap[numberOfSprites];

        int row = 0;

        //loop
        for (int i = 0; i < spritesArray.length; i++) {
            /**
             *Our image has XxX sprites so [0] is the first sprite,  [1] the second,  [2] the third ...
             */
            if (i % rowLength == 0 && i > 0) row++;
            spritesArray[i] = Bitmap.createBitmap(imageThatHasSprites,
                    (i - (rowLength * row)) * width, row * height,
                    width, height);
        }


        // now that we know all the info about the bitmap image
        // we need to set the animation and the delay

        // init to row 0
        animation.setSpriteRow(0);
        animation.setRowLength(rowLength);
        // pass the sprites array
        animation.setSprites(spritesArray);
        saveSpritesArray = spritesArray;
        animation.setDelay(200);

        //Now we initiate the timer so we can use in the update method
        lastUpdateTime = System.nanoTime();
    }//end constructor


    // update method


    @Override
    public void update() {

    }

    /**
     * draw our object on the canvas (screen)
     */
    public void draw(Canvas canvas) {
        if(show) { canvas.drawBitmap(animation.getImage(), x, y, null); }
    }//end draw


    /**
     * this will rotate the image clockwise, by "angle"
     * pay attention:
     * the rotation is always done from the base original image "spritesArray", this is the reason why we
     * use the "saveSpritesArray" to save the base original image to it, and when we call this method it
     * always start to rotate from the original image
     *
     * @param angle
     */

    /**
     * Setters and Getters *******************************************************************
     */
    public Animation getAnimation() {
        return animation;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public float getRotationAngle() {
        return rotationAngle;
    }

    public void setRotationAngle(float rotationAngle) {
        this.rotationAngle = rotationAngle;
    }

    // this will retrieve the current sprite image
    public Bitmap getImage() {
        return animation.getImage();
    }

}
