package ru.yandex.practicum.calcRemAuto.panelsAndButtons.panels;

import ru.yandex.practicum.calcRemAuto.model.Client;
import ru.yandex.practicum.calcRemAuto.panelsAndButtons.buttons.Buttons;
import ru.yandex.practicum.calcRemAuto.panelsAndButtons.frame.FindParentFrame;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.stream.Stream;
import java.text.ParseException;

public class AddClientPanel {

    JTextField name = new JTextField(); // Поле ввода имени клиента
    JFormattedTextField numberFone;    // Поле ввода телефонного номера
    JTextField numberAuto = new JTextField(); // Поле ввода гос/номера автомобиля клиента
    JTextField modelAuto = new JTextField();  // Поле ввода модели автомобиля
    Buttons but = new Buttons(); // Класс с кнопками

    public void clientAdd(JPanel panel, Client client) {
        panel.setLayout(new BorderLayout());
        // Панель для полей ввода
        JPanel addClienPanel = new JPanel(new GridBagLayout());
        JPanel addClienPanelXYZPanel = new JPanel(new BorderLayout());
        // Панель для кнопок "назад" и "вперед"
        JPanel buttonsPanel = new JPanel(new GridBagLayout());
        JPanel buttonsXYZPanel = new JPanel(new BorderLayout());

        try {
            MaskFormatter formatter = new MaskFormatter("+7(###)-###-##-##");
            formatter.setPlaceholderCharacter(' ');
            numberFone = new JFormattedTextField(formatter);
        } catch (ParseException e) {
            e.printStackTrace();
            numberFone = new JFormattedTextField();
        }

        name.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                // Поле имени принимает только буквы, пробелы и ограничено по длине символов 30.
                char c = e.getKeyChar();
                if (!(Character.isLetter(c) || c == ' ') || name.getText().length() >= 30) {
                    e.consume();
                }
            }
        });
        numberAuto.addKeyListener(new KeyAdapter() {
            // Ввод гос/номера должен быть в формате
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                String text = numberAuto.getText();
                if (text.length() == 0) {
                    // Первый символ буква
                    if (!Character.isLetter(c)) {
                        e.consume();
                    }
                } else if (text.length() >= 1 && text.length() <= 3) {
                    // Следующие 3 это цифры
                    if (!Character.isDigit(c)) {
                        e.consume();
                    }
                } else if (text.length() >= 4 && text.length() <= 5) {
                    // Пятый и шестой символ буквы
                    if (!Character.isLetter(c)) {
                        e.consume();
                    }
                } else if (text.length() >= 6) {
                    // Последние два или три опять цифры
                    if (!Character.isDigit(c) || numberAuto.getText().length() >= 9) {
                        e.consume();
                    }
                } else {
                    e.consume();
                }
            }
        });
        modelAuto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                // Поле модель авто принимает буквы, цифры, пробелы и ограничено по длине символов 20.
                char c = e.getKeyChar();
                if (!(Character.isLetter(c) || Character.isDigit(c) || c == ' ') || modelAuto.getText().length() >= 20)
                    e.consume();
            }
        });

        // Панель с вводом данных по клиенту.
        addClienPanel.add(new JLabel("Имя клиента."), new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 20, 2, 2), 0, 10));
        addClienPanel.add(name, new GridBagConstraints(1, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 200), 100, 10));
        addClienPanel.add(new JLabel("Телефон."), new GridBagConstraints(0, 1, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 20, 2, 2), 0, 10));
        addClienPanel.add(numberFone, new GridBagConstraints(1, 1, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 200), 100, 10));
        addClienPanel.add(new JLabel("Гос/номер."), new GridBagConstraints(0, 2, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 20, 2, 2), 0, 10));
        addClienPanel.add(numberAuto, new GridBagConstraints(1, 2, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 200), 100, 10));
        addClienPanel.add(new JLabel("Модель автомобиля."), new GridBagConstraints(0, 3, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 20, 2, 2), 0, 10));
        addClienPanel.add(modelAuto, new GridBagConstraints(1, 3, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 200), 100, 10));
        // Панель с 2 кнопками
        buttonsPanel.add(but.getButtonBack(), new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 10, 2), 10, 0));
        buttonsPanel.add(but.getButtonNext(), new GridBagConstraints(1, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 10, 10), 1, 0));

        changeColor(name, 3);
        changeColorFoneNumber(numberFone, 11);
        changeColor(numberAuto, 7);
        changeColor(modelAuto, 3);
        // Отправляем на проверку клиента и если он не равен null печатаем его значения в поля ввода.
        printIfClientDuNotNull(client);
        // Панель с полем ввода мы устанавливаем в верхнею часть панели и кладем в центр основной панели.
        addClienPanelXYZPanel.add(addClienPanel, BorderLayout.NORTH);
        panel.add(addClienPanelXYZPanel, BorderLayout.CENTER);
        // Панель с кнопками устанавливаем в правую часть панели и кладем в низ окна основной панели.
        buttonsXYZPanel.add(buttonsPanel, BorderLayout.EAST);
        panel.add(buttonsXYZPanel, BorderLayout.SOUTH);

        but.getButtonBack().addActionListener(e -> {
            panel.removeAll();
            FirstPanel firstPanel = new FirstPanel();
            firstPanel.createFirstPanel(panel);
        });
        but.getButtonNext().addActionListener(e -> {
            try {
                boolean allBordersAreGreen = Stream.of(name, numberFone, numberAuto, modelAuto)
                        .map(component -> (LineBorder) component.getBorder())
                        .allMatch(border -> border.getLineColor().equals(Color.green));

                if (allBordersAreGreen) {
                    AddWorkPanel addWorkPanel = new AddWorkPanel();
                    client.setName(name.getText());
                    client.setFoneNumber(numberFone.getText());
                    client.setNumberAuto(numberAuto.getText());
                    client.setModelAuto(modelAuto.getText());
                    panel.removeAll();
                    panel.updateUI();
                    addWorkPanel.startPanel(panel, client, FindParentFrame.findParentJFrame(panel));
                }
            } catch (Exception o) {
                JOptionPane.showMessageDialog(null, "Необходимо заполнить все поля!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.updateUI();
    } // Стартовое поле с графами ввода данных клиента

    private void changeColor(JTextField line, int numberOfMinSymbols) {
        line.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void removeUpdate(DocumentEvent e) {
                // в зависимости от количества символов цвет строки изменяется.
                if (line.getText().length() < numberOfMinSymbols) {
                    line.setBorder(BorderFactory.createLineBorder(Color.red));
                } else {
                    line.setBorder(BorderFactory.createLineBorder(Color.green));
                }
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                // в зависимости от количества символов цвет строки изменяется.
                if (line.getText().length() < numberOfMinSymbols) {
                    line.setBorder(BorderFactory.createLineBorder(Color.red));
                } else {
                    line.setBorder(BorderFactory.createLineBorder(Color.green));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // в зависимости от количества символов цвет строки изменяется.
                if (line.getText().length() < numberOfMinSymbols) {
                    line.setBorder(BorderFactory.createLineBorder(Color.red));
                } else {
                    line.setBorder(BorderFactory.createLineBorder(Color.green));
                }
            }
        });
    } // Изменение цвета граф ввода

    private void changeColorFoneNumber(JTextField line, int numberOfMinSymbols) {
        line.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void removeUpdate(DocumentEvent e) {
                // в зависимости от количества символов цвет строки изменяется.
                if (line.getText().replaceAll("[^0-9]", "").length() < numberOfMinSymbols) {
                    line.setBorder(BorderFactory.createLineBorder(Color.red));
                } else {
                    line.setBorder(BorderFactory.createLineBorder(Color.green));
                }
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                // в зависимости от количества символов цвет строки изменяется.
                if (line.getText().replaceAll("[^0-9]", "").length() < numberOfMinSymbols) {
                    line.setBorder(BorderFactory.createLineBorder(Color.red));
                } else {
                    line.setBorder(BorderFactory.createLineBorder(Color.green));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // в зависимости от количества символов цвет строки изменяется.
                if (line.getText().replaceAll("[^0-9]", "").length() < numberOfMinSymbols) {
                    line.setBorder(BorderFactory.createLineBorder(Color.red));
                } else {
                    line.setBorder(BorderFactory.createLineBorder(Color.green));
                }
            }
        });
    } // Изменение цвета графы телефонного номера

    private void printIfClientDuNotNull(Client client) {
        if (client.getName() != null) {
            name.setText(client.getName());
            numberFone.setValue(client.getFoneNumber());
            numberAuto.setText(client.getNumberAuto());
            modelAuto.setText(client.getModelAuto());
        }
    } // При возврате из следующих полей отправляет в графы текст параметров клиента
}