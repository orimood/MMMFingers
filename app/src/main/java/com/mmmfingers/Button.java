package com.mmmfingers;


import android.graphics.Bitmap;
import android.media.MediaPlayer;

import com.mmmfingers.AnimatedSpritesObject;

public class Button extends AnimatedSpritesObject {

    int spriteNumber = 0;

    private int motionEventID;

    private boolean pressed = false;

    private OnButtonTouchListener buttonTouchListener;

    public Button(Bitmap imageThatHasSprites, int numberOfSprites, int rowLength) {
        super(imageThatHasSprites, numberOfSprites, rowLength);
    }


    @Override
    public void update() {
        animation.setSprite(spriteNumber);
    }

    public int getSpriteNumber() {
        return spriteNumber;
    }

    public void setSpriteNumber(int spriteNumber) {
        this.spriteNumber = spriteNumber;
    }

    public boolean isPressed() {
        return pressed;
    }

    public void setPressed(boolean pressed) {
        if (!this.pressed && pressed && buttonTouchListener != null){
            buttonTouchListener.onTouchDown();
        }
        if (this.pressed && !pressed && buttonTouchListener != null){
            buttonTouchListener.onTouchUp();
        }
        this.pressed = pressed;
    }

    public int getMotionEventID() {
        return motionEventID;
    }

    public void setMotionEventID(int motionEventID) {
        this.motionEventID = motionEventID;
    }

    public OnButtonTouchListener getButtonTouchListener() { return buttonTouchListener; }

    public void setButtonTouchListener(OnButtonTouchListener buttonTouchListener) { this.buttonTouchListener = buttonTouchListener; }

    public interface OnButtonTouchListener{
        void onTouchDown();
        void onTouchUp();
    }
}