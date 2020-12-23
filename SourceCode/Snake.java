/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 *
 * @author 张俊良(Injoker)
 */
public class Snake extends JFrame {
    public Snake() {
        initUI();
        initComponents();
    }
    
    private void initUI() {
        add(new Board());
        
        setResizable(false);
        pack();
        
        setTitle("Snake");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    private void initComponents() {
        
        add(new Guide());
        
        setResizable(false);
        pack();
        
        setLocationRelativeTo(null);

    }
    
    public static void main(String[] args) {
        
        EventQueue.invokeLater(() -> {
            JFrame ex = new Snake();
            ex.setVisible(true);
        });
    }
}