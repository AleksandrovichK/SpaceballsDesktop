package com.company;

import java.awt.*;

class Circle {
     private int x;
     private int y;

     private int width;
     private int height;

     private double speedw;
     private double speedh;

     private int borderHeight=800;
     private int borderWidth=800;

     private boolean isStopped;

     private Color color;

     Circle(int x, int y, int width, int height, double speedw, double speedh, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speedw = speedw;
        this.speedh = speedh;
        this.color = color;
        this.isStopped = false;
    }
     void toMove(){
         if ((Math.abs(speedw) < 0.01 && !isStopped) || (Math.abs(speedh) < 0.01 && !isStopped)) {speedw=0;speedh=0; isStopped = true;}

         if (x < 0.3*width-speedw)              speedw=-0.9*speedw;
         if (y < 10*height-speedh)               speedh=-0.9*speedh;
         if (x > borderWidth-2*width-speedw)      speedw=-0.9*speedw;
         if (y > borderHeight-2*height-speedh)    speedh=-0.9*speedh;


         x = x +(int) speedw;
         y = y +(int) speedh;

     }

     Color getColor() {


        return color;
    }

    public void setX(int x) {

        this.x = x;
    }

    public void setY(int y) {

        this.y = y;
    }

    int getX() {

        return x;
    }
     int getY() {

         return y;
    }
     int getWidth() {

         return width;
    }
     int getHeight() {

         return height;
    }

     void setSpeedw(double speedw) {

        this.speedw = speedw;
    }
     void setSpeedh(double speedh) {

        this.speedh = speedh;
    }

     double getSpeedw() {

        return speedw;
    }
     double getSpeedh() {

        return speedh;
    }
}
