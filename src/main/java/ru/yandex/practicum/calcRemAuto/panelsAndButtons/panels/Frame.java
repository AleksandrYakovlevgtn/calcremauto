package ru.yandex.practicum.calcRemAuto.panelsAndButtons.panels;

import lombok.Getter;

import javax.swing.*;
@Getter
public class Frame {
    JFrame frame = new JFrame("CalcRemAuto");
    public void createFrame(JPanel panel) {
        frame.add(panel);
        frame.setSize(800, 600);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
