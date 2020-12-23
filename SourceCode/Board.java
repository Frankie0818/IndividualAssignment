/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakegame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author 张俊良(Injoker)
 */
public class Board extends JPanel implements ActionListener {
    final int boardWidth = 300;
    final int boardHeight = 300;
    final int dotSize = 10;
    final int allDots = 900;
    final int randomDots = 29;
    final int speed = 129;
    
    final int x[] = new int[allDots];
    final int y[] = new int[allDots];
    
    int dots;
    int lengthApple;
    int heightApple;
    
    boolean leftDirection = false;
    boolean rightDirection = true;
    boolean upDirection = false;
    boolean downDirection = false;
    boolean inGame = true;
    
    public Timer timer;
    public Image body;
    public Image head;
    public Image apple;
    
    public Board() {
        initBoard();
    }
    
    private void initBoard() {
        addKeyListener(new TAdapter());
        setBackground(Color.yellow);
        setFocusable(true);
        
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        loadImages();
        initGame();
    }
    
    private void loadImages() {
        
        ImageIcon iid = new ImageIcon("src/resources/dot.png");
        body = iid.getImage();
        
        ImageIcon iih = new ImageIcon("src/resources/head.png");
        head = iih.getImage();
        
        ImageIcon iia = new ImageIcon("src/resources/apple.png");
        apple = iia.getImage();
    }
    
    private void initGame() {
        dots = 3;
        
        for(int z = 0; z < dots; z++) {
            x[z] = 50 - z * 10;
            y[z] = 50;        
        }
        locateApple();
        
        timer = new Timer(speed, this);
        timer.start();
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        doDrawing(g);
    }
    
    private void doDrawing(Graphics g) {
        if(inGame) {
            g.drawImage(apple,lengthApple, heightApple, this);
            
            for(int z = 0; z < dots; z++) {
                if(z == 0) {
                    g.drawImage(head, x[z], y[z], this);
                }
                else {
                    g.drawImage(body, x[z], y[z], this);
                }
            }
            
            Toolkit.getDefaultToolkit().sync();         
        }
        else {
            gameOver(g);
        }
    }
    
    private void gameOver(Graphics g) {
        String message = "Game Over";
        Font small = new Font("Ink Free",Font.BOLD, 32);
        FontMetrics metr = getFontMetrics(small);
        
        g.setColor(Color.red);
        g.setFont(small);
        g.drawString(message,(boardWidth - metr.stringWidth(message))/2, boardHeight / 2);
    }
    
    private void checkApple() {
        if((x[0] == lengthApple) && (y[0] == heightApple)) {
            dots++;
            locateApple();
        }
    }
    
    private void move() {
        for(int z = dots; z > 0; z--) {
            x[z] = x[(z - 1)];
            y[z] = y[(z - 1)];
        }
        if(leftDirection) {
            x[0] -= dotSize;
        }
        if(rightDirection) {
            x[0] += dotSize;
        }
        if(downDirection) {
            y[0] += dotSize;
        }
        if(upDirection) {
            y[0] -= dotSize;
        }
    }
    
    private void checkCollision() {
        for(int z = dots; z > 0; z--) {
            if ((z > 4) && (x[0] == x[z]) && (y[0] == y[z])) {
                inGame  = false;
            }
        }
        if(y[0] >= boardHeight) {
            inGame = false;
        }
        if(y[0] < 0) {
            inGame = false;
        }
        if(x[0] >= boardHeight) {
            inGame = false;
        }
        if(x[0] < 0) {
            inGame = false;
        }
        if(!inGame) {
            timer.stop();
        }
    }
    
    private void locateApple() {
        int r = (int) (Math.random() * randomDots);
        lengthApple = ((r * dotSize));
        
        r = (int) (Math.random() * randomDots);
        heightApple = ((r * dotSize));
    }
    
    public void actionPerformed(ActionEvent e) {       
        if(inGame) {
            checkApple();
            checkCollision();
            move();  
        }
        repaint();
    }
    
    private class TAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            
            if((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if((key == KeyEvent.VK_UP) && (!downDirection)) {
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
            if((key == KeyEvent.VK_DOWN) && (!upDirection)) {
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
            }
        }
    }
