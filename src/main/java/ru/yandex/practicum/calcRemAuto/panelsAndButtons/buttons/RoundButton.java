package ru.yandex.practicum.calcRemAuto.panelsAndButtons.buttons;
import java.awt.*;
import javax.swing.*;
import java.awt.geom.RoundRectangle2D;

public class RoundButton extends JButton {
    public RoundButton(String label) {
        super(label);
        setOpaque(false); // делаем кнопку непрозрачной
    }

    public void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(Color.lightGray);
        } else {
            g.setColor(getBackground());
        }
        // рисуем круглую кнопку
        g.fillRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 50, 50);

        super.paintComponent(g);
    }

    public void paintBorder(Graphics g) {
        g.setColor(getForeground());
        // рисуем границу круглой кнопки
        g.drawRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 50, 50);
    }

    // рисуем содержимое кнопки в центре
    Shape shape;
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 50, 50);
        }
        return shape.contains(x, y);
    }
}
