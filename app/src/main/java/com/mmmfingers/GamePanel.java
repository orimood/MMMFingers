package com.mmmfingers;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.RequiresApi;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GamePanel extends SurfaceView
        implements
        SurfaceHolder.Callback{

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

    Player player;
    Square square;
    Square square2;
    Teeth obstacle2[] = new Teeth[6];


    List<GameObject> animatedObjects = new ArrayList<>();

    int groupx = 100;

    List<GameObject> groupobst = new ArrayList<>();
    // TODO for later use for game score
    private int score;

    //the random class
    private Random rnd = new Random();



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


        // create GameLogic OBJECT
        gameLogic = new GameLogic(this);

        // create thread OBJECT
        gameThread = new GameThread(getHolder(), this);


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




        // background of the game, some background picture
        background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.background1),
                1, 1);
        background1 = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.background1),
                1, 1);
  //      animatedObjects.add(background1);

        // background of the game, some background picture
        background2 = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.background1),
                1, 1);
        background2.setY(-background2.height);
     //   animatedObjects.add(background2);




        /**
         * Here we create animated objects:
         * in this command we pass:
         * the image,
         * numberOfSprites
         * and, rowLength
         */


        // create girl object
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.player),
                1, 1);
        player.setX(Constants.ORIGINAL_SCREEN_WIDTH / 2);
        player.setY((Constants.ORIGINAL_SCREEN_HEIGHT / 4));
        player.setGirlWalkingDirection(WalkingDirection.RIGHT);
     //   animatedObjects.add(player);


        // create girl jumping object


        // create obsti objectR.drawable.obstacle
        square = new Square(BitmapFactory.decodeResource(getResources(), R.drawable.square),
                 1, 1);
        square.setHeight(100);
        square.setWidth(100);
        square.setX(groupx);
        square.setY(Constants.ORIGINAL_SCREEN_HEIGHT / 2);
        groupobst.add(square);

        square2 = new Square(BitmapFactory.decodeResource(getResources(), R.drawable.square),
                1, 1);
        square2.setHeight(100);
        square2.setWidth(100);
        square2.setX(groupx+ 500);
        square2.setY(Constants.ORIGINAL_SCREEN_HEIGHT / 2);
        groupobst.add(square2);


        for (int i = 0; i < obstacle2.length; i++) {
            int rotateAngel = rnd.nextInt(10) + 3;
            int distance = GamePanel.HEIGHT / (obstacle2.length - 1);
            obstacle2[i] = new Teeth(BitmapFactory.decodeResource(getResources(), R.drawable.obstacle_sprite1),
                    15, 15, i * -distance, rotateAngel, distance);
            animatedObjects.add(obstacle2[i]);
        }

        // play game start sound



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

    int movesq1 = -10;
    int movesq2 = +10;
    public void update() {
        // if game started
        if (isPlaying) {
            // TODO to be implemented later - background music of our game

// TODO: 11/10/2021 fix objects?
            for (GameObject gameObject : animatedObjects) {
                gameObject.update();
            }
            for (GameObject gameObject : groupobst) {
                gameObject.update();
            }

            background1.update();
            background2.update();
            player.update();
            square.update();
            square2.update();

            square2.setX(square.getX()+600);

            if (square.getX() < -200){
                movesq1 = -movesq1;
            }

            if (square.getX() > 400){
                movesq1 = -movesq1;
            }

            square.setX(square.getX()+movesq1);


            if(gameLogic.collision(player, (AnimatedSpritesObject) square)){
                score = score -1;
            }if(gameLogic.collision(player, (AnimatedSpritesObject) square2)){
                score = score -1;
            }


            for (GameObject gameObject : animatedObjects) {
                if (gameLogic.collision(player,(AnimatedSpritesObject) gameObject)){
                    score = score -1;
                }
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
                background1.draw(canvas);
                background2.draw(canvas);

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
                for (GameObject gameObject : groupobst) {
                    gameObject.draw(canvas);
                }

                square.draw(canvas);
                square2.draw(canvas);

                player.draw(canvas);


                showStateMessage(canvas);

// TODO: 11/10/2021 fix the objects
            }
            // game not stared, so show game start message
            else {
                // set background color
                canvas.drawColor(Color.argb(50, 0, 102, 102));
                canvas.scale(scaleFactorX * scaleFactorXMul, scaleFactorY * scaleFactorYMul);

                // set game start message on middle of x coordinate of the screen

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
    public void showStateMessage(Canvas canvas) {
        // and create a bitmap ref for panel image, on this panel we show a message
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setTextSize(80);
        // set text to Bold
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));
        // write in the message

        canvas.drawText("" + score, 100, 100, paint);



    }// end drawText

    /**
     * Sounds
     */

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




    public Player getGirl1() {
        return player;
    }

    public void setplayer(Player player) {
        this.player = player;
    }



    /**
     * Gesture methods
     *
     * @param motionEvent
     * @return
     */






}//end of class
