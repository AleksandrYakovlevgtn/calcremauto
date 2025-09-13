package ru.yandex.practicum.calcRemAuto.panelsAndButtons.panels;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.calcRemAuto.logToFail.LogToFailManager;
import ru.yandex.practicum.calcRemAuto.model.Client;
import ru.yandex.practicum.calcRemAuto.panelsAndButtons.buttons.Buttons;
import ru.yandex.practicum.calcRemAuto.panelsAndButtons.frame.StartFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Slf4j
public class FirstPanel {
    Buttons but = new Buttons(); // Кнопки
    private List<JButton> buttons = new ArrayList<>(); // Список необходимых данной панели кнопок.
    LogToFailManager logManager = new LogToFailManager();


    public void createFirstPanel(JPanel panel) {
        logManager.log("Запущен метод createFirstPanel в классе FirstPanel");
        buttons.add(but.getButtonCalc());
        buttons.add(but.getButtonSearch());
        buttons.add(but.getButtonCloseApp());

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);

        int buttonWidth = 150; // Размер кнопок

        for (JButton button : buttons) {
            button.setPreferredSize(new Dimension(buttonWidth, button.getPreferredSize().height));
            panel.add(button, gbc);
            gbc.gridy++;
        }

        but.getButtonCloseApp().addActionListener(e -> {
            logManager.log("Нажата кнопка Выход");
            StartFrame startFrame = StartFrame.getCurrentInstance();
            if (startFrame != null) {
                // Закрываем поток
                startFrame.shutdownExecutor();
            }
            System.exit(0);
        });
        but.getButtonSearch().addActionListener(e -> {
            logManager.log("Нажата кнопка Поиск.");
            SearchPanel searchPanel = new SearchPanel();
            panel.removeAll();
            panel.updateUI();
            logManager.log("Создаем JPanel SearchPanel.");
            searchPanel.createSearchPanel(panel);
        });
        but.getButtonCalc().addActionListener(e -> {
            logManager.log("Нажата кнопка Расчет.");
            AddClientPanel addClientPanel = new AddClientPanel();
            panel.removeAll();
            panel.updateUI();
            Client client = new Client();
            logManager.log("Создаем JPanel AddClientPanel.");
            addClientPanel.clientAdd(panel, client);
        });
        panel.updateUI();
    }
}