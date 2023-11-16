package ru.yandex.practicum.calcRemAuto.panelsAndButtons.panels;

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
    JPanel panelGlass = new JPanel();
    JTextArea elementListTextViewing = new JTextArea(30, 19);
    Double elementArmatureSide = 0.0;
    Double elementKuzDetReplaceSide = 0.0;
    String elementRemont;
    Double elementPaintSide = 0.0;
    Integer haveGlass = 0;
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
    JButton glassButtonPushed;

    public JPanel startAddPanel(JPanel panel, Client client, JFrame frame) {
        elementListTextViewing.setLineWrap(true);
        panel.removeAll();
        JPanel panelStartAdd = new JPanel();
        JPanel panelXYZ = new JPanel();
        JPanel panelSaveAndBack = new JPanel();
        JPanel panelXYZSaveAndBack = new JPanel();
        JPanel panelAdd = new JPanel();
        JPanel panelForTextArea = new JPanel();
        JPanel panelForTextAreaXY = new JPanel(new BorderLayout());

        panelXYZ.setLayout(new BorderLayout());
        panelXYZSaveAndBack.setLayout(new BorderLayout());
        panel.setLayout(new BorderLayout());
        panelAdd.setLayout(new BorderLayout());
        panelSaveAndBack.setLayout(new GridBagLayout());
        panelStartAdd.setLayout(new GridBagLayout());
        panelForTextArea.setLayout(new GridBagLayout());
        // Добавляем начальные кнопки слева вверху
        panelStartAdd.add(new JLabel("Выбери сторону."), new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 10));
        panelStartAdd.add(takeColor(removeActionListener(but.getButtonLeft()), 1), new GridBagConstraints(0, 1, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        panelStartAdd.add(takeColor(removeActionListener(but.getButtonCenter()), 1), new GridBagConstraints(0, 2, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        panelStartAdd.add(takeColor(removeActionListener(but.getButtonRight()), 1), new GridBagConstraints(0, 3, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        panelStartAdd.add(takeColor(removeActionListener(but.getButtonGlass()), 1), new GridBagConstraints(0, 4, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        // Добавляем кнопки снизу с права "назад" и "сохранить"
        panelSaveAndBack.add(but.getButtonBack(), new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 10, 2), 40, 0));
        panelSaveAndBack.add(but.getButtonSave(), new GridBagConstraints(1, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 10, 10), 1, 0));
        // Добавляем текстовое окно с добавленными элементами
        panelForTextArea.add(new JScrollPane(elementListTextViewing), new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(20, 20, 20, 10), 20, 10));
        // Добавляем текстовое окно в правую область
        panelForTextAreaXY.add(panelForTextArea, BorderLayout.NORTH);
        panel.add(panelForTextAreaXY, BorderLayout.EAST);
        // Добавляем панель с основными кнопками верхний левый угол
        panelXYZ.add(panelStartAdd, BorderLayout.NORTH);
        panel.add(panelXYZ, BorderLayout.WEST);
        // Добавляем панель нижнего правого угла с 2 кнопками
        panelXYZSaveAndBack.add(panelSaveAndBack, BorderLayout.EAST);
        panel.add(panelXYZSaveAndBack, BorderLayout.SOUTH);
        // Добавляем в основную панель запененную начальную панель
        panel.add(panelAdd, BorderLayout.CENTER);
        panelStartAdd.updateUI();
        // Назначаем действия кнопкам
        but.getButtonLeft().addActionListener(e -> {
            sideButtonPushed = changeColorPushedButton(sideButtonPushed, but.getButtonLeft(), 1);
            addElementLeftRightSide(clearAll(panelAdd));
        });
        but.getButtonRight().addActionListener(e -> {
            sideButtonPushed = changeColorPushedButton(sideButtonPushed, but.getButtonRight(), 1);
            addElementLeftRightSide(clearAll(panelAdd));
        });
        but.getButtonGlass().addActionListener(e -> {
            sideButtonPushed = changeColorPushedButton(sideButtonPushed, but.getButtonGlass(), 1);
            addElementGlass(clearAll(panelAdd));
        });
        but.getButtonCenter().addActionListener(e -> {
            sideButtonPushed = changeColorPushedButton(sideButtonPushed, but.getButtonCenter(), 1);
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
        elementLeftRightSidePanel.add(takeColor(removeActionListener(but.getButtonFrontWing()), 2), new GridBagConstraints(0, 1, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        elementLeftRightSidePanel.add(takeColor(removeActionListener(but.getButtonFrontDoor()), 2), new GridBagConstraints(0, 2, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        elementLeftRightSidePanel.add(takeColor(removeActionListener(but.getButtonBackDoor()), 2), new GridBagConstraints(0, 3, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        elementLeftRightSidePanel.add(takeColor(removeActionListener(but.getButtonBackWing()), 2), new GridBagConstraints(0, 4, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        elementLeftRightSidePanel.add(takeColor(removeActionListener(but.getButtonBalk()), 2), new GridBagConstraints(0, 5, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        elementLeftRightSidePanel.add(takeColor(removeActionListener(but.getButtonDoorStep()), 2), new GridBagConstraints(0, 6, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        elementLeftRightSidePanel.add(takeColor(removeActionListener(but.getButtonFrontDoorway()), 2), new GridBagConstraints(0, 7, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        elementLeftRightSidePanel.add(takeColor(removeActionListener(but.getButtonBackDoorway()), 2), new GridBagConstraints(0, 8, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));

        panelXYZ.add(elementLeftRightSidePanel, BorderLayout.NORTH);
        panelXYZ2.add(panelXYZ, BorderLayout.WEST);
        panelAdd.add(panelXYZ2, BorderLayout.WEST);
        panelAdd.updateUI();

        but.getButtonFrontWing().addActionListener(e -> {
            clearPushedButtonAfterElementAdd();
            elementButtonPushed = changeColorPushedButton(elementButtonPushed, but.getButtonFrontWing(), 2);
            addElementWorks(elementLeftRightSidePanel, clearCenter(panelAdd));
        });
        but.getButtonFrontDoor().addActionListener(e -> {
            clearPushedButtonAfterElementAdd();
            elementButtonPushed = changeColorPushedButton(elementButtonPushed, but.getButtonFrontDoor(), 2);
            addElementWorks(elementLeftRightSidePanel, clearCenter(panelAdd));
        });
        but.getButtonBackDoor().addActionListener(e -> {
            clearPushedButtonAfterElementAdd();
            elementButtonPushed = changeColorPushedButton(elementButtonPushed, but.getButtonBackDoor(), 2);
            addElementWorks(elementLeftRightSidePanel, clearCenter(panelAdd));
        });
        but.getButtonBackWing().addActionListener(e -> {
            clearPushedButtonAfterElementAdd();
            elementButtonPushed = changeColorPushedButton(elementButtonPushed, but.getButtonBackWing(), 2);
            addElementWorks(elementLeftRightSidePanel, clearCenter(panelAdd));
        });
        but.getButtonDoorStep().addActionListener(e -> {
            clearPushedButtonAfterElementAdd();
            elementButtonPushed = changeColorPushedButton(elementButtonPushed, but.getButtonDoorStep(), 2);
            addElementWorks(elementLeftRightSidePanel, clearCenter(panelAdd));
        });
        but.getButtonBalk().addActionListener(e -> {
            clearPushedButtonAfterElementAdd();
            elementButtonPushed = changeColorPushedButton(elementButtonPushed, but.getButtonBalk(), 2);
            addElementWorks(elementLeftRightSidePanel, clearCenter(panelAdd));
        });
        but.getButtonFrontDoorway().addActionListener(e -> {
            clearPushedButtonAfterElementAdd();
            elementButtonPushed = changeColorPushedButton(elementButtonPushed, but.getButtonFrontDoorway(), 2);
            addElementWorks(elementLeftRightSidePanel, clearCenter(panelAdd));
        });
        but.getButtonBackDoorway().addActionListener(e -> {
            clearPushedButtonAfterElementAdd();
            elementButtonPushed = changeColorPushedButton(elementButtonPushed, but.getButtonBackDoorway(), 2);
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

    public void addElementWorks(JPanel elementLeftRightSidePanel, JPanel panelAdd) {
        JTextField remont = but.getRemontJText();
        remont.removeAll();
        elementRemont = null;
        elementKuzDetReplaceSide = 0.0;
        elementArmatureSide = 0.0;
        elementPaintSide = 0.0;
        haveGlass = 0;
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
        // Удаляем лишние строчки из работ так как не везде есть молдинги, зеркала, ручки и стекла.
        if (elementLeftRightSidePanel.getComponents().length > 9) {
            for (int i = elementLeftRightSidePanel.getComponents().length - 1; i >= 9; i--) {
                elementLeftRightSidePanel.remove(i);
            }
            elementLeftRightSidePanel.updateUI();
        }
        elementLeftRightSidePanel.add(new JLabel("Выбери работы."), new GridBagConstraints(1, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        if (!elementButtonPushed.getText().equals("Пер.Проём")) {
            if (!elementButtonPushed.getText().equals("Зад.Проём")) {
                // Замена проемов отдельно не существует (кузовная деталь) и входит либо в порог, либо в брус.
                elementLeftRightSidePanel.add(takeColor(removeActionListener(but.getReplaceButton()), 3), new GridBagConstraints(1, 1, 1, 1, 1, 1,
                        GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                        new Insets(2, 2, 2, 2), 0, 0));
                // Станешь умнее УБЕРИ ЭТО ЗАДВОЕНИЕ!!!!!!!!
            } else {
                elementLeftRightSidePanel.add(new JLabel("         Замена"), new GridBagConstraints(1, 1, 1, 1, 1, 1,
                        GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
                        new Insets(2, 2, 2, 2), 0, 0));
            }
            // Станешь умнее УБЕРИ ЭТО ЗАДВОЕНИЕ!!!!!!!!
        } else {
            elementLeftRightSidePanel.add(new JLabel("         Замена"), new GridBagConstraints(1, 1, 1, 1, 1, 1,
                    GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
                    new Insets(2, 2, 2, 2), 0, 0));
        }
        elementLeftRightSidePanel.add(takeColor(removeActionListener(but.getDisassembleButton()), 3), new GridBagConstraints(2, 1, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));

        elementLeftRightSidePanel.add(takeColor(removeActionListener(but.getPaint1xButton()), 3), new GridBagConstraints(1, 2, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));

        elementLeftRightSidePanel.add(takeColor(removeActionListener(but.getPaint2xButton()), 3), new GridBagConstraints(2, 2, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));

        elementLeftRightSidePanel.add(takeColor(removeActionListener(but.getRepairButton()), 3), new GridBagConstraints(1, 3, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));

        elementLeftRightSidePanel.add(remont, new GridBagConstraints(2, 3, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        if (elementButtonPushed.getText().contains("Пер.Дверь") || elementButtonPushed.getText().contains("Зад.Дверь")) {
            if (elementButtonPushed.getText().contains("Пер.Дверь")) {
                elementLeftRightSidePanel.add(takeColor(removeActionListener(but.getZerkaloButton()), 3), new GridBagConstraints(1, 6, 1, 1, 1, 1,
                        GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                        new Insets(2, 2, 2, 2), 0, 0));
            }
            elementLeftRightSidePanel.add(takeColor(removeActionListener(but.getRuchkaButton()), 3), new GridBagConstraints(1, 4, 1, 1, 1, 1,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(2, 2, 2, 5), 0, 0));

            elementLeftRightSidePanel.add(takeColor(removeActionListener(but.getMoldingButton()), 3), new GridBagConstraints(1, 5, 1, 1, 1, 1,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(2, 2, 2, 2), 0, 0));
        }
        if (elementButtonPushed.getText().contains("Брус")) {
            elementLeftRightSidePanel.add(new JLabel("    Остекление"), new GridBagConstraints(1, 4, 1, 1, 1, 1,
                    GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
                    new Insets(2, 2, 2, 5), 0, 0));
            elementLeftRightSidePanel.add(takeColor(removeActionListener(but.getButtonWindshield()), 3), new GridBagConstraints(1, 5, 1, 1, 1, 1,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(2, 2, 2, 5), 0, 0));
        }
        if (elementButtonPushed.getText().contains("Зад.Крыло")) {
            elementLeftRightSidePanel.add(new JLabel("    Остекление"), new GridBagConstraints(1, 4, 1, 1, 1, 1,
                    GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
                    new Insets(2, 2, 2, 5), 0, 0));
            elementLeftRightSidePanel.add(takeColor(removeActionListener(but.getButtonRearWindow()), 3), new GridBagConstraints(1, 5, 1, 1, 1, 1,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(2, 2, 2, 5), 0, 0));
        }
        remont.setText(elementRemont);
        elementLeftRightSidePanel.updateUI();

        but.getReplaceButton().addActionListener(e -> { // замена
            zamenaOrRsButtonPushed = but.getReplaceButton();
            if (elementButtonPushed.getText().equals("Пер.Дверь") || elementButtonPushed.getText().equals("Зад.Дверь")) {
                // При замене дверей идет окраска с 2-х сторон и надбавка к арматурщику.
                elementPaintSide = 4.5;
                elementArmatureSide = 2.6;
                paint1xOr2xButtonPushed = but.getPaint2xButton();
                but.getPaint2xButton().setBorder(BorderFactory.createLineBorder(Color.red, 1));
                but.getPaint1xButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
            } else if (elementButtonPushed.getText().equals("Брус") || elementButtonPushed.getText().equals("Порог") || elementButtonPushed.getText().equals("Зад.Крыло")) {
                // При замене приварной детали идет окраска с 1 стороны + 30% от кузовщика и надбавка к арматуре.
                if (elementButtonPushed.getText().equals("Брус") || elementButtonPushed.getText().equals("Порог")) {
                    elementPaintSide = 2 + 2.4;
                } else {
                    elementPaintSide = 3 + 2.4;
                }
                elementArmatureSide = 3.4;
                elementKuzDetReplaceSide = 8.0;
                paint1xOr2xButtonPushed = but.getPaint1xButton();
                but.getPaint1xButton().setBorder(BorderFactory.createLineBorder(Color.red, 1));
                but.getPaint2xButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
            } else {
                // Во всех остальных случаях окраска с 1 стороны и обычный норматив арматурщику.
                elementPaintSide = 3.0;
                elementArmatureSide = 2.0;
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
            elementPaintSide = 3.0;
            elementArmatureSide = 2.0;
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
            elementPaintSide = 3.0;
            elementArmatureSide = 2.0;
            but.getPaint2xButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
            but.getPaint1xButton().setBorder(BorderFactory.createLineBorder(Color.red, 1));
            addElementToList(clearCenter(panelAdd));
            panelAdd.updateUI();
        });
        but.getPaint2xButton().addActionListener(e -> { // окраска 2х
            paint1xOr2xButtonPushed = but.getPaint2xButton();
            if (zamenaOrRsButtonPushed == null) {
                zamenaOrRsButtonPushed = but.getDisassembleButton(); // Р/С
                but.getReplaceButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
                but.getDisassembleButton().setBorder(BorderFactory.createLineBorder(Color.red, 1));
            }
            elementPaintSide = 4.5;
            elementArmatureSide = 2.0;
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
            // Если окраска только ручки выставляем 0.6 арматура
            if (paint1xOr2xButtonPushed == null) {
                if (elementArmatureSide > 0) {
                    elementArmatureSide = elementArmatureSide + 0.6;
                } else {
                    elementArmatureSide = 0.6;
                }
                addElementToList(clearCenter(panelAdd));
                panelAdd.updateUI();
            }
            ruchkaButtonPushed = removeActionListener(but.getRuchkaButton());
            but.getRuchkaButton().setBorder(BorderFactory.createLineBorder(Color.red, 1));
        });
        but.getMoldingButton().addActionListener(e -> {
            // Если окраска только молдинга выставляем 0.6 арматура
            if (paint1xOr2xButtonPushed == null) {
                if (elementArmatureSide > 0) {
                    elementArmatureSide = elementArmatureSide + 0.6;
                } else {
                    elementArmatureSide = 0.6;
                }
                addElementToList(clearCenter(panelAdd));
                panelAdd.updateUI();
            }
            moldingButtonPushed = removeActionListener(but.getMoldingButton());
            but.getMoldingButton().setBorder(BorderFactory.createLineBorder(Color.red, 1));
        });
        but.getZerkaloButton().addActionListener(e -> {
            // Если окраска только зеркала выставляем 0.6 арматура
            if (paint1xOr2xButtonPushed == null) {
                if (elementArmatureSide > 0) {
                    elementArmatureSide = elementArmatureSide + 0.6;
                } else {
                    elementArmatureSide = 0.6;
                }
                addElementToList(clearCenter(panelAdd));
                panelAdd.updateUI();
            }
            zercaloButtonPushed = removeActionListener(but.getZerkaloButton());
            but.getZerkaloButton().setBorder(BorderFactory.createLineBorder(Color.red, 1));
        });
        but.getButtonFrontWing().addActionListener(e -> {
            haveGlass++;
            glassButtonPushed = removeActionListener(but.getButtonFrontWing());
            but.getButtonFrontWing().setBorder(BorderFactory.createLineBorder(Color.red, 1));
        });
        but.getButtonRearWindow().addActionListener(e -> {
            haveGlass++;
            glassButtonPushed = removeActionListener(but.getButtonRearWindow());
            but.getButtonRearWindow().setBorder(BorderFactory.createLineBorder(Color.red, 1));
        });
    }

    private void addElementGlass(JPanel panelAdd) {
        JPanel panelXY = new JPanel();
        JPanel panelXY2 = new JPanel();

        panelGlass.setLayout(new GridBagLayout());
        panelXY.setLayout(new BorderLayout());
        panelXY2.setLayout(new BorderLayout());

        panelGlass.add(new JLabel("Выбери стекло."), new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 10));
        panelGlass.add(takeColor(removeActionListener(but.getButtonWindshield()), 2), new GridBagConstraints(0, 1, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        panelGlass.add(takeColor(removeActionListener(but.getButtonRearWindow()), 2), new GridBagConstraints(0, 2, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));

        panelXY.add(panelGlass, BorderLayout.NORTH);
        panelXY2.add(panelXY, BorderLayout.WEST);
        panelAdd.add(panelXY2, BorderLayout.WEST);
        panelAdd.updateUI();

        but.getButtonWindshield().addActionListener(e -> {
            if (sideButtonPushed.getText().equals("Остекление")) {
                elementButtonPushed = changeColorPushedButton(elementButtonPushed, removeActionListener(but.getButtonWindshield()), 2);
                addElementToList(clearCenter(panelAdd));
            }
        });
        but.getButtonRearWindow().addActionListener(e -> {
            if (sideButtonPushed.getText().equals("Остекление")) {
                elementButtonPushed = changeColorPushedButton(elementButtonPushed, removeActionListener(but.getButtonRearWindow()), 2);
                addElementToList(clearCenter(panelAdd));
            }
        });
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
        panelAddElementToList.add(takeColor(removeActionListener(but.getAddButton()), 1), new GridBagConstraints(0, 2, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 2, 2, 2), 30, 0));

        panelXY.add(panelAddElementToList, BorderLayout.NORTH);
        panelXY2.add(panelXY, BorderLayout.WEST);
        panel.add(panelXY2, BorderLayout.CENTER);
        panel.updateUI();

        but.getAddButton().addActionListener(o -> {
            Element element = createElement();
            //sideButtonPushed.setBorder(BorderFactory.createLineBorder(Color.green, 1));
            //elementButtonPushed.setBorder(BorderFactory.createLineBorder(Color.green, 1));
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
                // Находим первую кнопку сторона
                if (lineBorderColorMap.get(button.getText()) != null) {
                    button.setBorder(BorderFactory.createLineBorder(Color.green));
                }
                break;
            case 2:
                // Находим по первому ключу(стороне) второй ключ(элемент)
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
                                //Если есть кнопка ремонт, то делим ее на значение "ремонт" и "число"
                                if (check.contains("Ремонт")) {
                                    button.setBorder(BorderFactory.createLineBorder(Color.green));
                                    String[] split = check.split(" ");
                                    // Возвращаем значение "elementRemont" для того чтобы отобразить сколько у данного элемента это значение
                                    if (split.length > 1) {
                                        elementRemont = split[1];
                                    }
                                }
                            }
                        }
                        //listOfAddWorks = null;
                    }
                    //panel3 = null;
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + panelNumber);
        }
        return button;
    }

    private JButton changeColorPushedButton(JButton lastPushedButton, JButton pushedButton, int panelNumber) {
        //Сначала проверяем была ли старая кнопка нажата и ищем было ли у данной кнопки раньше "добавление" в элемент
        if (lastPushedButton != null) {
            // если было, у нее меняется рамка на зеленую если нет, то на серую.
            takeColor(lastPushedButton, panelNumber);
        }
        // Дальше меняем местами нажатую кнопку и выставляем у нее рамку на красную для понимания какая кнопка нажата
        lastPushedButton = pushedButton;
        lastPushedButton.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
        return lastPushedButton;
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
        if (glassButtonPushed != null) {
            color.add(glassButtonPushed.getText());
            glassButtonPushed.setBorder(BorderFactory.createLineBorder(Color.green, 1));
        }
        return color;
    }

    private JPanel clearAll(JPanel panel) {
        BorderLayout layout = (BorderLayout) panel.getLayout();
        clearPushedButtonAfterElementAdd();
        elementLeftRightSidePanel.removeAll();
        elementCenterSide.removeAll();
        panelGlass.removeAll();
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

    private void clearPushedButtonAfterElementAdd() {
        zamenaOrRsButtonPushed = null;
        paint1xOr2xButtonPushed = null;
        remontButtonPushed = null;
        ruchkaButtonPushed = null;
        moldingButtonPushed = null;
        zercaloButtonPushed = null;
        glassButtonPushed = null;
        haveGlass = 0;
    }

    private Element createElement() {
        Element element = new Element();
        // Первое оформляем имя (левая правая сторона)
        if (sideButtonPushed.getText().equals("Левая") || sideButtonPushed.getText().equals("Правая")) {
            if (elementButtonPushed.getText().equals("Пер.Крыло") || elementButtonPushed.getText().equals("Зад.Крыло")) {
                element.setName(sideButtonPushed.getText().substring(0, sideButtonPushed.getText().length() - 2) + "ое " + elementButtonPushed.getText());
            } else if (elementButtonPushed.getText().equals("Пер.Дверь") || elementButtonPushed.getText().equals("Зад.Дверь")) {
                element.setName(sideButtonPushed.getText() + " " + elementButtonPushed.getText());
            } else {
                element.setName(sideButtonPushed.getText().substring(0, sideButtonPushed.getText().length() - 2) + "ый " + elementButtonPushed.getText());
            }
            // Если Элемент меняется, то к имени добавляем "замена".
            if(zamenaOrRsButtonPushed.getText().equals("Замена")){
                element.setName(element.getName() + " " + zamenaOrRsButtonPushed.getText());
            }
            // Второе оформление имени остекление
        } else if (sideButtonPushed.getText().equals("Остекление")) {
            element.setName(elementButtonPushed.getText() + " " + sideButtonPushed.getText());
            element.setGlass(2);
            // Третье оформление имени центральная часть авто (сторону упускаем, оставляем только элемент)
        } else {
            element.setName(elementButtonPushed.getText());
        }
        // От сих пор начинаем проставлять нормативы. Если это не стекло.
        if (!sideButtonPushed.getText().equals("Остекление")) {
            element.setPaintSide(elementPaintSide);
            element.setArmatureSide(elementArmatureSide);
            element.setKuzDetReplaceSide(elementKuzDetReplaceSide);
            // Если нажата кнопка ремонта, то добавляем, добавив "." перед последним символом
            // переведя в двоичное число
            if (remontButtonPushed != null) {
                String rem;
                rem = new StringBuilder(elementRemont).insert(elementRemont.length() - 1, ".").toString();
                element.setRemont(Double.parseDouble(rem));
            }
            // Если имя элемента входит в перечень, то добавляем ручки, молдинги и зеркала.
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
            // Если у элемента есть стекло, то проверяем надо ли снимать.
            if (haveGlass > 0) {
                // Умножаем 2 так как норма на стекло 2
                element.setGlass(haveGlass * 2);
                if (haveGlass.equals(2)) {
                    element.setNameGlass("Лобовое и Заднее" + " стекло c/у");
                } else {
                    element.setNameGlass(glassButtonPushed.getText() + " стекло c/у");
                }
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