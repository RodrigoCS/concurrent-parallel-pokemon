import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Robot;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.util.function.Consumer;

import javafx.scene.control.Alert;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

class Sprite extends JPanel {
    public BufferedImage buffer, temp;
    private Graphics gPixel;

    public Sprite() {
        this.setBackground(Color.WHITE);

        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        gPixel = (Graphics2D) buffer.createGraphics();
    }

    public void putPixel(int x, int y, Color c) {
        this.buffer.setRGB(0, 0, c.getRGB());
        this.getGraphics().drawImage(this.buffer, x, y, this);
    }
}