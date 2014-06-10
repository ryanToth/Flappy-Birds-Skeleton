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
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.Timer;

/**
 *
 * @author Ryan
 */
public class MovingGround implements ActionListener {
    
    double x, y, velx = -0.5;
    double length;
    Timer t;
    Image sky;
    Image grass;
    
    public MovingGround(Timer t, double x, double y, double length) {
        this.x = x;
        this.y = y;
        this.length = length;
        this.t = t;
        
        URL imageurl;
        
        imageurl = getClass().getResource("/flappybirds/purpsky.png");
        sky = Toolkit.getDefaultToolkit().getImage(imageurl);
        
        imageurl = getClass().getResource("/flappybirds/grass.png");
        grass = Toolkit.getDefaultToolkit().getImage(imageurl);
    }
    
    public void paint(Graphics g) {
        
        Graphics2D g2 = (Graphics2D) g;
        
        g2.drawImage(sky,(int)x, (int)0, (int)length, 560, null);
        g2.drawImage(grass,(int)x,(int)y,(int)length,140,null);
        /*
        g2.setColor(Color.green);
        g2.fillRect((int)x,(int)y,(int)length,20);
        
        g2.setColor(Color.black);
        
        for (int i = 0; i < length-10; i += 20)
            g2.drawLine((int)x + i, (int)y, (int)x+20+i, (int)y+20);
                */
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        x += velx;
    }
    
}
