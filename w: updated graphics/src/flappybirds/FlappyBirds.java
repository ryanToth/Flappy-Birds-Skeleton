/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package flappybirds;

import java.io.IOException;
import javax.swing.JFrame;

/**
 *
 * @author Ryan
 */
public class FlappyBirds {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        GameFrame game = new GameFrame();
        JFrame frame = new JFrame();
        frame.setSize(400,700);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        
        frame.add(game);
    }
    
}
