package com.mmmfingers.sceneBased;


import android.graphics.Canvas;
import android.view.MotionEvent;

public interface PopUp {

    public void update();
    public void draw (Canvas canvas);
    public  void receiveTouch(MotionEvent event);
}
