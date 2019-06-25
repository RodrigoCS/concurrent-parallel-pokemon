import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Proyecto2 extends Frame {
    Scene scene = new Scene(this);

    Proyecto2() {
        // this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLayout(new BorderLayout());
        this.setUndecorated(true);
        this.add(this.scene);
        this.pack();
        this.setBounds(100, 100, 320, 320);
        this.scene.render();
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Proyecto2();
    }
}