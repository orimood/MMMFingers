package com.mmmfingers;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.MotionEvent;

public interface Scene {
    public void terminate();
    public void receiveTouch(MotionEvent event);
    public void update();
    public void draw(Canvas canvas);
    public void resetAll();
}



