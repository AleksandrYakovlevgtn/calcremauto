package ru.yandex.practicum.calcRemAuto.panelsAndButtons.panels;

import ru.yandex.practicum.calcRemAuto.model.Client;
import ru.yandex.practicum.calcRemAuto.panelsAndButtons.buttons.Buttons;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AddClientPanel extends JFrame {
    Buttons but = new Buttons();
    AddWorkPanel workPanel = new AddWorkPanel();

    public JPanel clientAdd(JPanel panel, Client client) {
        panel.setLayout(new GridLayout(6, 2));
        panel.setBounds(200, 150, 400, 200);

        JTextField name = new JTextField();
        JLabel nameLabel = new JLabel("Имя клиента.");
        name.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                // если количество символов меньше, то ввод не будет произведен.
                if (name.getText().length() >= 10)
                    e.consume();
            }
        });

        JTextField numberFone = new JTextField();
        JLabel foneLabel = new JLabel("Телефон.");

        numberFone.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                // если количество символов меньше, то ввод не будет произведен.
                if (numberFone.getText().length() > 9)
                    e.consume();
            }
        });

        JTextField numberAuto = new JTextField();
        JLabel numberAutoLabel = new JLabel("Гос/номер.");

        numberAuto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                // если количество символов меньше, то ввод не будет произведен.
                if (numberAuto.getText().length() >= 11)
                    e.consume();
            }
        });
        JTextField modelAuto = new JTextField();
        JLabel modelLabel = new JLabel("Модель автомобиля.");

        modelAuto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                // если количество символов меньше, то ввод не будет произведен.
                if (modelAuto.getText().length() >= 11)
                    e.consume();
            }
        });
        changeColor(name, 3);
        changeColor(numberFone, 9);
        changeColor(numberAuto, 8);
        changeColor(modelAuto, 3);
        panel.add(nameLabel);
        panel.add(name);
        panel.add(foneLabel);
        panel.add(numberFone);
        panel.add(numberAutoLabel);
        panel.add(numberAuto);
        panel.add(modelLabel);
        panel.add(modelAuto);
        panel.add(but.getButtonBack());

        but.getButtonBack().addActionListener(e -> {
            FirstPanel firstPanel = new FirstPanel();
            panel.removeAll();
            firstPanel.firstPanel(panel);
        });
        panel.add(but.getButtonNext());

        but.getButtonNext().addActionListener(e -> {
            try {
                if ((((LineBorder) name.getBorder()).getLineColor().equals(Color.green))
                        && (((LineBorder) numberFone.getBorder()).getLineColor().equals(Color.green))
                        && (((LineBorder) numberAuto.getBorder()).getLineColor().equals(Color.green))
                        && (((LineBorder) modelAuto.getBorder()).getLineColor().equals(Color.green))) {
                    client.setName(name.getText());
                    client.setFoneNumber(numberFone.getText());
                    client.setNumberAuto(numberAuto.getText());
                    client.setModelAuto(modelAuto.getText());
                    panel.removeAll();
                    panel.updateUI();
                    //workPanel.addWorks(panel,client);
                }
            } catch (Exception o) {
                JFrame bugFrame = new JFrame("Ошибка!");
                JPanel bugPanel = new JPanel();

                bugFrame.setSize(400, 200);
                bugFrame.setLayout(null);
                bugFrame.setLocationRelativeTo(null);
                bugFrame.setVisible(true);

                bugPanel.setBounds(100, 75, 200, 50);

                bugPanel.add(but.getButtonNullWried());
                bugFrame.add(bugPanel);
                but.getButtonNullWried().addActionListener(e1 -> bugFrame.dispose());
            }
        });
        panel.updateUI();
        return panel;
    }


    private void changeColor(JTextField line, int numberOfMinSymbols) {
        line.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                // в зависимости от количества символов цвет строки изменяется.
                if (line.getText().length() < numberOfMinSymbols) {
                    line.setBorder(BorderFactory.createLineBorder(Color.red));
                }
                if (line.getText().length() > numberOfMinSymbols) {
                    line.setBorder(BorderFactory.createLineBorder(Color.green));
                }
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                // в зависимости от количества символов цвет строки изменяется.
                if (line.getText().length() < numberOfMinSymbols) {
                    line.setBorder(BorderFactory.createLineBorder(Color.red));
                }
                if (line.getText().length() > numberOfMinSymbols) {
                    line.setBorder(BorderFactory.createLineBorder(Color.green));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // в зависимости от количества символов цвет строки изменяется.
                if (line.getText().length() < numberOfMinSymbols) {
                    line.setBorder(BorderFactory.createLineBorder(Color.red));
                }
                if (line.getText().length() > numberOfMinSymbols) {
                    line.setBorder(BorderFactory.createLineBorder(Color.green));
                }
            }
        });
    }
}