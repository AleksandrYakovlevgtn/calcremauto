package ru.yandex.practicum.calcRemAuto.panelsAndButtons.panels;

import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.calcRemAuto.model.Client;
import ru.yandex.practicum.calcRemAuto.model.Element;
import ru.yandex.practicum.calcRemAuto.panelsAndButtons.buttons.Buttons;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class AddWorkPanel {
    Buttons but = new Buttons();
    JPanel elementLeftRightSidePanel = new JPanel();
    JPanel elementCenterSide = new JPanel();
    String elementPainSide;
    Double elementArmatureSide;
    Double elementKuzDetReplaceSide;
    String elementRemont;
    Double elementPaintSide;
    List<Element> elementList = new ArrayList<>();
    Map<String, Map<String, List<String>>> lineBorderColorMap = new HashMap<>();
    JButton sideButtonPushed;
    JButton elementButtonPushed;
    JButton zamenaOrRsButtonPushed;
    JButton paint1xOr2xButtonPushed;
    JButton remontButtonPushed;
    JButton ruchkaButtonPushed;
    JButton moldingButtonPushed;
    JButton zercaloButtonPushed;

    public JPanel startAddPanel(JPanel panel, Client client, JFrame frame) {
        panel.removeAll();
        JPanel panelStartAdd = new JPanel();
        JPanel panelXYZ = new JPanel();
        JPanel panelSaveAndBack = new JPanel();
        JPanel panelXYZSaveAndBack = new JPanel();
        JPanel panelAdd = new JPanel();

        panelXYZ.setLayout(new BorderLayout());
        panel.setLayout(new BorderLayout());
        panelSaveAndBack.setLayout(new GridLayout(1, 2));
        panelXYZSaveAndBack.setLayout(new BorderLayout());
        panelAdd.setLayout(new BorderLayout());

        panel.setBounds(0, 0, 775, 550);
        panelStartAdd.setBounds(0, 0, 125, 125);
        panelSaveAndBack.setBounds(0, 0, 125, 125);
        panelXYZSaveAndBack.setBounds(0, 100, 125, 125);

        panelStartAdd.setLayout(new GridLayout(5, 1));
        panelStartAdd.add(new JLabel("Выбери сторону."));
        panelStartAdd.add(takeColor(but.getButtonLeft(), 1));
        panelStartAdd.add(takeColor(but.getButtonCenter(), 1));
        panelStartAdd.add(takeColor(but.getButtonRight(), 1));
        panelStartAdd.add(takeColor(but.getButtonGlass(), 1));

        panelSaveAndBack.add(but.getButtonBack());
        panelSaveAndBack.add(but.getButtonSave());

        panelXYZ.add(panelStartAdd, BorderLayout.NORTH);
        panel.add(panelXYZ, BorderLayout.WEST);

        panelXYZSaveAndBack.add(panelSaveAndBack, BorderLayout.EAST);
        panel.add(panelXYZSaveAndBack, BorderLayout.SOUTH);
        panel.add(panelAdd, BorderLayout.CENTER);

        panelStartAdd.updateUI();

        but.getButtonLeft().addActionListener(e -> {
            elementPainSide = but.getButtonLeft().getText();
            sideButtonPushed = but.getButtonLeft();
            addElementLeftRightSide(clearAll(panelAdd));
        });
        but.getButtonRight().addActionListener(e -> {
            elementPainSide = but.getButtonRight().getText();
            sideButtonPushed = but.getButtonRight();
            addElementLeftRightSide(clearAll(panelAdd));
        });
        but.getButtonGlass().addActionListener(e -> {
            sideButtonPushed = but.getButtonGlass();
            elementPainSide = but.getButtonGlass().getText();
            addElementGlass(clearAll(panelAdd));
        });
        but.getButtonCenter().addActionListener(e -> {
            sideButtonPushed = but.getButtonCenter();
            addElementCenterSide(clearAll(panelAdd));
        });
        return panel;
    }

    private void addElementLeftRightSide(JPanel panelAdd) {
        JPanel panelXYZ = new JPanel();
        JPanel panelXYZ2 = new JPanel();

        elementLeftRightSidePanel.setLayout(new GridBagLayout());
        panelXYZ.setLayout(new BorderLayout());
        panelXYZ2.setLayout(new BorderLayout());

        elementLeftRightSidePanel.add(new JLabel("Выбери Элемент."), new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 10));
        elementLeftRightSidePanel.add(takeColor(but.getButtonFrontWing(), 2), new GridBagConstraints(0, 1, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        elementLeftRightSidePanel.add(takeColor(but.getButtonFrontDoor(), 2), new GridBagConstraints(0, 2, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        elementLeftRightSidePanel.add(takeColor(but.getButtonBackDoor(), 2), new GridBagConstraints(0, 3, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        elementLeftRightSidePanel.add(takeColor(but.getButtonBackWing(), 2), new GridBagConstraints(0, 4, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        elementLeftRightSidePanel.add(takeColor(but.getButtonBalk(), 2), new GridBagConstraints(0, 5, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        elementLeftRightSidePanel.add(takeColor(but.getButtonDoorStep(), 2), new GridBagConstraints(0, 6, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        elementLeftRightSidePanel.add(takeColor(but.getButtonFrontDoorway(), 2), new GridBagConstraints(0, 7, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        elementLeftRightSidePanel.add(takeColor(but.getButtonBackDoorway(), 2), new GridBagConstraints(0, 8, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));

        panelXYZ.add(elementLeftRightSidePanel, BorderLayout.NORTH);
        panelXYZ2.add(panelXYZ, BorderLayout.WEST);
        panelAdd.add(panelXYZ2, BorderLayout.WEST);
        panelAdd.updateUI();

        but.getButtonFrontWing().addActionListener(e -> {
            clearColorButton();
            elementButtonPushed = but.getButtonFrontWing();
            addElementWorks(elementLeftRightSidePanel, clearCenter(panelAdd));
        });
        but.getButtonFrontDoor().addActionListener(e -> {
            clearColorButton();
            elementButtonPushed = but.getButtonFrontDoor();
            addElementWorks(elementLeftRightSidePanel, clearCenter(panelAdd));
        });
        but.getButtonBackDoor().addActionListener(e -> {
            clearColorButton();
            elementButtonPushed = but.getButtonBackDoor();
            addElementWorks(elementLeftRightSidePanel, clearCenter(panelAdd));
        });
        but.getButtonBackWing().addActionListener(e -> {
            clearColorButton();
            elementButtonPushed = but.getButtonBackWing();
            addElementWorks(elementLeftRightSidePanel, clearCenter(panelAdd));
        });
        but.getButtonDoorStep().addActionListener(e -> {
            clearColorButton();
            elementButtonPushed = but.getButtonDoorStep();
            addElementWorks(elementLeftRightSidePanel, clearCenter(panelAdd));
        });
        but.getButtonBalk().addActionListener(e -> {
            clearColorButton();
            elementButtonPushed = but.getButtonBalk();
            addElementWorks(elementLeftRightSidePanel, clearCenter(panelAdd));
        });
        but.getButtonFrontDoorway().addActionListener(e -> {
            clearColorButton();
            elementButtonPushed = but.getButtonFrontDoorway();
            addElementWorks(elementLeftRightSidePanel, clearCenter(panelAdd));
        });
        but.getButtonBackDoorway().addActionListener(e -> {
            clearColorButton();
            elementButtonPushed = but.getButtonBackDoorway();
            addElementWorks(elementLeftRightSidePanel, clearCenter(panelAdd));
        });
    }

    private void addElementCenterSide(JPanel panel) {
        JPanel panelXYZ = new JPanel();
        JPanel panelXYZ2 = new JPanel();

        elementCenterSide.setLayout(new GridBagLayout());
        panelXYZ.setLayout(new BorderLayout());
        panelXYZ2.setLayout(new BorderLayout());

        elementCenterSide.add(new JLabel("Выбери Элемент."), new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 10));
        elementCenterSide.add(but.getFrontBumperButton(), new GridBagConstraints(0, 1, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
        elementCenterSide.add(but.getBonnetButton(), new GridBagConstraints(0, 2, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
        elementCenterSide.add(but.getRoofButton(), new GridBagConstraints(0, 3, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
        elementCenterSide.add(but.getTrunkLidButton(), new GridBagConstraints(0, 4, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
        elementCenterSide.add(but.getRearBumperButton(), new GridBagConstraints(0, 5, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
        elementCenterSide.add(but.getEngineCompartmentButton(), new GridBagConstraints(0, 6, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
        elementCenterSide.add(but.getBackPanelButton(), new GridBagConstraints(0, 7, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));

        panelXYZ.add(elementCenterSide, BorderLayout.NORTH);
        panelXYZ2.add(panelXYZ, BorderLayout.WEST);
        panel.add(panelXYZ2, BorderLayout.WEST);
        panel.updateUI();

        but.getFrontBumperButton().addActionListener(e -> {
            elementButtonPushed = but.getFrontBumperButton();
            addElementWorks(elementCenterSide, panel);
        });
        but.getBonnetButton().addActionListener(e -> {
            elementButtonPushed = but.getBonnetButton();
            addElementWorks(elementCenterSide, panel);
        });
        but.getRoofButton().addActionListener(e -> {
            elementButtonPushed = but.getRoofButton();
            addElementWorks(elementCenterSide, panel);
        });
        but.getTrunkLidButton().addActionListener(e -> {
            elementButtonPushed = but.getTrunkLidButton();
            addElementWorks(elementCenterSide, panel);
        });
        but.getRearBumperButton().addActionListener(e -> {
            elementButtonPushed = but.getRearBumperButton();
            addElementWorks(elementCenterSide, panel);
        });
        but.getEngineCompartmentButton().addActionListener(e -> {
            elementButtonPushed = but.getEngineCompartmentButton();
            addElementWorks(elementCenterSide, panel);
        });
        but.getBackPanelButton().addActionListener(e -> {
            elementButtonPushed = but.getBackPanelButton();
            addElementWorks(elementCenterSide, panel);
        });
    }

    @Transactional
    public void addElementWorks(JPanel panelElement, JPanel panelAdd) {
        JTextField remont = but.getRemontJText();
        remont.removeAll();
        elementRemont = null;
        remont.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) throws RuntimeException {
                // Проверяем что введено число, если да то цвет поля зеленый нет розовый.
                try {
                    if (!remont.getText().isBlank()) {
                        Integer.parseInt(remont.getText());
                        remont.setBackground(Color.green);
                    } else {
                        remont.setBackground(null);
                    }
                } catch (NumberFormatException o) {
                    remont.setBackground(Color.pink);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                // Проверяем что введено число, если да то цвет поля зеленый нет розовый.
                try {
                    if (!remont.getText().isBlank()) {
                        Integer.parseInt(remont.getText());
                        remont.setBackground(Color.green);
                    } else {
                        remont.setBackground(null);
                    }
                } catch (NumberFormatException o) {
                    remont.setBackground(Color.pink);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Проверяем что введено число, если да то цвет поля зеленый нет розовый.
                try {
                    if (!remont.getText().isBlank()) {
                        Integer.parseInt(remont.getText());
                        remont.setBackground(Color.green);
                    } else {
                        remont.setBackground(null);
                    }
                } catch (NumberFormatException o) {
                    remont.setBackground(Color.pink);
                }
            }
        });
        // Удаляем лишние строчки из работ так как не везде есть молдинги, зеркала и ручки.
        if (panelElement.getComponents().length > 9) {
            for (int i = panelElement.getComponents().length - 1; i >= 9; i--) {
                panelElement.remove(i);
            }
            panelElement.updateUI();
        }
        panelElement.add(new JLabel("Выбери работы."), new GridBagConstraints(1, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        if (!elementButtonPushed.getText().matches("Пер.Проём")) {
            if (!elementButtonPushed.getText().equals("Зад.Проём")) {
                // Замена проемов отдельно не существует (кузовная деталь) и входит либо в порог, либо в брус.
                panelElement.add(takeColor(removeActionListener(but.getReplaceButton()), 3), new GridBagConstraints(1, 1, 1, 1, 1, 1,
                        GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                        new Insets(2, 2, 2, 2), 0, 0));
                // Станешь умнее УБЕРИ ЭТО ЗАДВОЕНИЕ!!!!!!!!
            } else {
                panelElement.add(new JLabel("         Замена"), new GridBagConstraints(1, 1, 1, 1, 1, 1,
                        GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
                        new Insets(2, 2, 2, 2), 0, 0));
            }
            // Станешь умнее УБЕРИ ЭТО ЗАДВОЕНИЕ!!!!!!!!
        } else {
            panelElement.add(new JLabel("         Замена"), new GridBagConstraints(1, 1, 1, 1, 1, 1,
                    GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
                    new Insets(2, 2, 2, 2), 0, 0));
        }
        panelElement.add(takeColor(removeActionListener(but.getDisassembleButton()), 3), new GridBagConstraints(2, 1, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));

        panelElement.add(takeColor(removeActionListener(but.getPaint1xButton()), 3), new GridBagConstraints(1, 2, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));

        panelElement.add(takeColor(removeActionListener(but.getPaint2xButton()), 3), new GridBagConstraints(2, 2, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));

        panelElement.add(takeColor(removeActionListener(but.getRepairButton()), 3), new GridBagConstraints(1, 3, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));

        panelElement.add(remont, new GridBagConstraints(2, 3, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        if (elementButtonPushed.getText().contains("Пер.Дверь") || elementButtonPushed.getText().contains("Зад.Дверь")) {
            if (elementButtonPushed.getText().contains("Пер.Дверь")) {
                panelElement.add(takeColor(removeActionListener(but.getZerkaloButton()), 3), new GridBagConstraints(1, 6, 1, 1, 1, 1,
                        GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                        new Insets(2, 2, 2, 2), 0, 0));
            }
            panelElement.add(takeColor(removeActionListener(but.getRuchkaButton()), 3), new GridBagConstraints(1, 4, 1, 1, 1, 1,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(2, 2, 2, 5), 0, 0));

            panelElement.add(takeColor(removeActionListener(but.getMoldingButton()), 3), new GridBagConstraints(1, 5, 1, 1, 1, 1,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(2, 2, 2, 2), 0, 0));
        }
        remont.setText(elementRemont);
        panelElement.updateUI();

        but.getReplaceButton().addActionListener(e -> { // замена
            zamenaOrRsButtonPushed = but.getReplaceButton();
            if (elementButtonPushed.getText().equals("Пер.Дверь") || elementButtonPushed.getText().equals("Зад.Дверь")) {
                // При замене дверей идет окраска с 2-х сторон и надбавка к арматурщику.
                elementPaintSide = 1.55555556;
                elementArmatureSide = 1.333333333333333;
                paint1xOr2xButtonPushed = but.getPaint2xButton();
                but.getPaint2xButton().setBorder(BorderFactory.createLineBorder(Color.red, 1));
                but.getPaint1xButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
            } else if (elementButtonPushed.getText().equals("Брус") || elementButtonPushed.getText().equals("Порог") || elementButtonPushed.getText().equals("Зад.Крыло")) {
                // При замене приварной детали идет окраска с 1 стороны + 30% от кузовщика и надбавка к арматуре.
                elementPaintSide = 1.77777777778;
                elementArmatureSide = 1.66666666667;
                elementKuzDetReplaceSide = 6.0;
                paint1xOr2xButtonPushed = but.getPaint1xButton();
                but.getPaint1xButton().setBorder(BorderFactory.createLineBorder(Color.red, 1));
                but.getPaint2xButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
            } else {
                // Во всех остальных случаях окраска с 1 стороны и обычный норматив арматурщику.
                elementPaintSide = 1.0;
                elementArmatureSide = 1.0;
                paint1xOr2xButtonPushed = but.getPaint1xButton();
                but.getPaint1xButton().setBorder(BorderFactory.createLineBorder(Color.red, 1));
                but.getPaint2xButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
            }
            but.getDisassembleButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
            but.getReplaceButton().setBorder(BorderFactory.createLineBorder(Color.red, 1));
            addElementToList(clearCenter(panelAdd));
            panelAdd.updateUI();
        });

        but.getDisassembleButton().addActionListener(e -> { // Р\С
            zamenaOrRsButtonPushed = but.getDisassembleButton();
            paint1xOr2xButtonPushed = but.getPaint1xButton();
            elementPaintSide = 1.0;
            elementArmatureSide = 1.0;
            but.getReplaceButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
            but.getDisassembleButton().setBorder(BorderFactory.createLineBorder(Color.red, 1));
            but.getPaint2xButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
            but.getPaint1xButton().setBorder(BorderFactory.createLineBorder(Color.red, 1));
            addElementToList(clearCenter(panelAdd));
            panelAdd.updateUI();
        });

        but.getPaint1xButton().addActionListener(e -> { // окраска 1х
            paint1xOr2xButtonPushed = but.getPaint1xButton();
            if (zamenaOrRsButtonPushed == null) {
                zamenaOrRsButtonPushed = but.getDisassembleButton();
                but.getReplaceButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
                but.getDisassembleButton().setBorder(BorderFactory.createLineBorder(Color.red, 1));
            }
            elementPaintSide = 1.0;
            but.getPaint2xButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
            but.getPaint1xButton().setBorder(BorderFactory.createLineBorder(Color.red, 1));
            addElementToList(clearCenter(panelAdd));
            panelAdd.updateUI();
        });

        but.getPaint2xButton().addActionListener(e -> { // окраска 2х
            paint1xOr2xButtonPushed = but.getPaint2xButton();
            if (zamenaOrRsButtonPushed == null) {
                zamenaOrRsButtonPushed = but.getDisassembleButton();
                but.getReplaceButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
                but.getDisassembleButton().setBorder(BorderFactory.createLineBorder(Color.red, 1));
            }
            elementPaintSide = 1.55555556;
            but.getPaint1xButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
            but.getPaint2xButton().setBorder(BorderFactory.createLineBorder(Color.red, 1));
            addElementToList(clearCenter(panelAdd));
            panelAdd.updateUI();
        });

        but.getRepairButton().addActionListener(e -> { // ремонт
            // проверяем добелена ли окраска и разборка.
            if (zamenaOrRsButtonPushed != null && paint1xOr2xButtonPushed != null) {
                if (remont.getBackground() != Color.pink && remont.getText().length() > 0) {
                    // добавляем перед последним символом точку переведя число в двоичное.
                    remontButtonPushed = but.getRepairButton();
                    elementRemont = remont.getText();
                    removeActionListener(but.getRepairButton()).setBorder(BorderFactory.createLineBorder(Color.red, 1));
                    addElementToList(clearCenter(panelAdd));
                    panelAdd.updateUI();
                }
            }
        });
        but.getRuchkaButton().addActionListener(e -> {
            ruchkaButtonPushed = removeActionListener(but.getRuchkaButton());
            but.getRuchkaButton().setBorder(BorderFactory.createLineBorder(Color.red, 1));
        });
        but.getMoldingButton().addActionListener(e -> {
            moldingButtonPushed = removeActionListener(but.getMoldingButton());
            but.getMoldingButton().setBorder(BorderFactory.createLineBorder(Color.red, 1));
        });
        but.getZerkaloButton().addActionListener(e -> {
            zercaloButtonPushed = removeActionListener(but.getZerkaloButton());
            but.getZerkaloButton().setBorder(BorderFactory.createLineBorder(Color.red, 1));
        });
    }

    private JPanel addElementGlass(JPanel panelAdd) {
        JPanel panelGlass = new JPanel();
        JPanel panelXY = new JPanel();
        JPanel panelXY2 = new JPanel();

        panelGlass.setLayout(new GridLayout(3, 1));
        panelXY.setLayout(new BorderLayout());
        panelXY2.setLayout(new BorderLayout());

        panelGlass.add(new JLabel("Выбери стекло."));
        panelGlass.add(but.getButtonWindshield());
        panelGlass.add(but.getButtonRearWindow());

        panelXY.add(panelGlass, BorderLayout.NORTH);
        panelXY2.add(panelXY, BorderLayout.WEST);
        panelAdd.add(panelXY2, BorderLayout.WEST);
        panelAdd.updateUI();

        but.getButtonWindshield().addActionListener(e -> {
            elementButtonPushed = removeActionListener(but.getButtonWindshield());
            addElementToList(addElementGlass(clearAll(panelAdd)));
        });
        but.getButtonRearWindow().addActionListener(e -> {
            elementButtonPushed = removeActionListener(but.getButtonRearWindow());
            addElementToList(addElementGlass(clearAll(panelAdd)));
        });
        return panelAdd;
    }

    private void addElementToList(JPanel panel) {
        JPanel panelAddElementToList = new JPanel();
        JPanel panelXY = new JPanel();
        JPanel panelXY2 = new JPanel();

        panelAddElementToList.setLayout(new GridBagLayout());
        panelXY.setLayout(new BorderLayout());
        panelXY2.setLayout(new BorderLayout());

        panelAddElementToList.add(new JLabel(" "), new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
        panelAddElementToList.add(new JLabel(" "), new GridBagConstraints(0, 1, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
        panelAddElementToList.add(removeActionListener(but.getAddButton()), new GridBagConstraints(0, 2, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));

        panelXY.add(panelAddElementToList, BorderLayout.NORTH);
        panelXY2.add(panelXY, BorderLayout.WEST);
        panel.add(panelXY2, BorderLayout.CENTER);
        panel.updateUI();

        but.getAddButton().addActionListener(o -> {
            Element element = createElement();
            sideButtonPushed.setBorder(BorderFactory.createLineBorder(Color.green, 1));
            elementButtonPushed.setBorder(BorderFactory.createLineBorder(Color.green, 1));
            if (elementList.isEmpty()) {
                elementList.add(element);
                if (lineBorderColorMap.get(sideButtonPushed.getText()) != null) {
                    lineBorderColorMap.get(sideButtonPushed.getText()).put(elementButtonPushed.getText(), addColorOfAddWorks());
                } else {
                    lineBorderColorMap.put(sideButtonPushed.getText(), new HashMap<>(Map.of(elementButtonPushed.getText(), addColorOfAddWorks())));
                }
            } else {
                Integer q = null;
                Element el = null;
                for (int i = 0; i <= elementList.size() - 1; i++) {
                    if (elementList.get(i).getName().equals(element.getName())) {
                        q = i;
                        el = elementList.get(i);
                    }
                }
                if (q == null) {
                    elementList.add(element);
                    if (lineBorderColorMap.get(sideButtonPushed.getText()) != null) {
                        lineBorderColorMap.get(sideButtonPushed.getText()).put(elementButtonPushed.getText(), addColorOfAddWorks());
                    } else {
                        lineBorderColorMap.put(sideButtonPushed.getText(), new HashMap<>(Map.of(elementButtonPushed.getText(), addColorOfAddWorks())));
                    }
                } else {
                    elementList.remove(el);
                    elementList.add(element);
                    if (lineBorderColorMap.get(sideButtonPushed.getText()) != null) {
                        lineBorderColorMap.get(sideButtonPushed.getText()).put(elementButtonPushed.getText(), addColorOfAddWorks());
                    } else {
                        lineBorderColorMap.put(sideButtonPushed.getText(), new HashMap<>(Map.of(elementButtonPushed.getText(), addColorOfAddWorks())));
                    }
                }
            }
            clearCenter(panel).updateUI();
        });
    }

    private JButton takeColor(JButton button, int panelNumber) {
        button.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
        switch (panelNumber) {
            case 1:
                if (lineBorderColorMap.get(button.getText()) != null) {
                    button.setBorder(BorderFactory.createLineBorder(Color.green));
                }
                break;
            case 2:
                if (lineBorderColorMap.get(sideButtonPushed.getText()) != null) {
                    if (lineBorderColorMap.get(sideButtonPushed.getText()).get(button.getText()) != null) {
                        button.setBorder(BorderFactory.createLineBorder(Color.green));
                    }
                }
                break;
            case 3:
                if (lineBorderColorMap.get(sideButtonPushed.getText()) != null) {
                    // находим первый ключ кнопку1 (сторона)
                    Map<String, List<String>> panel3 = lineBorderColorMap.get(sideButtonPushed.getText());
                    if (panel3.get(elementButtonPushed.getText()) != null) {
                        // находим в значении ключа второй ключ (имя элемента) и берем его значение лист с работами
                        List<String> listOfAddWorks = panel3.get(elementButtonPushed.getText());
                        if (!listOfAddWorks.isEmpty()) {
                            // проходим по работам и возвращаем кнопки с зеленой рамкой если они есть в списке
                            for (String check : listOfAddWorks) {
                                if (check.equals(button.getText())) {
                                    button.setBorder(BorderFactory.createLineBorder(Color.green));
                                }
                                if (check.contains("Ремонт")) {
                                    button.setBorder(BorderFactory.createLineBorder(Color.green));
                                    String[] split = check.split(" ");
                                    if (split.length > 1) {
                                        elementRemont = split[1];
                                    }
                                }
                            }
                        }
                        listOfAddWorks = null;
                    }
                    panel3 = null;
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + panelNumber);
        }
        return button;
    }

    private List<String> addColorOfAddWorks() {
        List<String> color = new ArrayList<>();
        if (zamenaOrRsButtonPushed != null) {
            color.add(zamenaOrRsButtonPushed.getText());
            zamenaOrRsButtonPushed.setBorder(BorderFactory.createLineBorder(Color.green, 1));
        }
        if (paint1xOr2xButtonPushed != null) {
            color.add(paint1xOr2xButtonPushed.getText());
            paint1xOr2xButtonPushed.setBorder(BorderFactory.createLineBorder(Color.green, 1));
        }
        if (remontButtonPushed != null) {
            color.add(remontButtonPushed.getText() + " " + elementRemont);
            remontButtonPushed.setBorder(BorderFactory.createLineBorder(Color.green, 1));
        }
        if (elementButtonPushed.getText().equals("Пер.Дверь") || elementButtonPushed.getText().equals("Зад.Дверь")) {
            if (elementButtonPushed.getText().contains("Пер.Дверь")) {
                if (ruchkaButtonPushed != null) {
                    color.add(ruchkaButtonPushed.getText());
                    ruchkaButtonPushed.setBorder(BorderFactory.createLineBorder(Color.green, 1));
                }
            }
            if (moldingButtonPushed != null) {
                color.add(moldingButtonPushed.getText());
                moldingButtonPushed.setBorder(BorderFactory.createLineBorder(Color.green, 1));
            }
            if (zercaloButtonPushed != null) {
                color.add(zercaloButtonPushed.getText());
                zercaloButtonPushed.setBorder(BorderFactory.createLineBorder(Color.green, 1));
            }
        }
        return color;
    }

    private JPanel clearAll(JPanel panel) {
        BorderLayout layout = (BorderLayout) panel.getLayout();
        clearColorButton();
        elementLeftRightSidePanel.removeAll();
        elementCenterSide.removeAll();
        if (layout.getLayoutComponent(BorderLayout.WEST) != null) {
            panel.remove(layout.getLayoutComponent(BorderLayout.WEST));
        }
        return clearCenter(panel);
    }

    private JPanel clearCenter(JPanel panel) {
        BorderLayout layout = (BorderLayout) panel.getLayout();
        if (layout.getLayoutComponent(BorderLayout.CENTER) != null) {
            panel.remove(layout.getLayoutComponent(BorderLayout.CENTER));
        }
        panel.updateUI();
        return panel;
    }

    private void clearColorButton() {
        zamenaOrRsButtonPushed = null;
        paint1xOr2xButtonPushed = null;
        remontButtonPushed = null;
        ruchkaButtonPushed = null;
        moldingButtonPushed = null;
        zercaloButtonPushed = null;
    }

    private Element createElement() {
        Element element = new Element();
        if (sideButtonPushed.getText().equals("Левая") || sideButtonPushed.getText().equals("Правая")) {
            element.setName(sideButtonPushed.getText() + " " + elementButtonPushed.getText());
        } else {
            if (sideButtonPushed.getText().equals("Остекление")) {
                element.setName(sideButtonPushed.getText() + " " + elementButtonPushed.getText());
                element.setGlass(1);
            } else {
                element.setName(elementButtonPushed.getText());
            }
        }
        element.setPaintSide(elementPaintSide);
        if (remontButtonPushed != null) {
            String rem;
            rem = new StringBuilder(elementRemont).insert(elementRemont.length() - 1, ".").toString();
            element.setRemont(Double.parseDouble(rem));
        }
        if (elementButtonPushed.getText().equals("Пер.Дверь") || elementButtonPushed.getText().equals("Зад.Дверь")) {
            if (elementButtonPushed.getText().contains("Пер.Дверь")) {
                if (zercaloButtonPushed != null) {
                    element.setZerkalo(1);
                }
            }
            if (ruchkaButtonPushed != null) {
                element.setRuchka(1);
            }
            if (moldingButtonPushed != null) {
                element.setMolding(1);
            }
        }
        return element;
    }

    private JButton removeActionListener(JButton button) {
        /* Очень глупое решение, но другого не нашел, суть его в том чтобы постоянно обновлять актионЛистенер
           Если его нет то, так как при каждом нажатии создаётся новая панель добавления и при
           нажатии "дабавить" актионЛистенер выполняется столько раз сколько была раз создана панель.*/
        ActionListener[] al = button.getActionListeners();
        if (al.length > 0) {
            but.getAddButton().removeActionListener(al[0]);
        }
        return button;
    }
}