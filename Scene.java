import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.BorderLayout;

import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.function.Consumer;

import javafx.scene.control.Alert;

class Scene extends GameCanvas implements KeyListener, Runnable {
    final int WIDTH = 64;
    final int HEIGHT = 64;
    long gameTime = 0;
    final String UP = "UP";
    final String DOWN = "DOWN";
    final String LEFT = "LEFT";
    final String RIGHT = "RIGHT";
    Thread game;
    Player player;
    Charizard zard;
    Frame frame;
    int playerPosX = 10;
    int playerPosY = 10;
    int charizardPosX = 14;
    int charizardPosY = 5;

    int[][] trees = new int[][]{
        { 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1 },
        { 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1 },
        { 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1 },
        { 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1 },
        { 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1 },
        { 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1 },
        { 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1 },
        { 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1 },
        { 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1 },
        { 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1 },
        { 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1 },
        { 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1 },
        { 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1 },
        { 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1 },
        { 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1 },
        { 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
      };

    Scene(Frame frame) {
        super(frame);
        this.frame = frame;
        addKeyListener(this);
        Random random = new Random();
        int song = random.nextInt(4 - 1 + 1) + 1;
        this.player = new Player(playerPosX * 16, playerPosY * 16, this);
        this.zard = new Charizard(charizardPosX * 16, charizardPosY * 16, this);
        playSound(song + ".wav");
    }


    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        int tempX = playerPosX;
        int tempY = playerPosY;
        String cmd = DOWN;
        player.clear(tempX * 16, tempY * 16);

        if (key == KeyEvent.VK_RIGHT) {
            playerPosX ++;
            cmd = RIGHT;
        }
        if (key == KeyEvent.VK_LEFT) {
            playerPosX--;
            cmd = LEFT;
        }
        if (key == KeyEvent.VK_UP) {
            playerPosY--;
            cmd = UP;
        }
        if (key == KeyEvent.VK_DOWN) {
            playerPosY++;
            cmd = DOWN;
        }
        this.player.setPos(playerPosX * 16, playerPosY * 16, cmd);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        this.player.setIdle();
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void run() {
        long lastLoopTime = System.nanoTime();
        final int TARGET_FPS = 60;
        final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
        long lastFpsTime = 0;
        while(true){
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            double delta = updateLength / ((double)OPTIMAL_TIME);

            lastFpsTime += updateLength;
            if(lastFpsTime >= 1000000000){
                lastFpsTime = 0;
            }
            render();
            try{
                this.gameTime = (lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1000000;
                System.out.println(this.gameTime);
                Thread.sleep(this.gameTime);
            }catch(Exception e){
            }
        }
    }

    void render() {
        this.game = new Thread () {
            public void run () {
                renderPlayer(playerPosX, playerPosY);
            }
        };
        Thread pokemon = new Thread () {
            public void run () {
                while(true) {
                    try {
                        Thread.sleep(1300);
                        if(zard.clickCount < 3) {
                            zard.paint();
                        }
                    } catch (Exception e) {
                        //TODO: handle exception
                    }
                   
                }
            }
          };

        Thread background = new Thread () {
            public void run () {
                renderBackground();
                pokemon.start();
                frame.setVisible(true);
            }
          };

          Thread foreground = new Thread () {
            public void run () {
                renderForeground();
            }
          };

      
          background.start();
          foreground.start();
          this.game.start();
    }

    void renderBackground() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                GrassTile grass = new GrassTile(16 * j, 16 * i);
                grass.paint(this); 
            }
        }
    }

    void renderPlayer(int x, int y) {
        this.player.setPos(x * 16, y * 16, DOWN);
    }

    public static synchronized void playSound(final String url) {
        new Thread(new Runnable() {
          public void run() {
            try {
              Clip clip = AudioSystem.getClip();
              AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                Scene.class.getResourceAsStream("./" + url));
              clip.open(inputStream);
              clip.loop(Clip.LOOP_CONTINUOUSLY);
              clip.start(); 
            } catch (Exception e) {
              System.err.println(e.getMessage());
            }
          }
        }).start();
    }

    void renderForeground() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                
                if(i <= 10 && j <= 10) {
                    if(trees[i][j] == 1) {
                        Tree tree = new Tree(32 * i, 32 * j);
                        tree.paint(this);
                    } 
                } else {
                    Bush bush = new Bush(16 * i, 16 * j);
                    bush.paint(this);
                }
            }
        }
    }
}

