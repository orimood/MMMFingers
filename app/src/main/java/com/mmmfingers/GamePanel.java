package com.mmmfingers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;

import com.mmmfingers.sceneBased.PopUp;
import com.mmmfingers.sceneBased.Scene;
import com.mmmfingers.sceneBased.SceneManager;

import java.util.HashMap;
import java.util.Map;

public class GamePanel extends SurfaceView
        implements SurfaceHolder.Callback {

    /**
     * Now we will set our game screen width and height
     * display size in pixels
     * the screen upper left corner is 0,0
     */
    private static int WIDTH;
    private static int HEIGHT;

    private final SceneManager sceneManager = new SceneManager();
    private final StartScene startScene;
    private final GameScene gameScene;
    private final EndScene endScene;

    private Map<String, Scene> sceneDictionary = new HashMap<>();
    private Map<String, PopUp> popUpDictionary = new HashMap<>();

    /**
     * ******************************************************************
     * game logic class - the "BRAIN" of our game, rules and more ...
     * very important class - all the game algorithms are there
     */
    private final GameLogic gameLogic;
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
    private final GameThread gameThread;
    /**
     * ******************************************************************
     */

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
    private final float scaleFactorXMul;
    private final float scaleFactorYMul;

    public View parentView;

    //lets create the constructor of our new class,that is going to help us calling objects and methods!
    public GamePanel(View parentView, int WIDTH, int HEIGHT) {

        /**
         context we receive from our activity,
         by the COMMAND:
         setContentView(new GamePanel(this, WIDTH, HEIGHT)) - see the MainActivity code;
         context - is the context (HEKSHER in hebrew) of our activity
         and we pass the context to the super class - which is "SurfaceView", cause it needs it
         to do it staff.
         */

        super(parentView.getContext());

        this.parentView = parentView;

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

        sceneManager.addScene(new StartScene(this));

        // create GameLogic OBJECT
        gameLogic = new GameLogic();

        // create scenes
        startScene = new StartScene(this);
        gameScene = new GameScene(this);
        endScene = new EndScene(this);

        goToStartScreen();

        // create thread OBJECT
        gameThread = new GameThread(getHolder(), this);

        getHolder().addCallback(this);

        /**
         make gamePanel focusable so it can handle events.
         setFocusable mainly used for enable/disable view's focus event
         on both touch mode and keypad mode(using up/down/next key).
         */
        setFocusable(true);
    }

    /**
     * This method tells us that the surface is READY,
     * so we can put object and draw them on it !
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        sceneManager.initialize(this);

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

        sceneManager.receiveTouch(action, xPosition, yPosition);

        // if game started
//        switch (gameLogic.getGameState()) {
//            case GAME_PLAYING_STATE: {
//                gameScene.receiveTouch(action, xPosition, yPosition);
//                break;
//            }
//            case GAME_START_STATE: {
//                // start the game  after first tap on the screen
//                if (action == MotionEvent.ACTION_UP)
//                    gameLogic.setGameState(GameState.GAME_PLAYING_STATE);
//            }
//        }

        return true;
    }

    /**
     * in content view GamePanel we must constantly update our image
     * GamePanel cooperates with our thread
     * So our game run a new game loop ....
     */
    public void update() {
        sceneManager.update();

        // if game started
//        if (gameLogic.getGameState() == GameState.GAME_PLAYING_STATE) {
//            gameScene.update();
//        }
    }

    /**
     * GamePanel cooperates with our thread
     * So our game draw new screen every 33 milli seconds
     * GamePanel draw method , SurfaceView override
     */
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

            /**
             * set the drawing scaled to the screen size WIDTH & HEIGHT, using the defaults
             * the defaults of our screen are in the Constants class
             * in this case: ORIGINAL_SCREEN_WIDTH, and ORIGINAL_SCREEN_HEIGHT
             *
             */
            canvas.scale(scaleFactorX * scaleFactorXMul, scaleFactorY * scaleFactorYMul);

            sceneManager.draw(canvas);
            // if game started
//            switch (gameLogic.getGameState()) {
//                case GAME_PLAYING_STATE: {
//                    gameScene.draw(canvas);
//                    break;
//                }
//                // game not stared, so show game start message
//                case GAME_START_STATE: {
//                    // set background color
//                    canvas.drawColor(Color.argb(50, 0, 102, 102));
//
//                    break;
//                }
//            }

            // restore the saves canvas state back
            canvas.restoreToCount(savedState);
        }
    }

    public void nextScene() {
        sceneManager.nextScene();
    }

    public void startNewGame() {
        gameLogic.resetScore();
        nextScene();
        sceneManager.addScene(endScene);
        sceneManager.addScene(gameScene);
    }

    public void goToStartScreen() {
        gameLogic.resetScore();
        nextScene();
        sceneManager.addScene(endScene);
        sceneManager.addScene(gameScene);
        sceneManager.addScene(startScene);
    }

    public void addScene(String sceneName){
        sceneManager.addScene(sceneDictionary.get(sceneName));
        sceneManager.getScenes().peek().resetAll();

    }

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

    public GameLogic getGameLogic() {
        return gameLogic;
    }

    public View getParentView() {
        return parentView;
    }
}//end of class
