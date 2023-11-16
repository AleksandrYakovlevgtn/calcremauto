package ru.yandex.practicum.calcRemAuto.panelsAndButtons.panels;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;

@Getter
public class Frame {
    JFrame frame = new JFrame("CalcRemAuto");
    public void createFrame(JPanel panel) {
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
