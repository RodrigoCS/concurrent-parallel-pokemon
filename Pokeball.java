import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.BorderLayout;

import javax.swing.JLabel;
import java.util.function.Consumer;

import javafx.scene.control.Alert;

class Pokeball extends Sprite {
    final int WIDTH = 32;
    final int HEIGHT = 48;
    int x, y;

    Pokeball(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void paint(int xC, int yC, int xR, int yR, Color c) {
        if (xC < xR) {
            int xT = xC;
            xC = xR;
            xR = xT;
        }
        int r = xC - xR;
        double p = 5.0 / 4.0 - (double) r;
        if (r % 1 == 0)
            p = 1 - r;
        int x, y;
        int dx = 1;
        int dy = -2 * r;
        x = 0;
        y = (int) r;
        putPixel(xC, yC + r, c);
        putPixel(xC, yC - r, c);
        putPixel(xC + r, yC, c);
        putPixel(xC - r, yC, c);
        while (x < y) {
            if (p >= 0) {
                y--;
                dy += 2;
                p += dy;
            }
            x++;
            dx += 2;
            p += dx;
            putPixel(xC + x, yC + y, c); // x, y
            putPixel(xC - x, yC + y, c); // -x, y
            putPixel(xC + x, yC - y, c); // x, -y
            putPixel(xC - x, yC - y, c); // -x, -y
            putPixel(xC + y, yC + x, c); // y, x
            putPixel(xC - y, yC + x, c); // -y, x
            putPixel(xC + y, yC - x, c); // y, -x
            putPixel(xC - y, yC - x, c); // -y, -x
        }
    }

}

//
