import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Proyecto2 extends JFrame {
    Scene scene = new Scene(this);

    Proyecto2() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setLayout(new BorderLayout());
        this.setUndecorated(true);
        this.getContentPane().add(this.scene, BorderLayout.CENTER);
        this.pack();
        this.setBounds(100, 100, 320, 320);
        this.setVisible(true);
        this.scene.render();
    }

    public static void main(String[] args) {
        new Proyecto2();
    }
}