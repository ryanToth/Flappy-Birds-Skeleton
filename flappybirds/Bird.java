/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package flappybirds;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import javax.swing.Timer;

/**
 *
 * @author Ryan
 */
public class Bird implements ActionListener, KeyListener {

    Timer t;
    long time = System.currentTimeMillis();
    double width = 50, height = 30;
    Rectangle[] bounds;
    double x, y, velx = 0, vely;
    double maxY;
    boolean dead = false;
    boolean dropping = false;
    
    public Bird(Timer t, double x, double y, double maxY) {
        this.t = t;
        this.x = x;
        this.y = y;
        this.maxY = maxY;
        
        bounds = new Rectangle[] {new Rectangle((int)x,(int)y+10,3,10),new Rectangle((int)x+3,(int)y+5,5,20),
                new Rectangle((int)x+8,(int)y+2,3,26),new Rectangle((int)x+11,(int)y,25,30),new Rectangle((int)x+36,(int)y+2,5,26),
                new Rectangle((int)x+41,(int)y+5,5,20),new Rectangle((int)x+47,(int)y+10,3,10)};
    }
    
    public void paint(Graphics g) {
        
        Graphics2D g2 = (Graphics2D) g;
        
        g2.setColor(Color.CYAN);
        
        if (!dropping)
            g2.fill(new Ellipse2D.Double((int)x, (int)y,(int) width, (int)height));
        
        else 
            g2.fill(new Ellipse2D.Double((int)x+width-height, (int)y-height,(int) height, (int)width));
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {

        vely = 0.002*(System.currentTimeMillis() - time) - 1;
        
        if (System.currentTimeMillis() - time > 1000) dropping = true;
        else dropping = false;
        
        if ((y + vely < maxY - height && !dropping) || (y + vely < maxY + height - width && dropping))
            y += vely;
        
        else dead = true;
        
        if (!dropping) {
            bounds = new Rectangle[] {new Rectangle((int)x,(int)y+10,3,10),new Rectangle((int)x+3,(int)y+5,5,20),
                    new Rectangle((int)x+8,(int)y+2,3,26),new Rectangle((int)x+11,(int)y,25,30),new Rectangle((int)x+36,(int)y+2,5,26),
                    new Rectangle((int)x+41,(int)y+5,5,20),new Rectangle((int)x+47,(int)y+10,3,10)};
        }
        
        else {
            bounds = new Rectangle[] {new Rectangle((int)(x+10+width-height),(int)(y-height),10,3),new Rectangle((int)(x+5+width-height),(int)(y+3-height),20,5),
                    new Rectangle((int)(x+2+width-height),(int)(y+8-height),26,3),new Rectangle((int)(x+width-height),(int)(y+11-height),30,25),
                    new Rectangle((int)(x+2+width-height),(int)(y+36-height),26,5), new Rectangle((int)(x+5+width-height),(int)(y+41-height),20,5),
                    new Rectangle((int)(x+10+width-height),(int)(y+47-height),10,3)};
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
