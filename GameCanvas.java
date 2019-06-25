import javax.swing.*;
import java.awt.*;
import java.awt.Canvas;
import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Robot;
import java.awt.BorderLayout;

import javax.swing.JLabel;
import java.util.function.Consumer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javafx.scene.control.Alert;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

class GameCanvas extends Canvas implements MouseMotionListener {
    int clickCount = 0;


    public BufferedImage backgroundBuffer, foregroundBuffer, player, temp, pokemon, cacheBuffer;
    Graphics gPixel, gPlayer;
    private final Robot robot;
    Coordinate first = new Coordinate();
    Coordinate second = new Coordinate();

    public GameCanvas(Frame frame) {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
        this.setBackground(Color.BLACK);
        setFocusable(true);
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                handlePress(me);
            }
        });
        this.addMouseMotionListener(this);
        cacheBuffer = new BufferedImage(320, 320, BufferedImage.TYPE_INT_RGB);
        backgroundBuffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        foregroundBuffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        player = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        pokemon = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        temp = new BufferedImage(320, 320, BufferedImage.TYPE_INT_RGB);
        gPixel = (Graphics2D) backgroundBuffer.createGraphics();
        gPlayer = (Graphics2D) player.createGraphics();
    }

    public void mouseDragged(MouseEvent e) {
    }
    
    public void mouseMoved(MouseEvent e) {
    }

    public static synchronized void playSound(final String url) {
        new Thread(new Runnable() {
          public void run() {
            try {
              Clip clip = AudioSystem.getClip();
              AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                Scene.class.getResourceAsStream("./" + url));
              clip.open(inputStream);
              clip.start(); 
            } catch (Exception e) {
              System.err.println(e.getMessage());
            }
          }
        }).start();
      }

    public void handlePress(MouseEvent e) {
        if(this.clickCount < 4) {
            System.out.println(e.getX() +" - "+ e.getY());
            if(e.getX() > 221 && e.getX() < 255 && e.getY() > 79 && e.getY() < 109) {
                playSound("./charizard.wav");
            }
        } else {
        } 
        this.clickCount ++;

    }

    void catchCharizard() {

    }

    public void putPixel(int x, int y, Color c) {
        this.backgroundBuffer.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(this.backgroundBuffer, x, y, this);
        temp.getGraphics().drawImage(backgroundBuffer, x, y, this);
    }

    public void putPixel2(int x, int y, Color c) {
        this.foregroundBuffer.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(this.foregroundBuffer, x, y, this);
        temp.getGraphics().drawImage(foregroundBuffer, x, y, this);
    }

    public void renderSprite(BufferedImage sprite, int x, int y) {
        this.getGraphics().drawImage(sprite, x, y, this);
    }

    public void putPixel3(int x, int y, Color c) {
        this.player.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(this.player, x, y, this);
        // temp.getGraphics().drawImage(player, x, y, this);
    }

    public void putPixelPokemon(int x, int y, Color c) {
        this.pokemon.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(this.pokemon, x, y, this);
        // temp.getGraphics().drawImage(player, x, y, this);
    }
}