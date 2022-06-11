package com.mmmfingers;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
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

    private Typeface typeface;

    public EndScene(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        getHolder().addCallback(this);

        typeface = Typeface.createFromAsset(context.getAssets(), "font/inkfree.ttf");

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

        SharedPreferences sp = endFragment.getActivity().getSharedPreferences("myGameShared", MODE_PRIVATE);
        updateSp(sp);
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

        Paint textPaint = new Paint();
        textPaint.setTypeface(typeface);
        textPaint.setColor(Color.RED);
        textPaint.setTextSize(200);

        Rect bounds = new Rect();

        int y = 200;

        String endGameText = "Game Over";
        textPaint.getTextBounds(endGameText, 0, endGameText.length(), bounds);
        canvas.drawText(endGameText, (GamePanel.getWIDTH() - bounds.width()) / 2, y, textPaint);
        y += bounds.height() + 50;

        String scoreText = "Final Score: " + GameLogic.getInstance().getScore();
        textPaint.getTextBounds(scoreText, 0, scoreText.length(), bounds);
        canvas.drawText(scoreText, (GamePanel.getWIDTH() - bounds.width()) / 2, y, textPaint);
        y += bounds.height() + 50;

        startGameButton.setY(y);

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


    public void updateSp(SharedPreferences sp) {
        int highScoresCounter;

        // if sp exists then get highScoresCounter from sp
        if (sp != null)
            highScoresCounter = sp.getInt("highScoresCounter", 0);
        else highScoresCounter = 0;

        highScoresCounter++;
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("highScoresCounter", highScoresCounter);
        editor.putLong("score" + highScoresCounter, GameLogic.getInstance().getScore());

        // save
        editor.commit();
    }
}