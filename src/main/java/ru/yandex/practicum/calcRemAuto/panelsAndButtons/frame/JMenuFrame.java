package ru.yandex.practicum.calcRemAuto.panelsAndButtons.frame;

import lombok.Getter;
import ru.yandex.practicum.calcRemAuto.model.Prices;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

@Getter
public class JMenuFrame extends JDialog {
    JMenuBar menuBar = new JMenuBar(); // Верхняя панель с выпадающим окном
    JMenu settingsMenu = new JMenu("Настройки"); // Элемент на выпадающем окне
    JMenuItem changeRatesItem = new JMenuItem("Изменить стоимость"); // Открывающееся окно элемента из выпадающей панели
    private int hourlyRate; // Стоимость общего н/ч
    private int mechanicHourlyRate; // Стоимость норматива механика
    private int masterHourlyRate;  // Стоимость норматива мастера
    Prices prices = new Prices();  // Класс с ценами

    public JMenuFrame(JFrame parentFrame) {
        super(parentFrame, "Изменение стоимости н/ч!", true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                deleteComponentsFromJDialog();
            }
        });

        hourlyRate = prices.getHourlyRate();
        mechanicHourlyRate = prices.getMechanicHourlyRate();
        masterHourlyRate = prices.getMasterHourlyRate();

        changeRatesItem.addActionListener(e -> openFrame(parentFrame));

        settingsMenu.add(changeRatesItem);
        menuBar.add(settingsMenu);
    }  // Начальная настройка параметров

    private void openFrame(Frame parentFrame) {

        setLayout(new GridBagLayout());

        JTextField hourlyRateField = new JTextField(String.valueOf(hourlyRate));
        JTextField mechanicHourlyRateField = new JTextField(String.valueOf(mechanicHourlyRate));
        JTextField masterHourlyRateField = new JTextField(String.valueOf(masterHourlyRate));

        JButton saveButton = new JButton("Сохранить");
        JButton cancelButton = new JButton("Отмена");

        add(new JLabel("Почасовая ставка:"), createGridBagConstraints(1, 0, 1, 1));
        add(hourlyRateField, createGridBagConstraints(2, 0, 1, 1));
        add(new JLabel("Почасовая ставка механика:"), createGridBagConstraints(1, 1, 1, 1));
        add(mechanicHourlyRateField, createGridBagConstraints(2, 1, 1, 1));
        add(new JLabel("Почасовая ставка мастера:"), createGridBagConstraints(1, 2, 1, 1));
        add(masterHourlyRateField, createGridBagConstraints(2, 2, 1, 1));
        add(saveButton, createGridBagConstraints(1, 3, 1, 1));
        add(cancelButton, createGridBagConstraints(2, 3, 1, 1));

        saveButton.addActionListener(saveEvent -> {
            hourlyRate = Integer.parseInt(hourlyRateField.getText());
            mechanicHourlyRate = Integer.parseInt(mechanicHourlyRateField.getText());
            masterHourlyRate = Integer.parseInt(masterHourlyRateField.getText());
            prices.writeRatesToFile(hourlyRate,mechanicHourlyRate,masterHourlyRate);
            deleteComponentsFromJDialog();
            dispose();
        });
        cancelButton.addActionListener(cancelEvent -> {
            deleteComponentsFromJDialog();
            dispose();
        });

        pack();
        setLocationRelativeTo(parentFrame);
        setVisible(true);
        //prices.writeRatesToFile(hourlyRate,mechanicHourlyRate,masterHourlyRate);
    } // Создание окна с элементами

    private void deleteComponentsFromJDialog() {
        Container contentPane = getContentPane();
        // Получение всех компонентов, расположенных на панели содержимого JDialog
        Component[] components = contentPane.getComponents();
        for (Component component : components) {
            // Удаление компонентов
            contentPane.remove(component);
        }
    } // Удаление из окна элементов для устранения эффекта наложения при повторном открытии

    private GridBagConstraints createGridBagConstraints(int gridx, int gridy, int gridwidth, int gridheight) {
        return new GridBagConstraints(gridx, gridy, gridwidth, gridheight, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 1, 1);
    } // Устроение повторного кода конструктора
}