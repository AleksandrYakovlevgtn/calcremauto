package ru.yandex.practicum.calcRemAuto.panelsAndButtons.frame;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;

@Getter
public class StartFrame {
    JFrame frame = new JFrame("CalcRemAuto");
    public JPanel createFrame(JPanel panel) {
        JMenuFrame menuFrame = new JMenuFrame(frame);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());
        frame.add(panel);
        frame.setJMenuBar(menuFrame.getMenuBar());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return panel;
    }
}
