package ru.yandex.practicum.calcRemAuto.panelsAndButtons.frame;

import java.awt.Component;
import javax.swing.JFrame;

public class FindParentFrame {
    public static JFrame findParentJFrame(Component component) {
        if (component instanceof JFrame) {
            return (JFrame) component;
        } else if (component != null && component.getParent() != null) {
            return findParentJFrame(component.getParent());
        }
        return null;
    }
}