import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.BorderLayout;

import javax.swing.JLabel;
import java.util.function.Consumer;

import javafx.scene.control.Alert;

class GrassTile extends Sprite {
    final int WIDTH = 16;
    final int HEIGHT = 16;
    int x, y;

    Color[] colors = {
        Color.decode("#70c8a0"),
        Color.decode("#40b088"),
        Color.decode("#88d8b8")
    };

    int[][] map = new int[][]{
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 2, 0, 0, 2 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 2, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1 },
        { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0 },
      };

    GrassTile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    void paint(GameCanvas gameCanvas) {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if(gameCanvas.temp.getRGB(this.x + j, this.y + i) == -16777216) {
                    int tileValue = this.map[i][j];
                    gameCanvas.putPixel(this.x + j, this.y + i, this.colors[tileValue]);
                }
            }
        }
    }
}

//
