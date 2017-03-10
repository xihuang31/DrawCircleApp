package com.example.huangxi.drawcircleapp;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.Display;

/**
 * Created by huangxi on 2/20/17.
 */

public class Circle {
    private float radius,centerX,centerY;
    private int color;
    private Paint paint = new Paint();
    protected boolean isMoving = false;
    private float xVelocity,yVelocity;
    private int xdirect,ydirect;
    private int boundLeft,boundRigth,boundTop,boundBottom;

    private float maxmiumRadius;

    public void setDirection(int xdirect,int ydirect){
        this.xVelocity = xdirect;
        this.yVelocity = ydirect;
    }

    public void setXdirect(int xdirect) {
        this.xdirect = xdirect;
    }

    public void setYdirect(int ydirect) {
        this.ydirect = ydirect;
    }

    public void setBound(int boundBottom,int boundTop,int boundLeft,int boundRigth){
        this.boundBottom = boundBottom;
        this.boundLeft = boundLeft;
        this.boundRigth = boundRigth;
        this.boundTop = boundTop;
    }

    public void setVelocity(float xVelocity,float yVelocity){
        this.xVelocity = xVelocity/100;
        this.yVelocity = yVelocity/100;
    }

    public void setCenter(float x ,float y){
        this.centerY = y;
        this.centerX = x;
        maxmiumRadius = Math.min(Math.min(x,y),Math.min(boundBottom-y,boundRigth-x));
    }

    public void setRadius(float radius){
        if(radius<maxmiumRadius)
            this.radius = radius;
        else
            this.radius = maxmiumRadius;
    }

    public void setColor(int c){
        this.color = c;
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setColor(this.color);
    }

    public Circle(){

    }

    public void calculateRaidu(float endX,float endY){
        this.radius =  (float)Math.sqrt(Math.pow((endX-centerX),2)+ Math.pow(endY-centerY,2));
        setRadius(this.radius);
    }

//    public Circle(float startX, float startY,float endX,float endY, int c ){
//        setCenter(startX,startY);
//        this.radius =  (float)Math.sqrt(Math.pow((endX-startX),2)+ Math.pow(endY-startY,2));
//        setColor(c);
//
//    }

    public boolean isCoverPoint(float x,float y){
        float distance = (float)Math.sqrt(Math.pow((x-centerX),2)+ Math.pow(y-centerY,2));
        if(distance <= radius){
            return true;
        }else{
            return  false;
        }
    }

    public void drawOn(Canvas canvas){
        canvas.drawCircle(centerX,centerY,radius,paint);
        if(isMoving){
            movingCenter();
        }
    }

    private void movingCenter(){
        if(centerX - radius <= boundLeft  ){
            xVelocity = Math.abs(xVelocity)/2;
        }
        if(centerY - radius <= boundTop){
            yVelocity = Math.abs(yVelocity)/2;
        }
        if(centerX + radius >= boundRigth){
            if(xVelocity >=0 )
                xVelocity *= -1;
            xVelocity *= 0.5;
        }
        if(centerY + radius >= boundBottom){
            if(yVelocity >=0)
                yVelocity *= -1;
            yVelocity *= 0.5;
        }


        this.centerX = centerX + xVelocity;
        this.centerY = centerY + yVelocity;
    }
}
