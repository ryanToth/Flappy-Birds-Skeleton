/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package flappybirds;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.net.URL;
import javax.swing.Timer;

/**
 *
 * @author Ryan
 */
public class Bird implements ActionListener, KeyListener {

    Timer t;
    long time = System.currentTimeMillis();
    double width = 60, height = 50;
    Rectangle[] bounds;
    double x, y, velx = 0, vely;
    double maxY;
    boolean dead = false;
    boolean dropping1 = false;
    boolean dropping2 = false;
    Image flying1;
    Image falling1;
    Image falling2;
    
    public Bird(Timer t, double x, double y, double maxY) {
        this.t = t;
        this.x = x;
        this.y = y;
        this.maxY = maxY;
        
        URL imageurl;
        
        imageurl = getClass().getResource("/flappybirds/flying 1.png");
        flying1 = Toolkit.getDefaultToolkit().getImage(imageurl);
        
        imageurl = getClass().getResource("/flappybirds/falling 1.png");
        falling1 = Toolkit.getDefaultToolkit().getImage(imageurl);
        
        imageurl = getClass().getResource("/flappybirds/falling 2.png");
        falling2 = Toolkit.getDefaultToolkit().getImage(imageurl);
        
        bounds = new Rectangle[] {new Rectangle((int)x+31,(int)y+10,5,30),new Rectangle((int)x+36,(int)y+12,5,26),
                    new Rectangle((int)x+41,(int)y+8,5,20),new Rectangle((int)x+47,(int)y+15,3,10),
                    new Rectangle((int)x+10,(int)y+20,20,10),new Rectangle((int)x+8,(int)y+30,20,10)};
    }
    
    public void paint(Graphics g) {
        
        Graphics2D g2 = (Graphics2D) g;
        
        g2.setColor(Color.CYAN);
        
        if (!dropping1 && !dropping2)
            //g2.fill(new Ellipse2D.Double((int)x, (int)y,(int) width, (int)height));
            g2.drawImage(flying1,(int) x,(int) y, (int)width,(int) height, null);
        
        else if (dropping1 && !dropping2)
            //g2.fill(new Ellipse2D.Double((int)x+width-height, (int)y-height,(int) height, (int)width));
            g2.drawImage(falling1,(int) x,(int) y, (int)width,(int) height, null);
        
        else
            g2.drawImage(falling2,(int) (x+width-height),(int)(y), (int)height,(int) width, null);
        /*
        for (int i = 0; i < bounds.length; i++)
            g2.draw(bounds[i]);
                */
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {

        vely = 0.002*(System.currentTimeMillis() - time) - 1;
        
        if (System.currentTimeMillis() - time > 1000) dropping1 = true;
        else dropping1 = false;
        
        if (System.currentTimeMillis() - time > 1200) dropping2 = true;
        else dropping2 = false;
        
        if (y + vely < maxY - height)
            y += vely;
        
        else dead = true;
        
        if (!dropping2) {
            bounds = new Rectangle[] {new Rectangle((int)x+31,(int)y+10,5,30),new Rectangle((int)x+36,(int)y+12,5,26),
                    new Rectangle((int)x+41,(int)y+8,5,20),new Rectangle((int)x+47,(int)y+15,3,10),
                    new Rectangle((int)x+10,(int)y+20,20,10),new Rectangle((int)x+8,(int)y+30,20,10)};
        }
        
        else {
            bounds = new Rectangle[] {new Rectangle((int)(x+10+width-height),(int)(y+5),20,20),
                    new Rectangle((int)(x+15+width-height),(int)(y+25),25,30),
            new Rectangle((int)(x+5+width-height),(int)(y+35),10,20)};
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        int code = e.getKeyCode();
        
        if (code == e.VK_SPACE && y > 0 && !dead)
            time = System.currentTimeMillis();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
}
