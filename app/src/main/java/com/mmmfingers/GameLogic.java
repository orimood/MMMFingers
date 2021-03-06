package com.mmmfingers;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

/**
 * @author Ori Sinvani.
 * @version version 1.50
 * MMM Fingers Project
 * Modi-in, YACHAD high-school.
 *
 * * *****************************************************************
 * * Class description:
 * * this class GameLogic class, all the logic of the game, the game rules and BRAIN
 * * are handled here !!!
 * * *****************************************************************
 */

public class GameLogic {

    private static GameLogic instance;

    private volatile boolean gameOver;

    private volatile int score;

    /**
     * Constructor
     */
    public GameLogic() {

    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver() {
        this.gameOver = true;
    }

    public int getScore() {
        return score;
    }

    public void incScore(int diff) {
        if (!isGameOver()) {
            score += diff;
        }
    }


    public void resetGame() {
        score = 0;
        gameOver = false;
    }


    /**
     * show message of state:
     * in this method we can implement different popup message by using
     * a Paint class to write something, and more...
     */
    public void showStateMessage(Canvas canvas) {
        // and create a bitmap ref for panel image, on this panel we show a message
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setTextSize(80);
        // set text to Bold
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));
        // write in the message

        canvas.drawText("" + score, 100, 100, paint);
    }// end drawText

    /**********************************************************************************************
     * COLLISION DETECTION METHODS
     * ********************************************************************************************
     *
     * check for collision between two image objects
     * in these methods with overloading methods, we check a collision without the transparent
     * areas of the image, and also, we can scale the touching area
     */

    /**
     * @param bm        the bitmap image for resizing
     * @param newWidth  resize size width
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
     * @param obj1 First object
     *             x1 x-position of bitmap1 on screen.
     *             y1 y-position of bitmap1 on screen.
     * @param obj2 Second object.
     *             x2 x-position of bitmap2 on screen.
     *             y2 y-position of bitmap2 on screen.
     *             <p>
     *             Overloaded method for isCollisionDetected, without resize
     */

    // collision method , near collision
    public boolean collision(AnimatedSpritesObject obj1, AnimatedSpritesObject obj2) {
        if (Rect.intersects(obj1.getRectangle(), obj2.getRectangle())) {
            // if it is a near collision, then check exact collision
            return isCollisionDetected(obj1, obj2);
        }
        return false;
    }

    // exact collision method
    public boolean isCollisionDetected(AnimatedSpritesObject obj1,
                                       AnimatedSpritesObject obj2) {
        Bitmap bitmap1;
        int x1, y1;
        Bitmap bitmap2;
        int x2, y2;

        bitmap1 = obj1.getImage();
        x1 = obj1.getX();
        y1 = obj1.getY();
        bitmap2 = obj2.getImage();
        x2 = obj2.getX();
        y2 = obj2.getY();


        Rect bounds1 = new Rect(x1, y1, x1 + bitmap1.getWidth(), y1 + bitmap1.getHeight());
        Rect bounds2 = new Rect(x2, y2, x2 + bitmap2.getWidth(), y2 + bitmap2.getHeight());

        if (Rect.intersects(bounds1, bounds2)) {
            Rect collisionBounds = getCollisionBounds(bounds1, bounds2);
            for (int i = collisionBounds.left; i < collisionBounds.right; i++) {
                for (int j = collisionBounds.top; j < collisionBounds.bottom; j++) {
                    int bitmap1Pixel = bitmap1.getPixel(i - x1, j - y1);
                    int bitmap2Pixel = bitmap2.getPixel(i - x2, j - y2);
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

    public synchronized static GameLogic getInstance() {
        if (instance == null) {
            instance = new GameLogic();
        }
        return instance;
    }

}

