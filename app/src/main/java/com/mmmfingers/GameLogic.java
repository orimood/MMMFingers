package com.mmmfingers;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;

import java.util.Random;

/**
 * @author Ori Sinvani.
 * @version version 2.00
 * @since version 2.00
 * Study Android,
 * Modi'in, Yachad high-school.
 *
 * * *****************************************************************
 * * Class description:
 * * this class GameLogic class, all the logic of the game, the game rules and BRAIN
 * * are handled here !!!
 * * *****************************************************************
 */

public class GameLogic {

    /**
     * this pointer will point to our gamePanel
     * so we can address it and other game object from this class
     */
    private GamePanel gamePanel;

    // random for dices roll results
    Random random = new Random();

    // random number we get 1..6
    int randomNumber;

    /**
     * Constructor
     */
    public GameLogic(GamePanel gamePanel) {
        // here we point to gamePanel object
        this.gamePanel = gamePanel;
    }

    /**
     * this is the main method called from the gamePanel class, from the onTouchEvent
     * (when we press on the screen)
     *
     * @param xPosition
     * @param yPosition
     */
    public void mainLogic(int xPosition, int yPosition) {
        switch (gamePanel.getGameState()) {
            case GAME_PLAYING_STATE:
                // do something
                gamePanel.getGirl1().setY(yPosition);
                gamePanel.getGirl1().setX(xPosition);
                // gamePanel.getGameState() = GameState.GAME_START_STATE;
                break;
            default:
        }
    }

    public void changeDirection() {
        if (gamePanel.getGirlWalkingDirection1() == WalkingDirection.RIGHT) {
            gamePanel.getGirl1().setGirlWalkingDirection(WalkingDirection.LEFT);
            gamePanel.setGirlWalkingDirection1(WalkingDirection.LEFT);
        }
        else {
            gamePanel.getGirl1().setGirlWalkingDirection(WalkingDirection.RIGHT);
            gamePanel.setGirlWalkingDirection1(WalkingDirection.RIGHT);
        }
     }



    /**********************************************************************************************
     * COLLISION DETECTION METHODS
     * ********************************************************************************************
     *
     * check for collision between two image objects
     * in these methods with overloading methods, we check a collision without the transparent
     * areas of the image, and also, we can scale the touching area
     */

    /**
     *
     * @param bm the bitmap image for resizing
     * @param newWidth resize size width
     * @param newHeight resize size height
     * @return
     */
    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    /**
     * @param obj1 first object
     * x1 x-position of bitmap1 on screen.
     *  y1 y-position of bitmap1 on screen.
     * @param scaleW1 resize width bitmap11
     * @param scaleH1
     * @param obj2 Second object.
     * @param scaleW2
     * @param scaleH2
     *  x2 x-position of bitmap2 on screen.
     *  y2 y-position of bitmap2 on screen.
     *
     *           Overloaded method for isCollisionDetected, with resize
     */
    public boolean isCollisionDetected(AnimatedSpritesObject obj1, int scaleW1, int scaleH1,
                                       AnimatedSpritesObject obj2, int scaleW2, int scaleH2) {
        Bitmap bitmap11;
        int x1, y1;
        Bitmap bitmap22;
        int x2,y2;

        bitmap11 = obj1.getImage();
        x1 = obj1.getX();
        y1 = obj1.getY();
        bitmap22 = obj2.getImage();
        x2 = obj2.getX();
        y2 = obj2.getY();


        // copy bitmap
        Bitmap bitmap1 = bitmap11.copy(bitmap11.getConfig(), true);
        Bitmap bitmap2 = bitmap22.copy(bitmap22.getConfig(), true);

        // scale bitmap
        bitmap1 = getResizedBitmap(bitmap1 , scaleW1, scaleH1);
        bitmap2 = getResizedBitmap(bitmap2 , scaleW2, scaleH2);

        Rect bounds1 = new Rect(x1, y1, x1+bitmap1.getWidth(), y1+bitmap1.getHeight());
        Rect bounds2 = new Rect(x2, y2, x2+bitmap2.getWidth(), y2+bitmap2.getHeight());

        if (Rect.intersects(bounds1, bounds2)) {
            Rect collisionBounds = getCollisionBounds(bounds1, bounds2);
            for (int i = collisionBounds.left; i < collisionBounds.right; i++) {
                for (int j = collisionBounds.top; j < collisionBounds.bottom; j++) {
                    int bitmap1Pixel = bitmap1.getPixel(i-x1, j-y1);
                    int bitmap2Pixel = bitmap2.getPixel(i-x2, j-y2);
                    if (isFilled(bitmap1Pixel) && isFilled(bitmap2Pixel)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * @param obj1 First object
     * x1 x-position of bitmap1 on screen.
     * y1 y-position of bitmap1 on screen.
     * @param obj2 Second object.
     * x2 x-position of bitmap2 on screen.
     * y2 y-position of bitmap2 on screen.
     *
     *           Overloaded method for isCollisionDetected, without resize
     */

    public boolean isCollisionDetected(AnimatedSpritesObject obj1,
                                       AnimatedSpritesObject obj2) {
        Bitmap bitmap1;
        int x1, y1;
        Bitmap bitmap2;
        int x2,y2;

        bitmap1 = obj1.getImage();
        x1 = obj1.getX();
        y1 = obj1.getY();
        bitmap2 = obj2.getImage();
        x2 = obj2.getX();
        y2 = obj2.getY();


        Rect bounds1 = new Rect(x1, y1, x1+bitmap1.getWidth(), y1+bitmap1.getHeight());
        Rect bounds2 = new Rect(x2, y2, x2+bitmap2.getWidth(), y2+bitmap2.getHeight());

        if (Rect.intersects(bounds1, bounds2)) {
            Rect collisionBounds = getCollisionBounds(bounds1, bounds2);
            for (int i = collisionBounds.left; i < collisionBounds.right; i++) {
                for (int j = collisionBounds.top; j < collisionBounds.bottom; j++) {
                    int bitmap1Pixel = bitmap1.getPixel(i-x1, j-y1);
                    int bitmap2Pixel = bitmap2.getPixel(i-x2, j-y2);
                    if (isFilled(bitmap1Pixel) && isFilled(bitmap2Pixel)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    private static Rect getCollisionBounds(Rect rect1, Rect rect2) {
        int left = Math.max(rect1.left, rect2.left);
        int top = Math.max(rect1.top, rect2.top);
        int right = Math.min(rect1.right, rect2.right);
        int bottom = Math.min(rect1.bottom, rect2.bottom);
        return new Rect(left, top, right, bottom);
    }

    private static boolean isFilled(int pixel) {
        return pixel != Color.TRANSPARENT;
    }

/**********************************************************************************************
 */

}

