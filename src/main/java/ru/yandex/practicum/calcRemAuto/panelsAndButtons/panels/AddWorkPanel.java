package ru.yandex.practicum.calcRemAuto.panelsAndButtons.panels;

import ru.yandex.practicum.calcRemAuto.model.Client;
import ru.yandex.practicum.calcRemAuto.model.Element;
import ru.yandex.practicum.calcRemAuto.panelsAndButtons.buttons.Buttons;
import ru.yandex.practicum.calcRemAuto.panelsAndButtons.frame.SaveDialog;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class AddWorkPanel {
    List<Element> elementList = new ArrayList<>(); // Список добавленных элементов
    Map<String, Map<String, List<String>>> lineBorderColorMap = new HashMap<>(); // Таблица с нажатыми кнопками добавленных элементов
    Buttons but = new Buttons();
    JPanel elementLeftRightSidePanel = new JPanel();
    JPanel elementCenterSide = new JPanel();
    JPanel panelGlass = new JPanel();
    JTextField remont = but.getRemontJText();
    JTextArea elementListTextViewing = new JTextArea(30, 19);
    Double elementPaintSide = 0.0;
    double elementArmatureSide = 0.0;
    Double elementKuzDetReplaceSide = 0.0;
    String elementRemont;
    Integer haveGlass = 0;
    JButton sideButtonPushed;
    JButton elementButtonPushed;
    JButton zamenaOrRsButtonPushed;
    JButton paint1xOr2xButtonPushed;
    JButton remontButtonPushed;
    JButton ruchkaButtonPushed;
    JButton moldingButtonPushed;
    JButton zercaloButtonPushed;
    JButton glassButtonPushed;
    JButton expanderButtonPushed;
    JButton overlayButtonPushed;

    public void startPanel(JPanel panel, Client client, Frame startFrame) {
        elementListTextViewing.setLineWrap(true);
        elementListTextViewing.setEditable(false);

        panel.removeAll();
        panel.setLayout(new BorderLayout());

        JPanel panelStartAdd = new JPanel(new GridBagLayout());
        JPanel panelXYZ = new JPanel(new BorderLayout());
        JPanel panelSaveAndBack = new JPanel(new GridBagLayout());
        JPanel panelXYZSaveAndBack = new JPanel(new BorderLayout());
        JPanel panelAdd = new JPanel(new BorderLayout());
        JPanel panelForTextArea = new JPanel(new GridBagLayout());
        JPanel panelForTextAreaXY = new JPanel(new BorderLayout());

        // Добавляем начальные кнопки слева вверху
        panelStartAdd.add(new JLabel("Выбери сторону."), new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 10));
        panelStartAdd.add(takeColorOfButtons(removeActionListener(but.getButtonLeft()), 1), new GridBagConstraints(0, 1, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        panelStartAdd.add(takeColorOfButtons(removeActionListener(but.getButtonCenter()), 1), new GridBagConstraints(0, 2, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        panelStartAdd.add(takeColorOfButtons(removeActionListener(but.getButtonRight()), 1), new GridBagConstraints(0, 3, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        panelStartAdd.add(takeColorOfButtons(removeActionListener(but.getButtonGlass()), 1), new GridBagConstraints(0, 4, 1, 1, 1, 1,
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
            leftRightSidePanel(clearAll(panelAdd));
        });
        but.getButtonRight().addActionListener(e -> {
            sideButtonPushed = changeColorPushedButton(sideButtonPushed, but.getButtonRight(), 1);
            leftRightSidePanel(clearAll(panelAdd));
        });
        but.getButtonGlass().addActionListener(e -> {
            sideButtonPushed = changeColorPushedButton(sideButtonPushed, but.getButtonGlass(), 1);
            glassSidePanel(clearAll(panelAdd));
        });
        but.getButtonCenter().addActionListener(e -> {
            sideButtonPushed = changeColorPushedButton(sideButtonPushed, but.getButtonCenter(), 1);
            centerSidePanel(clearAll(panelAdd));
        });

        but.getButtonBack().addActionListener(e -> {
            panel.removeAll();
            AddClientPanel addClientPanel = new AddClientPanel();
            addClientPanel.clientAdd(panel, client);
        }); // Кнопка назад
        but.getButtonSave().addActionListener(e -> {
            if (elementList.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Не добавлен ни один элемент\nСохранить нечего!", "Ошибка", JOptionPane.INFORMATION_MESSAGE);
            } else {
                SaveDialog saveDialog = new SaveDialog(startFrame, client, elementList, lineBorderColorMap);
                saveDialog.setVisible(true);
            }
        }); // Кнопка сохранить
    } // Начальная панель Стороны авто

    private void leftRightSidePanel(JPanel panelAdd) {
        JPanel panelXYZ = new JPanel();
        JPanel panelXYZ2 = new JPanel();

        elementLeftRightSidePanel.setLayout(new GridBagLayout());
        panelXYZ.setLayout(new BorderLayout());
        panelXYZ2.setLayout(new BorderLayout());

        elementLeftRightSidePanel.add(new JLabel("Выбери Элемент."), new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 10));
        elementLeftRightSidePanel.add(takeColorOfButtons(removeActionListener(but.getButtonFrontWing()), 2), new GridBagConstraints(0, 1, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        elementLeftRightSidePanel.add(takeColorOfButtons(removeActionListener(but.getButtonFrontDoor()), 2), new GridBagConstraints(0, 2, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        elementLeftRightSidePanel.add(takeColorOfButtons(removeActionListener(but.getButtonBackDoor()), 2), new GridBagConstraints(0, 3, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        elementLeftRightSidePanel.add(takeColorOfButtons(removeActionListener(but.getButtonBackWing()), 2), new GridBagConstraints(0, 4, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        elementLeftRightSidePanel.add(takeColorOfButtons(removeActionListener(but.getButtonBalk()), 2), new GridBagConstraints(0, 5, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        elementLeftRightSidePanel.add(takeColorOfButtons(removeActionListener(but.getButtonDoorStep()), 2), new GridBagConstraints(0, 6, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        elementLeftRightSidePanel.add(takeColorOfButtons(removeActionListener(but.getButtonFrontDoorway()), 2), new GridBagConstraints(0, 7, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        elementLeftRightSidePanel.add(takeColorOfButtons(removeActionListener(but.getButtonBackDoorway()), 2), new GridBagConstraints(0, 8, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));

        panelXYZ.add(elementLeftRightSidePanel, BorderLayout.NORTH);
        panelXYZ2.add(panelXYZ, BorderLayout.WEST);
        panelAdd.add(panelXYZ2, BorderLayout.WEST);
        panelAdd.updateUI();

        but.getButtonFrontWing().addActionListener(e -> {
            clearPushedButtonAfterElementAdd();
            elementButtonPushed = changeColorPushedButton(elementButtonPushed, but.getButtonFrontWing(), 2);
            WorksPanel(elementLeftRightSidePanel, clearCenter(panelAdd));
        });
        but.getButtonFrontDoor().addActionListener(e -> {
            clearPushedButtonAfterElementAdd();
            elementButtonPushed = changeColorPushedButton(elementButtonPushed, but.getButtonFrontDoor(), 2);
            WorksPanel(elementLeftRightSidePanel, clearCenter(panelAdd));
        });
        but.getButtonBackDoor().addActionListener(e -> {
            clearPushedButtonAfterElementAdd();
            elementButtonPushed = changeColorPushedButton(elementButtonPushed, but.getButtonBackDoor(), 2);
            WorksPanel(elementLeftRightSidePanel, clearCenter(panelAdd));
        });
        but.getButtonBackWing().addActionListener(e -> {
            clearPushedButtonAfterElementAdd();
            elementButtonPushed = changeColorPushedButton(elementButtonPushed, but.getButtonBackWing(), 2);
            WorksPanel(elementLeftRightSidePanel, clearCenter(panelAdd));
        });
        but.getButtonDoorStep().addActionListener(e -> {
            clearPushedButtonAfterElementAdd();
            elementButtonPushed = changeColorPushedButton(elementButtonPushed, but.getButtonDoorStep(), 2);
            WorksPanel(elementLeftRightSidePanel, clearCenter(panelAdd));
        });
        but.getButtonBalk().addActionListener(e -> {
            clearPushedButtonAfterElementAdd();
            elementButtonPushed = changeColorPushedButton(elementButtonPushed, but.getButtonBalk(), 2);
            WorksPanel(elementLeftRightSidePanel, clearCenter(panelAdd));
        });
        but.getButtonFrontDoorway().addActionListener(e -> {
            clearPushedButtonAfterElementAdd();
            elementButtonPushed = changeColorPushedButton(elementButtonPushed, but.getButtonFrontDoorway(), 2);
            WorksPanel(elementLeftRightSidePanel, clearCenter(panelAdd));
        });
        but.getButtonBackDoorway().addActionListener(e -> {
            clearPushedButtonAfterElementAdd();
            elementButtonPushed = changeColorPushedButton(elementButtonPushed, but.getButtonBackDoorway(), 2);
            WorksPanel(elementLeftRightSidePanel, clearCenter(panelAdd));
        });
    } // Панель с элементами Левая\Правая часть авто

    private void centerSidePanel(JPanel panelAdd) {
        JPanel panelXYZ = new JPanel(new BorderLayout());
        JPanel panelXYZ2 = new JPanel(new BorderLayout());

        elementCenterSide.setLayout(new GridBagLayout());

        elementCenterSide.add(new JLabel("Выбери Элемент."), new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 10));
        elementCenterSide.add(takeColorOfButtons(removeActionListener(but.getFrontBumperButton()), 2), new GridBagConstraints(0, 1, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        elementCenterSide.add(takeColorOfButtons(removeActionListener(but.getBonnetButton()), 2), new GridBagConstraints(0, 2, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        elementCenterSide.add(takeColorOfButtons(removeActionListener(but.getRoofButton()), 2), new GridBagConstraints(0, 3, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        elementCenterSide.add(takeColorOfButtons(removeActionListener(but.getTrunkLidButton()), 2), new GridBagConstraints(0, 4, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        elementCenterSide.add(takeColorOfButtons(removeActionListener(but.getRearBumperButton()), 2), new GridBagConstraints(0, 5, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        elementCenterSide.add(takeColorOfButtons(removeActionListener(but.getEngineCompartmentButton()), 2), new GridBagConstraints(0, 6, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        elementCenterSide.add(takeColorOfButtons(removeActionListener(but.getBackPanelButton()), 2), new GridBagConstraints(0, 7, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));

        panelXYZ.add(elementCenterSide, BorderLayout.NORTH);
        panelXYZ2.add(panelXYZ, BorderLayout.WEST);
        panelAdd.add(panelXYZ2, BorderLayout.WEST);
        panelAdd.updateUI();

        but.getFrontBumperButton().addActionListener(e -> {
            clearPushedButtonAfterElementAdd();
            elementButtonPushed = changeColorPushedButton(elementButtonPushed, but.getFrontBumperButton(), 2);
            WorksPanel(elementCenterSide, panelAdd);
        });
        but.getBonnetButton().addActionListener(e -> {
            clearPushedButtonAfterElementAdd();
            elementButtonPushed = changeColorPushedButton(elementButtonPushed, but.getBonnetButton(), 2);
            WorksPanel(elementCenterSide, panelAdd);
        });
        but.getRoofButton().addActionListener(e -> {
            clearPushedButtonAfterElementAdd();
            elementButtonPushed = changeColorPushedButton(elementButtonPushed, but.getRoofButton(), 2);
            WorksPanel(elementCenterSide, panelAdd);
        });
        but.getTrunkLidButton().addActionListener(e -> {
            clearPushedButtonAfterElementAdd();
            elementButtonPushed = changeColorPushedButton(elementButtonPushed, but.getTrunkLidButton(), 2);
            WorksPanel(elementCenterSide, panelAdd);
        });
        but.getRearBumperButton().addActionListener(e -> {
            clearPushedButtonAfterElementAdd();
            elementButtonPushed = changeColorPushedButton(elementButtonPushed, but.getRearBumperButton(), 2);
            WorksPanel(elementCenterSide, panelAdd);
        });
        but.getEngineCompartmentButton().addActionListener(e -> {
            clearPushedButtonAfterElementAdd();
            elementButtonPushed = changeColorPushedButton(elementButtonPushed, but.getEngineCompartmentButton(), 2);
            WorksPanel(elementCenterSide, panelAdd);
        });
        but.getBackPanelButton().addActionListener(e -> {
            clearPushedButtonAfterElementAdd();
            elementButtonPushed = changeColorPushedButton(elementButtonPushed, but.getBackPanelButton(), 2);
            WorksPanel(elementCenterSide, panelAdd);
        });
    } // Панель с элементами Центральная часть авто

    private void glassSidePanel(JPanel panelAdd) {
        JPanel panelXY = new JPanel();
        JPanel panelXY2 = new JPanel();

        panelGlass.setLayout(new GridBagLayout());
        panelXY.setLayout(new BorderLayout());
        panelXY2.setLayout(new BorderLayout());

        panelGlass.add(new JLabel("Выбери стекло."), new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 10));
        panelGlass.add(takeColorOfButtons(removeActionListener(but.getButtonWindshield()), 2), new GridBagConstraints(0, 1, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        panelGlass.add(takeColorOfButtons(removeActionListener(but.getButtonRearWindow()), 2), new GridBagConstraints(0, 2, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));

        panelXY.add(panelGlass, BorderLayout.NORTH);
        panelXY2.add(panelXY, BorderLayout.WEST);
        panelAdd.add(panelXY2, BorderLayout.WEST);
        panelAdd.updateUI();

        but.getButtonWindshield().addActionListener(e -> {
            if (sideButtonPushed.getText().equals("Остекление")) {
                if (elementButtonPushed == null || elementButtonPushed.getText().equals(but.getButtonRearWindow().getText())) {
                    elementButtonPushed = changeColorPushedButton(elementButtonPushed, but.getButtonWindshield(), 2);
                    addAndRemovePanel(clearCenter(panelAdd));
                } else {
                    elementButtonPushed = null;
                    takeColorOfButtons(but.getButtonWindshield(), 2);
                    clearCenter(panelAdd);
                }
            }
        }); // Лобовое стекло
        but.getButtonRearWindow().addActionListener(e -> {
            if (sideButtonPushed.getText().equals("Остекление")) {
                if (elementButtonPushed == null || elementButtonPushed.getText().equals(but.getButtonWindshield().getText())) {
                    elementButtonPushed = changeColorPushedButton(elementButtonPushed, but.getButtonRearWindow(), 2);
                    addAndRemovePanel(clearCenter(panelAdd));
                } else {
                    elementButtonPushed = null;
                    takeColorOfButtons(but.getButtonRearWindow(), 2);
                    clearCenter(panelAdd);
                }
            }
        }); // Заднее стекло
    } // Панель с остеклением

    public void WorksPanel(JPanel elementLeftRightSidePanel, JPanel panelAdd) {
        clearPushedButtonAfterElementAdd();
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
                elementLeftRightSidePanel.add(takeColorOfButtons(removeActionListener(but.getReplaceButton()), 3), new GridBagConstraints(1, 1, 1, 1, 1, 1,
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
        elementLeftRightSidePanel.add(takeColorOfButtons(removeActionListener(but.getDisassembleButton()), 3), new GridBagConstraints(2, 1, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));

        elementLeftRightSidePanel.add(takeColorOfButtons(removeActionListener(but.getPaint1xButton()), 3), new GridBagConstraints(1, 2, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));

        elementLeftRightSidePanel.add(takeColorOfButtons(removeActionListener(but.getPaint2xButton()), 3), new GridBagConstraints(2, 2, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));

        elementLeftRightSidePanel.add(takeColorOfButtons(removeActionListener(but.getRepairButton()), 3), new GridBagConstraints(1, 3, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));

        elementLeftRightSidePanel.add(remont, new GridBagConstraints(2, 3, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        if (elementButtonPushed.getText().contains("Пер.Дверь") || elementButtonPushed.getText().contains("Зад.Дверь") || elementButtonPushed.getText().contains("Крышка баг.")) {
            if (elementButtonPushed.getText().contains("Пер.Дверь")) {
                elementLeftRightSidePanel.add(takeColorOfButtons(removeActionListener(but.getZerkaloButton()), 3), new GridBagConstraints(1, 6, 1, 1, 1, 1,
                        GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                        new Insets(2, 2, 2, 2), 0, 0));
            }
            elementLeftRightSidePanel.add(takeColorOfButtons(removeActionListener(but.getRuchkaButton()), 3), new GridBagConstraints(1, 4, 1, 1, 1, 1,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(2, 2, 2, 5), 0, 0));

            elementLeftRightSidePanel.add(takeColorOfButtons(removeActionListener(but.getMoldingButton()), 3), new GridBagConstraints(1, 5, 1, 1, 1, 1,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(2, 2, 2, 2), 0, 0));
        }
        if (elementButtonPushed.getText().contains("Порог") || elementButtonPushed.getText().contains("Пер.Бампер") || elementButtonPushed.getText().contains("Зад.Бампер")) {
            elementLeftRightSidePanel.add(takeColorOfButtons(removeActionListener(but.getOverlayButton()), 3), new GridBagConstraints(1, 4, 1, 1, 1, 1,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(2, 2, 2, 2), 0, 0));
        }
        if (elementButtonPushed.getText().contains("Брус") || elementButtonPushed.getText().contains("Крыша")) {
            elementLeftRightSidePanel.add(new JLabel("    Остекление"), new GridBagConstraints(1, 4, 1, 1, 1, 1,
                    GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
                    new Insets(2, 5, 2, 0), 0, 0));
            elementLeftRightSidePanel.add(takeColorOfButtons(removeActionListener(but.getButtonWindshield()), 3), new GridBagConstraints(1, 5, 1, 1, 1, 1,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(2, 2, 2, 0), 0, 0));
        }
        if (elementButtonPushed.getText().contains("Зад.Крыло") || elementButtonPushed.getText().contains("Пер.Крыло") || elementButtonPushed.getText().contains("Крыша") || elementButtonPushed.getText().contains("Крышка баг.")) {
            if (elementButtonPushed.getText().contains("Зад.Крыло") || elementButtonPushed.getText().contains("Пер.Крыло")) {
                elementLeftRightSidePanel.add(takeColorOfButtons(removeActionListener(but.getExpanderButton()), 3), new GridBagConstraints(1, 4, 1, 1, 1, 1,
                        GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                        new Insets(2, 2, 2, 0), 0, 0));
            }
            if (!elementButtonPushed.getText().contains("Пер.Крыло")) {
                int gridY1 = 5;
                int gridY2 = 6;
                if (elementButtonPushed.getText().contains("Крышка баг.")) {
                    gridY1 = 6;
                    gridY2 = 7;
                }
                elementLeftRightSidePanel.add(new JLabel("    Остекление"), new GridBagConstraints(1, gridY1, 1, 1, 1, 1,
                        GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
                        new Insets(2, 5, 2, 0), 0, 0));
                elementLeftRightSidePanel.add(takeColorOfButtons(removeActionListener(but.getButtonRearWindow()), 3), new GridBagConstraints(1, gridY2, 1, 1, 1, 1,
                        GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                        new Insets(2, 2, 2, 0), 0, 0));
            }
        }
        remont.setText(elementRemont);

        but.getReplaceButton().addActionListener(e -> { // замена
            // Обозначаем нажатую кнопку и выставляем цвет рамки
            zamenaOrRsButtonPushed = but.getReplaceButton();
            but.getDisassembleButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
            but.getReplaceButton().setBorder(BorderFactory.createLineBorder(Color.red, 1));

            if (elementButtonPushed.getText().equals("Пер.Дверь") || elementButtonPushed.getText().equals("Зад.Дверь")) {
                // При замене дверей идет окраска с 2-х сторон и надбавка к арматурщику.
                but.getPaint2xButton().doClick();
                elementArmatureSide = 2.6;
            } else if (elementButtonPushed.getText().equals("Брус")
                    || elementButtonPushed.getText().equals("Порог")
                    || elementButtonPushed.getText().equals("Зад.Крыло")
                    || elementButtonPushed.getText().equals("Моторный отсек")
                    || elementButtonPushed.getText().equals("Задняя панель")) {
                // При замене приварной детали идет окраска с 1 стороны +30% от кузовщика и надбавка к арматуре.
                but.getPaint1xButton().doClick();
                elementArmatureSide = 3.4;
                elementKuzDetReplaceSide = 8.0;
            } else if (elementButtonPushed.getText().equals("Крыша")) {
                // При замене приварной крыше идет окраска с 1 стороны +30% от кузовщика и надбавка к арматуре.
                but.getPaint1xButton().doClick();
                elementArmatureSide = 4;
                elementKuzDetReplaceSide = 12.0;
            } else {
                // Во всех остальных случаях окраска с 1 стороны и обычный норматив арматурщику.
                but.getPaint1xButton().doClick();
                elementArmatureSide = 2.0;
            }
            addAndRemovePanel(clearCenter(panelAdd));
            panelAdd.updateUI();
        }); // замена
        but.getDisassembleButton().addActionListener(e -> { // Р\С
            zamenaOrRsButtonPushed = but.getDisassembleButton();
            but.getReplaceButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
            but.getDisassembleButton().setBorder(BorderFactory.createLineBorder(Color.red, 1));
            elementKuzDetReplaceSide = 0.0;
            but.getPaint1xButton().doClick();
            elementArmatureSide = 2.0;
            addAndRemovePanel(clearCenter(panelAdd));
            panelAdd.updateUI();
        }); // р\с
        but.getPaint1xButton().addActionListener(e -> { // окраска 1х
            // Обозначаем нажатую кнопку и выставляем цвет рамки
            paint1xOr2xButtonPushed = but.getPaint1xButton();
            but.getPaint2xButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
            but.getPaint1xButton().setBorder(BorderFactory.createLineBorder(Color.red, 1));
            // Если кнопка "замены" или "Р/С" не нажата, то мы ее прожимаем для выставления нормативов арматуры.
            if (zamenaOrRsButtonPushed == null) {
                but.getDisassembleButton().doClick();
            }
            if (elementButtonPushed.getText().equals("Брус") || elementButtonPushed.getText().equals("Порог") || elementButtonPushed.getText().equals("Зад.Крыло")) {
                // При замене приварной детали идет окраска с 1 стороны + 30% от кузовщика.
                if (elementButtonPushed.getText().equals("Брус") || elementButtonPushed.getText().equals("Порог")) {
                    // При замене этих элементах норматив окраски 2н\ч + 30% от кузовщика
                    if (zamenaOrRsButtonPushed.equals(but.getReplaceButton())) {
                        elementPaintSide = 2 + 2.4;
                    } else {
                        // При окраске без замены
                        elementPaintSide = 2.0;
                    }
                } else {
                    // А при замене крыла
                    if (zamenaOrRsButtonPushed.equals(but.getReplaceButton())) {
                        elementPaintSide = 3 + 2.4;
                    } else {
                        // При окраске без замены крыла
                        elementPaintSide = 3.0;
                    }
                }
            } else if (elementButtonPushed.getText().equals("Крыша")) {
                if (zamenaOrRsButtonPushed.equals(but.getReplaceButton())) {
                    // При замене крыши норматив окраски 6н\ч + 1н\ч от кузовщика за герметизацию швов
                    elementPaintSide = 6.0 + 1;
                } else {
                    // При окраске без замены
                    elementPaintSide = 6.0;
                }
            } else if (elementButtonPushed.getText().equals("Капот")
                    || elementButtonPushed.getText().equals("Крышка баг.")) {
                // если окраска с 1х капота или крышки багажника, то норматив 4.5.
                elementPaintSide = 4.5;
            } else {
                // Во всех остальных элементах окраска просто 3н\ч
                elementPaintSide = 3.0;
            }
            addAndRemovePanel(clearCenter(panelAdd));
            panelAdd.updateUI();
        }); // окраска 1х
        but.getPaint2xButton().addActionListener(e -> { // окраска 2х
            // Обозначаем нажатую кнопку и выставляем цвет рамки
            paint1xOr2xButtonPushed = but.getPaint2xButton();
            but.getPaint1xButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
            but.getPaint2xButton().setBorder(BorderFactory.createLineBorder(Color.red, 1));
            // Если кнопка "замены" или "Р/С" не нажата, то мы ее прожимаем для выставления нормативов арматуры.
            if (zamenaOrRsButtonPushed == null) {
                but.getDisassembleButton().doClick(); // Р/С
            }
            if (elementButtonPushed.getText().equals("Крыша")) {
                // если окраска с 2х крыши (хотя это и крайне невозможно), то норматив 6х1.5= 9.
                elementPaintSide = 9.0;
            } else if (elementButtonPushed.getText().equals("Брус")
                    || elementButtonPushed.getText().equals("Порог")) {
                // если окраска с 2х бруса или порога (хотя это и крайне невозможно), то норматив 2х1.5= 3.
                elementPaintSide = 3.0;
            } else if (elementButtonPushed.getText().equals("Капот")
                    || elementButtonPushed.getText().equals("Крышка баг.")) {
                // если окраска с 2х капота или крышки багажника, то норматив 6.
                elementPaintSide = 6.0;
            } else {
                // При всех остальных элементах норматив 4.5
                elementPaintSide = 4.5;
            }
            addAndRemovePanel(clearCenter(panelAdd));
            panelAdd.updateUI();
        }); // окраска 2х
        but.getRepairButton().addActionListener(e -> { // ремонт
            // проверяем добелена ли окраска и разборка.
            if (remontButtonPushed == null) {
                if (zamenaOrRsButtonPushed != null && paint1xOr2xButtonPushed != null) {
                    if (remont.getBackground() != Color.pink && remont.getText().length() > 0) {
                        // добавляем перед последним символом точку переведя число в двоичное.
                        remontButtonPushed = but.getRepairButton();
                        elementRemont = remont.getText();
                        remontButtonPushed.setBorder(BorderFactory.createLineBorder(Color.red, 1));
                        addAndRemovePanel(clearCenter(panelAdd));
                        panelAdd.updateUI();
                    }
                }
            } else {
                // Если ремонт уже добавлен, а мы хотим ее убрать необходимо повторно нажать на кнопку
                LineBorder color = (LineBorder) remontButtonPushed.getBorder();
                // Если кнопка уже была нажата и элемент был добавлен (то есть имеет цвет рамки зеленый) мы убираем нормативы
                if (color.getLineColor() == (Color.green)) {
                    elementRemont = null;
                    // Устанавливаем цвет кнопки на серый тем самым убрав ее из элемента
                    but.getRepairButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
                    remontButtonPushed = null;
                    addAndRemovePanel(clearCenter(panelAdd));
                    panelAdd.updateUI();
                } else {
                    // Если кнопка была нажата, но элемент не был добавлен (то есть имеет цвет рамки красный) мы убираем нормативы
                    elementRemont = null;
                    // Устанавливаем цвет кнопки на серый тем самым убрав ее из элемента
                    but.getRepairButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
                    remontButtonPushed = null;
                    addAndRemovePanel(clearCenter(panelAdd));
                    panelAdd.updateUI();
                }
            }
        });  // ремонт
        but.getRuchkaButton().addActionListener(e -> {       // ручка
            // Если окраска только ручки выставляем 0.6 арматура
            if (ruchkaButtonPushed == null) {
                if (paint1xOr2xButtonPushed == null) {
                    if (elementArmatureSide > 0.1) {
                        elementArmatureSide = elementArmatureSide + 0.6;
                    } else {
                        elementArmatureSide = 0.6;
                    }
                }
                addAndRemovePanel(clearCenter(panelAdd));
                panelAdd.updateUI();
                ruchkaButtonPushed = but.getRuchkaButton();
                but.getRuchkaButton().setBorder(BorderFactory.createLineBorder(Color.red, 1));
            } else {
                // Если ручка уже добавлена, а мы хотим ее убрать необходимо повторно нажать на кнопку
                LineBorder color = (LineBorder) ruchkaButtonPushed.getBorder();
                // Если кнопка уже была нажата и элемент был добавлен (то есть имеет цвет рамки зеленый) мы убираем нормативы
                if (color.getLineColor() == (Color.green)) {
                    if (paint1xOr2xButtonPushed == null) {
                        if (elementArmatureSide >= 0.6) {
                            elementArmatureSide = elementArmatureSide - 0.6;
                        } else {
                            elementArmatureSide = 0.0;
                        }
                    }
                    // Устанавливаем цвет кнопки на серый тем самым убрав ее из элемента
                    but.getRuchkaButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
                    ruchkaButtonPushed = null;
                    addAndRemovePanel(clearCenter(panelAdd));
                    panelAdd.updateUI();
                } else {
                    // Если кнопка была нажата, но элемент не был добавлен (то есть имеет цвет рамки красный) мы убираем нормативы
                    if (paint1xOr2xButtonPushed == null) {
                        if (elementArmatureSide >= 0.6) {
                            elementArmatureSide = elementArmatureSide - 0.6;
                        } else {
                            elementArmatureSide = 0.0;
                        }
                    }
                    // Устанавливаем цвет кнопки на серый тем самым убрав ее из элемента
                    addAndRemovePanel(clearCenter(panelAdd));
                    panelAdd.updateUI();
                    but.getRuchkaButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
                    ruchkaButtonPushed = null;
                }
            }
        });  // ручка
        but.getMoldingButton().addActionListener(e -> {
            // Если окраска только молдинга выставляем 0.6 арматура
            if (moldingButtonPushed == null) {
                if (paint1xOr2xButtonPushed == null) {
                    if (elementArmatureSide > 0.1) {
                        elementArmatureSide = elementArmatureSide + 0.6;
                    } else {
                        elementArmatureSide = 0.6;
                    }
                }
                addAndRemovePanel(clearCenter(panelAdd));
                panelAdd.updateUI();
                moldingButtonPushed = but.getMoldingButton();
                but.getMoldingButton().setBorder(BorderFactory.createLineBorder(Color.red, 1));
            } else {
                // Если молдинг уже добавлен, а мы хотим его убрать необходимо повторно нажать на кнопку
                LineBorder color = (LineBorder) moldingButtonPushed.getBorder();
                // Если кнопка уже была нажата и элемент был добавлен (то есть имеет цвет рамки зеленый) мы убираем нормативы
                if (color.getLineColor() == (Color.green)) {
                    if (paint1xOr2xButtonPushed == null) {
                        if (elementArmatureSide >= 0.6) {
                            elementArmatureSide = elementArmatureSide - 0.6;
                        } else {
                            elementArmatureSide = 0.0;
                        }
                    }
                    // Устанавливаем цвет кнопки на серый тем самым убрав ее из элемента
                    but.getMoldingButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
                    moldingButtonPushed = null;
                    addAndRemovePanel(clearCenter(panelAdd));
                    panelAdd.updateUI();
                } else {
                    // Если кнопка была нажата, но элемент не был добавлен (то есть имеет цвет рамки красный) мы убираем нормативы
                    if (paint1xOr2xButtonPushed == null) {
                        if (elementArmatureSide >= 0.6) {
                            elementArmatureSide = elementArmatureSide - 0.6;
                        } else {
                            elementArmatureSide = 0.0;
                        }
                    }
                    // Устанавливаем цвет кнопки на серый тем самым убрав ее из элемента
                    addAndRemovePanel(clearCenter(panelAdd));
                    panelAdd.updateUI();
                    but.getMoldingButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
                    moldingButtonPushed = null;
                }
            }
        }); // молдинг
        but.getZerkaloButton().addActionListener(e -> {
            // Если окраска только зеркала выставляем 0.6 арматура
            if (zercaloButtonPushed == null) {
                if (paint1xOr2xButtonPushed == null) {
                    if (elementArmatureSide > 0.1) {
                        elementArmatureSide = elementArmatureSide + 0.6;
                    } else {
                        elementArmatureSide = 0.6;
                    }
                }
                addAndRemovePanel(clearCenter(panelAdd));
                panelAdd.updateUI();
                zercaloButtonPushed = but.getZerkaloButton();
                but.getZerkaloButton().setBorder(BorderFactory.createLineBorder(Color.red, 1));
            } else {
                // Если зеркало уже добавлено, а мы хотим его убрать необходимо повторно нажать на кнопку
                LineBorder color = (LineBorder) zercaloButtonPushed.getBorder();
                // Если кнопка уже была нажата и элемент был добавлен (то есть имеет цвет рамки зеленый) мы убираем нормативы
                if (color.getLineColor() == (Color.green)) {
                    if (paint1xOr2xButtonPushed == null) {
                        if (elementArmatureSide >= 0.6) {
                            elementArmatureSide = elementArmatureSide - 0.6;
                        } else {
                            elementArmatureSide = 0.0;
                        }
                    }
                    // Устанавливаем цвет кнопки на серый тем самым убрав ее из элемента
                    but.getZerkaloButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
                    zercaloButtonPushed = null;
                    addAndRemovePanel(clearCenter(panelAdd));
                    panelAdd.updateUI();
                } else {
                    // Если кнопка была нажата, но элемент не был добавлен (то есть имеет цвет рамки красный) мы убираем нормативы
                    if (paint1xOr2xButtonPushed == null) {
                        if (elementArmatureSide >= 0.6) {
                            elementArmatureSide = elementArmatureSide - 0.6;
                        } else {
                            elementArmatureSide = 0.0;
                        }
                    }
                    // Устанавливаем цвет кнопки на серый тем самым убрав ее из элемента
                    addAndRemovePanel(clearCenter(panelAdd));
                    panelAdd.updateUI();
                    but.getZerkaloButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
                    zercaloButtonPushed = null;
                }
            }
        }); // зеркало
        but.getButtonWindshield().addActionListener(e -> {
            if (!sideButtonPushed.getText().equals("Остекление")) {
                if (glassButtonPushed == null || (haveGlass == 1 && !glassButtonPushed.getText().equals(but.getButtonWindshield().getText()))) {
                    haveGlass++;
                    glassButtonPushed = but.getButtonWindshield();
                    but.getButtonWindshield().setBorder(BorderFactory.createLineBorder(Color.red, 1));
                } else {
                    haveGlass--;
                    if (haveGlass > 0) {
                        glassButtonPushed = but.getButtonRearWindow();
                    } else {
                        glassButtonPushed = null;
                    }
                    but.getButtonWindshield().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
                }
            }
        }); // лобовое стекло
        but.getButtonRearWindow().addActionListener(e -> {
            if (!sideButtonPushed.getText().equals("Остекление")) {
                if (glassButtonPushed == null || (haveGlass == 1 && !glassButtonPushed.getText().equals(but.getButtonRearWindow().getText()))) {
                    haveGlass++;
                    glassButtonPushed = but.getButtonRearWindow();
                    but.getButtonRearWindow().setBorder(BorderFactory.createLineBorder(Color.red, 1));
                } else {
                    haveGlass--;
                    if (haveGlass > 0) {
                        glassButtonPushed = but.getButtonWindshield();
                    } else {
                        glassButtonPushed = null;
                    }
                    but.getButtonRearWindow().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
                }
            }
        }); // заднее стекло
        but.getExpanderButton().addActionListener(e -> {
            // Если окраска только расширителя выставляем 0.6 арматура
            if (expanderButtonPushed == null) {
                if (paint1xOr2xButtonPushed == null) {
                    if (elementArmatureSide > 0.1) {
                        elementArmatureSide = elementArmatureSide + 0.6;
                    } else {
                        elementArmatureSide = 0.6;
                    }
                }
                addAndRemovePanel(clearCenter(panelAdd));
                panelAdd.updateUI();
                expanderButtonPushed = but.getExpanderButton();
                but.getExpanderButton().setBorder(BorderFactory.createLineBorder(Color.red, 1));
            } else {
                // Если расширитель уже добавлен, а мы хотим его убрать необходимо повторно нажать на кнопку
                LineBorder color = (LineBorder) expanderButtonPushed.getBorder();
                // Если кнопка уже была нажата и элемент был добавлен (то есть имеет цвет рамки зеленый) мы убираем нормативы
                if (color.getLineColor() == (Color.green)) {
                    if (paint1xOr2xButtonPushed == null) {
                        if (elementArmatureSide >= 0.6) {
                            elementArmatureSide = elementArmatureSide - 0.6;
                        } else {
                            elementArmatureSide = 0.0;
                        }
                    }
                    // Устанавливаем цвет кнопки на серый тем самым убрав ее из элемента
                    but.getExpanderButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
                    expanderButtonPushed = null;
                    addAndRemovePanel(clearCenter(panelAdd));
                    panelAdd.updateUI();
                } else {
                    // Если кнопка была нажата, но элемент не был добавлен (то есть имеет цвет рамки красный) мы убираем нормативы
                    if (paint1xOr2xButtonPushed == null) {
                        if (elementArmatureSide >= 0.6) {
                            elementArmatureSide = elementArmatureSide - 0.6;
                        } else {
                            elementArmatureSide = 0.0;
                        }
                    }
                    // Устанавливаем цвет кнопки на серый тем самым убрав ее из элемента
                    addAndRemovePanel(clearCenter(panelAdd));
                    panelAdd.updateUI();
                    but.getExpanderButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
                    expanderButtonPushed = null;
                }
            }
        });  // Расширитель
        but.getOverlayButton().addActionListener(e -> {
            // Если окраска только накладки порога выставляем 0.6 арматура
            if (overlayButtonPushed == null) {
                if (paint1xOr2xButtonPushed == null) {
                    if (elementArmatureSide > 0.1) {
                        elementArmatureSide = elementArmatureSide + 0.6;
                    } else {
                        elementArmatureSide = 0.6;
                    }
                }
                addAndRemovePanel(clearCenter(panelAdd));
                panelAdd.updateUI();
                overlayButtonPushed = but.getOverlayButton();
                but.getOverlayButton().setBorder(BorderFactory.createLineBorder(Color.red, 1));
            } else {
                // Если накладка порога уже добавлена, а мы хотим её убрать необходимо повторно нажать на кнопку
                LineBorder color = (LineBorder) overlayButtonPushed.getBorder();
                // Если кнопка уже была нажата и элемент был добавлен (то есть имеет цвет рамки зеленый) мы убираем нормативы
                if (color.getLineColor() == (Color.green)) {
                    if (paint1xOr2xButtonPushed == null) {
                        if (elementArmatureSide >= 0.6) {
                            elementArmatureSide = elementArmatureSide - 0.6;
                        } else {
                            elementArmatureSide = 0.0;
                        }
                    }
                    // Устанавливаем цвет кнопки на серый тем самым убрав ее из элемента
                    but.getOverlayButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
                    overlayButtonPushed = null;
                    addAndRemovePanel(clearCenter(panelAdd));
                    panelAdd.updateUI();
                } else {
                    // Если кнопка была нажата, но элемент не был добавлен (то есть имеет цвет рамки красный) мы убираем нормативы
                    if (paint1xOr2xButtonPushed == null) {
                        if (elementArmatureSide >= 0.6) {
                            elementArmatureSide = elementArmatureSide - 0.6;
                        } else {
                            elementArmatureSide = 0.0;
                        }
                    }
                    // Устанавливаем цвет кнопки на серый тем самым убрав ее из элемента
                    addAndRemovePanel(clearCenter(panelAdd));
                    panelAdd.updateUI();
                    but.getOverlayButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
                    overlayButtonPushed = null;
                }
            }
        });   // Накладка

        checkEarlyPushedButtonsWorksPanel();
        panelAdd.updateUI();
    } // Панель с работами

    private void addAndRemovePanel(JPanel panel) {

        JPanel addAndRemovePanel = new JPanel(new GridBagLayout());
        JPanel panelXY = new JPanel(new BorderLayout());
        JPanel panelXY2 = new JPanel(new BorderLayout());

        addAndRemovePanel.add(new JLabel(" "), new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
        addAndRemovePanel.add(new JLabel(" "), new GridBagConstraints(0, 1, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
        addAndRemovePanel.add(takeColorOfButtons(removeActionListener(but.getAddButton()), 1), new GridBagConstraints(0, 2, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 2, 2, 2), 30, 0));
        addAndRemovePanel.add(takeColorOfButtons(removeActionListener(but.getRemoveButton()), 1), new GridBagConstraints(0, 3, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 2, 2, 2), 30, 0));


        panelXY.add(addAndRemovePanel, BorderLayout.NORTH);
        panelXY2.add(panelXY, BorderLayout.WEST);
        panel.add(panelXY2, BorderLayout.CENTER);
        panel.updateUI();

        but.getAddButton().addActionListener(o -> {
            Element element = createElement();
            if (elementList.isEmpty()) {
                // Если Элемент меняется, то к имени добавляем "замена".
                // Применяем здесь, а не в createElement() по причине метода elementList.get(i).getName().contains(element.getName())
                // Если применить в createElement() то при contains "**** ****.**** замена" getName с "**** ****.****" без замена он его не засчитает и вернет false
                elementList.add(addToNameZamenaIfZamenaButtonPushed(element));
                // Добавляем в lineBorderColorMap сначала - сторона(ключ) -> (значение)элемент(он же ключ) -> (значение)список работ
                if (lineBorderColorMap.get(sideButtonPushed.getText()) != null) {
                    lineBorderColorMap.get(sideButtonPushed.getText()).put(elementButtonPushed.getText(), addColorOfButtonsWorks());
                } else {
                    lineBorderColorMap.put(sideButtonPushed.getText(), new HashMap<>(Map.of(elementButtonPushed.getText(), addColorOfButtonsWorks())));
                }
            } else {
                // Сначала удаляем элемент из List
                removeElementFromList(element.getName());
                // Если Элемент меняется, то к имени добавляем "замена".
                // Применяем здесь, а не в createElement() по причине метода elementList.get(i).getName().contains(element.getName())
                // Если применить в createElement() то при contains "**** ****.**** замена" getName с "**** ****.****" без замена он его не засчитает и вернет false*/
                elementList.add(addToNameZamenaIfZamenaButtonPushed(element));
                if (lineBorderColorMap.get(sideButtonPushed.getText()) != null) {
                    lineBorderColorMap.get(sideButtonPushed.getText()).put(elementButtonPushed.getText(), addColorOfButtonsWorks());
                } else {
                    lineBorderColorMap.put(sideButtonPushed.getText(), new HashMap<>(Map.of(elementButtonPushed.getText(), addColorOfButtonsWorks())));
                }
            }
            // Так как у остекления всего 2 панели то добавляем ей после добавления зеленую рамку тут!
            if (sideButtonPushed.getText().equals("Остекление")) {
                elementButtonPushed.setBorder(BorderFactory.createLineBorder(Color.green, 1));
                elementButtonPushed = null;
            }
            // Отправляем новый список на отображение в окошке.
            sendToStringInElementListTextViewing();
            // Убираем панель addAndRemovePanel
            clearCenter(panel).updateUI();
        });   // Добавить
        but.getRemoveButton().addActionListener(e -> {
            // Сначала удаляем элемент из List
            removeElementFromList(createName());
            // Отправляем новый список на отображение в окошке.
            sendToStringInElementListTextViewing();
            // Удаляем из Map элемент
            if (lineBorderColorMap.get(sideButtonPushed.getText()) != null) {
                lineBorderColorMap.get(sideButtonPushed.getText()).remove(elementButtonPushed.getText());
            }
            // Прожимаем кнопку выбранного элемента для обновления кнопок
            elementButtonPushed.doClick();
            // Убираем панель addAndRemovePanel
            clearCenter(panel).updateUI();
        });// Удалить
    } // Панель добавить\удалить

    private JButton takeColorOfButtons(JButton button, int panelNumber) {
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
                                if (check.contains("Ремонт") && button.getText().equals("Ремонт")) {
                                    button.setBorder(BorderFactory.createLineBorder(Color.green));
                                    String[] split = check.split(" ");
                                    // Возвращаем значение "elementRemont" для того чтобы отобразить сколько у данного элемента это значение
                                    if (split.length > 1) {
                                        elementRemont = split[1];
                                    }
                                }
                            }
                        }
                    }
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + panelNumber);
        }
        return button;
    } // Пробег по Map для оформления рамок кнопок

    private JButton changeColorPushedButton(JButton lastPushedButton, JButton pushedButton, int panelNumber) {
        //Сначала проверяем была ли старая кнопка нажата и ищем было ли у данной кнопки раньше "добавление" в элемент
        if (lastPushedButton != null) {
            // если было, у нее меняется рамка на зеленую если нет, то на серую.
            takeColorOfButtons(lastPushedButton, panelNumber);
        }
        // Дальше меняем местами нажатую кнопку и выставляем у нее рамку на красную для понимания какая кнопка нажата
        lastPushedButton = pushedButton;
        lastPushedButton.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
        return lastPushedButton;
    } // Изменение цвета нажатой кнопки(местами с прошлой кнопкой)

    private List<String> addColorOfButtonsWorks() {
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
        if (zercaloButtonPushed != null) {
            color.add(zercaloButtonPushed.getText());
            zercaloButtonPushed.setBorder(BorderFactory.createLineBorder(Color.green, 1));
        }
        if (moldingButtonPushed != null) {
            color.add(moldingButtonPushed.getText());
            moldingButtonPushed.setBorder(BorderFactory.createLineBorder(Color.green, 1));
        }
        if (ruchkaButtonPushed != null) {
            color.add(ruchkaButtonPushed.getText());
            ruchkaButtonPushed.setBorder(BorderFactory.createLineBorder(Color.green, 1));
        }
        if (haveGlass > 0) {
            if (haveGlass == 1) {
                color.add(glassButtonPushed.getText());
                glassButtonPushed.setBorder(BorderFactory.createLineBorder(Color.green, 1));
            } else {
                color.add(but.getButtonRearWindow().getText());
                but.getButtonRearWindow().setBorder(BorderFactory.createLineBorder(Color.green, 1));
                color.add(but.getButtonWindshield().getText());
                but.getButtonWindshield().setBorder(BorderFactory.createLineBorder(Color.green, 1));
            }
        }
        if (expanderButtonPushed != null) {
            color.add(expanderButtonPushed.getText());
            expanderButtonPushed.setBorder(BorderFactory.createLineBorder(Color.green, 1));
        }
        if (overlayButtonPushed != null) {
            color.add(overlayButtonPushed.getText());
            overlayButtonPushed.setBorder(BorderFactory.createLineBorder(Color.green, 1));
        }
        return color;
    } // Добавление зеленой рамки кнопке и занесение нажатий в Map при добавлении в список

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
    } // Удалить все панели кроме начальной с выбором сторон

    private JPanel clearCenter(JPanel panel) {
        BorderLayout layout = (BorderLayout) panel.getLayout();
        if (layout.getLayoutComponent(BorderLayout.CENTER) != null) {
            panel.remove(layout.getLayoutComponent(BorderLayout.CENTER));
        }
        panel.updateUI();
        return panel;
    } // Удаление панели добавить\удалить

    private void clearPushedButtonAfterElementAdd() {
        zamenaOrRsButtonPushed = null;
        paint1xOr2xButtonPushed = null;
        remontButtonPushed = null;
        ruchkaButtonPushed = null;
        moldingButtonPushed = null;
        zercaloButtonPushed = null;
        expanderButtonPushed = null;
        overlayButtonPushed = null;
        glassButtonPushed = null;
        elementRemont = null;
        elementKuzDetReplaceSide = 0.0;
        elementArmatureSide = 0.0;
        elementPaintSide = 0.0;
        haveGlass = 0;
    } // Стирание (приведя их к null) нажатых в процессе кнопок

    private Element createElement() {
        Element element = new Element();
        // С начало выставляем имя.
        element.setName(createName());
        // От сих пор начинаем проставлять нормативы. Если это стекло.
        if (sideButtonPushed.getText().equals("Остекление")) {
            element.setGlass(2);
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
            if (zercaloButtonPushed != null) {
                element.setZerkalo(1);
            }
            if (ruchkaButtonPushed != null) {
                element.setRuchka(1);
            }
            if (moldingButtonPushed != null) {
                element.setMolding(1);
            }
            // Если имя элемента входит в перечень добавляем расширитель крыльев
            if (expanderButtonPushed != null) {
                element.setExpander(2);
            }
            // Если имя элемента входит в перечень, то добавляем накладку
            if (overlayButtonPushed != null) {
                element.setOverlay(2);
            }
            // Если у элемента есть стекло, то проверяем надо ли снимать.
            if (haveGlass > 0) {
                // Умножаем 2 так как норма на стекло 2
                element.setGlass(haveGlass * 2);
                if (haveGlass == 2) {
                    element.setNameGlass("Лобовое и Заднее" + " стекло c/у");
                } else {
                    element.setNameGlass(glassButtonPushed.getText() + " стекло c/у");
                }
            }
        }
        return element;
    }  // Создание элемента

    private JButton removeActionListener(JButton button) {
        /* Очень глупое решение, но другого не нашел, суть его в том чтобы постоянно обновлять актионЛистенер
           Если его нет то, так как при каждом нажатии создаётся новая панель добавления и при
           нажатии "дабавить" актионЛистенер выполняется столько раз сколько была раз создана панель.*/
        ActionListener[] al = button.getActionListeners();
        for (ActionListener actionListener : al) {
            button.removeActionListener(actionListener);
        }
        return button;
    } // Удаление у кнопки ActionListener дабы удалить удвоенность нажатий

    private void sendToStringInElementListTextViewing() {
        elementListTextViewing.setText(Arrays.toString(elementList.toArray()).replaceAll("^\\[|\\]$", ""));
    }  // Вывод на окно просмотра добавленных элементов

    private Element addToNameZamenaIfZamenaButtonPushed(Element element) {
        // Если Элемент меняется, то к имени добавляем "замена".
        if (zamenaOrRsButtonPushed != null) {
            if (zamenaOrRsButtonPushed.getText().equals("Замена")) {
                element.setName(element.getName() + " " + zamenaOrRsButtonPushed.getText());
            }
        }
        return element;
    }  // Добавление к имени элемента "замена" если элемент меняется

    private String createName() {
        String name;
        // Первое оформляем имя (левая правая сторона)
        if (sideButtonPushed.getText().equals("Левая") || sideButtonPushed.getText().equals("Правая")) {
            if (elementButtonPushed.getText().equals("Пер.Крыло") || elementButtonPushed.getText().equals("Зад.Крыло")) {
                name = sideButtonPushed.getText().substring(0, sideButtonPushed.getText().length() - 2) + "ое " + elementButtonPushed.getText();
            } else if (elementButtonPushed.getText().equals("Пер.Дверь") || elementButtonPushed.getText().equals("Зад.Дверь")) {
                name = sideButtonPushed.getText() + " " + elementButtonPushed.getText();
            } else {
                name = sideButtonPushed.getText().substring(0, sideButtonPushed.getText().length() - 2) + "ый " + elementButtonPushed.getText();
            }
            // Второе оформление имени остекление
        } else if (sideButtonPushed.getText().equals("Остекление")) {
            name = elementButtonPushed.getText() + " " + sideButtonPushed.getText();
            // Третье оформление имени центральная часть авто (сторону упускаем, оставляем только элемент)
        } else {
            name = elementButtonPushed.getText();
        }
        return name;
    }  // Создание имени элемента

    private void checkEarlyPushedButtonsWorksPanel() {
        /*
        Проверяем по цвету рамки какие кнопки были нажаты ранее.
         */
        LineBorder colorReplaceButton = (LineBorder) but.getReplaceButton().getBorder();
        LineBorder colorDisassembleButton = (LineBorder) but.getDisassembleButton().getBorder();
        LineBorder colorPaint1xButton = (LineBorder) but.getPaint1xButton().getBorder();
        LineBorder colorPaint2xButton = (LineBorder) but.getPaint2xButton().getBorder();
        LineBorder colorRepair = (LineBorder) but.getRepairButton().getBorder();

        if (colorReplaceButton.getLineColor().equals(Color.GREEN)) {
            but.getReplaceButton().doClick();
        }
        if (colorDisassembleButton.getLineColor().equals(Color.GREEN)) {
            but.getDisassembleButton().doClick();
        }
        if (colorPaint1xButton.getLineColor().equals(Color.GREEN)) {
            but.getPaint1xButton().doClick();
        }
        if (colorPaint2xButton.getLineColor().equals(Color.GREEN)) {
            but.getPaint2xButton().doClick();
        }

        if (colorRepair.getLineColor().equals(Color.GREEN)) {
            but.getRepairButton().doClick();
        }
        if (elementButtonPushed.getText().contains("Пер.Дверь") || elementButtonPushed.getText().contains("Зад.Дверь") || elementButtonPushed.getText().contains("Крышка баг.")) {
            if (elementButtonPushed.getText().contains("Пер.Дверь")) {
                LineBorder colorZerkaloButton = (LineBorder) but.getZerkaloButton().getBorder();
                if (colorZerkaloButton.getLineColor().equals(Color.GREEN)) {
                    but.getZerkaloButton().doClick();
                }
            }
            LineBorder colorRuchkaButton = (LineBorder) but.getRuchkaButton().getBorder();
            LineBorder colorMoldingButton = (LineBorder) but.getMoldingButton().getBorder();
            if (colorRuchkaButton.getLineColor().equals(Color.GREEN)) {
                but.getRuchkaButton().doClick();
            }
            if (colorMoldingButton.getLineColor().equals(Color.GREEN)) {
                but.getMoldingButton().doClick();
            }
        }
        if (elementButtonPushed.getText().contains("Пер.Крыло") || elementButtonPushed.getText().contains("Зад.Крыло")) {
            LineBorder colorExpander = (LineBorder) but.getExpanderButton().getBorder();
            if (colorExpander.getLineColor().equals(Color.GREEN)) {
                but.getExpanderButton().doClick();
            }
        }
        if (elementButtonPushed.getText().contains("Зад.Крыло") || elementButtonPushed.getText().contains("Крыша") || elementButtonPushed.getText().contains("Крышка баг.")) {
            LineBorder colorRearWindow = (LineBorder) but.getButtonRearWindow().getBorder();

            if (colorRearWindow.getLineColor().equals(Color.GREEN)) {
                but.getButtonRearWindow().doClick();
            }
        }
        if (elementButtonPushed.getText().contains("Брус") || elementButtonPushed.getText().contains("Крыша")) {
            LineBorder colorWindow = (LineBorder) but.getButtonWindshield().getBorder();
            if (colorWindow.getLineColor().equals(Color.GREEN)) {
                but.getButtonWindshield().doClick();
            }
        }
        if (elementButtonPushed.getText().contains("Порог") || elementButtonPushed.getText().contains("Пер.Бампер") || elementButtonPushed.getText().contains("Зад.Бампер")) {
            LineBorder colorWindow = (LineBorder) but.getOverlayButton().getBorder();
            if (colorWindow.getLineColor().equals(Color.GREEN)) {
                but.getOverlayButton().doClick();
            }
        }
    } // Проверка и нажатие кнопок добавленного элемента

    private void removeElementFromList(String elementName) {
        Integer q = null;
        Element el = null;
        for (int i = 0; i <= elementList.size() - 1; i++) {
            if (elementList.get(i).getName().contains(elementName)) {
                q = i;
                el = elementList.get(i);
            }
        }
        if (q != null) {
            elementList.remove(el);
        }
    } // Удаление из Списка элементов "element"
}