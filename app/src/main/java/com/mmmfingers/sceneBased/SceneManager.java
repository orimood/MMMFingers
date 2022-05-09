package com.mmmfingers.sceneBased;

import android.graphics.Canvas;
import android.view.View;

import java.util.Stack;

public class SceneManager {

    private final Stack<Scene> scenes;
    private final Stack<PopUp> popups;

    public SceneManager() {
        scenes = new Stack<Scene>();
        popups = new Stack<PopUp>();
    }

    public void update() {
        if (!popups.isEmpty()) {
            popups.peek().update();
        } else
            scenes.peek().update();
    }

    public void initialize(View view){
        for (Scene scene: scenes) {
            scene.initialize(view);
        }

        activate();
    }

    public void draw(Canvas canvas) {
        if (!scenes.isEmpty()) {
            scenes.peek().draw(canvas);
        }

        if (!popups.isEmpty()) {
            popups.peek().draw(canvas);
        }
    }

    public void receiveTouch(int action, int xPosition, int yPosition) {
        if (!popups.isEmpty()) {
            popups.peek().receiveTouch(action, xPosition, yPosition);
        } else
            scenes.peek().receiveTouch(action, xPosition, yPosition);
    }

    public void addScene(Scene scene) {
        scenes.push(scene);
    }

    public void openPopUp(PopUp popUp) {
        popups.push(popUp);
    }

    public void closePopUp(PopUp popUp) {
        if (!popups.isEmpty()) {
            popups.pop();
        }
    }

    public void nextScene() {
        if (!scenes.isEmpty()) {
            deactivate();
            scenes.pop();
        }
        activate();
    }

    public void deactivate() {
        if (!scenes.isEmpty()) {
            Scene current = scenes.peek();
            if (current != null) {
                current.deactivate();
            }
        }
    }

    public void activate() {
        if (!scenes.isEmpty()) {
            Scene current = scenes.peek();
            if (current != null) {
                current.activate();
            }
        }
    }

    public Stack<Scene> getScenes() {
        return scenes;
    }

    public Stack<PopUp> getPopups() {
        return popups;
    }
}
