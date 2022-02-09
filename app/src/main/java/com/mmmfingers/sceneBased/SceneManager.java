package com.mmmfingers.sceneBased;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.Stack;

public class SceneManager {

    private Stack<Scene> scenes;
    private Stack<PopUp> popups;

    public SceneManager() {
        scenes = new Stack<Scene>();
        popups = new Stack<PopUp>();
    }

    public void update(){
        if(!popups.isEmpty()){
            popups.peek().update();
        }
        else
            scenes.peek().update();
    }

    public void draw(Canvas canvas)
    {
        scenes.peek().draw(canvas);

        if(!popups.isEmpty()){
            popups.peek().draw(canvas);
        }
    }

        public void receiveTouch(MotionEvent touch)
        {
            if(!popups.isEmpty()){
                popups.peek().receiveTouch(touch);
            }
            else
                scenes.peek().receiveTouch(touch);
        }

    public void addScene(Scene scene) {scenes.push(scene);}

    public void openPopUp(PopUp popUp) {popups.push(popUp);}

    public void closePopUp(PopUp popUp) {popups.pop();}

    public void previousScene(Scene scene) {scenes.pop();}

    public Stack<Scene> getScenes() {return scenes;}

    public Stack<PopUp> getPopups() {return popups;}



}
