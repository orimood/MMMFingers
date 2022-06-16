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

    public class StartScene extends SurfaceView implements SurfaceHolder.Callback {

        private StartFragment startFragment;

        private Background background;

        private final Button startGameButton;

        public StartScene(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            getHolder().addCallback(this);

            // create the start game button
            startGameButton = new Button((BitmapFactory.decodeResource(context.getResources(), R.drawable.start_btn)), 1, 1);
            startGameButton.setX(Constants.SCREEN_WIDTH / 2 - startGameButton.getWidth() / 2);
            startGameButton.setY(Constants.SCREEN_HEIGHT / 2 - startGameButton.getHeight());

            startGameButton.setButtonTouchListener(new Button.OnButtonTouchListener() {
                @Override
                public void onTouchDown() {

                }

                @Override
                public void onTouchUp() {
                    // if start button was clicked, tell the start fragment to move to the game fragment
                    if (startFragment != null) {
                        NavHostFragment.findNavController(startFragment)
                                .navigate(R.id.action_StartFragment_to_GameFragment);
                    }
                }
            });
        }

        public void setStartFragment(StartFragment startFragment) {
            this.startFragment = startFragment;
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

            // check if start game button was clicked
            if (startGameButton.onTouchEvent(event)) {
                return true;
            }

            return true;
        }
    }