package com.example.huangxi.drawcircleapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.TimerTask;

/**
 * Created by huangxi on 2/20/17.
 */

public class DrawCircleView extends View implements View.OnTouchListener{

    ArrayList<Circle> circles = new ArrayList<Circle>();
    ArrayList<Circle> movingCircles = new ArrayList<Circle>();
    ArrayList<Circle> deleteCircles = new ArrayList<Circle>();
    ArrayList<Circle> newMovingCircles = new ArrayList<Circle>();
    private Mode mode;
    private int color;
    private boolean isMoving = false;
    private Circle newCircle ;
    private VelocityTracker velocityTracker;
    private float startX,startY,endX,endY;
    protected int boundLeft,boundRigth,boundTop,boundBottom;


    public void setMode(Mode mode) {
        this.mode = mode;
        if(mode == Mode.Draw || mode == Mode.Delete){
            stopMovingCircles();
            this.isMoving = false;
        }else{
            startMovingCircles();
            this.isMoving = true;
            invalidate();
        }
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public Mode getMode() {
        return mode;
    }

    public  DrawCircleView(Context context, AttributeSet attr){
        super(context,attr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (Circle each : circles)
            each.drawOn(canvas);
        if(isMoving){
            this.postDelayed(drawing,100);
        }
    }

    TimerTask drawing = new TimerTask() {
        @Override  public void run() {
            invalidate();
        }
    };


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(mode ==Mode.Draw){
            return createCircle(event);
        }else if(mode==Mode.Delete){
            return deleteCircle(event);
        }else if(mode==Mode.Move){
            return movingCircles(event);
        }else{
            return false;
        }
    }

    private void stopMovingCircles(){
        for(Circle each:movingCircles){
            each.isMoving = false;
        }
    }

    private void startMovingCircles(){
        for(Circle each:movingCircles){
            each.isMoving = true;
        }

    }
    private boolean deleteCircle(MotionEvent event){
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                for(Circle each:circles){
                    if(each.isCoverPoint(event.getX(),event.getY())){
                        deleteCircles.add(each);
                    }
                }

                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                for(int i=0;i<deleteCircles.size();i++){
                    if(!deleteCircles.get(i).isCoverPoint(event.getX(),event.getY())){
                        deleteCircles.remove(i);
                        i--;
                    }
                }
                circles.removeAll(deleteCircles);
                this.invalidate();
                break;
            default:
        }
        return true;
    }

    private boolean createCircle(MotionEvent event){
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("rew", "down");
                newCircle = new Circle();
                newCircle.setBound(boundBottom,boundTop,boundLeft,boundRigth);
                newCircle.setCenter(event.getX(),event.getY());
                newCircle.setColor(this.color);//blac
                circles.add(newCircle);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("rew", "move " + event.getHistorySize() );
                newCircle.calculateRaidu(event.getX(),event.getY());
                this.invalidate();
                break;
            case MotionEvent.ACTION_UP:
                Log.i("rew", "UP");
                newCircle.calculateRaidu(event.getX(),event.getY());
                this.invalidate();
                break;
            default:
        }
        return true;
    }

    private boolean movingCircles(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                newMovingCircles.clear();
                for(Circle each:circles) {
                    if (each.isCoverPoint(event.getX(), event.getY())) {
                        newMovingCircles.add(each);
                    }
                }
                velocityTracker = VelocityTracker.obtain();
                velocityTracker.addMovement(event);
                break;
            case MotionEvent.ACTION_MOVE:
                velocityTracker.addMovement(event);

                break;
            case MotionEvent.ACTION_UP:
                velocityTracker.computeCurrentVelocity(1000);
                Log.i("rew", "X vel " + velocityTracker.getXVelocity() + " Y vel " + velocityTracker.getYVelocity());
                setUpMovingVelocity(velocityTracker.getXVelocity(),velocityTracker.getYVelocity());
                movingCircles.addAll(newMovingCircles);
                startMovingCircles();
                velocityTracker.recycle();
                velocityTracker = null;
                this.isMoving = true;
                this.invalidate();
                break;
            default:
                break;
        }
        return true;
    }

    private void setUpMovingVelocity(float xVelocity,float yVelocity){
        for(Circle each:newMovingCircles){
            each.setVelocity(xVelocity,yVelocity);
        }
    }

}
