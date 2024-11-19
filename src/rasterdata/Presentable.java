package rasterdata;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Presentable extends JFrame {
    private final Control panel;

    public Presentable(int width, int height) {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("PGRF1 Task2");

        panel = new Control(width, height);

        add(panel, BorderLayout.CENTER);
        pack();

        setLocationRelativeTo(null);

       
        panel.setFocusable(true);
        panel.grabFocus(); 

        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                panel.resize();
            }
        });
    }

    public Control getPanel() {
        return panel;
    }
}
