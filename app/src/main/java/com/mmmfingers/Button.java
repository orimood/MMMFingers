package com.mmmfingers;


import android.graphics.Bitmap;
import android.view.MotionEvent;
/**
 * @author Ori Sinvani.
 * @version version 1.50
 * MMM Fingers Project
 * Modi-in, YACHAD high-school.
 *
 * *************************************************************
 * this class extends the AnimatedSpritesObject class
 * and adds:
 * Button class as a type of object with some helpful method
 * *************************************************************
 */

public class Button extends AnimatedSpritesObject {

    int spriteNumber = 0;

    private OnButtonTouchListener buttonTouchListener;

    public Button(Bitmap imageThatHasSprites, int numberOfSprites, int rowLength) {
        super(imageThatHasSprites, numberOfSprites, rowLength);
    }


    @Override
    public void update() {
        animation.setSprite(spriteNumber);
    }

    public boolean onTouchEvent(MotionEvent event) {
        final int action = event.getAction();

        final int evX = (int) event.getX();
        final int evY = (int) event.getY();

        // if event is in the button rectangle
        if (getRectangle().contains(evX, evY)) {

            // if we have a listener defined
            if (buttonTouchListener != null) {

                // on action up, call onTouchUp
                if (action == MotionEvent.ACTION_UP) {
                    buttonTouchListener.onTouchUp();
                    return true;
                }

                // on action down, call onTouchDown
                else if (action == MotionEvent.ACTION_DOWN) {
                    buttonTouchListener.onTouchDown();
                    return true;
                }
            }
        }

        return false;
    }

    //sets listener that waits for when button is touched
    public void setButtonTouchListener(OnButtonTouchListener buttonTouchListener) {
        this.buttonTouchListener = buttonTouchListener;
    }

    public interface OnButtonTouchListener {
        void onTouchDown();

        void onTouchUp();
    }
}