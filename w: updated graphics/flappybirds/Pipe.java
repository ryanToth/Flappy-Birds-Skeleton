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
import java.net.URL;
import javax.swing.Timer;

/**
 *
 * @author Ryan
 */
public class Pipe implements ActionListener {

    double x, y, velx = -0.5, topOpeningY, bottomOpeningY, frameSize;
    Rectangle pointBoundary;
    Timer t;
    Image upPipe;
    Image downPipe;
    Image flagPole;
    
    public Pipe(Timer t, double x, double y, double frameSize) {
        
        this.t = t;
        this.x = x;
        this.y = y;
        this.frameSize = frameSize;
        
        topOpeningY = Math.random()*(frameSize-220) + 62;
        bottomOpeningY = topOpeningY + 95;
        
        pointBoundary = new Rectangle((int)x+45,(int)topOpeningY,20,100);
        
        URL imageurl;
        
        imageurl = getClass().getResource("/flappybirds/upside down flag.png");
        upPipe = Toolkit.getDefaultToolkit().getImage(imageurl);
        
        imageurl = getClass().getResource("/flappybirds/flag.png");
        downPipe = Toolkit.getDefaultToolkit().getImage(imageurl);
        
        imageurl = getClass().getResource("/flappybirds/flag pole.png");
        flagPole = Toolkit.getDefaultToolkit().getImage(imageurl);
        
    }
    
    public void actionPerformed(ActionEvent e) {
        x += velx;
        
        if (pointBoundary != null)
            pointBoundary = new Rectangle((int)x+45,(int)topOpeningY,20,95);
    }
    
    public void paint(Graphics g) {
        
        Graphics2D g2 = (Graphics2D) g;

        /*
        g2.setColor(Color.black);
        g2.fillRect((int)x+4, (int)y-4, 90, (int)topOpeningY);
        g2.fillRect((int)x+4, (int)bottomOpeningY-4, 90, (int)frameSize - (int)bottomOpeningY);
        
        g2.setColor(Color.ORANGE);
        g2.fillRect((int)x, (int)y, 90, (int)topOpeningY);
        g2.fillRect((int)x, (int)bottomOpeningY, 90, (int)frameSize - (int)bottomOpeningY);
        */
        
        g2.drawImage(upPipe, (int)x, (int)topOpeningY-62, 90, 62, null);
        g2.drawImage(flagPole, (int)x, 0, 90, (int)topOpeningY-62 ,null);
        
        g2.drawImage(downPipe, (int)x, (int)bottomOpeningY, 90, 62, null);
        g2.drawImage(flagPole, (int)x, (int)bottomOpeningY+62, 90, (int)frameSize-62-(int)bottomOpeningY, null);
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
