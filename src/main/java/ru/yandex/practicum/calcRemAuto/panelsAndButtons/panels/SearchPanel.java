package ru.yandex.practicum.calcRemAuto.panelsAndButtons.panels;

import ru.yandex.practicum.calcRemAuto.panelsAndButtons.buttons.Buttons;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SearchPanel extends JFrame {
    Buttons but = new Buttons();
    public void SearchPanel(JPanel panel) {
        panel.removeAll();
        panel.setBounds(150,150,500,200);
        JTextField search = new JTextField();
        search.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (search.getText().length() >= 9 )
                    e.consume();
            }
        });

        panel.add(but.getButtonBack());
        panel.add(search);
        panel.updateUI();

        but.getButtonBack().addActionListener(e -> {
            FirstPanel firstPanel = new FirstPanel();
            panel.removeAll();
            firstPanel.firstPanel(panel);
        });
        search.addActionListener(e -> {
            boolean test = e.getActionCommand().matches("[a-zA-Z0-9]+");
        });
    }
}
