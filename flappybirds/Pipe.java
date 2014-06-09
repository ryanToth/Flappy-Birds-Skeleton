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
import javax.swing.Timer;

/**
 *
 * @author Ryan
 */
public class Pipe implements ActionListener {

    double x, y, velx = -0.5, topOpeningY, bottomOpeningY, frameSize;
    Rectangle pointBoundary;
    Timer t;
    
    public Pipe(Timer t, double x, double y, double frameSize) {
        
        this.t = t;
        this.x = x;
        this.y = y;
        this.frameSize = frameSize;
        
        topOpeningY = Math.random()*(frameSize-160) + 30;
        bottomOpeningY = topOpeningY + 90;
        
        pointBoundary = new Rectangle((int)x+45,(int)topOpeningY,20,90);
        
    }
    
    public void actionPerformed(ActionEvent e) {
        x += velx;
        
        if (pointBoundary != null)
            pointBoundary = new Rectangle((int)x+45,(int)topOpeningY,20,90);
    }
    
    public void paint(Graphics g) {
        
        Graphics2D g2 = (Graphics2D) g;
        
        g2.setColor(Color.ORANGE);
        g2.drawRect((int)x, (int)y, 90, (int)topOpeningY);
        g2.drawRect((int)x, (int)bottomOpeningY, 90, (int)frameSize - (int)bottomOpeningY);
    }
    
    public boolean getPoint(Rectangle[] bird) {
        
        boolean point = false;
        
        for (int i = 0; i < bird.length && pointBoundary != null; i++) {
            if (bird[i].intersects(pointBoundary)) {
                point = true;
                pointBoundary = null;
            }
        }
        
        return point;
    }
    
    public boolean didHit(Rectangle[] bird) {
        
        boolean dead = false;
        
        for (int i = 0; i < bird.length && !dead; i++) {
            if (bird[i].intersects(new Rectangle((int)x, (int)y, 90, (int)topOpeningY)) ||
                    bird[i].intersects(new Rectangle((int)x, (int)bottomOpeningY, 90, (int)frameSize - (int)bottomOpeningY)))
                dead = true;
        }
        
        return dead;
    }
    
}
