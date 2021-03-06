package com.mmmfingers;

import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * @author Ori Sinvani.
 * @version version 1.50
 * MMM Fingers Project
 * Modi-in, YACHAD high-school.
 *
 * ******************************************************************
 * Class description:
 * This is the main image object
 * it is abstract, that mean, it is telling has what to do
 * and not how to do, other subclasses will deal with how to do,
 * they will implement these method
 */

public abstract class GameObject {
    protected volatile int x;
    protected volatile int y;
    protected int width;
    protected int height;


    //we must create the set and get methods, for future use ...
    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    //same for y cord
    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    /**
     * we need the height and the width of our objects...
     * we already set the values of them when we created them
     **/
    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    /**
     * we need the rectangle method to create a rectangle around our image..
     * we will use for collision checking between objects
     */
    public Rect getRectangle() {
        return new Rect(x, y, x + width, y + height);
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public abstract void update();

    public abstract void draw(Canvas canvas);

}//end of class
