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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public GameFrame() throws UnsupportedEncodingException, IOException {

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
                    bird.gameStart = true;
                    
                }
                
                if (bird != null && gameStart)
                    bird.keyPressed(e);
                
                if (!gameStart && code == e.VK_R) try {
                    resetHighScore();
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(GameFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(GameFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });

        setVisible(true);
        setFocusable(true);
    }
    
    public void paint(Graphics g) {
        
        repaint();
      
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.darkGray);
        //g2.fillRect(0, 0, this.getWidth(), this.getHeight());

        if (ground1 != null)
            ground1.paint(g);
        if (ground2 != null)
            ground2.paint(g);
        
        //g2.setColor(Color.yellow);
        //g2.fillRect(0, getHeight()/5*4+20, getWidth(), getHeight()/5-20);
        
        for (int i = 0; i < pipes.size(); i++)
            pipes.get(i).paint(g);
               
        g2.setColor(Color.black);
        g2.drawString("High Score: " + String.valueOf(highScore), 1, 9);
        
        g2.setColor(Color.white);
        g2.drawString("High Score: " + String.valueOf(highScore), 0, 10);
        
        bird.paint(g);
        
        g2.setFont(new Font("serif",Font.BOLD,40));
        
        g2.setColor(Color.black);
        
        g2.drawString(String.valueOf(score), this.getWidth()/2-7, this.getHeight()/8-3);
        
        if (!gameStart) g2.drawString("Press Space to Begin", 25, this.getHeight()/5-3);
        
        if (bird.dead) 
            g2.drawString("Game Over", 103, (this.getHeight()-this.getHeight()/5)/2-3);
        
        
        g2.setColor(Color.white);

        g2.drawString(String.valueOf(score), this.getWidth()/2-10, this.getHeight()/8);
        
        if (!gameStart) {
            g2.drawString("Press Space to Begin", 22, this.getHeight()/5);
            
            g2.setFont(new Font("serif",Font.BOLD,20));
            g2.setColor(Color.black);
            g2.drawString("Press R to Reset High Score", 83, this.getHeight()/4-2);
            g2.setColor(Color.white);
            g2.drawString("Press R to Reset High Score", 81, this.getHeight()/4);
        }

        if (bird.dead) {
            g2.setColor(Color.red);
            g2.setFont(new Font("serif",Font.BOLD,40));
            g2.drawString("Game Over", 100, (this.getHeight()-this.getHeight()/5)/2);
        }
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        try {
            highScore = loadHighScore();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(GameFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GameFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
                        try {
                            setHighScore();
                    } catch (UnsupportedEncodingException ex) {
                        Logger.getLogger(GameFrame.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(GameFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
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

    private int loadHighScore() throws FileNotFoundException, UnsupportedEncodingException, IOException {

        FileInputStream configStream = new FileInputStream("highscore/highscore.txt");
        BufferedReader configReader = new BufferedReader(new InputStreamReader(configStream, "UTF-8"));

        return Integer.parseInt(configReader.readLine());
    }

    private void setHighScore() throws FileNotFoundException, UnsupportedEncodingException, IOException {

        BufferedWriter writer = new BufferedWriter(new PrintWriter("highscore/highscore.txt", "UTF-8"));
        writer.write(String.valueOf(score));
        writer.close();

        highScore = score;
    }
    
    private void resetHighScore() throws FileNotFoundException, UnsupportedEncodingException, IOException {
        
        BufferedWriter writer = null;

        writer = new BufferedWriter(new PrintWriter("highscore/highscore.txt", "UTF-8"));
        writer.write("0");
        writer.close();

        highScore = 0;
        
    }
    
}
