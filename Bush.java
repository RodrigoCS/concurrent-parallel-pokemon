import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Frame;
import javax.swing.JLabel;
import java.util.function.Consumer;

import javafx.scene.control.Alert;

class Bush extends Sprite {
    final int WIDTH = 16;
    final int HEIGHT = 16;
    int x, y;

    Color[] colors = {
        Color.decode("#70c8a0"),
        Color.decode("#b0f47c"),
        Color.decode("#74c854"),
        Color.decode("#2c8424"),
        Color.decode("#2c4c08"),
        Color.decode("#2c8424"),
        Color.decode("#34a87c"),
        Color.decode("#10945c")
    };

    int[][] map = new int[][]{
        { 0, 0, 0, 6, 0, 0, 0, 6, 5, 0, 0, 0, 6, 0, 0, 0 },
        { 0, 6, 0, 0, 0, 0, 6, 5, 1, 5, 0, 0, 0, 0, 0, 6 },
        { 0, 0, 5, 5, 5, 6, 6, 1, 1, 5, 6, 4, 5, 4, 0, 0 },
        { 0, 7, 1, 1, 1, 5, 5, 1, 0, 5, 4, 1, 1, 1, 4, 6 },
        { 0, 0, 7, 1, 1, 1, 5, 0, 0, 4, 6, 0, 1, 4, 5, 6 },
        { 0, 0, 0, 7, 0, 0, 0, 5, 6, 4, 6, 6, 5, 5, 6, 0 },
        { 0, 7, 7, 7, 5, 5, 0, 5, 5, 5, 6, 4, 4, 4, 4, 0 },
        { 5, 1, 1, 1, 0, 5, 5, 0, 4, 6, 4, 6, 0, 0, 0, 4 },
        { 6, 5, 1, 0, 0, 6, 5, 5, 4, 4, 6, 6, 6, 6, 4, 6 },
        { 0, 6, 5, 6, 6, 6, 6, 4, 4, 5, 5, 5, 4, 4, 5, 5 },
        { 0, 0, 6, 5, 5, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 6 },
        { 0, 6, 5, 0, 0, 6, 4, 5, 4, 4, 5, 6, 6, 4, 6, 0 },
        { 0, 5, 0, 0, 6, 5, 5, 5, 5, 4, 5, 5, 5, 6, 4, 6 },
        { 6, 5, 4, 4, 4, 4, 6, 6, 6, 5, 4, 4, 4, 4, 4, 5 },
        { 6, 6, 5, 5, 5, 5, 4, 6, 5, 4, 5, 5, 5, 5, 5, 5 },
        { 0, 0, 6, 6, 5, 5, 5, 4, 4, 5, 5, 5, 6, 6, 0, 0 }
      };

    Bush(int x, int y) {
        this.x = x;
        this.y = y;
    }

    void paint(GameCanvas gameCanvas) {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if(gameCanvas.temp.getRGB(this.x + j, this.y + i) == -16777216) {
                    int tileValue = this.map[i][j];
                    gameCanvas.putPixel2(this.x + j, this.y + i, this.colors[tileValue]);
                }
            }
        }
    }
}

//
