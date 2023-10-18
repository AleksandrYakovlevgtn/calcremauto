package ru.yandex.practicum.calcRemAuto.panelsAndButtons.panels;

import lombok.Getter;
import lombok.Setter;
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
    AddPanel addPanel =new AddPanel();
    private List<JButton> buttons = new ArrayList<>();
    public JPanel firstPanel(JPanel panel){
        buttons.add(but.getButtonCalc());
        buttons.add(but.getButtonSearch());
        buttons.add(but.getButtonCloseApp());
        GridLayout firstFrame = new GridLayout(3, 1);

        panel.setBounds(300,100,200,200);
        panel.setLayout(firstFrame);
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
            addPanel.clientAdd(panel);
        });
        return panel;
    }
}