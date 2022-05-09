package com.mmmfingers.sceneBased;

import android.graphics.Canvas;
import android.view.View;

public interface Scene {
    void update();

    void draw(Canvas canvas);

    void initialize(View view);

    void terminate();

    void receiveTouch(int action, int xPosition, int yPosition);

    void resetAll();

    void activate();

    void deactivate();
}
