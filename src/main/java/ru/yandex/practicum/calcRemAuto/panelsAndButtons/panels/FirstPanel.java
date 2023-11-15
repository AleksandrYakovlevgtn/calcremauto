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
public class FirstPanel extends JFrame{
    Buttons but = new Buttons();
    Frame frame = new Frame();
    SearchPanel searchPanel = new SearchPanel();
    AddClientPanel addPanel =new AddClientPanel();
    private List<JButton> buttons = new ArrayList<>();
    public JPanel firstPanel(JPanel panel){
        buttons.add(but.getButtonCalc());
        buttons.add(but.getButtonSearch());
        buttons.add(but.getButtonCloseApp());

        panel.setBounds(300,100,200,200);
        panel.setLayout(new GridLayout(3, 1));

        buttons.forEach(panel::add);
        panel.updateUI();

        but.getButtonCloseApp().addActionListener(e -> System.exit(0));
        but.getButtonSearch().addActionListener(e -> {
            panel.remove(0);
            panel.remove(1);
            panel.updateUI();
            searchPanel.SearchPanel(panel);
        });
        but.getButtonCalc().addActionListener(e -> {
            panel.removeAll();
            panel.updateUI();
            Client client = new Client();
            addPanel.clientAdd(panel,client);
        });
        return panel;
    }
}