package ru.yandex.practicum.calcRemAuto.panelsAndButtons.panels;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class CorrectionPanel extends JPanel {
    private double value;
    private final Consumer<Double> onChangeCallback;
    private final JTextField displayField;

    /**
     * Конструктор принимает начальное значение и callback для уведомления об изменениях.
     *
     * @param initialValue      Начальное значение для отображения
     * @param onChangeCallback Callback, выполняемый при изменении значения
     */
    public CorrectionPanel(double initialValue, Consumer<Double> onChangeCallback) {
        this.value = initialValue;
        this.onChangeCallback = onChangeCallback;

        // Устанавливаем GridBagLayout для равномерного распределения элементов
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JButton minusButton = new JButton();
        JButton plusButton = new JButton();

        // Загружаем иконки и масштабируем их
        minusButton.setIcon(new ImageIcon(new ImageIcon("Системные/left-arrow.png").getImage().getScaledInstance(16, 12, Image.SCALE_SMOOTH)));
        plusButton.setIcon(new ImageIcon(new ImageIcon("Системные/right-arrow.png").getImage().getScaledInstance(16, 12, Image.SCALE_SMOOTH)));

        // Не редактируемое текстовое поле для отображения значения
        displayField = new JTextField(Double.toString(value), 3);
        displayField.setEditable(false);
        displayField.setBackground(Color.WHITE);

        // Регистрация обработчиков событий
        minusButton.addActionListener(e -> changeValue(-0.5));
        plusButton.addActionListener(e -> changeValue(+0.5));

        // Настройки для GridBagLayout
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(0, 2, 0, 2);

        // Ширина и высота каждой ячейки равна 1
        gbc.gridwidth = 1;
        gbc.gridheight = 1;

        // Расположение кнопок и поля
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(minusButton, gbc);

        gbc.gridx = 1;
        add(displayField, gbc);

        gbc.gridx = 2;
        add(plusButton, gbc);

        // Задание фиксированного размера для кнопок и поля
        Dimension buttonSize = new Dimension(20, 18);
        minusButton.setPreferredSize(buttonSize);
        plusButton.setPreferredSize(buttonSize);
        displayField.setPreferredSize(buttonSize);
    }

    /**
     * Приватный метод, изменяющий текущее значение и уведомляющий внешний класс о событии.
     *
     * @param delta Величина изменения (+/- 0.5)
     */
    private void changeValue(double delta) {
        value += delta;
        displayField.setText(String.format("%.1f", value));
        if (onChangeCallback != null) {
            onChangeCallback.accept(value);
        }
    }

    /**
     * Публичный метод для принудительного обновления значения в панели.
     *
     * @param newValue Новое значение, которое нужно установить
     */
    public void updateValue(double newValue) {
        value = newValue;
        displayField.setText(String.format("%.1f", value));
        if (onChangeCallback != null) {
            onChangeCallback.accept(value);
        }
    }
}