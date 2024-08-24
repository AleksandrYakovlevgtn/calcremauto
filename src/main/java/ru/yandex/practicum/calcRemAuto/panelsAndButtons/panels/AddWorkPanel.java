package ru.yandex.practicum.calcRemAuto.panelsAndButtons.panels;

import ru.yandex.practicum.calcRemAuto.model.Client;
import ru.yandex.practicum.calcRemAuto.model.Element;
import ru.yandex.practicum.calcRemAuto.model.Lkm;
import ru.yandex.practicum.calcRemAuto.model.LkmPrices;
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
import javax.swing.text.*;

public class AddWorkPanel {
    List<Element> elementList = new ArrayList<>(); // Список добавленных элементов
    Map<String, Map<String, List<String>>> lineBorderColorMap = new HashMap<>(); // Таблица с нажатыми кнопками добавленных элементов
    Buttons but = new Buttons(); // Кнопки
    JPanel elementLeftRightSidePanel = new JPanel(); // Панель с элементами которые расположены по бокам авто
    JPanel elementCenterSide = new JPanel();   // Панель с элементами расположенными по центру авто
    JPanel panelGlass = new JPanel(); // Панель с остеклением авто
    private String imagePath = "Системные/полировка_фон.png"; // Путь до фона. Картинка разложенного авто
    JPanel polirovkaPanel;// = new JPanel(new GridBagLayout()); // Панель для полировки использует фон на котором cheakBox,сы для элементов
    private JComboBox<String> remontComboBox = new JComboBox<>(new String[]{"Маляр", "Кузовщик", "Арматурщик"});
    JTextField remont = but.getRemontJText(); // Графа ввода н\ч ремонта элемента
    JTextField dopWorksArmaturchik = new JTextField();  // Графа ввода доп работ Арматурщик
    JTextField dopWorksPainter = new JTextField();  // Графа ввода доп работ Маляр
    JTextField dopWorksKuzovchik = new JTextField();  // Графа ввода доп работ Кузовщик
    JTextArea elementListTextViewing = new JTextArea(30, 19); // Окно отображения добавленных в List<Element> elementList элементов
    Double elementPaintSide = 0.0;  // Норматив окраски с одной или двух сторон
    double elementPaintAllForLkm = 0.0; //  Общее количество норматива для подсчета ЛКМ
    double elementArmatureSide = 0.0; // Норматив на арматурные работы
    Double elementKuzDetReplaceSide = 0.0; // Норматив на кузовные работы (сварка, замена приварных деталей)
    String elementRemont; // Норматив на ремонтные работы (шпаклевка)
    Integer haveGlass = 0; // Значение есть ли на элементе остекление(вклеенное)
    // Далее идет гряда кнопок которые мы инициализируем при нажатии
    JButton sideButtonPushed; // Сторона
    JButton elementButtonPushed; // Сам Элемент
    JButton zamenaOrRsButtonPushed; // Замена или разборка/сборка
    JButton paint1xOr2xButtonPushed; // Окраска с 1 или 2х сторон
    JButton remontButtonPushed; // Кнопка ремонта
    JButton ruchkaButtonPushed; // Ручка
    JButton moldingButtonPushed; // Молдинг
    JButton zercaloButtonPushed; // Зеркало
    JButton glassButtonPushed; // Стекло
    JButton expanderButtonPushed; // Расширитель
    JButton overlayButtonPushed;  // Накладка
    JButton dopWorksArmaturchikButtonPushed; // Арматурщик
    JButton dopWorksPainterButtonPushed;    // Маляр
    JButton dopWorksKuzovchikButtonPushed;  // Кузовщик
    Lkm lkm = new Lkm();
    LkmPrices lkmPrices = new LkmPrices();
    int lkmTotalPrice = 0;

    public void startPanel(JPanel panel, Client client, JFrame startFrame) {
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
        panelStartAdd.add(takeColorOfButtons(removeActionListener(but.getPolirovkaButton()), 1), new GridBagConstraints(0, 5, 1, 1, 1, 1,
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
        // Добавляем в основную панель заполненную начальную панель
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
        but.getPolirovkaButton().addActionListener(e -> {
            sideButtonPushed = changeColorPushedButton(sideButtonPushed, but.getPolirovkaButton(), 1);
            polirovkaPanel(clearAll(panelAdd));
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
                SaveDialog saveDialog = new SaveDialog(startFrame, client, elementList, lineBorderColorMap, lkm, lkmTotalPrice);
                saveDialog.setVisible(true);
                if (saveDialog.getAnswer()) {
                    FirstPanel firstPanel = new FirstPanel();
                    panel.removeAll();
                    firstPanel.createFirstPanel(panel);
                }
            }
        }); // Кнопка сохранить
    } // Начальная панель Стороны авто + назад и сохранить

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

    private void polirovkaPanel(JPanel panelAdd) {
        polirovkaPanel = setPanelBackground(polirovkaPanel, imagePath);
        JPanel test = new JPanel(new GridBagLayout());
        test.setPreferredSize(new Dimension(300, 200));
        JPanel xyzPanel = new JPanel(new GridBagLayout());
        xyzPanel.setOpaque(false);
        xyzPanel.setPreferredSize(new Dimension(300, 200));
        polirovkaPanel.add(xyzPanel);

        test.add(polirovkaPanel, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.NONE,
                new Insets(2, 2, 1, 2), 300, 200));
        panelAdd.add(test, BorderLayout.WEST);

        JCheckBox perBamperCheckBox = new JCheckBox();
        perBamperCheckBox.setToolTipText("Пер.Бампер");
        perBamperCheckBox.setOpaque(false);  // Устанавливаем прозрачность
        perBamperCheckBox.setBackground(null);  // Убираем фон

        JCheckBox perWingLeftCheckBox = new JCheckBox();
        perWingLeftCheckBox.setToolTipText("Пер.Лев.Крыло");
        perWingLeftCheckBox.setOpaque(false);  // Устанавливаем прозрачность
        perWingLeftCheckBox.setBackground(null);  // Убираем фон

        JCheckBox perWingRightCheckBox = new JCheckBox();
        perWingRightCheckBox.setToolTipText("Пер.Пр.крыло");
        perWingRightCheckBox.setOpaque(false);  // Устанавливаем прозрачность
        perWingRightCheckBox.setBackground(null);  // Убираем фон

        JCheckBox bonnetCheckBox = new JCheckBox();
        bonnetCheckBox.setToolTipText("Капот");
        bonnetCheckBox.setOpaque(false);  // Устанавливаем прозрачность
        bonnetCheckBox.setBackground(null);  // Убираем фон

        JCheckBox perDoorLeftCheckBox = new JCheckBox();
        perDoorLeftCheckBox.setToolTipText("Пер.Лев.Дверь");
        perDoorLeftCheckBox.setOpaque(false);  // Устанавливаем прозрачность
        perDoorLeftCheckBox.setBackground(null);  // Убираем фон

        JCheckBox perDoorRightCheckBox = new JCheckBox();
        perDoorRightCheckBox.setToolTipText("Пер.Пр.Дверь");
        perDoorRightCheckBox.setOpaque(false);  // Устанавливаем прозрачность
        perDoorRightCheckBox.setBackground(null);  // Убираем фон

        JCheckBox backDoorLeftCheckBox = new JCheckBox();
        backDoorLeftCheckBox.setToolTipText("Зад.Лев.Дверь");
        backDoorLeftCheckBox.setOpaque(false);  // Устанавливаем прозрачность
        backDoorLeftCheckBox.setBackground(null);  // Убираем фон

        JCheckBox backDoorRightCheckBox = new JCheckBox();
        backDoorRightCheckBox.setToolTipText("Зад.Пр.Дверь");
        backDoorRightCheckBox.setOpaque(false);  // Устанавливаем прозрачность
        backDoorRightCheckBox.setBackground(null);  // Убираем фон

        JCheckBox balkLeftCheckBox = new JCheckBox();
        balkLeftCheckBox.setToolTipText("Лев.Брус");
        balkLeftCheckBox.setOpaque(false);  // Устанавливаем прозрачность
        balkLeftCheckBox.setBackground(null);  // Убираем фон

        JCheckBox balkRightCheckBox = new JCheckBox();
        balkRightCheckBox.setToolTipText("Пр.Брус");
        balkRightCheckBox.setOpaque(false);  // Устанавливаем прозрачность
        balkRightCheckBox.setBackground(null);  // Убираем фон

        JCheckBox roofCheckBox = new JCheckBox();
        roofCheckBox.setToolTipText("Крыша");
        roofCheckBox.setOpaque(false);  // Устанавливаем прозрачность
        roofCheckBox.setBackground(null);  // Убираем фон

        JCheckBox backWingLeftCheckBox = new JCheckBox();
        backWingLeftCheckBox.setToolTipText("Зад.Лев.Крыло");
        backWingLeftCheckBox.setOpaque(false);  // Устанавливаем прозрачность
        backWingLeftCheckBox.setBackground(null);  // Убираем фон

        JCheckBox backWingRightCheckBox = new JCheckBox();
        backWingRightCheckBox.setToolTipText("Зад.Пр.Крыло");
        backWingRightCheckBox.setOpaque(false);  // Устанавливаем прозрачность
        backWingRightCheckBox.setBackground(null);  // Убираем фон

        JCheckBox trunkLidCheckBox = new JCheckBox();
        trunkLidCheckBox.setToolTipText("Крышка баг.");
        trunkLidCheckBox.setOpaque(false);  // Устанавливаем прозрачность
        trunkLidCheckBox.setBackground(null);  // Убираем фон

        JCheckBox rearBumperCheckBox = new JCheckBox();
        rearBumperCheckBox.setToolTipText("Зад.Бампер");
        rearBumperCheckBox.setOpaque(false);  // Устанавливаем прозрачность
        rearBumperCheckBox.setBackground(null);  // Убираем фон

        JCheckBox lampLeftCheckBox = new JCheckBox();
        lampLeftCheckBox.setToolTipText("Фонарь левый");
        lampLeftCheckBox.setOpaque(false);  // Устанавливаем прозрачность
        lampLeftCheckBox.setBackground(null);  // Убираем фон

        JCheckBox lampRightCheckBox = new JCheckBox();
        lampRightCheckBox.setToolTipText("Фонарь правый");
        lampRightCheckBox.setOpaque(false);  // Устанавливаем прозрачность
        lampRightCheckBox.setBackground(null);  // Убираем фон

        JCheckBox headlightLeftCheckBox = new JCheckBox();
        headlightLeftCheckBox.setToolTipText("Фара левая");
        headlightLeftCheckBox.setOpaque(false);  // Устанавливаем прозрачность
        headlightLeftCheckBox.setBackground(null);  // Убираем фон

        JCheckBox headlightRightCheckBox = new JCheckBox();
        headlightRightCheckBox.setToolTipText("Фара правая");
        headlightRightCheckBox.setOpaque(false);  // Устанавливаем прозрачность
        headlightRightCheckBox.setBackground(null);  // Убираем фон

        JCheckBox mirrorLeftCheckBox = new JCheckBox();
        mirrorLeftCheckBox.setToolTipText("Зеркало левое");
        mirrorLeftCheckBox.setOpaque(false);  // Устанавливаем прозрачность
        mirrorLeftCheckBox.setBackground(null);  // Убираем фон

        JCheckBox mirrorRightCheckBox = new JCheckBox();
        mirrorRightCheckBox.setToolTipText("Зеркало правое");
        mirrorRightCheckBox.setOpaque(false);  // Устанавливаем прозрачность
        mirrorRightCheckBox.setBackground(null);  // Убираем фон


        // Пер лев крыло
        xyzPanel.add(perWingLeftCheckBox, new GridBagConstraints(1, 2, 1, 1, 1, 1,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(0, 35, 0, 2), 0, 0));
        // Пер лев дверь
        xyzPanel.add(perDoorLeftCheckBox, new GridBagConstraints(1, 3, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(0, 35, 2, 2), 0, 0));
        // Зад лев дверь
        xyzPanel.add(backDoorLeftCheckBox, new GridBagConstraints(1, 5, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.NONE,
                new Insets(0, 35, 50, 2), 0, 0));
        // Заднее левое крыло
        xyzPanel.add(backWingLeftCheckBox, new GridBagConstraints(1, 6, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.NONE,
                new Insets(2, 50, 30, 2), 0, 0));
        // Фара левая
        xyzPanel.add(headlightLeftCheckBox, new GridBagConstraints(2, 1, 1, 1, 1, 1,
                GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
                new Insets(2, 2, 2, 2), 0, 0));
        // Зеркало левое
        xyzPanel.add(mirrorLeftCheckBox, new GridBagConstraints(2, 3, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.NONE,
                new Insets(0, 2, 50, 2), 0, 0));
        // Фонарь левый
        xyzPanel.add(lampLeftCheckBox, new GridBagConstraints(2, 7, 1, 1, 1, 1,
                GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
                new Insets(2, 2, 2, 2), 0, 0));
        // Левый брус крыши
        xyzPanel.add(balkLeftCheckBox, new GridBagConstraints(3, 4, 1, 1, 1, 1,
                GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                new Insets(2, 2, 2, 20), 0, 0));
        // Пер бампер
        xyzPanel.add(perBamperCheckBox, new GridBagConstraints(4, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(2, 2, 2, 2), 0, 0));
        // Капот
        xyzPanel.add(bonnetCheckBox, new GridBagConstraints(4, 2, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.NORTH,
                new Insets(2, 2, 50, 2), 0, 0));
        // Крыша
        xyzPanel.add(roofCheckBox, new GridBagConstraints(4, 4, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(2, 2, 2, 2), 0, 0));
        // Крышка багажника
        xyzPanel.add(trunkLidCheckBox, new GridBagConstraints(4, 6, 1, 1, 1, 1,
                GridBagConstraints.SOUTH, GridBagConstraints.NONE,
                new Insets(2, 2, 2, 2), 0, 0));
        // Задний бампер
        xyzPanel.add(rearBumperCheckBox, new GridBagConstraints(4, 8, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(2, 2, 2, 2), 0, 0));
        // Правый брус крыши
        xyzPanel.add(balkRightCheckBox, new GridBagConstraints(5, 4, 1, 1, 1, 1,
                GridBagConstraints.NORTHEAST, GridBagConstraints.NONE,
                new Insets(2, 20, 2, 2), 0, 0));
        // Фара правая
        xyzPanel.add(headlightRightCheckBox, new GridBagConstraints(6, 1, 1, 1, 1, 1,
                GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                new Insets(2, 2, 2, 2), 0, 0));
        // Зеркало правое
        xyzPanel.add(mirrorRightCheckBox, new GridBagConstraints(6, 3, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.NONE,
                new Insets(0, 2, 40, 2), 0, 0));
        // Фонарь правый
        xyzPanel.add(lampRightCheckBox, new GridBagConstraints(6, 7, 1, 1, 1, 1,
                GridBagConstraints.NORTHWEST, GridBagConstraints.NONE,
                new Insets(2, 2, 2, 2), 0, 0));
        // Пер прав крыло
        xyzPanel.add(perWingRightCheckBox, new GridBagConstraints(7, 2, 1, 1, 1, 1,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(0, 2, 0, 35), 0, 0));
        // Пер прав дверь
        xyzPanel.add(perDoorRightCheckBox, new GridBagConstraints(7, 3, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(2, 2, 2, 35), 0, 0));
        // Зад прав дверь
        xyzPanel.add(backDoorRightCheckBox, new GridBagConstraints(7, 5, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.NONE,
                new Insets(0, 2, 50, 35), 0, 0));
        // Заднее правое крыло
        xyzPanel.add(backWingRightCheckBox, new GridBagConstraints(7, 6, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.NONE,
                new Insets(2, 2, 30, 50), 0, 0));


        addAndRemovePanel(clearCenter(panelAdd));
        panelAdd.updateUI();
    }

    private JPanel setPanelBackground(JPanel panel, String imagePath) {
        // Создаем ImageIcon из указанного пути
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image image = imageIcon.getImage();

        // Переопределяем paintComponent для рисования изображения
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(new GridLayout(1, 1));
        panel.setPreferredSize(new Dimension(300, 200));
        return panel;
    } // Добавления фона для панели полировки

    public void WorksPanel(JPanel elementLeftRightSidePanel, JPanel panelAdd) {
        clearPushedButtonAfterElementAdd();

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

        elementLeftRightSidePanel.add(addDocumentListener(remont), new GridBagConstraints(2, 3, 1, 1, 1, 1,
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
        //************************* добавляем доп работы
        int gridY = getMaxGridY(elementLeftRightSidePanel, 1);
        elementLeftRightSidePanel.add(new JLabel("Доп работы"), new GridBagConstraints(1, gridY + 1, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(2, 5, 2, 0), 0, 0));
        elementLeftRightSidePanel.add(takeColorOfButtons(removeActionListener(but.getDopWorksArmaturchikButton()), 3), new GridBagConstraints(1, gridY + 2, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 5, 2, 0), 0, 0));
        elementLeftRightSidePanel.add(takeColorOfButtons(removeActionListener(but.getDopWorksPainterButton()), 3), new GridBagConstraints(1, gridY + 3, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 5, 2, 0), 0, 0));
        elementLeftRightSidePanel.add(takeColorOfButtons(removeActionListener(but.getDopWorksKuzovchikButton()), 3), new GridBagConstraints(1, gridY + 4, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 5, 2, 0), 0, 0));
        elementLeftRightSidePanel.add(addDocumentListener(dopWorksArmaturchik), new GridBagConstraints(2, gridY + 2, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        elementLeftRightSidePanel.add(addDocumentListener(dopWorksPainter), new GridBagConstraints(2, gridY + 3, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        elementLeftRightSidePanel.add(addDocumentListener(dopWorksKuzovchik), new GridBagConstraints(2, gridY + 4, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));


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
        but.getDopWorksArmaturchikButton().addActionListener(e -> {
            if (dopWorksArmaturchikButtonPushed == null) {
                if (dopWorksArmaturchik.getBackground() != Color.pink && dopWorksArmaturchik.getText().length() > 0) {
                    dopWorksArmaturchikButtonPushed = but.getDopWorksArmaturchikButton();
                    dopWorksArmaturchikButtonPushed.setBorder(BorderFactory.createLineBorder(Color.red, 1));
                    addAndRemovePanel(clearCenter(panelAdd));
                    panelAdd.updateUI();
                }
            } else {
                // Если доп. работы уже добавлены, а мы хотим их убрать необходимо повторно нажать на кнопку
                LineBorder color = (LineBorder) dopWorksArmaturchikButtonPushed.getBorder();
                // Если кнопка уже была нажата и элемент был добавлен (то есть имеет цвет рамки зеленый) мы убираем нормативы
                if (color.getLineColor() == (Color.green)) {
                    // Устанавливаем цвет кнопки на серый тем самым убрав ее из элемента
                    but.getDopWorksArmaturchikButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
                    dopWorksArmaturchikButtonPushed = null;
                    addAndRemovePanel(clearCenter(panelAdd));
                    panelAdd.updateUI();
                } else {
                    // Если кнопка была нажата, но элемент не был добавлен (то есть имеет цвет рамки красный) мы убираем нормативы
                    dopWorksArmaturchik.setText("");
                    // Устанавливаем цвет кнопки на серый тем самым убрав ее из элемента
                    but.getDopWorksArmaturchikButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
                    dopWorksArmaturchikButtonPushed = null;
                    addAndRemovePanel(clearCenter(panelAdd));
                    panelAdd.updateUI();
                }
            }
        }); // Доп работы арматурщик
        but.getDopWorksPainterButton().addActionListener(e -> {
            if (dopWorksPainterButtonPushed == null) {
                if (dopWorksPainter.getBackground() != Color.pink && dopWorksPainter.getText().length() > 0) {
                    dopWorksPainterButtonPushed = but.getDopWorksPainterButton();
                    dopWorksPainterButtonPushed.setBorder(BorderFactory.createLineBorder(Color.red, 1));
                    addAndRemovePanel(clearCenter(panelAdd));
                    panelAdd.updateUI();
                }
            } else {
                // Если доп. работы уже добавлены, а мы хотим их убрать необходимо повторно нажать на кнопку
                LineBorder color = (LineBorder) dopWorksPainterButtonPushed.getBorder();
                // Если кнопка уже была нажата и элемент был добавлен (то есть имеет цвет рамки зеленый) мы убираем нормативы
                if (color.getLineColor() == (Color.green)) {
                    // Устанавливаем цвет кнопки на серый тем самым убрав ее из элемента
                    but.getDopWorksPainterButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
                    dopWorksPainterButtonPushed = null;
                    addAndRemovePanel(clearCenter(panelAdd));
                    panelAdd.updateUI();
                } else {
                    // Если кнопка была нажата, но элемент не был добавлен (то есть имеет цвет рамки красный) мы убираем нормативы
                    dopWorksPainter.setText("");
                    // Устанавливаем цвет кнопки на серый тем самым убрав ее из элемента
                    but.getDopWorksPainterButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
                    dopWorksPainterButtonPushed = null;
                    addAndRemovePanel(clearCenter(panelAdd));
                    panelAdd.updateUI();
                }
            }
        });    // Доп работы маляр
        but.getDopWorksKuzovchikButton().addActionListener(e -> {
            if (dopWorksKuzovchikButtonPushed == null) {
                if (dopWorksKuzovchik.getBackground() != Color.pink && dopWorksKuzovchik.getText().length() > 0) {
                    dopWorksKuzovchikButtonPushed = but.getDopWorksKuzovchikButton();
                    dopWorksKuzovchikButtonPushed.setBorder(BorderFactory.createLineBorder(Color.red, 1));
                    addAndRemovePanel(clearCenter(panelAdd));
                    panelAdd.updateUI();
                }
            } else {
                // Если доп. работы уже добавлены, а мы хотим их убрать необходимо повторно нажать на кнопку
                LineBorder color = (LineBorder) dopWorksKuzovchikButtonPushed.getBorder();
                // Если кнопка уже была нажата и элемент был добавлен (то есть имеет цвет рамки зеленый) мы убираем нормативы
                if (color.getLineColor() == (Color.green)) {
                    // Устанавливаем цвет кнопки на серый тем самым убрав ее из элемента
                    but.getDopWorksKuzovchikButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
                    dopWorksKuzovchikButtonPushed = null;
                    addAndRemovePanel(clearCenter(panelAdd));
                    panelAdd.updateUI();
                } else {
                    // Если кнопка была нажата, но элемент не был добавлен (то есть имеет цвет рамки красный) мы убираем нормативы
                    dopWorksKuzovchik.setText("");
                    // Устанавливаем цвет кнопки на серый тем самым убрав ее из элемента
                    but.getDopWorksKuzovchikButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
                    dopWorksKuzovchikButtonPushed = null;
                    addAndRemovePanel(clearCenter(panelAdd));
                    panelAdd.updateUI();
                }
            }
        }); // Доп работы кузовщик

        checkEarlyPushedButtonsWorksPanel();
        panelAdd.updateUI();
    } // Панель с работами

    public static int getMaxGridY(JPanel panel, int targetGridX) {
        int maxGridY = -1;

        // Перебор всех компонентов в JPanel
        for (Component comp : panel.getComponents()) {
            GridBagConstraints gbc = ((GridBagLayout) panel.getLayout()).getConstraints(comp);
            if (gbc.gridx == targetGridX && gbc.gridy > maxGridY) {
                maxGridY = gbc.gridy;
            }
        }

        return maxGridY;
    }

    public static JTextField addDocumentListener(JTextField textField) {
        Document document = textField.getDocument();
        AbstractDocument abstractDocument = (AbstractDocument) document;

        // Add DocumentFilter для избежания ввода чего угодна кроме цифр
        abstractDocument.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string != null && string.matches("\\d*")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attr) throws BadLocationException {
                if (string != null && string.matches("\\d*")) {
                    super.replace(fb, offset, length, string, attr);
                }
            }

            @Override
            public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
                super.remove(fb, offset, length);
            }
        });

        // Add DocumentListener для изменения цвета поля ввода
        document.addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateBackgroundColor();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateBackgroundColor();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateBackgroundColor();
            }

            private void updateBackgroundColor() {
                try {
                    if (!textField.getText().isBlank()) {
                        Integer.parseInt(textField.getText());
                        textField.setBackground(Color.green);
                    } else {
                        textField.setBackground(Color.white);
                    }
                } catch (NumberFormatException ex) {
                    textField.setBackground(Color.pink);
                }
            }
        });
        return textField;
    } // Добавление слушателя для JTextField-ов


    private void addAndRemovePanel(JPanel panel) {
        Dimension buttonSize = new Dimension(58, 20);
        remontComboBox.setBackground(Color.WHITE);
        remontComboBox.setPreferredSize(buttonSize);

        JPanel addAndRemovePanel = new JPanel(new GridBagLayout());
        JPanel panelXY = new JPanel(new BorderLayout());
        JPanel panelXY2 = new JPanel(new BorderLayout());

        addAndRemovePanel.add(new JLabel(" "), new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0));
        addAndRemovePanel.add(new JLabel(" "), new GridBagConstraints(0, 1, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 2, 0, 2), 0, 0));
        addAndRemovePanel.add(takeColorOfButtons(removeActionListener(but.getAddButton()), 1), new GridBagConstraints(0, 2, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 2, 2, 2), 30, 0));
        addAndRemovePanel.add(takeColorOfButtons(removeActionListener(but.getRemoveButton()), 1), new GridBagConstraints(0, 3, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 30, 0));
        if (remontButtonPushed != null) {
            // Добавляем JComboBox только если кнопка remontButtonPushed активирована
            addAndRemovePanel.add(remontComboBox, new GridBagConstraints(0, 4, 1, 1, 1, 1,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(2, 2, 2, 2), 30, 0));
        }


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
            // Затем обнуляем ЛКМ
            clearLkm();
            // Пересчитываем ЛКМ оставшихся элементов
            reCalcLkmPrice();
            // Отправляем новый список на отображение в окошке.
            sendToStringInElementListTextViewing();
            // Убираем панель addAndRemovePanel
            clearCenter(panel).updateUI();
        });   // Добавить
        but.getRemoveButton().addActionListener(e -> {
            // Сначала удаляем элемент из List
            removeElementFromList(createName());
            // Затем обнуляем ЛКМ
            clearLkm();
            // Пересчитываем ЛКМ оставшихся элементов
            reCalcLkmPrice();
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
                                //Если есть кнопка ремонт, то делим ее на значение "ремонт", "число" и "механик"
                                if (check.contains("Ремонт") && button.getText().equals("Ремонт")) {
                                    button.setBorder(BorderFactory.createLineBorder(Color.green));
                                    String[] split = check.split(" ");
                                    // Возвращаем значение "elementRemont" для того чтобы отобразить сколько у данного элемента это значение
                                    if (split.length > 1) {
                                        elementRemont = split[1];
                                    }
                                    if (split.length > 2) {
                                        switch (split[2]) {
                                            case "Маляр":
                                                remontComboBox.setSelectedIndex(0);
                                                break;
                                            case "Кузовщик":
                                                remontComboBox.setSelectedIndex(1);
                                                break;
                                            case "Арматурщик":
                                                remontComboBox.setSelectedIndex(2);
                                                break;
                                        }
                                    }
                                } else if (check.contains(button.getText()) && (check.contains("Маляр") || check.contains("Кузовщик") || check.contains("Арматурщик"))) {
                                    String[] split = check.split(" ");
                                    switch (split[0]) {
                                        case "Арматурщик":
                                            dopWorksArmaturchik.setText(split[1]);
                                            button.setBorder(BorderFactory.createLineBorder(Color.green));
                                            break;
                                        case "Маляр":
                                            dopWorksPainter.setText(split[1]);
                                            button.setBorder(BorderFactory.createLineBorder(Color.green));
                                            break;
                                        case "Кузовщик":
                                            dopWorksKuzovchik.setText(split[1]);
                                            button.setBorder(BorderFactory.createLineBorder(Color.green));
                                            break;
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
            color.add(remontButtonPushed.getText() + " " + elementRemont + " " + remontComboBox.getSelectedItem());
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
        if (dopWorksArmaturchikButtonPushed != null) {
            color.add(dopWorksArmaturchikButtonPushed.getText() + " " + dopWorksArmaturchik.getText());
            dopWorksArmaturchikButtonPushed.setBorder(BorderFactory.createLineBorder(Color.green, 1));
        }
        if (dopWorksPainterButtonPushed != null) {
            color.add(dopWorksPainterButtonPushed.getText() + " " + dopWorksPainter.getText());
            dopWorksPainterButtonPushed.setBorder(BorderFactory.createLineBorder(Color.green, 1));
        }
        if (dopWorksKuzovchikButtonPushed != null) {
            color.add(dopWorksKuzovchikButtonPushed.getText() + " " + dopWorksKuzovchik.getText());
            dopWorksKuzovchikButtonPushed.setBorder(BorderFactory.createLineBorder(Color.green, 1));
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
        dopWorksArmaturchikButtonPushed = null;
        dopWorksPainterButtonPushed = null;
        dopWorksKuzovchikButtonPushed = null;
        dopWorksArmaturchik.setText("");
        dopWorksPainter.setText("");
        dopWorksKuzovchik.setText("");
        remont.setText("");
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
            elementPaintAllForLkm += elementPaintSide;  // Добавляем в ЛКМ норматив объем окраски общего для всех Элементов
            element.setLkmForElement(element.getPaintSide()); // Добавляем в ЛКМ норматив объем окраски для конкретного Элемента
            // Если нажата кнопка ремонта, то добавляем, добавив "." перед последним символом
            // переведя в двоичное число
            if (remontButtonPushed != null) {
                String rem;
                rem = new StringBuilder(elementRemont).insert(elementRemont.length() - 1, ".").toString();
                element.setRemont(Double.parseDouble(rem));
                element.setHoDoRemont((String) remontComboBox.getSelectedItem());
            }
            // Если имя элемента входит в перечень, то добавляем ручки, молдинги и зеркала.
            if (zercaloButtonPushed != null) {
                element.setZerkalo(1);
                element.setLkmForElement(element.getLkmForElement() + 1);
                elementPaintAllForLkm += 1; // Добавляем в ЛКМ общее, норматив объем окраски согласно элементу
            }
            if (ruchkaButtonPushed != null) {
                element.setRuchka(1);
                element.setLkmForElement(element.getLkmForElement() + 1);
                elementPaintAllForLkm += 1; // Добавляем в ЛКМ общее, норматив объем окраски согласно элементу
            }
            if (moldingButtonPushed != null) {
                element.setMolding(1);
                element.setLkmForElement(element.getLkmForElement() + 1);
                elementPaintAllForLkm += 1; // Добавляем в ЛКМ общее, норматив объем окраски согласно элементу
            }
            // Если имя элемента входит в перечень добавляем расширитель крыльев
            if (expanderButtonPushed != null) {
                element.setExpander(2);
                element.setLkmForElement(element.getLkmForElement() + 2);
                elementPaintAllForLkm += 2; // Добавляем в ЛКМ общее, норматив объем окраски согласно элементу
            }
            // Если имя элемента входит в перечень, то добавляем накладку
            if (overlayButtonPushed != null) {
                element.setOverlay(2);
                element.setLkmForElement(element.getLkmForElement() + 2);
                elementPaintAllForLkm += 2; // Добавляем в ЛКМ общее, норматив объем окраски согласно элементу
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
            if (dopWorksArmaturchikButtonPushed != null) {
                String dopWorks;
                dopWorks = new StringBuilder(dopWorksArmaturchik.getText()).insert(dopWorksArmaturchik.getText().length() - 1, ".").toString();
                element.setDopWorksArmoturchik(Double.parseDouble(dopWorks));
            }
            if (dopWorksPainterButtonPushed != null) {
                String dopWorks;
                dopWorks = new StringBuilder(dopWorksPainter.getText()).insert(dopWorksPainter.getText().length() - 1, ".").toString();
                element.setDopWorksPainter(Double.parseDouble(dopWorks));
            }
            if (dopWorksKuzovchikButtonPushed != null) {
                String dopWorks;
                dopWorks = new StringBuilder(dopWorksKuzovchik.getText()).insert(dopWorksKuzovchik.getText().length() - 1, ".").toString();
                element.setDopWorksKuzovchik(Double.parseDouble(dopWorks));
            }
        }
        calcLkm(element);
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
        elementListTextViewing.setText("ЛКМ Итого: " + lkmTotalPrice + "руб.\n" + Arrays.toString(elementList.toArray()).replaceAll("^\\[|\\]$", ""));
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
        LineBorder colorDopWorksArmaturchik = (LineBorder) but.getDopWorksArmaturchikButton().getBorder();
        LineBorder colorDopWorksPainter = (LineBorder) but.getDopWorksPainterButton().getBorder();
        LineBorder colorDopWorksKuzovchik = (LineBorder) but.getDopWorksKuzovchikButton().getBorder();

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
        if (colorDopWorksArmaturchik.getLineColor().equals(Color.GREEN)) {
            but.getDopWorksArmaturchikButton().doClick();
        }
        if (colorDopWorksPainter.getLineColor().equals(Color.GREEN)) {
            but.getDopWorksPainterButton().doClick();
        }
        if (colorDopWorksKuzovchik.getLineColor().equals(Color.GREEN)) {
            but.getDopWorksKuzovchikButton().doClick();
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

    public void load(List<Element> elementList, Map<String, Map<String, List<String>>> lineBorderColorMap, Client client, Lkm lkm, JPanel panel) {
        this.elementList = elementList;
        this.lineBorderColorMap = lineBorderColorMap;
        reCalcLkmPrice();
        this.lkm = lkm;
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(panel);
        startPanel(panel, client, parentFrame);
        sendToStringInElementListTextViewing();
    }

    private void calcLkm(Element element) {
        if (!element.getName().contains("Остекление")) {
            lkm.plusOneCircles(); // Добавляем круги всех +1
            lkm.setNapkin(lkm.getNapkin() + 3); // Добавляем салфетку
            int remForStrip = (int) Math.ceil(element.getRemont() * 0.2); // считаем коэффициент ремонта
            for (int i = 0; i < remForStrip; i++) { // По коэффициенту высчитываем необходимое количество полосок
                lkm.plusStrip(); // Добавляем полоски +1
                lkm.setNapkin(lkm.getNapkin() + 1); // Добавляем салфетку
            }
            // По коэффициенту высчитываем необходимое количество шпатлевки
            if (remForStrip == 1) {
                lkm.setPuttyUniversal(lkm.getPuttyUniversal() + 0.3); // Универсальная
            } else if (remForStrip == 2) {
                lkm.setPuttyFiber(lkm.getPuttyFiber() + 0.2);         // Волокнистая
                lkm.setPuttyUniversal(lkm.getPuttyUniversal() + 0.5); // Универсальная
            } else if (remForStrip >= 3) {
                lkm.setPuttyFiber(lkm.getPuttyFiber() + 0.4);        // Волокнистая
                lkm.setPuttyUniversal(lkm.getPuttyUniversal() + 0.7);// Универсальная
            }
            // Добавляем скотчБрайт образив из расчета 0.2 на элемент
            lkm.setScotchBrite(lkm.getScotchBrite() + 0.2);
            // Добавляем скотч малярный из расчета 0.5 на элемент и не более 1 на весь авто
            if (lkm.getStickyTape() < 1) {
                lkm.setStickyTape(lkm.getStickyTape() + 0.5);
            }
            // Добавляем пленку укрывочную 7 метров на авто
            lkm.setCoveringFilm(7);
            // Если есть замена приварного элемента добавляем 1 герметик
            if (element.getKuzDetReplaceSide() > 0) {
                lkm.setHermetic(1);
            }
            // Добавляем обезжириватель
            lkm.setSiliconRemover(lkm.getSiliconRemover() + 0.2);
            // Добавляем Грунт 200гр на элемент
            lkm.setPriming(elementPaintAllForLkm / 15);
            // Добавляем Лак 300гр на элемент
            lkm.setClear(elementPaintAllForLkm / 10);
            // Добавляем Краску 300гр на элемент
            lkm.setBasePaint(elementPaintAllForLkm / 10);
            // Добавляем Разбавитель 15% на грунт и лак, 50% на краску
            lkm.setBaseDilution((0.5 * lkm.getBasePaint()) + (0.15 * (lkm.getPriming() + lkm.getClear())));
        }
        calcLkmPrice();
    } // Метод подсчета необходимых ЛКМ материалов

    private void calcLkmPrice() {
        int circle = lkm.getP80() + lkm.getP180() + lkm.getP280() + lkm.getP400() + lkm.getP500();
        int strips = lkm.getStripP80() + lkm.getStripP120() + lkm.getStripP180();
        lkmTotalPrice = (int) (((circle * lkmPrices.getCircle()) + (strips * lkmPrices.getStrip())
                + (lkm.getScotchBrite() * lkmPrices.getScotchBrite())
                + (lkm.getPriming() * lkmPrices.getPriming())
                + (lkm.getClear() * lkmPrices.getClear())
                + (lkm.getBaseDilution() * lkmPrices.getBaseDilution())
                + (lkm.getBasePaint() * lkmPrices.getSiliconRemover())
                + (lkm.getStickyTape() * lkmPrices.getStickyTape())
                + (lkm.getCoveringFilm() * lkmPrices.getCoveringFilm())
                + (lkm.getPuttyFiber() * lkmPrices.getPuttyFiber())
                + (lkm.getPuttyUniversal() * lkmPrices.getPuttyUniversal())
                + (lkm.getNapkin() * lkmPrices.getNapkin())
                + (lkm.getHermetic() * lkmPrices.getHermetic())) * 1.1);
    } // Метод подсчета стоимости необходимого ЛКМ

    private void reCalcLkmPrice() {
        lkmTotalPrice = 0;
        elementPaintAllForLkm = 0;
        if (!elementList.isEmpty()) {
            for (Element element : elementList) {
                elementPaintAllForLkm = elementPaintAllForLkm + element.getLkmForElement();
                calcLkm(element);
            }
        } else {
            calcLkmPrice();
        }
    } // Пересчитать ЛКМ заново

    private void clearLkm() {
        lkm.setP80(0);
        lkm.setP180(0);
        lkm.setP280(0);
        lkm.setP280(0);
        lkm.setP400(0);
        lkm.setP500(0);
        lkm.setStripP80(0);
        lkm.setStripP120(0);
        lkm.setStripP180(0);
        lkm.setScotchBrite(0);
        lkm.setPriming(0);
        lkm.setClear(0);
        lkm.setBaseDilution(0);
        lkm.setBasePaint(0);
        lkm.setSiliconRemover(0);
        lkm.setStickyTape(0);
        lkm.setCoveringFilm(0);
        lkm.setPuttyFiber(0);
        lkm.setPuttyUniversal(0);
        lkm.setNapkin(0);
        lkm.setHermetic(0);
    } // Обнуляем ЛКМ
}