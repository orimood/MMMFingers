package com.mmmfingers.sceneBased;


import android.graphics.Canvas;

public interface PopUp {

    void update();

    void draw(Canvas canvas);

    void receiveTouch(int action, int xPosition, int yPosition);
}
