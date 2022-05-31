package com.mmmfingers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;

public class EndScene extends SurfaceView implements SurfaceHolder.Callback {

    private EndFragment endFragment;

    private Background background;

    public EndScene(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        getHolder().addCallback(this);
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

        canvas.drawColor(Color.RED);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (endFragment != null && event.getAction() == MotionEvent.ACTION_UP) {
            NavHostFragment.findNavController(endFragment)
                    .navigate(R.id.action_EndFragment_to_StartFragment);
            endFragment.getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS |
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        return true;
    }
}