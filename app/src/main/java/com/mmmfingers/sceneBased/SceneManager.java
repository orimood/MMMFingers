package com.mmmfingers.sceneBased;

import android.graphics.Canvas;

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

    public void draw(Canvas canvas) {
        scenes.peek().draw(canvas);

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
        popups.pop();
    }

    public void previousScene(Scene scene) {
        scenes.pop();
    }

    public Stack<Scene> getScenes() {
        return scenes;
    }

    public Stack<PopUp> getPopups() {
        return popups;
    }


}
