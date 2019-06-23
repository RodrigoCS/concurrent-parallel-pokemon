import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Robot;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.util.function.Consumer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javafx.scene.control.Alert;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

class Canvas extends JPanel implements MouseMotionListener {
    final String LINE = "LINE";
    final String IMPROVED_LINE = "IMPROVED_LINE";
    final String DDA_LINE = "DDA_LINE";
    final String BRESENHAM_LINE = "BRESENHAM_LINE";
    final String MIDDLE_POINT_LINE = "MIDDLE_POINT_LINE";
    final String RECTANGLE = "RECTANGLE";
    final String CIRCLE = "CIRCLE";
    final String POLAR_CIRCLE = "POLAR_CIRCLE";
    final String SYMMETRY_CIRCLE = "SYMMETRY_CIRCLE";
    final String MIDDLE_POINT_CIRCLE = "MIDDLE_POINT_CIRCLE";
    final String BRESENHAM_CIRCLE = "BRESENHAM_CIRCLE";
    final String FIGURES = "FIGURES";
    final String CLEAR = "CLEAR";
    int clickCount = 0;


    public BufferedImage backgroundBuffer, foregroundBuffer, player, temp, pokemon;
    Graphics gPixel, gPlayer;
    private final Robot robot;
    Coordinate first = new Coordinate();
    Coordinate second = new Coordinate();
    String command = LINE;

    public Canvas(JFrame frame) {
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
        public void setCommand(String cmd) {
        this.command = cmd;
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


    public void drawLine(int x1, int y1, int x2, int y2, Color c) {
        double m = 0;
        double b = 0;
        try {
            if (x2 == x1)
                m = 9999999;
            else
                m = ((double) y2 - (double) y1) / ((double) x2 - (double) x1);
            b = (double) y1 - (m * (double) x1);
        } catch (Exception e) {
        }
        int xT, xUno = x1, xDos = x2;
        int yT, yUno = y1, yDos = y2;
        if (Math.abs(m) < 1) {
            if (xDos < xUno) {
                xT = xDos;
                xDos = xUno;
                xUno = xT;
            }
            for (int x = xUno; x <= xDos; x++) {
                double y = (m * x) + b;
                putPixel(x, (int) y, c);
            }
        } else {
            if (yDos < yUno) {
                yT = yDos;
                yDos = yUno;
                yUno = yT;
            }
            for (int y = yUno; y <= yDos; y++) {
                double x = (y - b) / m;
                putPixel((int) x, y, c);
            }
        }
    }

    public void drawImprovedLine(int x1, int y1, int x2, int y2, Color c) {
        int mask = 0;
        double m;
        if (x2 == x1)
            m = 99999;
        else
            m = ((double) y2 - (double) y1) / ((double) x2 - (double) x1);
        int dx = x2 - x1;
        int dy = y2 - y1;
        int steps;
        if (Math.abs(dx) > Math.abs(dy))
            steps = Math.abs(dx);
        else
            steps = Math.abs(dy);
        double xinc = (double) dx / (double) steps;
        double yinc = (double) dy / (double) steps;
        double x = (double) x1, y = (double) y1;
        putPixel((int) x, (int) y, c);

        for (int k = 1; k <= steps; k++) {
            x += xinc;
            y += yinc;
            // if (mask < 5)
            putPixel((int) x, (int) y, c);
            // mask++;
            // if (mask == 8)
            // mask = 0;
        }
    }

    public void drawDDALine(int x1, int y1, int x2, int y2, Color c) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        int steps;
        if (Math.abs(dx) > Math.abs(dy))
            steps = Math.abs(dx);
        else
            steps = Math.abs(dy);
        double xinc = (double) dx / (double) steps;
        double yinc = (double) dy / (double) steps;
        double x = (double) x1, y = (double) y1;
        putPixel((int) x, (int) y, c);
        for (int k = 1; k <= steps; k++) {
            x += xinc;
            y += yinc;
            putPixel((int) x, (int) y, c);
        }
    }

    public void drawBresenham(int x1, int y1, int x2, int y2, Color c) {
        int x, y, dx, dy, p, incE, incNE, stepx, stepy;
        dx = (x2 - x1);
        dy = (y2 - y1);
        if (dy < 0) {
            dy = -dy;
            stepy = -1;
        } else
            stepy = 1;
        if (dx < 0) {
            dx = -dx;
            stepx = -1;
        } else
            stepx = 1;
        x = x1;
        y = y1;
        putPixel(x, y, c);
        if (dx > dy) {
            p = 2 * dy - dx;
            incE = 2 * dy;
            incNE = 2 * (dy - dx);
            while (x != x2) {
                x = x + stepx;
                if (p < 0)
                    p = p + incE;
                else {
                    y = y + stepy;
                    p = p + incNE;
                }
                putPixel(x, y, c);
            }
        } else {
            p = 2 * dx - dy;
            incE = 2 * dx;
            incNE = 2 * (dx - dy);
            while (y != y2) {
                y = y + stepy;
                if (p < 0)
                    p = p + incE;
                else {
                    x = x + stepx;
                    p = p + incNE;
                }
                putPixel(x, y, c);
            }
        }
    }

    public void drawMiddlePointLine(int x1, int y1, int x2, int y2, Color c) {
        putPixel(x1, y1, c);
        int dx = x2 - x1;
        int dy = y2 - y1;
        int x = x1;
        int y = y1;
        int steps, p, incE, incNE, stepx, stepy;
        if (Math.abs(dx) > Math.abs(dy))
            steps = Math.abs(dx);
        else
            steps = Math.abs(dy);
        if (dy < 0) {
            dy = -dy;
            stepy = -1;
        } else
            stepy = 1;
        if (dx < 0) {
            dx = -dx;
            stepx = -1;
        } else
            stepx = 1;
        if (dx > dy) {
            p = (2 * dy) - (dx);
            incE = 2 * dy;
            incNE = 2 * (dy - dx);
            for (int k = 1; k <= steps; k++) {
                x = x + stepx;
                if (p < 0)
                    p = p + incE;
                else {
                    y = y + stepy;
                    p = p + incNE;
                }
                putPixel(x, y, c);
            }
        } else {
            p = (2 * dx) - dy;
            incE = 2 * dx;
            incNE = 2 * (dx - dy);
            for (int k = 1; k <= steps; k++) {
                y = y + stepy;
                if (p < 0)
                    p = p + incE;
                else {
                    x = x + stepx;
                    p = p + incNE;
                }
                putPixel(x, y, c);
            }
        }
    }

    public void drawRectangle(int x1, int y1, int x2, int y2, Color c) {
        int xT, yT;
        if (x2 < x1) {
            xT = x2;
            x2 = x1;
            x1 = xT;
        }
        if (y2 < y1) {
            yT = y2;
            y2 = y1;
            y1 = yT;
        }
        for (int x = x1; x <= x2; x++) {
            putPixel(x, y1, c);
            putPixel(x, y2, c);
        }
        for (int y = y1; y <= y2; y++) {
            putPixel(x1, y, c);
            putPixel(x2, y, c);
        }
    }

    public void drawCircle(int xC, int yC, int xR, int yR, Color c) {
        if (xC < xR) {
            int xT = xC;
            xC = xR;
            xR = xT;
        }
        int r = xC - xR;
        for (double x = xC - r; x <= xC + r; x++) {
            double y;
            y = yC + (Math.sqrt((Math.pow(r, 2)) - (Math.pow(x - xC, 2))));
            putPixel((int) x, (int) y, c);
            y = yC - (Math.sqrt((Math.pow(r, 2)) - (Math.pow(x - xC, 2))));
            putPixel((int) x, (int) y, c);
        }
    }

    public void drawOval(int xC, int yC, int xR, int yR, Color c) {
        if (xC < xR) {
            int xT = xC;
            xC = xR;
            xR = xT;
        }
        int r = xC - xR;
        for (double x = xC - r; x <= xC + r; x++) {
            double y;
            y = yC + (Math.sqrt((Math.pow(r, 2)) - (Math.pow(x - xC, 2))));
            putPixel((int) x, (int) y, c);
            y = yC - (Math.sqrt((Math.pow(r, 2)) - (Math.pow(x - xC, 2))));
            putPixel((int) x, (int) y, c);
        }
    }

    public void drawPolarCircle(int xC, int yC, int xR, int yR, Color c) {
        if (xC < xR) {
            int xT = xC;
            xC = xR;
            xR = xT;
        }
        int r = xC - xR;
        for (double t = xC - r; t <= xC + r; t++) {
            double y = (r * Math.cos(t));
            double x = (r * Math.sin(t));
            putPixel(xC + (int) x, yC + (int) y, c); // x, y
        }
    }

    public void draw8SymmetryCircle(int xC, int yC, int xR, int yR, Color c) {
        int mask = 0;
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
            if (mask < 4) {
                putPixel(xC + x, yC + y, c); // x, y
                putPixel(xC - x, yC + y, c); // -x, y
                putPixel(xC + x, yC - y, c); // x, -y
                putPixel(xC - x, yC - y, c); // -x, -y
                putPixel(xC + y, yC + x, c); // y, x
                putPixel(xC - y, yC + x, c); // -y, x
                putPixel(xC + y, yC - x, c); // y, -x
                putPixel(xC - y, yC - x, c); // -y, -x
            }
            mask++;
            if (mask == 7)
                mask = 0;
        }
    }

    public void drawMiddlePointCircle(int xC, int yC, int xR, int yR, Color c) {
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
        x = 0;
        y = (int) r;
        putPixel(xC, yC + r, c);
        putPixel(xC, yC - r, c);
        putPixel(xC + r, yC, c);
        putPixel(xC - r, yC, c);
        while (x < y) {
            x++;
            if (p < 0)
                p = p + 2 * x + 1;
            else {
                y--;
                p = p + 2 * (x - y) + 1;
            }
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

    public void drawBresenhamCircle(int xC, int yC, int xR, int yR, Color c) {
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

    public void drawFigures() {
        // DRAW LINES
        this.drawBresenham(30, 30, 130, 130, Color.BLACK);
        this.drawBresenham(230, 60, 330, 60, Color.BLACK);
        this.drawBresenham(430, 130, 530, 30, Color.BLACK);
        this.drawBresenham(630, 60, 730, 60, Color.BLACK);

        // DRAW CIRCLES
        this.drawBresenhamCircle(30, 330, 120, 330, Color.BLACK);
        this.drawBresenhamCircle(60, 330, 120, 330, Color.BLACK);
        this.drawBresenhamCircle(80, 330, 120, 330, Color.BLACK);
        this.drawBresenhamCircle(100, 330, 120, 330, Color.BLACK);

        // DRAW RECTANGLES
        this.drawRectangle(330, 260, 530, 400, Color.BLACK);
        this.drawRectangle(360, 290, 500, 370, Color.BLACK);

        // DRAW OVAL

    }
}