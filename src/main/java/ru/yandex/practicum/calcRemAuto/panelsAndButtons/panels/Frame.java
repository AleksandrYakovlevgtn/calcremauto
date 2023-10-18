package ru.yandex.practicum.calcRemAuto.panelsAndButtons.panels;

import javax.swing.*;

public class Frame {
    JFrame frame = new JFrame("CalcRemAuto");
    public void frame(JPanel panel) {
        frame.add(panel);
        frame.setSize(800, 600);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}
