/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package flappybirds;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author Ryan
 */
public class GameFrame extends JPanel implements ActionListener {
    
    Timer t;
    int score = 0;
    Bird bird;
    LinkedList<Pipe> pipes = new LinkedList<>();
    long timePassedSincePipe = System.currentTimeMillis();
    boolean gameStart = false;
    double height = 700, width = 400;
    int highScore = 0;
    MovingGround ground1;
    MovingGround ground2;

    public GameFrame() {

        t = new Timer(5,this);
        
        setSize(400,700);
        bird = new Bird(t,this.getWidth()/4,(this.getHeight()-this.getHeight()/5)/2,this.getHeight()/5*4);
        t.start();
        
        ground1 = new MovingGround(t,0,this.getHeight()/5*4,this.getWidth());
        ground2 = new MovingGround(t,this.getWidth(),this.getHeight()/5*4,this.getWidth());
        
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                
                int code = e.getKeyCode();
                
                if (code == e.VK_SPACE && !gameStart && System.currentTimeMillis() - timePassedSincePipe > 1000) {
                    gameStart = true;
                    bird = new Bird(t,width/4,(height-height/5)/2,height/5*4);
                    score = 0;
                    pipes = new LinkedList<>();
                    timePassedSincePipe = System.currentTimeMillis() + 500;
                }
                
                if (bird != null && gameStart)
                    bird.keyPressed(e);
            }

        });

        setVisible(true);
        setFocusable(true);
    }
    
    public void paint(Graphics g) {
        
        repaint();
      
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.black);
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());
        
        g2.setColor(Color.white);
        g2.drawString("High Score: " + String.valueOf(highScore), 0, 10);
        
        if (ground1 != null)
            ground1.paint(g);
        if (ground2 != null)
            ground2.paint(g);
        
        g2.setColor(Color.yellow);
        g2.fillRect(0, getHeight()/5*4+20, getWidth(), getHeight()/5-20);
        
        for (int i = 0; i < pipes.size(); i++)
            pipes.get(i).paint(g);
                
        bird.paint(g);
        
        g2.setColor(Color.white);
        g2.setFont(new Font("serif",Font.BOLD,40));
        
        g2.drawString(String.valueOf(score), this.getWidth()/2-10, this.getHeight()/8);
        
        if (!gameStart) g2.drawString("Press Space to Begin", 22, this.getHeight()/5);

        if (bird.dead) {
            g2.setColor(Color.red);
            g2.drawString("Game Over", 100, (this.getHeight()-this.getHeight()/5)/2);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (bird != null) {
            
            if (gameStart || bird.dead)
                bird.actionPerformed(e);
            for (int i = 0; i < pipes.size() && gameStart; i++) {
                
                if (pipes.get(i).x + 90 < 0) {
                    pipes.remove(i);
                    break;
                }
                
                pipes.get(i).actionPerformed(e);
                
                if (pipes.get(i).getPoint(bird.bounds)) {
                    score++;
                    if (score > highScore) 
                        highScore = score;
                }
                if (pipes.get(i).didHit(bird.bounds)) {
                    timePassedSincePipe = System.currentTimeMillis();
                    bird.dead = true;
                }
            }
            
            if (ground1 != null && !bird.dead)
                ground1.actionPerformed(e);
            if (ground2 != null && !bird.dead)
                ground2.actionPerformed(e);
            
            if (ground1 != null && ground1.x < -this.width) ground1 = new MovingGround(t,this.getWidth()-1,this.getHeight()/5*4,this.getWidth());
            
            if (ground2 != null && ground2.x < -this.width) ground2 = new MovingGround(t,this.getWidth()-1,this.getHeight()/5*4,this.getWidth());
        }
        
        if (bird.dead) gameStart = false;
        
        if (System.currentTimeMillis() - timePassedSincePipe > 3000 && gameStart) {
            pipes.add(new Pipe(t,width,0,height/5*4));
            timePassedSincePipe = System.currentTimeMillis();
        }
    }
    
}
