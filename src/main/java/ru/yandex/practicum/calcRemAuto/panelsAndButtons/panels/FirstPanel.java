package ru.yandex.practicum.calcRemAuto.panelsAndButtons.panels;

import lombok.Getter;
import lombok.Setter;
import ru.yandex.practicum.calcRemAuto.model.Client;
import ru.yandex.practicum.calcRemAuto.panelsAndButtons.buttons.Buttons;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FirstPanel {
    Buttons but = new Buttons(); // Кнопки
    private List<JButton> buttons = new ArrayList<>(); // Список необходимых данной панели кнопок.

    public void firstPanel(JPanel panel) {
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

        but.getButtonCloseApp().addActionListener(e -> System.exit(0));
        but.getButtonSearch().addActionListener(e -> {
            SearchPanel searchPanel = new SearchPanel();
            panel.removeAll();
            panel.updateUI();
            searchPanel.SearchPanel(panel);
        });
        but.getButtonCalc().addActionListener(e -> {
            AddClientPanel addClientPanel = new AddClientPanel();
            panel.removeAll();
            panel.updateUI();
            Client client = new Client();
            addClientPanel.clientAdd(panel, client);
        });
        panel.updateUI();
    }
}