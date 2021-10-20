package com.mmmfingers;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


class Obsti1 extends AnimatedSpritesObject {

    // start time pointer, for many uses
    private long startTime;

    public Obsti1(Bitmap imageThatHasSprites, int numberOfSprites, int rowLength) {
        super(imageThatHasSprites, numberOfSprites, rowLength);

        //Now we initiate the timer so we can use in the update method
        startTime = System.nanoTime();

    }

    @Override
    public void update() {
        animation.update();
    }
}
