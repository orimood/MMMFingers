    package com.mmmfingers;

    import android.content.Context;
    import android.graphics.BitmapFactory;
    import android.graphics.Canvas;
    import android.util.AttributeSet;
    import android.view.MotionEvent;
    import android.view.SurfaceHolder;
    import android.view.SurfaceView;

    import androidx.annotation.NonNull;
    import androidx.fragment.app.Fragment;
    import androidx.fragment.app.FragmentManager;
    import androidx.navigation.fragment.NavHostFragment;
    /**
     * @author Ori Sinvani.
     * @version version 1.50
     * MMM Fingers Project
     * Modi-in, YACHAD high-school.
     *
     * *************************************************************
     * this class is the view where our game is started at
     * *************************************************************
     */

    public class StartView extends SurfaceView implements SurfaceHolder.Callback {

        private Background background;

        private final Button startGameButton;

        public StartView(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            getHolder().addCallback(this);

            background = new Background((BitmapFactory.decodeResource(context.getResources(), R.drawable.start_screen)), 1, 1);

            // create the start game button
            startGameButton = new Button((BitmapFactory.decodeResource(context.getResources(), R.drawable.button_startgame)), 1, 1);
            startGameButton.setX(Constants.SCREEN_WIDTH / 2 - startGameButton.getWidth() / 2);
            startGameButton.setY(Constants.SCREEN_HEIGHT / 3 - startGameButton.getHeight());

            startGameButton.setButtonTouchListener(new Button.OnButtonTouchListener() {
                @Override
                public void onTouchDown() {

                }

                @Override
                public void onTouchUp() {
                    // if start button was clicked, tell the start fragment to move to the game fragment
                    Fragment fragment = FragmentManager.findFragment(StartView.this);
                    NavHostFragment.findNavController(fragment)
                            .navigate(R.id.action_StartFragment_to_GameFragment);
                }
            });
        }

        //locks canvas between ticks
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
            background.draw(canvas);
            startGameButton.draw(canvas);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {

            // check if start game button was clicked
            if (startGameButton.onTouchEvent(event)) {
                return true;
            }

            return true;
        }
    }