package com.mmmfingers;


import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.Stack;



public class SceneManager {

    private Stack<Scene> scenes;
    private Stack<PopUp> popUps;

    public SceneManager(){

        scenes = new Stack<Scene>();
        popUps = new Stack<PopUp>();

    }


    public void update(){
        if(!popUps.isEmpty())
            popUps.peek().update();
        else
            scenes.peek().update();

    }

    public void draw(Canvas canvas){
        scenes.peek().draw(canvas);
        if(!popUps.isEmpty())
            popUps.peek().draw(canvas);

    }

    public void receiveTouch(MotionEvent touch){
        if(!popUps.isEmpty())
            popUps.peek().receiveTouch(touch);
        else
            scenes.peek().receiveTouch(touch);

    }

    public void addScene(Scene scene){
        scenes.push(scene);
    }

    public void openPopUp(PopUp popUp){
        popUps.push(popUp);
    }

    public void closePopUp(){
        popUps.pop();
    }

    public void removeScene(){
        scenes.pop();
    }

    public Stack<Scene> getScenes() {
        return scenes;
    }

    public Stack<PopUp> getPopUps() {
        return popUps;
    }
}
