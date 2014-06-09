/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package flappybirds;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author Ryan
 */
public class MovingGround implements ActionListener {
    
    double x, y, velx = -0.5;
    double length;
    Timer t;
    
    public MovingGround(Timer t, double x, double y, double length) {
        this.x = x;
        this.y = y;
        this.length = length;
        this.t = t;
    }
    
    public void paint(Graphics g) {
        
        Graphics2D g2 = (Graphics2D) g;
        
        g2.setColor(Color.green);
        g2.fillRect((int)x,(int)y,(int)length,20);
        
        g2.setColor(Color.black);
        
        for (int i = 0; i < length-10; i += 20)
            g2.drawLine((int)x + i, (int)y, (int)x+20+i, (int)y+20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        x += velx;
    }
    
}
