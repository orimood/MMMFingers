package com.mmmfingers;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;

import com.mmmfingers.sceneBased.Scene;

import java.util.ArrayList;
import java.util.List;

public class GameScene implements Scene {
    private final static int DISTANCE = 100;

    private final GamePanel gamePanel;

    public static String SCENE_NAME = "GAME_SCENE";

    private final List<GameObject> animatedObjects = new ArrayList<>();

    private final List<GameObject> groupobst = new ArrayList<>();

    // the background of our game
    private Background background, background1, background2;
    private Player player;
    private Square square;
    private Square square2;

    private final Teeth[] obstacles = new Teeth[6];

    private int moveX = -10;

    public GameScene(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void initialize(View view) {
        background = new Background(BitmapFactory.decodeResource(view.getResources(), R.drawable.background1),
                1, 1);
        background1 = new Background(BitmapFactory.decodeResource(view.getResources(), R.drawable.background1),
                1, 1);

        // background of the game, some background picture
        background2 = new Background(BitmapFactory.decodeResource(view.getResources(), R.drawable.background1),
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
        player = new Player(BitmapFactory.decodeResource(view.getResources(), R.drawable.player),
                1, 1);
        player.setX(Constants.ORIGINAL_SCREEN_WIDTH / 2);
        player.setY((Constants.ORIGINAL_SCREEN_HEIGHT / 4));
        player.setGirlWalkingDirection(WalkingDirection.RIGHT);
        //   animatedObjects.add(player);


        // create girl jumping object


        // create obsti objectR.drawable.obstacle
        square = new Square(BitmapFactory.decodeResource(view.getResources(), R.drawable.square),
                1, 1);
        square.setHeight(100);
        square.setWidth(100);
        square.setX(DISTANCE);
        square.setY(Constants.ORIGINAL_SCREEN_HEIGHT / 2);
        groupobst.add(square);

        square2 = new Square(BitmapFactory.decodeResource(view.getResources(), R.drawable.square),
                1, 1);
        square2.setHeight(100);
        square2.setWidth(100);
        square2.setX(DISTANCE + 500);
        square2.setY(Constants.ORIGINAL_SCREEN_HEIGHT / 2);
        groupobst.add(square2);

        for (int i = 0; i < obstacles.length; i++) {
            int rotateAngel = gamePanel.getGameLogic().random.nextInt(10) + 3;
            int distance = GamePanel.getHEIGHT() / (obstacles.length - 1);
            obstacles[i] = new Teeth(BitmapFactory.decodeResource(view.getResources(), R.drawable.obstacle_sprite1),
                    15, 15, i * -distance, rotateAngel, distance);
            animatedObjects.add(obstacles[i]);
        }
    }

    @Override
    public void terminate() {

    }

    @Override
    public void receiveTouch(int action, int xPosition, int yPosition) {
        switch (action) {
            case MotionEvent.ACTION_MOVE:
                player.setY(yPosition);
                player.setX(xPosition);
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
        for (GameObject gameObject : groupobst) {
            gameObject.update();
        }

        background1.update();
        background2.update();
        player.update();
        square.update();
        square2.update();

        square2.setX(square.getX() + 600);

        if (square.getX() < -200) {
            moveX = -moveX;
        }

        if (square.getX() > 400) {
            moveX = -moveX;
        }

        square.setX(square.getX() + moveX);

        if (gamePanel.getGameLogic().collision(player, square)) {
            gamePanel.getGameLogic().decScore(1);
        }
        if (gamePanel.getGameLogic().collision(player, square2)) {
            gamePanel.getGameLogic().decScore(1);
        }

        for (GameObject gameObject : animatedObjects) {
            if (gamePanel.getGameLogic().collision(player, (AnimatedSpritesObject) gameObject)) {
                gamePanel.getGameLogic().decScore(1);
            }
        }
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
        for (GameObject gameObject : groupobst) {
            gameObject.draw(canvas);
        }

        square.draw(canvas);
        square2.draw(canvas);

        player.draw(canvas);

        gamePanel.getGameLogic().showStateMessage(canvas);
    }

    @Override
    public void resetAll() {

    }

    @Override
    public void activate() {

    }

    @Override
    public void deactivate() {

    }
}
