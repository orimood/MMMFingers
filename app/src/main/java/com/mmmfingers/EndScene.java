package com.mmmfingers;

import static android.content.Context.MODE_PRIVATE;
import static com.mmmfingers.ScoreUtils.getScoreList;
import static com.mmmfingers.ScoreUtils.updateScore;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;

public class EndScene extends SurfaceView implements SurfaceHolder.Callback {

    private EndFragment endFragment;


    private Background background;

    private final Button startAgainButton;
    private final Button highScoresButton;

    private Typeface typeface;

    public EndScene(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        getHolder().addCallback(this);

        typeface = Typeface.createFromAsset(context.getAssets(), "font/inkfree.ttf");

            // create the start game button
        startAgainButton = new Button((BitmapFactory.decodeResource(context.getResources(), R.drawable.button_again)), 1, 1);
        startAgainButton.setX(Constants.SCREEN_WIDTH / 2 - startAgainButton.getWidth() / 2);
        startAgainButton.setY(Constants.SCREEN_HEIGHT / 2 - startAgainButton.getHeight());

        startAgainButton.setButtonTouchListener(new Button.OnButtonTouchListener() {
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

        highScoresButton = new Button((BitmapFactory.decodeResource(context.getResources(), R.drawable.button_highscores)), 1, 1);
        highScoresButton.setX(Constants.SCREEN_WIDTH / 2 - highScoresButton.getWidth() / 2);
        highScoresButton.setY(Constants.SCREEN_HEIGHT / 2 - startAgainButton.getHeight() + 50);

        highScoresButton.setButtonTouchListener(new Button.OnButtonTouchListener() {
            @Override
            public void onTouchDown() {

            }

            @Override
            public void onTouchUp() {
                if (endFragment != null) {
                    NavHostFragment.findNavController(endFragment)
                            .navigate(R.id.action_EndFragment_to_ScoreFragment);
                }
            }
        });
    }

    public void setEndFragment(EndFragment endFragment) {
        this.endFragment = endFragment;

        SharedPreferences sp = endFragment.getActivity().getSharedPreferences("myGameShared", MODE_PRIVATE);
        updateScore(sp, GameLogic.getInstance().getScore());
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
        textPaint.setTextSize(150);

        Paint highScorePaint = new Paint();
        highScorePaint.setTypeface(typeface);
        highScorePaint.setColor(Color.rgb(218,165,32));
        highScorePaint.setTextSize(150);

        Rect bounds = new Rect();

        int y = 200;

        String endGameText = "Ouch, that hurt!";
        textPaint.getTextBounds(endGameText, 0, endGameText.length(), bounds);
        canvas.drawText(endGameText, (GamePanel.getWIDTH() - bounds.width()) / 2, y, textPaint);
        y += bounds.height() + 50;

        String scoreText = "Final Score: " + GameLogic.getInstance().getScore();
        textPaint.getTextBounds(scoreText, 0, scoreText.length(), bounds);
        canvas.drawText(scoreText, (GamePanel.getWIDTH() - bounds.width()) / 2, y, textPaint);
        y += bounds.height() + 50;

        SharedPreferences sp = endFragment.getActivity().getSharedPreferences("myGameShared", MODE_PRIVATE);
        ArrayList<Long> scoreList = getScoreList(sp);
        if (GameLogic.getInstance().getScore() >= scoreList.get(0)) {
            String highScoreText = "New Highscore!";
            highScorePaint.getTextBounds(highScoreText, 0, highScoreText.length(), bounds);
            canvas.drawText(highScoreText, (GamePanel.getWIDTH() - bounds.width()) / 2, y, highScorePaint);
            y += bounds.height() + 50;
        }

        startAgainButton.setY(y);

        highScoresButton.setY(y+ 250 + startAgainButton.getHeight());


        startAgainButton.draw(canvas);
        highScoresButton.draw(canvas);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (startAgainButton.onTouchEvent(event)) {
            return true;
        }

        if (highScoresButton.onTouchEvent(event)){
            return true;
        }


        return true;
    }

}