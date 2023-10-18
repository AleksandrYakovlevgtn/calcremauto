package ru.yandex.practicum.calcRemAuto.panelsAndButtons.panels;

import ru.yandex.practicum.calcRemAuto.panelsAndButtons.buttons.Buttons;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AddPanel extends JFrame {
    Buttons but = new Buttons();
    public void clientAdd(JPanel panel){
        JTextField name = new JTextField();
        name.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (name.getText().length() >= 15 )
                    e.consume();
            }
        });
        JTextField numberFone = new JTextField();
        numberFone.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (numberFone.getText().length() >= 11 )
                    e.consume();
            }
        });
        JTextField numberAuto = new JTextField();
        numberAuto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (numberAuto.getText().length() >= 11 )
                    e.consume();
            }
        });
        JTextField modelAuto = new JTextField();
        modelAuto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (modelAuto.getText().length() >= 11 )
                    e.consume();
            }
        });
        panel.add(but.getButtonBack());
        but.getButtonBack().addActionListener(e -> {
            FirstPanel firstPanel = new FirstPanel();
            panel.removeAll();
            firstPanel.firstPanel(panel);
        });
        panel.add(name);
        panel.add(numberFone);
        panel.add(numberAuto);
        panel.add(modelAuto);
        panel.updateUI();
    }
}
