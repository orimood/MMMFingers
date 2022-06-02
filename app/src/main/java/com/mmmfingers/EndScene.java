package com.mmmfingers;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;

public class EndScene extends SurfaceView implements SurfaceHolder.Callback {

    private EndFragment endFragment;

    private Background background;

    private final Button startGameButton;

    public EndScene(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        getHolder().addCallback(this);

        // create the start game button
        startGameButton = new Button((BitmapFactory.decodeResource(context.getResources(), R.drawable.start_btn)), 1, 1);
        startGameButton.setX(Constants.ORIGINAL_SCREEN_WIDTH / 2 - startGameButton.getWidth() / 2);
        startGameButton.setY(Constants.ORIGINAL_SCREEN_HEIGHT / 2 - startGameButton.getHeight());

        startGameButton.setButtonTouchListener(new Button.OnButtonTouchListener() {
            @Override
            public void onTouchDown() {

            }

            @Override
            public void onTouchUp() {
                // if start button was clicked, tell the start fragment to move to the game fragment
                if (endFragment != null) {
                    NavHostFragment.findNavController(endFragment)
                            .navigate(R.id.action_EndFragment_to_GameFragment);
                }
            }
        });
    }

    public void setEndFragment(EndFragment endFragment) {
        this.endFragment = endFragment;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        Canvas c = getHolder().lockCanvas();
        draw(c);
        getHolder().unlockCanvasAndPost(c);
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        startGameButton.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (startGameButton.onTouchEvent(event)) {
            return true;
        }

        if (endFragment != null && event.getAction() == MotionEvent.ACTION_UP) {
            NavHostFragment.findNavController(endFragment)
                    .navigate(R.id.action_EndFragment_to_StartFragment);
        }

        return true;
    }
}