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

private Vector <Circle> circles;
private double speed = 4;
private boolean isRun1 = true;
private boolean isRun2 = true;
private Thread thread;
private Thread spiraleThread;

private Runnable runnable = new Runnable() {
    @Override
    public void run() {
                    while (true)
                        if (isRun1){
                            for (Circle circle : circles) circle.toMove();

                            repaint();
                            try {Thread.sleep((long) 12);} catch (InterruptedException e) {e.printStackTrace();}
                               }
    }
};
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



MainFrame() throws InterruptedException, IOException {
     settings();

     circles = new Vector<Circle>();

     for (int i=0; i< 70; i++)
         for (int j=0; j< 70; j++)
             circles.addElement(new Circle(i*11, j*11, 2,2, speed, speed, new Color((50+i+j)%255, (i+2*j)%255, (i+j)%255)));

             thread = new Thread(runnable);
             thread.start();
    }
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
private void settings() throws IOException {
    this.setLayout(null);

    this.setBounds(10 * Toolkit.getDefaultToolkit().getScreenSize().width / 100, 5 * Toolkit.getDefaultToolkit().getScreenSize().height / 100, 800, 800);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("src\\com\\company\\pics\\space1new.jpg")))));
    this.setFont(new Font("Consolas", Font.BOLD, 20));

    this.setVisible(true);
    this.addMouseListener(new TouchListener());
}
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
class TouchListener implements MouseListener{
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        isRun1 = false;
        try {Thread.sleep((long)12);} catch (InterruptedException e1) {e1.printStackTrace();}
        thread.interrupt();


        spiraleThread = new Thread(spiraleRunnable);
        spiraleThread.start();

        // for (Circle circle : circles) toSetSpeed(circle, e);
       //try {toMoveBySpirale(e);} catch (InterruptedException e1) {e1.printStackTrace();}

      // isRun1 = !isRun1;
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
}


