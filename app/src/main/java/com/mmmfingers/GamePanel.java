package com.mmmfingers;

/**
 * @author Ori Sinvani.
 * @version version 2.00
 * @since version 2.00
 * Study Android,
 * Modi'in, Yachad high-school.
 * <p>
 * *************************************************************
 * Class description:
 * <p>
 * This is the main class of our game,
 * it uses the SurfaceView class to implement screen management and touch
 * for our game, all the UI is here
 * *************************************************************
 */


import android.content.Context;
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

import androidx.annotation.RequiresApi;
import androidx.core.view.GestureDetectorCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GamePanel extends SurfaceView
        implements
        SurfaceHolder.Callback,
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {

    /**
     * Now we will set our game screen width and height
     * display size in pixels
     * the screen upper left corner is 0,0
     */
    public static int WIDTH;
    public static int HEIGHT;

    // start of the game flag
    boolean isPlaying;

    // the background of our game
    Background background, background1, background2;

    // game start message
    GameStartMessage gameStartMessage;

    // the animated message banner image
    AnimatedBanner animatedBanner;

    // init game objects - a boy and a girl
    Boy boy1;
    Girl girl1;
    Obstacle obstacle;
    Obstacle2 obstacle2[] = new Obstacle2[6];

    // enum for girl walking direction
    WalkingDirection girlWalkingDirection1;

    List<GameObject> animatedObjects = new ArrayList<>();

    // TODO for later use for game score
    private int score;

    //the random class
    private Random rnd = new Random();

    // sounds declaration section
    SoundPool applauseSound;
    int applauseSoundID;
    SoundPool pieceMoveSound;
    int pieceMoveSoundID;
    SoundPool twoDiceSound;
    int twoDiceSoundID;
    SoundPool startSound;
    int startSoundID;
    SoundPool openBannerSound;
    int openBannerSoundID;


    private GestureDetectorCompat gestureDetector;


    // TODO LATER - saved for later use Game Ending Picture
    private static boolean gameEnd = false;

    /**
     * ******************************************************************
     * game logic class - the "BRAIN" of our game, rules and more ...
     * very important class - all the game algorithms are there
     */
    GameLogic gameLogic;
    /**
     * ******************************************************************
     */


    /**
     * ******************************************************************
     * game loop class - this thread loops our game screen 30 frame for second ...
     * very important class - it address (by its run method) the update and draw method
     * on gamePanel methods, by that it updates all game objects parameters (and more things
     * that are periodically) and the draw method which refreshes the screen
     */
    private GameThread gameThread;
    /**
     * ******************************************************************
     */


    /**
     * This (ENUM CLASS) will help us navigate in our game states
     * // current game state - start of the game MESSAGE SHOWN
     */
    GameState gameState = GameState.GAME_START_STATE;

    /**
     * TODO later implementation of different screen different with resolutions
     * This a very important stage to be implemented - for dealing different phone with
     * different screen resolutions ...
     * <p>
     * <p>
     * When we are implementing graphic application we need to adjust our application
     * to other possible phone that will operate our application, which will have screen different resolutions
     * for that reason. we use a vars we call "xChangedFactor" and "yChangedFactor" (for x and y of screen)
     * We will need it on two places
     * 1. when drawing images (draw method),
     * 2 - on touching the screen (onTouchEvent) method
     * This vars will be initiated at first when we will get scaling factor of the
     * current phone screen we are using
     */
    float scaleFactorXMul = 1.0f;
    float scaleFactorYMul = 1.0f;


    //lets create the constructor of our new class,that is going to help us calling objects and methods!
    public GamePanel(Context context, int WIDTH, int HEIGHT) {

        /**
         context we receive from our activity,
         by the COMMAND:
         setContentView(new GamePanel(this, WIDTH, HEIGHT)) - see the MainActivity code;
         context - is the context (HEKSHER in hebrew) of our activity
         and we pass the context to the super class - which is "SurfaceView", cause it needs it
         to do it staff.
         */
        super(context);

        // of phone's dimensions
        GamePanel.WIDTH = WIDTH;
        GamePanel.HEIGHT = HEIGHT;

        /**
         * set the drawing scaled to the screen size WIDTH & HEIGHT, using the defaults
         * the defaults of our screen are in the Constants class
         * in this case: ORIGINAL_SCREEN_WIDTH, and ORIGINAL_SCREEN_HEIGHT
         *
         * calculate multipliers of scaleFactorX, scaleFactorY
         */
        scaleFactorXMul = 1.0f + ((WIDTH - Constants.ORIGINAL_SCREEN_WIDTH) * 1.0f / Constants.ORIGINAL_SCREEN_WIDTH);
        scaleFactorYMul = 1.0f + ((HEIGHT - Constants.ORIGINAL_SCREEN_HEIGHT) * 1.0f / Constants.ORIGINAL_SCREEN_HEIGHT);

        // gesture-s
        this.gestureDetector = new GestureDetectorCompat(this.getContext(), this);
        gestureDetector.setOnDoubleTapListener(this);

        // create GameLogic OBJECT
        gameLogic = new GameLogic(this);

        // create thread OBJECT
        gameThread = new GameThread(getHolder(), this);

        /** set sounds
         * the methods of sound is depended on the version of the SDK we use
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startSound = new SoundPool.Builder()
                    .setMaxStreams(10)
                    .build();
        } else {
            startSound = new SoundPool(10, AudioManager.STREAM_MUSIC, 1);
        }
        startSoundID = startSound.load(context, R.raw.start_sound, 1);

        /**
         * These (callback and focusable) are layers of android construction buildings,
         * we need them for SurfaceHolder class
         * of android (SurfaceHolder is a class which inherent the View class android and add
         * more capabilities.
         *
         Callback is used for the surfaceHolder to intercept events:
         Surface objects enable apps to render images to be presented on screens.
         SurfaceHolder interfaces enable apps to edit and control surfaces.
         */
        getHolder().addCallback(this);

        /**
         make gamePanel focusable so it can handle events.
         setFocusable mainly used for enable/disable view's focus event
         on both touch mode and keypad mode( using up/down/next key).
         */
        setFocusable(true);

    }//end of  constructor

    /**
     * This method tells us that the surface is READY,
     * so we can put object and draw them on it !
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        // game start message
        gameStartMessage = new GameStartMessage(BitmapFactory.decodeResource(getResources(), R.drawable.welcome_message),
                1, 1);
        // set on middle of x and y coordinates of the screen
        gameStartMessage.setX((Constants.ORIGINAL_SCREEN_WIDTH / 2) - (gameStartMessage.getWidth() / 2));
        gameStartMessage.setY((Constants.ORIGINAL_SCREEN_HEIGHT / 2) - (gameStartMessage.getHeight() / 2));


        // background of the game, some background picture
        background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.background),
                1, 1);
        background1 = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.background),
                1, 1);
        animatedObjects.add(background1);

        // background of the game, some background picture
        background2 = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.background),
                1, 1);
        background2.setY(-background2.height);
        animatedObjects.add(background2);


        // set message banner - for application use, this object is animated object
        animatedBanner = new AnimatedBanner(BitmapFactory.decodeResource(getResources(), R.drawable.animated_banner),
                8, 8);
        // set on middle of x coordinate of the screen
        animatedBanner.setX((Constants.ORIGINAL_SCREEN_WIDTH / 2) - (animatedBanner.getWidth() / 2));
        // set on middle of y coordinate of the screen
        animatedBanner.setY((Constants.ORIGINAL_SCREEN_HEIGHT / 2) - (animatedBanner.getHeight() / 2));
        // set show of the image off - don't show it
        animatedBanner.setShow(false);
        animatedBanner.getAnimation().setPlayedOnce(false);
        animatedBanner.getAnimation().setDelay(150);
        animatedObjects.add(animatedBanner);


        /**
         * Here we create animated objects:
         * in this command we pass:
         * the image,
         * numberOfSprites
         * and, rowLength
         */
        // create boy object
        boy1 = new Boy(BitmapFactory.decodeResource(getResources(), R.drawable.jumping_boy),
                6, 6);
        // set positions
        boy1.setX(Constants.ORIGINAL_SCREEN_WIDTH / 2);
        boy1.setY((Constants.ORIGINAL_SCREEN_HEIGHT / 5));
        boy1.getAnimation().setDelay(200);
        animatedObjects.add(boy1);


        // create girl object
        girl1 = new Girl(BitmapFactory.decodeResource(getResources(), R.drawable.gril_running),
                6, 6);
        girl1.setX(Constants.ORIGINAL_SCREEN_WIDTH / 2);
        girl1.setY((Constants.ORIGINAL_SCREEN_HEIGHT / 4));
        girl1.setGirlWalkingDirection(WalkingDirection.RIGHT);
        girlWalkingDirection1 = girl1.getGirlWalkingDirection();
        animatedObjects.add(girl1);


        // create girl jumping object


        // create obsti object
        obstacle = new Obstacle(BitmapFactory.decodeResource(getResources(), R.drawable.obstacle),
                1, 1);
        obstacle.setHeight(100);
        obstacle.setWidth(100);
        obstacle.setX(Constants.ORIGINAL_SCREEN_WIDTH / 2);
        obstacle.setY(Constants.ORIGINAL_SCREEN_HEIGHT / 2);
        animatedObjects.add(obstacle);

        for (int i = 0; i < obstacle2.length; i++) {
            int rotateAngel = rnd.nextInt(10) + 3;
            int distance = GamePanel.HEIGHT / (obstacle2.length - 1);
            obstacle2[i] = new Obstacle2(BitmapFactory.decodeResource(getResources(), R.drawable.obstacle2),
                    1, 1, i * -distance, rotateAngel, distance);
            animatedObjects.add(obstacle2[i]);
        }

        // play game start sound
        playStartSound();


        // start the game loop thread
        gameThread.setRunning(true);
        gameThread.start();
    }

    /**
     * This is called immediately after any structural changes (format or size) have been made to the surface.
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    /**
     * This is called immediately before a surface is being destroyed.
     * After returning from this call, you should no longer try to access this surface.
     * If you have a rendering thread that directly accesses the surface, you must ensure
     * that thread is no longer touching the Surface before returning from this function.
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        /**
         *The join() method is used to hold the execution of currently running
         *thread until the specified thread is dead(finished execution).
         */
        while (retry) {
            try {
                gameThread.setRunning(false);
                /**
                 *Wait for thread to die
                 */
                gameThread.join();

                // thread died, we can break
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }//end while
    }

    /**
     * onTouchEvent method - if we touch on the screen
     * this method which belongs to surfaceView class - we Override it ...
     * will bring us the MotionEvent object from where we can have
     * the x and y coordinates on our touch as well as the the kind of the touch
     * event.getAction() , there are many kinds of touch : UP, DOWN, MOVE, ACTION_CHANCEL and more...
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // gestures events
        this.gestureDetector.onTouchEvent(event);

        int action = event.getAction();
        int xPosition, yPosition;

        // adjust x and y the screen resolution by dividing by the factors
        xPosition = (int) (event.getX() / scaleFactorXMul);
        yPosition = (int) (event.getY() / scaleFactorYMul);

        // if game started
        if (isPlaying) {
            switch (action) {
                case MotionEvent.ACTION_MOVE:
                    gameLogic.mainLogic(xPosition, yPosition);
                    break;
                case MotionEvent.ACTION_DOWN:
                    break;
                // TODO 2
                case MotionEvent.ACTION_UP:
                    break;
                case MotionEvent.ACTION_CANCEL:
                    break;
            }
        } else
            // set isPlaying to start the game, after first tap on the screen
            if (action == MotionEvent.ACTION_UP)
                isPlaying = true;
        return true;
    }

    /**
     * in content view GamePanel we must constantly update our image
     * GamePanel cooperates with our thread
     * So our game run a new game loop ....
     */
    public void update() {
        // if game started
        if (isPlaying) {
            // TODO to be implemented later - background music of our game

// TODO: 11/10/2021 fix objects?
            for (GameObject gameObject : animatedObjects) {
                gameObject.update();
            }
/**
 obstacle21.update();
 obstacle22.update();
 obstacle23.update();
 obstacle24.update();
 obstacle25.update();
 */
            if (gameLogic.isCollisionDetected(boy1, girl1)) {
                girl1.flipImage(true, false);
                gameLogic.changeDirection();
            }
        }
    }//end update

    /**
     * GamePanel cooperates with our thread
     * So our game draw new screen every 33 milli seconds
     * GamePanel draw method , SurfaceView override
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void draw(Canvas canvas) {
        // pass to super class... to do its things
        super.draw(canvas);

        // here we start our implantation
        final float scaleFactorX = getWidth() / (WIDTH * 1.f);
        final float scaleFactorY = getHeight() / (HEIGHT * 1.f);

        /** so if something appears on our screen we must scale it
         * we would like that our graphic image can scale on different android phone screen sizes
         */
        if (canvas != null) {
            // if our canvas exists then we can use it - not pointing to a null

            // save current state of our canvas
            final int savedState = canvas.save();

            // if game started
            if (isPlaying) {

                /**
                 * set the drawing scaled to the screen size WIDTH & HEIGHT, using the defaults
                 * the defaults of our screen are in the Constants class
                 * in this case: ORIGINAL_SCREEN_WIDTH, and ORIGINAL_SCREEN_HEIGHT
                 *
                 */
                canvas.scale(scaleFactorX * scaleFactorXMul, scaleFactorY * scaleFactorYMul);

                // draw the static background (TODO: fix)
                background.draw(canvas);

                // check if we have to show message banner of any kind of game state
                switch (gameState) {
                    case GAME_START_STATE:
                        // do something
                        // change state
                        gameState = GameState.GAME_PLAYING_STATE;
                        break;
                    case GAME_END_STATE:
                        // do something
                        break;
                    default:
                }


                for (GameObject gameObject : animatedObjects) {
                    gameObject.draw(canvas);
                }

// TODO: 11/10/2021 fix the objects
                /**
                 obstacle21.draw(canvas);
                 obstacle22.draw(canvas);
                 obstacle23.draw(canvas);
                 obstacle24.draw(canvas);
                 obstacle25.draw(canvas);
                 */
            }
            // game not stared, so show game start message
            else {
                // set background color
                canvas.drawColor(Color.argb(50, 0, 102, 102));
                canvas.scale(scaleFactorX * scaleFactorXMul, scaleFactorY * scaleFactorYMul);

                // set game start message on middle of x coordinate of the screen
                gameStartMessage.setX((Constants.ORIGINAL_SCREEN_WIDTH / 2) - (gameStartMessage.getWidth() / 2));
                gameStartMessage.setY((Constants.ORIGINAL_SCREEN_HEIGHT / 2) - (gameStartMessage.getHeight() / 2));
                gameStartMessage.draw(canvas);
            }

            // restore the saves canvas state back
            canvas.restoreToCount(savedState);
        }
    }//end draw


    /**
     * show message of state:
     * in this method we can implement different popup message by using
     * a Paint class to write something, and more...
     */
    public void showStateMessage(Canvas canvas, String message) {
        // and create a bitmap ref for panel image, on this panel we show a message
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(60);
        // set text to Bold
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));
        // write in the message
        canvas.drawText(message, WIDTH / 2 - (animatedBanner.getWidth() / 2) + 50,
                (HEIGHT / 2) - 30, paint);
    }// end drawText

    /**
     * Sounds
     */
    public void playSoundPieceMove() {
        pieceMoveSound.play(pieceMoveSoundID, 0.5f, 0.5f, 1, 0, 1);
    }

    public void playSoundApplause() {
        applauseSound.play(applauseSoundID, 0.2f, 0.2f, 1, 0, 1);
    }

    // TODO 3, add sounds
    public void playDiceRollingSound() {
        twoDiceSound.play(twoDiceSoundID, 1.0f, 1.0f, 1, 0, 1);
    }

    // TODO 3, add sounds
    public void playStartSound() {
        startSound.play(startSoundID, 0.4f, 0.4f, 1, 0, 1);
    }

    // TODO 4, add sounds
    public void playOpenBannerSound() {
        openBannerSound.play(openBannerSoundID, 0.5f, 0.5f, 1, 0, 1);
    }

    /**
     * setter and Getters **********************************************************
     */
    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }


    public int getScore() {
        return score;
    }

    public static void setWIDTH(int WIDTH) {
        GamePanel.WIDTH = WIDTH;
    }

    public void setScore(int score) {
        this.score = score;
    }


    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }


    public AnimatedBanner getMessageBanner() {
        return animatedBanner;
    }

    public void setMessageBanner(AnimatedBanner messageBanner) {
        this.animatedBanner = messageBanner;
    }

    public Boy getBoy1() {
        return boy1;
    }

    public void setBoy1(Boy boy1) {
        this.boy1 = boy1;
    }

    public Girl getGirl1() {
        return girl1;
    }

    public void setGirl1(Girl girl1) {
        this.girl1 = girl1;
    }

    public WalkingDirection getGirlWalkingDirection1() {
        return girlWalkingDirection1;
    }

    public void setGirlWalkingDirection1(WalkingDirection girlWalkingDirection1) {
        this.girlWalkingDirection1 = girlWalkingDirection1;
    }

    /**
     * Gesture methods
     *
     * @param motionEvent
     * @return
     */

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        //   girl1.rotateImage(75);
        girl1.flipImage(true, false);
        gameLogic.changeDirection();
        boy1.flipImage(false, true);
        // boy1.rotateImage(75);

        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return true;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        // boy1.flipImage(true, false);
        boy1.rotate();
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }
}//end of class
