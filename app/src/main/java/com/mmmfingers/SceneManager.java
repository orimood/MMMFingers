package com.mmmfingers;


import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.Stack;

/**
 * Study Android:
 * MODI'IN, YACHAD high-school.
 *
 * @author Ori Sinvani.
 * @version version 2.00
 * @since version 2.00
 */

//not currently in use, but if in future i want to add more levels
public class SceneManager {

    private final Stack<Scene> scenes;


    public SceneManager() {
        scenes = new Stack<Scene>();

    }


    public void update() {
        if (!scenes.isEmpty())
            scenes.peek().update();

    }

    public void draw(Canvas canvas) {
        scenes.peek().draw(canvas);
    }

    public void receiveTouch(MotionEvent touch) {
        if (!scenes.isEmpty())
            scenes.peek().receiveTouch(touch);

    }

    public void addScene(Scene scene) {
        scenes.push(scene);
    }


    public void removeScene() {
        scenes.pop();
    }

    public Stack<Scene> getScenes() {
        return scenes;
    }

}
