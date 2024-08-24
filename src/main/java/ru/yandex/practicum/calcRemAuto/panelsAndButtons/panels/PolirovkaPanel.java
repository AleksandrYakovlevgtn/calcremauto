package ru.yandex.practicum.calcRemAuto.panelsAndButtons.panels;

import javax.swing.*;
import java.awt.*;

public class PolirovkaPanel {
    public JPanel takePolirovkaPanel() {
        JPanel panel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Загрузка изображения
                Image image = new ImageIcon("Системные/полировка_фон.png").getImage();
                // Рисование изображения как фона
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        return panel;
    }
}
