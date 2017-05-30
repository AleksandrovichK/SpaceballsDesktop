package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

class MainFrame extends JFrame{

private int borderHeight=800;
private int borderWidth=800;

private Vector <Circle> circles;
private double speed = 4;
private boolean isRun = true;

MainFrame() throws InterruptedException, IOException {
     settings();
     toFillVector();

     repaint();
    }

private void settings() throws IOException {
    this.setLayout(null);

    this.setBounds(10 * Toolkit.getDefaultToolkit().getScreenSize().width / 100, 5 * Toolkit.getDefaultToolkit().getScreenSize().height / 100, borderWidth, borderHeight);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("src\\com\\company\\pics\\space1new.jpg")))));
    this.setFont(new Font("Consolas", Font.BOLD, 20));

    this.setVisible(true);
    this.addMouseListener(new TouchListener());
}
private void toFillVector(){
    circles = new Vector<Circle>();

    int num=100;
    int distW = borderWidth/num;
    int distH = borderHeight/num;

    for (int i=0; i< num; i++)
        for (int j=0; j< num; j++)
            circles.addElement(new Circle(i*distW, j*distH, 2,2, speed, speed, new Color((50+i+j)%255, (28+i+j)%255, (17+i+j)%255)));
}
private void toMakeWave(MouseEvent e) throws InterruptedException {
    for(Circle circle: circles){
        circle.setX(circle.getX()+15);
        circle.setY(circle.getY()+15);
    }
    repaint();

    Thread.sleep(500);

    for(Circle circle: circles){
        circle.setX(circle.getX()-15);
        circle.setY(circle.getY()-15);
    }
    repaint();
};

private void redraw (Graphics2D g){
        if (null != circles){
            synchronized (circles){
                for (Circle circle : circles){
                    g.setColor(circle.getColor());
                    g.fillOval(circle.getX(),circle.getY(),circle.getWidth(),circle.getHeight());
                }
            }
        }
    }
public void paint(Graphics g){
        BufferedImage img = new BufferedImage(this.getWidth(),this.getHeight(),BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D gr = img.createGraphics();

        redraw(gr);
        g.drawImage(img, 0, 0, this);
    }
class TouchListener implements MouseListener{
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        try {toMakeWave(e);} catch (InterruptedException e1) {e1.printStackTrace();}
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
}




/*
private Runnable spiraleRunnable = new Runnable() {
    double phi =1, iter2 = 0, k1=0.07, r=300;

        @Override
        public void run() {
            while (phi < 100000000) {
                if (isRun2) {
                    for (Circle circle : circles) {

                        circle.setX(500 - (int) (r * Math.cos(phi)));
                        circle.setY(500 - (int) ((r-iter2) * Math.sin(phi)));

                        phi += 130000;
                        iter2 += 0.0009;

                        repaint();
                    }
                  //  try {Thread.sleep((long) 5);} catch (InterruptedException e) {e.printStackTrace();}
                }
            }
        }
    };




    private void toSetSpeed(Circle circle, MouseEvent e){

    double H, W;
    double coefw, coefh;
    if (e.getY() < circle.getY() && e.getX() > circle.getX()) //  I quant
    {
        coefw =  1.01;
        coefh = -1.01;

        H = circle.getY() - e.getY();
        W = e.getX() - circle.getX();

        circle.setSpeedw(coefw*(W*Math.sqrt(((circle.getSpeedh()*circle.getSpeedh() +  circle.getSpeedw()*circle.getSpeedw())/(H*H + W*W)))));
        circle.setSpeedh(coefh*(H*circle.getSpeedw())/W);
    }


    if (e.getY() < circle.getY() && e.getX() < circle.getX()) // II quant
    {
        coefw = -1.01;
        coefh = -1.01;

        H = circle.getY() - e.getY();
        W = circle.getX() - e.getX();

        circle.setSpeedw(coefw*(W*Math.sqrt(((circle.getSpeedh()*circle.getSpeedh() +  circle.getSpeedw()*circle.getSpeedw())/(H*H + W*W)))));
        circle.setSpeedh(-coefh*(H*circle.getSpeedw())/W);
    }


    if (e.getY() > circle.getY() && e.getX() < circle.getX()) //III quant
    {
        coefw =- 1.01;
        coefh =  1.01;

        H = e.getY() - circle.getY();
        W = circle.getX()- e.getX();

        circle.setSpeedw(coefw*(W*Math.sqrt(((circle.getSpeedh()*circle.getSpeedh() +  circle.getSpeedw()*circle.getSpeedw())/(H*H + W*W)))));
        circle.setSpeedh(-coefh*(H*circle.getSpeedw())/W);
    }


    if (e.getY() > circle.getY() && e.getX() > circle.getX()) // IV quant
    {
        coefw= 1.01;
        coefh= 1.01;

        H = e.getY() - circle.getY();
        W = e.getX() - circle.getX();

        circle.setSpeedw(coefw*(W*Math.sqrt(((circle.getSpeedh()*circle.getSpeedh() +  circle.getSpeedw()*circle.getSpeedw())/(H*H + W*W)))));
        circle.setSpeedh(coefh*(H*circle.getSpeedw())/W);
    }


    //r=k*phi
    //x=r*cos(phi)
    //y=r*sin(phi)
    //
}
private void toMoveBySpirale(MouseEvent e) throws InterruptedException {

   double iter=1, phi, r, k1=0.005, k2=3000000;

  //  while (true)
   //{
    for (int time=0; time <3; time++){
        repaint();
        Thread.sleep((long) 155);

        for (Circle circle : circles) {
            phi = iter * k1; //угол
            r = k2 / iter;   //радиус

            circle.setX(500 - (int) (r * Math.cos(phi)));
            circle.setY(500 - (int) (r * Math.sin(phi)));

            iter += 20;
        }
        repaint();
        Thread.sleep((long) 155);
    }
    //}
}













    */