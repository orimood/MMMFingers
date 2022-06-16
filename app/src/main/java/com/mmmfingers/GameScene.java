package com.mmmfingers;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

public class GameScene implements Scene {
    private final static int DISTANCE = 200;

    private final GamePanel gamePanel;

    public static String SCENE_NAME = "GAME_SCENE";

    private final List<GameObject> animatedObjects = new ArrayList<>();

    // the background of our game
    private Background background, background1, background2;
    private Player player;

    private final Obstacle[] animatedObstacles = new Obstacle[6];
    private final Obstacle[] simpleObstacles = new Obstacle[2];

    private int moveX = -10;

    public GameScene(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        background = new Background(BitmapFactory.decodeResource(gamePanel.getResources(), R.drawable.background1),
                1, 1);
        background1 = new Background(BitmapFactory.decodeResource(gamePanel.getResources(), R.drawable.background1),
                1, 1);

        // background of the game, some background picture
        background2 = new Background(BitmapFactory.decodeResource(gamePanel.getResources(), R.drawable.background1),
                1, 1);
        background2.setY(-background2.height);


        /**
         * Here we create animated objects:
         * in this command we pass:
         * the image,
         * numberOfSprites
         * and, rowLength
         */

        // create girl object
        player = new Player(BitmapFactory.decodeResource(gamePanel.getResources(), R.drawable.player),
                1, 1);
        player.setX((Constants.GAME_SCREEN_WIDTH - player.getWidth()) / 2);
        player.setY(Constants.GAME_SCREEN_HEIGHT - (Constants.GAME_SCREEN_HEIGHT / 4));

        // create square obstacles
        for (int i = 0; i < simpleObstacles.length; i++) {
            int distance = GamePanel.getHEIGHT() / (animatedObstacles.length - 1);
            simpleObstacles[i] = new Obstacle(BitmapFactory.decodeResource(gamePanel.getResources(), R.drawable.s_square),
                    1, 1, i * -distance, distance, true);
            animatedObjects.add(simpleObstacles[i]);
        }

        // create animated teeth obstacles
        for (int i = 0; i < animatedObstacles.length; i++) {
            int distance = GamePanel.getHEIGHT() / (animatedObstacles.length - 1);
            animatedObstacles[i] = new Obstacle(BitmapFactory.decodeResource(gamePanel.getResources(), R.drawable.obstacle_sprite2),
                    16, 16, i * -distance, distance, false);
            animatedObjects.add(animatedObstacles[i]);
        }
    }


    @Override
    public void terminate() {

    }

    @Override
    public void receiveTouch(MotionEvent touch) {

        int action = touch.getAction();

        switch (action) {
            case MotionEvent.ACTION_MOVE:
                player.setY((int) touch.getY());
                player.setX((int) touch.getX());
                break;
            case MotionEvent.ACTION_DOWN:
                break;
            // TODO 2
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
    }

    @Override
    public void update() {

        // TODO to be implemented later - background music of our game
        // TODO: 11/10/2021 fix objects?

        for (GameObject gameObject : animatedObjects) {
            gameObject.update();
        }

        background1.update();
        background2.update();
        player.update();

        for (GameObject gameObject : animatedObjects) {
            if (GameLogic.getInstance().collision(player, (AnimatedSpritesObject) gameObject)) {
                gamePanel.endGame();
                return;
            }
        }

        GameLogic.getInstance().incScore(1);
    }

    @Override
    public void draw(Canvas canvas) {
        // draw the static background (TODO: fix)
        background.draw(canvas);
        background1.draw(canvas);
        background2.draw(canvas);

        for (GameObject gameObject : animatedObjects) {
            gameObject.draw(canvas);
        }

        player.draw(canvas);

        GameLogic.getInstance().showStateMessage(canvas);
    }

    @Override
    public void resetAll() {

    }

}
