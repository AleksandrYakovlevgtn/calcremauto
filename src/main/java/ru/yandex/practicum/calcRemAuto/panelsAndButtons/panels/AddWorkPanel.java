package ru.yandex.practicum.calcRemAuto.panelsAndButtons.panels;

import ru.yandex.practicum.calcRemAuto.grammar.CustomDocumentHandler;
import ru.yandex.practicum.calcRemAuto.logToFail.LogToFailManager;
import ru.yandex.practicum.calcRemAuto.model.Client;
import ru.yandex.practicum.calcRemAuto.model.Element;
import ru.yandex.practicum.calcRemAuto.model.Lkm;
import ru.yandex.practicum.calcRemAuto.model.LkmPrices;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.calcRemAuto.panelsAndButtons.buttons.Buttons;
import ru.yandex.practicum.calcRemAuto.panelsAndButtons.frame.DescriptionWindow;
import ru.yandex.practicum.calcRemAuto.panelsAndButtons.frame.SaveDialog;
import ru.yandex.practicum.calcRemAuto.panelsAndButtons.frame.StartFrame;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import javax.swing.text.*;
import javax.swing.text.BadLocationException;

@Slf4j
public class AddWorkPanel {
    ExecutorService executor; // Для распараллеливания задач
    JFrame frame;
    List<Element> elementList = new ArrayList<>(); // Список добавленных элементов
    Map<String, Map<String, List<String>>> lineBorderColorMap = new HashMap<>(); // Таблица с нажатыми кнопками добавленных элементов
    Buttons but = new Buttons(); // Кнопки
    JPanel elementLeftRightSidePanel = new JPanel(); // Панель с элементами которые расположены по бокам авто
    JPanel elementCenterSide = new JPanel();   // Панель с элементами расположенными по центру авто
    JPanel panelGlass = new JPanel(); // Панель с остеклением авто
    JPanel panelNotNormWork = new JPanel(); // Панель с ненормативными работами
    private String imagePath = "Системные/полировка_фон.png"; // Путь до фона. Картинка разложенного авто
    JPanel polirovkaPanel;// = new JPanel(new GridBagLayout()); // Панель для полировки использует фон на котором cheakBox,сы для элементов
    private JComboBox<String> remontComboBox = new JComboBox<>(new String[]{"Маляр", "Кузовщик", "Арматурщик"});
    JTextField remont = but.getRemontJText(); // Графа ввода н\ч ремонта элемента
    JTextField dopWorksArmaturchik = new JTextField();  // Графа ввода норматива доп работ Арматурщик
    JTextField dopWorksPainter = new JTextField();  // Графа ввода норматива доп работ Маляр
    JTextField dopWorksKuzovchik = new JTextField();  // Графа ввода норматива доп работ Кузовщик
    JTextField notNormWorkJTextField = new JTextField();  // Графа ввода норматива ненормативных работ
    JTextArea dopWorksArmaturchikDescription = new JTextArea();  // Графа ввода описания доп работ Арматурщик
    JTextArea dopWorksPainterDescription = new JTextArea();  // Графа ввода описания доп работ Маляр
    JTextArea dopWorksKuzovchikDescription = new JTextArea();  // Графа ввода описания доп работ Кузовщик
    JTextArea elementListTextViewing = new JTextArea(30, 19); // Окно отображения добавленных в List<Element> elementList элементов
    Double elementPaintSide = 0.0;  // Норматив окраски с одной или двух сторон
    CorrectionPanel correctionPanelPaintSide = new CorrectionPanel(elementPaintSide, newValue -> {
        elementPaintSide = newValue;
    });                               // Панель с регулировкой норматива маляра +/- 0.5
    double elementPaintAllForLkm = 0.0; //  Общее количество норматива для подсчета ЛКМ
    double elementArmatureSide = 0.0; // Норматив на арматурные работы
    CorrectionPanel correctionPanelArmatureSide = new CorrectionPanel(elementArmatureSide, newValue -> {
        elementArmatureSide = newValue;
    });                               // Панель с регулировкой норматива арматурщика +/- 0.5
    Double elementKuzDetReplaceSide = 0.0; // Норматив на кузовные работы (сварка, замена приварных деталей)
    String elementRemontString; // Норматив на ремонтные работы (шпаклевка)
    String notNormWorkNormativeString; // Норматив на ненорматвиные работы
    String notNormWorkHoDoString;  // значение механика для правильного отображения его в не нормативных работах
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
    JButton dopWorksArmaturchikButtonPushed; // доп.работы Арматурщик
    JButton dopWorksPainterButtonPushed;    // доп.работы Маляр
    JButton dopWorksKuzovchikButtonPushed;  // доп.работы Кузовщик
    JButton dopWorksArmaturchikDescriptionPushed; // прожатие кнопки описания доп.работы Арматурщик
    JButton dopWorksPainterDescriptionPushed; // прожатие кнопки описания доп.работы Маляр
    JButton dopWorksKuzovchikDescriptionPushed; // прожатие кнопки описания доп.работы Кузовщик
    JButton notNormWorkDescriptionPushed; // прожатие кнопки описания ненормативные работы
    String inputDopWorksArmaturchikDescription; // Описание доп.работ Арматурщик (тестовое выражение)
    String inputDopWorksPainterDescription; // Описание доп.работ Маляр (тестовое выражение)
    String inputDopWorksKuzovchikDescription; // Описание доп.работ Кузовщик (тестовое выражение)
    String inputNotNormWorkDescription; // Описание ненормативные работы (тестовое выражение)
    int gridYForDopWorksDescription;
    JCheckBox[] checkBoxes = {
            createCheckBox("Пер.Бампер"), createCheckBox("Пер.Лев.Крыло"),
            createCheckBox("Пер.Пр.крыло"), createCheckBox("Капот"),
            createCheckBox("Пер.Лев.Дверь"), createCheckBox("Пер.Пр.Дверь"),
            createCheckBox("Зад.Лев.Дверь"), createCheckBox("Зад.Пр.Дверь"),
            createCheckBox("Лев.Брус"), createCheckBox("Пр.Брус"),
            createCheckBox("Крыша"), createCheckBox("Зад.Лев.Крыло"),
            createCheckBox("Зад.Пр.Крыло"), createCheckBox("Крышка баг."),
            createCheckBox("Зад.Бампер"), createCheckBox("Фонарь левый"),
            createCheckBox("Фонарь правый"), createCheckBox("Фара левая"),
            createCheckBox("Фара правая"), createCheckBox("Зеркало левое"),
            createCheckBox("Зеркало правое")
    }; // Массив чекбоксов с их описаниями и позициями для работ полировки
    JCheckBox finalPolirovkaElementCheckBox = createCheckBox("Финальная полировка после окраски."); // Чекбокс финальная полировка после покраски.
    Lkm lkm = new Lkm();
    LkmPrices lkmPrices = new LkmPrices();
    int lkmTotalPrice = 0;
    static LogToFailManager logManager = new LogToFailManager();

    public void startPanel(JPanel panel, Client client, JFrame frame) {
        logManager.log("Запущен метод startPanel в классе AddWorkPanel");
        this.frame = frame;
        // Получаем текущий экземпляр StartFrame
        StartFrame startFrame = StartFrame.getCurrentInstance();
        if (startFrame != null) {
            // Используем ExecutorService из StartFrame
            executor = startFrame.getExecutor();
        }

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
        panelStartAdd.add(takeColorOfButtons(removeActionListener(but.getNotNormWorkButton()), 1), new GridBagConstraints(0, 6, 1, 1, 1, 1,
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
            logManager.log("Нажата кнопка Левая");
            sideButtonPushed = changeColorPushedButton(sideButtonPushed, but.getButtonLeft(), 1);
            leftRightSidePanel(clearAll(panelAdd));
        });
        but.getButtonRight().addActionListener(e -> {
            logManager.log("Нажата кнопка Правая");
            sideButtonPushed = changeColorPushedButton(sideButtonPushed, but.getButtonRight(), 1);
            leftRightSidePanel(clearAll(panelAdd));
        });
        but.getButtonGlass().addActionListener(e -> {
            logManager.log("Нажата кнопка Остекление");
            sideButtonPushed = changeColorPushedButton(sideButtonPushed, but.getButtonGlass(), 1);
            glassSidePanel(clearAll(panelAdd));
        });
        but.getButtonCenter().addActionListener(e -> {
            logManager.log("Нажата кнопка Центр");
            sideButtonPushed = changeColorPushedButton(sideButtonPushed, but.getButtonCenter(), 1);
            centerSidePanel(clearAll(panelAdd));
        });
        but.getPolirovkaButton().addActionListener(e -> {
            logManager.log("Нажата кнопка Полировка");
            sideButtonPushed = changeColorPushedButton(sideButtonPushed, but.getPolirovkaButton(), 4);
            polirovkaPanel(clearAll(panelAdd));
        });
        but.getNotNormWorkButton().addActionListener(e -> {
            logManager.log("Нажата кнопка Ненормативные");
            sideButtonPushed = changeColorPushedButton(sideButtonPushed, but.getNotNormWorkButton(), 1);
            choiseNumberNotNormWorkPanel(clearAll(panelAdd));
        });

        but.getButtonBack().addActionListener(e -> {
            logManager.log("Нажата кнопка Назад");
            panel.removeAll();
            AddClientPanel addClientPanel = new AddClientPanel();
            addClientPanel.clientAdd(panel, client);
        }); // Кнопка назад
        but.getButtonSave().addActionListener(e -> {
            logManager.log("Нажата кнопка Сохранить");
            if (elementList.isEmpty()) {
                logManager.log("Не добавлен ни один элемент Сохранить нечего!");
                JOptionPane.showMessageDialog(null, "Не добавлен ни один элемент\nСохранить нечего!", "Ошибка", JOptionPane.INFORMATION_MESSAGE);
            } else {
                logManager.log("Начинается создание saveDialog");
                SaveDialog saveDialog = new SaveDialog(frame, client, elementList, lineBorderColorMap, lkm, lkmTotalPrice);
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
        logManager.log("Запущен метод leftRightSidePanel.");
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
            logManager.log("Нажата кнопка Переднее крыло.");
            clearPushedButtonAfterElementAdd();
            elementButtonPushed = changeColorPushedButton(elementButtonPushed, but.getButtonFrontWing(), 2);
            WorksPanel(elementLeftRightSidePanel, clearCenter(panelAdd));
        });
        but.getButtonFrontDoor().addActionListener(e -> {
            logManager.log("Нажата кнопка Передняя дверь.");
            clearPushedButtonAfterElementAdd();
            elementButtonPushed = changeColorPushedButton(elementButtonPushed, but.getButtonFrontDoor(), 2);
            WorksPanel(elementLeftRightSidePanel, clearCenter(panelAdd));
        });
        but.getButtonBackDoor().addActionListener(e -> {
            logManager.log("Нажата кнопка Задняя дверь.");
            clearPushedButtonAfterElementAdd();
            elementButtonPushed = changeColorPushedButton(elementButtonPushed, but.getButtonBackDoor(), 2);
            WorksPanel(elementLeftRightSidePanel, clearCenter(panelAdd));
        });
        but.getButtonBackWing().addActionListener(e -> {
            logManager.log("Нажата кнопка Заднее крыло.");
            clearPushedButtonAfterElementAdd();
            elementButtonPushed = changeColorPushedButton(elementButtonPushed, but.getButtonBackWing(), 2);
            WorksPanel(elementLeftRightSidePanel, clearCenter(panelAdd));
        });
        but.getButtonDoorStep().addActionListener(e -> {
            logManager.log("Нажата кнопка Порог.");
            clearPushedButtonAfterElementAdd();
            elementButtonPushed = changeColorPushedButton(elementButtonPushed, but.getButtonDoorStep(), 2);
            WorksPanel(elementLeftRightSidePanel, clearCenter(panelAdd));
        });
        but.getButtonBalk().addActionListener(e -> {
            logManager.log("Нажата кнопка Брус.");
            clearPushedButtonAfterElementAdd();
            elementButtonPushed = changeColorPushedButton(elementButtonPushed, but.getButtonBalk(), 2);
            WorksPanel(elementLeftRightSidePanel, clearCenter(panelAdd));
        });
        but.getButtonFrontDoorway().addActionListener(e -> {
            logManager.log("Нажата кнопка Передний проем двери.");
            clearPushedButtonAfterElementAdd();
            elementButtonPushed = changeColorPushedButton(elementButtonPushed, but.getButtonFrontDoorway(), 2);
            WorksPanel(elementLeftRightSidePanel, clearCenter(panelAdd));
        });
        but.getButtonBackDoorway().addActionListener(e -> {
            logManager.log("Нажата кнопка Задний проем двери.");
            clearPushedButtonAfterElementAdd();
            elementButtonPushed = changeColorPushedButton(elementButtonPushed, but.getButtonBackDoorway(), 2);
            WorksPanel(elementLeftRightSidePanel, clearCenter(panelAdd));
        });
    } // Панель с элементами Левая\Правая часть авто

    private void centerSidePanel(JPanel panelAdd) {
        logManager.log("Запущен метод centerSidePanel.");
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
            logManager.log("Нажата кнопка Передний бампер.");
            clearPushedButtonAfterElementAdd();
            elementButtonPushed = changeColorPushedButton(elementButtonPushed, but.getFrontBumperButton(), 2);
            WorksPanel(elementCenterSide, clearCenter(panelAdd));
        });
        but.getBonnetButton().addActionListener(e -> {
            logManager.log("Нажата кнопка Капот.");
            clearPushedButtonAfterElementAdd();
            elementButtonPushed = changeColorPushedButton(elementButtonPushed, but.getBonnetButton(), 2);
            WorksPanel(elementCenterSide, clearCenter(panelAdd));
        });
        but.getRoofButton().addActionListener(e -> {
            logManager.log("Нажата кнопка Крыша.");
            clearPushedButtonAfterElementAdd();
            elementButtonPushed = changeColorPushedButton(elementButtonPushed, but.getRoofButton(), 2);
            WorksPanel(elementCenterSide, clearCenter(panelAdd));
        });
        but.getTrunkLidButton().addActionListener(e -> {
            logManager.log("Нажата кнопка Крышка багажника.");
            clearPushedButtonAfterElementAdd();
            elementButtonPushed = changeColorPushedButton(elementButtonPushed, but.getTrunkLidButton(), 2);
            WorksPanel(elementCenterSide, clearCenter(panelAdd));
        });
        but.getRearBumperButton().addActionListener(e -> {
            logManager.log("Нажата кнопка Задний бампер.");
            clearPushedButtonAfterElementAdd();
            elementButtonPushed = changeColorPushedButton(elementButtonPushed, but.getRearBumperButton(), 2);
            WorksPanel(elementCenterSide, clearCenter(panelAdd));
        });
        but.getEngineCompartmentButton().addActionListener(e -> {
            logManager.log("Нажата кнопка Моторный отсек.");
            clearPushedButtonAfterElementAdd();
            elementButtonPushed = changeColorPushedButton(elementButtonPushed, but.getEngineCompartmentButton(), 2);
            WorksPanel(elementCenterSide, clearCenter(panelAdd));
        });
        but.getBackPanelButton().addActionListener(e -> {
            logManager.log("Нажата кнопка Задняя панель.");
            clearPushedButtonAfterElementAdd();
            elementButtonPushed = changeColorPushedButton(elementButtonPushed, but.getBackPanelButton(), 2);
            WorksPanel(elementCenterSide, clearCenter(panelAdd));
        });
    } // Панель с элементами Центральная часть авто

    private void glassSidePanel(JPanel panelAdd) {
        logManager.log("Запущен метод glassSidePanel.");
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
            logManager.log("Нажата кнопка Лобовое.");
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
            logManager.log("Нажата кнопка Заднее.");
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
        logManager.log("Запущен метод polirovkaPanel.");
        polirovkaPanel = setPanelBackground(polirovkaPanel, imagePath);
        JPanel poli = new JPanel(new GridBagLayout());
        poli.setPreferredSize(new Dimension(300, 200));
        JPanel xyzPanel = new JPanel(new GridBagLayout());
        xyzPanel.setOpaque(false);
        xyzPanel.setPreferredSize(new Dimension(300, 200));
        polirovkaPanel.add(xyzPanel);

        poli.add(polirovkaPanel, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.NONE,
                new Insets(2, 2, 1, 2), 300, 200));
        panelAdd.add(poli, BorderLayout.WEST);

        // Позиции чекбоксов
        GridBagConstraints[] constraints = {
                // Пер бампер
                new GridBagConstraints(4, 0, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0),
                // Пер лев крыло
                new GridBagConstraints(1, 2, 1, 1, 1, 1, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 35, 0, 2), 0, 0),
                // Пер прав крыло
                new GridBagConstraints(7, 2, 1, 1, 1, 1, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 2, 0, 35), 0, 0),
                // Капот
                new GridBagConstraints(4, 2, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(2, 2, 50, 2), 0, 0),
                // Пер лев дверь
                new GridBagConstraints(1, 3, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 35, 2, 2), 0, 0),
                // Пер прав дверь
                new GridBagConstraints(7, 3, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(2, 2, 2, 35), 0, 0),
                // Зад лев дверь
                new GridBagConstraints(1, 5, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 35, 50, 2), 0, 0),
                // Зад прав дверь
                new GridBagConstraints(7, 5, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 2, 50, 35), 0, 0),
                // Левый брус крыши
                new GridBagConstraints(3, 4, 1, 1, 1, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 20), 0, 0),
                // Правый брус крыши
                new GridBagConstraints(5, 4, 1, 1, 1, 1, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(2, 20, 2, 2), 0, 0),
                // Крыша
                new GridBagConstraints(4, 4, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0),
                // Заднее левое крыло
                new GridBagConstraints(1, 6, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(2, 50, 30, 2), 0, 0),
                // Заднее правое крыло
                new GridBagConstraints(7, 6, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(2, 2, 30, 50), 0, 0),
                // Крышка багажника
                new GridBagConstraints(4, 6, 1, 1, 1, 1, GridBagConstraints.SOUTH, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0),
                // Задний бампер
                new GridBagConstraints(4, 8, 1, 1, 1, 1, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0),
                // Фонарь левый
                new GridBagConstraints(2, 7, 1, 1, 1, 1, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0),
                // Фонарь правый
                new GridBagConstraints(6, 7, 1, 1, 1, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0),
                // Фара левая
                new GridBagConstraints(2, 1, 1, 1, 1, 1, GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0),
                // Фара правая
                new GridBagConstraints(6, 1, 1, 1, 1, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(2, 2, 2, 2), 0, 0),
                // Зеркало левое
                new GridBagConstraints(2, 3, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 2, 50, 2), 0, 0),
                // Зеркало правое
                new GridBagConstraints(6, 3, 1, 1, 1, 1, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 2, 40, 2), 0, 0)
        };

        // Добавление чекбоксов в панель
        for (int i = 0; i < checkBoxes.length; i++) {
            xyzPanel.add(checkBoxes[i], constraints[i]);
        }
        addAndRemovePanel(clearCenter(panelAdd));
        panelAdd.updateUI();
    } // Панель полировки

    public void WorksPanel(JPanel elementLeftRightSidePanel, JPanel panelAdd) {
        logManager.log("Запущен метод WorksPanel.");
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
        // Добавляем панель с кнопками увеличения уменьшения норматива работ. Для арматурщика и маляра
        elementLeftRightSidePanel.add(correctionPanelArmatureSide, new GridBagConstraints(3, 1, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(2, 0, 2, 2), 0, 0));
        elementLeftRightSidePanel.add(correctionPanelPaintSide, new GridBagConstraints(3, 2, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(2, 0, 2, 2), 0, 0));

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
        gridYForDopWorksDescription = gridY;
        elementLeftRightSidePanel.add(new JLabel("Доп работы"), new GridBagConstraints(1, gridY + 1, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(2, 5, 2, 0), 0, 0));
        // Кнопки доп.работ
        elementLeftRightSidePanel.add(takeColorOfButtons(removeActionListener(but.getDopWorksArmaturchikButton()), 3), new GridBagConstraints(1, gridY + 2, 1, 1, 0.7, 1,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(2, 5, 2, 2), 0, 0));
        elementLeftRightSidePanel.add(takeColorOfButtons(removeActionListener(but.getDopWorksPainterButton()), 3), new GridBagConstraints(1, gridY + 3, 1, 1, 0.7, 1,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(2, 5, 2, 0), 0, 0));
        elementLeftRightSidePanel.add(takeColorOfButtons(removeActionListener(but.getDopWorksKuzovchikButton()), 3), new GridBagConstraints(1, gridY + 4, 1, 1, 0.7, 1,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(2, 5, 2, 0), 0, 0));
        // Текстовое поля ввода норматива доп. работ Правее их кнопок
        elementLeftRightSidePanel.add(addDocumentListener(dopWorksArmaturchik), new GridBagConstraints(2, gridY + 2, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        elementLeftRightSidePanel.add(addDocumentListener(dopWorksPainter), new GridBagConstraints(2, gridY + 3, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        elementLeftRightSidePanel.add(addDocumentListener(dopWorksKuzovchik), new GridBagConstraints(2, gridY + 4, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));

        but.getDopWorksArmaturchikButton().setPreferredSize(new Dimension(75, 18));
        but.getDopWorksPainterButton().setPreferredSize(new Dimension(75, 18));
        but.getDopWorksKuzovchikButton().setPreferredSize(new Dimension(75, 18));

        //  Граффы ввода текста, описания доп работы по механикам !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        elementLeftRightSidePanel.add(takeColorOfButtons(removeActionListener(addGearIconToButton(but.getDopWorksArmaturchikDescriptionButton())), 3), new GridBagConstraints(1, gridY + 2, 1, 1, 0.3, 1,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(2, 2, 2, 0), 0, 0));
        elementLeftRightSidePanel.add(takeColorOfButtons(removeActionListener(addGearIconToButton(but.getDopWorksPainterDescriptionButton())), 3), new GridBagConstraints(1, gridY + 3, 1, 1, 0.3, 1,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(2, 2, 2, 0), 0, 0));
        elementLeftRightSidePanel.add(takeColorOfButtons(removeActionListener(addGearIconToButton(but.getDopWorksKuzovchikDescriptionButton())), 3), new GridBagConstraints(1, gridY + 4, 1, 1, 0.3, 1,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(2, 2, 2, 0), 0, 0));

        remont.setText(elementRemontString);

        but.getReplaceButton().addActionListener(e -> { // замена
            logManager.log("Нажата кнопка замена.");
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
                logManager.log("Установлено значение для elementKuzDetReplaceSide = 8");
            } else if (elementButtonPushed.getText().equals("Крыша")) {
                // При замене приварной крыше идет окраска с 1 стороны +30% от кузовщика и надбавка к арматуре.
                but.getPaint1xButton().doClick();
                elementArmatureSide = 4;
                elementKuzDetReplaceSide = 12.0;
                logManager.log("Установлено значение для elementKuzDetReplaceSide = 12");
            } else {
                // Во всех остальных случаях окраска с 1 стороны и обычный норматив арматурщику.
                but.getPaint1xButton().doClick();
                elementArmatureSide = 2.0;
            }
            correctionPanelArmatureSide.updateValue(elementArmatureSide);
            logManager.log("Установлено значение для elementArmatureSide = " + elementArmatureSide);
            addAndRemovePanel(clearCenter(panelAdd));
            panelAdd.updateUI();
        }); // замена
        but.getDisassembleButton().addActionListener(e -> { // Р\С
            logManager.log("Нажата кнопка Р\\С.");
            zamenaOrRsButtonPushed = but.getDisassembleButton();
            but.getReplaceButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
            but.getDisassembleButton().setBorder(BorderFactory.createLineBorder(Color.red, 1));
            elementKuzDetReplaceSide = 0.0;
            logManager.log("Установлено значение для elementKuzDetReplaceSide = 0");
            but.getPaint1xButton().doClick();
            elementArmatureSide = 2.0;
            correctionPanelArmatureSide.updateValue(elementArmatureSide);
            logManager.log("Установлено значение для elementArmatureSide = " + elementArmatureSide);
            addAndRemovePanel(clearCenter(panelAdd));
            panelAdd.updateUI();
        }); // р\с
        but.getPaint1xButton().addActionListener(e -> { // окраска 1х
            logManager.log("Нажата кнопка окраска 1х.");
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
                        logManager.log("Установлено значение для elementPaintSide = 2 + 2.4");
                        elementPaintSide = 2 + 2.4;
                    } else {
                        // При окраске без замены
                        logManager.log("Установлено значение для elementPaintSide = 2");
                        elementPaintSide = 2.0;
                    }
                } else {
                    // А при замене крыла
                    if (zamenaOrRsButtonPushed.equals(but.getReplaceButton())) {
                        logManager.log("Установлено значение для elementPaintSide = 3 + 2.4");
                        elementPaintSide = 3 + 2.4;
                    } else {
                        // При окраске без замены крыла
                        logManager.log("Установлено значение для elementPaintSide = 3");
                        elementPaintSide = 3.0;
                    }
                }
            } else if (elementButtonPushed.getText().equals("Крыша")) {
                if (zamenaOrRsButtonPushed.equals(but.getReplaceButton())) {
                    // При замене крыши норматив окраски 6н\ч + 1н\ч от кузовщика за герметизацию швов
                    logManager.log("Установлено значение для elementPaintSide = 6.0 + 1");
                    elementPaintSide = 6.0 + 1;
                } else {
                    // При окраске без замены
                    logManager.log("Установлено значение для elementPaintSide = 6.0");
                    elementPaintSide = 6.0;
                }
            } else if (elementButtonPushed.getText().equals("Капот")
                    || elementButtonPushed.getText().equals("Крышка баг.")) {
                // если окраска с 1х капота или крышки багажника, то норматив 4.5.
                logManager.log("Установлено значение для elementPaintSide = 4.5");
                elementPaintSide = 4.5;
            } else {
                // Во всех остальных элементах окраска просто 3н\ч
                logManager.log("Установлено значение для elementPaintSide = 3");
                elementPaintSide = 3.0;
            }
            correctionPanelPaintSide.updateValue(elementPaintSide);
            logManager.log("Установлено значение для elementPaintSide = " + elementPaintSide);
            addAndRemovePanel(clearCenter(panelAdd));
            panelAdd.updateUI();
        }); // окраска 1х
        but.getPaint2xButton().addActionListener(e -> { // окраска 2х
            logManager.log("Нажата кнопка 2х.");
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
                logManager.log("Установлено значение для elementPaintSide = 9.0");
                elementPaintSide = 9.0;
            } else if (elementButtonPushed.getText().equals("Брус")
                    || elementButtonPushed.getText().equals("Порог")) {
                // если окраска с 2х бруса или порога (хотя это и крайне невозможно), то норматив 2х1.5= 3.
                logManager.log("Установлено значение для elementPaintSide = 3");
                elementPaintSide = 3.0;
            } else if (elementButtonPushed.getText().equals("Капот")
                    || elementButtonPushed.getText().equals("Крышка баг.")) {
                // если окраска с 2х капота или крышки багажника, то норматив 6.
                logManager.log("Установлено значение для elementPaintSide = 6");
                elementPaintSide = 6.0;
            } else {
                // При всех остальных элементах норматив 4.5
                logManager.log("Установлено значение для elementPaintSide = 4.5");
                elementPaintSide = 4.5;
            }
            correctionPanelPaintSide.updateValue(elementPaintSide);
            addAndRemovePanel(clearCenter(panelAdd));
            panelAdd.updateUI();
        }); // окраска 2х
        but.getRepairButton().addActionListener(e -> { // ремонт
            logManager.log("Нажата кнопка ремонт.");
            // проверяем добелена ли окраска и разборка.
            if (remontButtonPushed == null) {
                if (zamenaOrRsButtonPushed != null && paint1xOr2xButtonPushed != null) {
                    if (remont.getBackground() != Color.pink && remont.getText().length() > 0) {
                        // добавляем перед последним символом точку переведя число в двоичное.
                        remontButtonPushed = but.getRepairButton();
                        elementRemontString = remont.getText();
                        logManager.log("Значение ремонт = " + elementRemontString);
                        remontButtonPushed.setBorder(BorderFactory.createLineBorder(Color.red, 1));
                        addAndRemovePanel(clearCenter(panelAdd));
                        panelAdd.updateUI();
                    }
                }
            } else {
                logManager.log("Нажата кнопка ремонт повторно.");
                // Если ремонт уже добавлен, а мы хотим ее убрать необходимо повторно нажать на кнопку
                LineBorder color = (LineBorder) remontButtonPushed.getBorder();
                // Если кнопка уже была нажата и элемент был добавлен (то есть имеет цвет рамки зеленый) мы убираем нормативы
                if (color.getLineColor() == (Color.green)) {
                    elementRemontString = null;
                    // Устанавливаем цвет кнопки на серый тем самым убрав ее из элемента
                    but.getRepairButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
                    remontButtonPushed = null;
                    addAndRemovePanel(clearCenter(panelAdd));
                    panelAdd.updateUI();
                } else {
                    // Если кнопка была нажата, но элемент не был добавлен (то есть имеет цвет рамки красный) мы убираем нормативы
                    elementRemontString = null;
                    // Устанавливаем цвет кнопки на серый тем самым убрав ее из элемента
                    but.getRepairButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
                    remontButtonPushed = null;
                    addAndRemovePanel(clearCenter(panelAdd));
                    panelAdd.updateUI();
                }
            }
        });  // ремонт
        but.getRuchkaButton().addActionListener(e -> {       // ручка
            logManager.log("Нажата кнопка ручка.");
            // Если окраска только ручки выставляем 0.6 арматура
            if (ruchkaButtonPushed == null) {
                if (paint1xOr2xButtonPushed == null) {
                    if (elementArmatureSide > 0.1) {
                        elementArmatureSide = elementArmatureSide + 0.6;
                        logManager.log("Установлено значение для elementArmatureSide = elementArmatureSide + 0.6");
                    } else {
                        elementArmatureSide = 0.6;
                        logManager.log("Установлено значение для elementArmatureSide = 0.6");
                    }
                }
                addAndRemovePanel(clearCenter(panelAdd));
                panelAdd.updateUI();
                ruchkaButtonPushed = but.getRuchkaButton();
                but.getRuchkaButton().setBorder(BorderFactory.createLineBorder(Color.red, 1));
            } else {
                logManager.log("Нажата кнопка ручка повторно.Норматив стерт");
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
            logManager.log("Нажата кнопка молдинг.");
            // Если окраска только молдинга выставляем 0.6 арматура
            if (moldingButtonPushed == null) {
                if (paint1xOr2xButtonPushed == null) {
                    if (elementArmatureSide > 0.1) {
                        elementArmatureSide = elementArmatureSide + 0.6;
                        logManager.log("Установлено значение для elementArmatureSide = elementArmatureSide + 0.6");
                    } else {
                        elementArmatureSide = 0.6;
                        logManager.log("Установлено значение для elementArmatureSide =  0.6");
                    }
                }
                addAndRemovePanel(clearCenter(panelAdd));
                panelAdd.updateUI();
                moldingButtonPushed = but.getMoldingButton();
                but.getMoldingButton().setBorder(BorderFactory.createLineBorder(Color.red, 1));
            } else {
                logManager.log("Нажата кнопка молдинг повторно.Норматив стерт");
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
            logManager.log("Нажата кнопка зеркало.");
            // Если окраска только зеркала выставляем 0.6 арматура
            if (zercaloButtonPushed == null) {
                if (paint1xOr2xButtonPushed == null) {
                    if (elementArmatureSide > 0.1) {
                        elementArmatureSide = elementArmatureSide + 0.6;
                        logManager.log("Установлено значение для elementArmatureSide = elementArmatureSide + 0.6");
                    } else {
                        elementArmatureSide = 0.6;
                        logManager.log("Установлено значение для elementArmatureSide = 0.6");
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
                    logManager.log("Нажата кнопка зеркало повторно.Норматив стерт");
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
            logManager.log("Нажата кнопка Остекление Лобовое.");
            if (!sideButtonPushed.getText().equals("Остекление")) {
                if (glassButtonPushed == null || (haveGlass == 1 && !glassButtonPushed.getText().equals(but.getButtonWindshield().getText()))) {
                    haveGlass++;
                    glassButtonPushed = but.getButtonWindshield();
                    but.getButtonWindshield().setBorder(BorderFactory.createLineBorder(Color.red, 1));
                } else {
                    logManager.log("Нажата кнопка Остекление Лобовое повторно.Норматив стерт");
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
            logManager.log("Нажата кнопка Остекление Заднее.");
            if (!sideButtonPushed.getText().equals("Остекление")) {
                if (glassButtonPushed == null || (haveGlass == 1 && !glassButtonPushed.getText().equals(but.getButtonRearWindow().getText()))) {
                    haveGlass++;
                    glassButtonPushed = but.getButtonRearWindow();
                    but.getButtonRearWindow().setBorder(BorderFactory.createLineBorder(Color.red, 1));
                } else {
                    logManager.log("Нажата кнопка Остекление Заднее повторно.Норматив стерт");
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
            logManager.log("Нажата кнопка расширителя.");
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
                logManager.log("Нажата кнопка расширителя повторно.Норматив стерт");
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
            logManager.log("Нажата кнопка накладка.");
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
                logManager.log("Нажата кнопка накладка повторно.Норматив стерт");
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
                    logManager.log("Нажата кнопка доп работы Арматурщик. До этого не была прожата!");
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
                    dopWorksArmaturchikDescription.setText("");
                    inputDopWorksArmaturchikDescription = "";
                    but.getDopWorksArmaturchikDescriptionButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
                    dopWorksArmaturchikDescriptionPushed = null;
                    panelAdd.updateUI();
                    logManager.log("Нажата кнопка доп работы Арматурщик.\nДо этого была прожата и элемент был добавлен!\nНормативы, описание стерты и прожатие стерто!");
                } else {
                    // Если кнопка была нажата, но элемент не был добавлен (то есть имеет цвет рамки красный) мы убираем нормативы
                    dopWorksArmaturchik.setText("");
                    // Устанавливаем цвет кнопки на серый тем самым убрав ее из элемента
                    but.getDopWorksArmaturchikButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
                    dopWorksArmaturchikButtonPushed = null;
                    dopWorksArmaturchikDescription.setText("");
                    inputDopWorksArmaturchikDescription = "";
                    but.getDopWorksArmaturchikDescriptionButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
                    dopWorksArmaturchikDescriptionPushed = null;
                    addAndRemovePanel(clearCenter(panelAdd));
                    panelAdd.updateUI();
                    logManager.log("Нажата кнопка доп работы Арматурщик.\nДо этого была прожата но элемент не был добавлен!\nНормативы, описание стерты и прожатие стерто!");
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
                    logManager.log("Нажата кнопка доп работы Маляр.\nДо этого не была прожата!");
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
                    dopWorksPainterDescription.setText("");
                    inputDopWorksPainterDescription = "";
                    but.getDopWorksPainterDescriptionButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
                    dopWorksPainterDescriptionPushed = null;
                    panelAdd.updateUI();
                    logManager.log("Нажата кнопка доп работы Маляр.\nДо этого была прожата и элемент был добавлен!\nНормативы, описание стерты и прожатие стерто!");
                } else {
                    // Если кнопка была нажата, но элемент не был добавлен (то есть имеет цвет рамки красный) мы убираем нормативы
                    dopWorksPainter.setText("");
                    // Устанавливаем цвет кнопки на серый тем самым убрав ее из элемента
                    but.getDopWorksPainterButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
                    dopWorksPainterButtonPushed = null;
                    dopWorksPainterDescription.setText("");
                    inputDopWorksPainterDescription = "";
                    but.getDopWorksPainterDescriptionButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
                    dopWorksPainterDescriptionPushed = null;
                    addAndRemovePanel(clearCenter(panelAdd));
                    panelAdd.updateUI();
                    logManager.log("Нажата кнопка доп работы Маляр.\nДо этого была прожата но элемент не был добавлен!\nНормативы, описание стерты и прожатие стерто!");
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
                    logManager.log("Нажата кнопка доп работы Кузовщик.\nДо этого не была прожата!");
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
                    dopWorksKuzovchikDescription.setText("");
                    inputDopWorksKuzovchikDescription = "";
                    but.getDopWorksKuzovchikDescriptionButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
                    dopWorksKuzovchikDescriptionPushed = null;
                    panelAdd.updateUI();
                    logManager.log("Нажата кнопка доп работы Кузовщик.\nДо этого была прожата и элемент был добавлен!\nНормативы, описание стерты и прожатие стерто!");
                } else {
                    // Если кнопка была нажата, но элемент не был добавлен (то есть имеет цвет рамки красный) мы убираем нормативы
                    dopWorksKuzovchik.setText("");
                    // Устанавливаем цвет кнопки на серый тем самым убрав ее из элемента
                    but.getDopWorksKuzovchikButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
                    dopWorksKuzovchikButtonPushed = null;
                    dopWorksKuzovchikDescription.setText("");
                    inputDopWorksKuzovchikDescription = "";
                    but.getDopWorksKuzovchikDescriptionButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
                    dopWorksKuzovchikDescriptionPushed = null;
                    addAndRemovePanel(clearCenter(panelAdd));
                    panelAdd.updateUI();
                    logManager.log("Нажата кнопка доп работы Кузовщик.\nДо этого была прожата но элемент не был добавлен!\nНормативы, описание стерты и прожатие стерто!");
                }
            }
        }); // Доп работы кузовщик

        but.getDopWorksArmaturchikDescriptionButton().addActionListener(e -> {
            if (dopWorksArmaturchikButtonPushed != null) {
                logManager.log("Нажата кнопка описания доп.работ Арматурщик");
                // Запуск задачи проверки грамматики в отдельном потоке
                executor.submit(() -> {
                    // Проверка грамматики
                    CustomDocumentHandler handler1 = new CustomDocumentHandler(dopWorksArmaturchikDescription, 100);
                    ((AbstractDocument) dopWorksArmaturchikDescription.getDocument()).setDocumentFilter(handler1);
                    dopWorksArmaturchikDescription.getDocument().addDocumentListener(handler1);
                });
                // Создаем модальное окно
                JDialog dialog = new JDialog(frame, "Описание доп.работ", true); // true - модальное окно
                dialog.setSize(300, 300);
                dialog.setLayout(new BorderLayout());
                logManager.log("Создана и открыто окно ввода текста описания доп.работ Арматурщик");
                JPanel textPanel = new JPanel();
                textPanel.setLayout(new BorderLayout());

                dopWorksArmaturchikDescription.setWrapStyleWord(true); // Включить перенос по словам
                dopWorksArmaturchikDescription.setLineWrap(true); // Перенос строк
                // Если описание есть в памяти, то его и прописываем
                if (inputDopWorksArmaturchikDescription != null) {
                    dopWorksArmaturchikDescription.setText(inputDopWorksArmaturchikDescription);
                    logManager.log("Произведена проверка текста описания доп.работ Арматурщик из памяти: он уже вводился и был сохранен в Map работ.");
                } else {
                    // Если же не было отправляем пустой текст
                    dopWorksArmaturchikDescription.setText("");
                    logManager.log("Произведена проверка текста описания доп.работ Арматурщик из памяти: в памяти нет ранее введенного текст, отправлен пустой символ.");
                }

                textPanel.add(dopWorksArmaturchikDescription, BorderLayout.CENTER);
                textPanel.setPreferredSize(new Dimension(200, 220));
                dialog.add(textPanel, BorderLayout.NORTH);

                // Панель для кнопок
                JPanel buttonPanel = new JPanel();
                buttonPanel.setLayout(new FlowLayout());

                JButton okButton = new JButton("OK");
                okButton.addActionListener(e1 -> {
                    dopWorksArmaturchikDescriptionPushed = but.getDopWorksArmaturchikDescriptionButton();
                    but.getDopWorksArmaturchikDescriptionButton().setBorder(BorderFactory.createLineBorder(Color.red, 1));
                    inputDopWorksArmaturchikDescription = dopWorksArmaturchikDescription.getText();
                    logManager.log("Описание доп.работ Арматурщик выбрана кнопка ОК. Текст: " + dopWorksArmaturchikDescription.getText());
                    dialog.dispose(); // Закрыть диалоговое окно
                }); // Кнопка "OK"

                JButton cancelButton = new JButton("Отмена");
                cancelButton.addActionListener(e1 -> {
                    dopWorksArmaturchikDescription.setText("");
                    inputDopWorksArmaturchikDescription = "";
                    but.getDopWorksArmaturchikDescriptionButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
                    dopWorksArmaturchikDescriptionPushed = null;
                    logManager.log("Описание доп.работ Арматурщик выбрана кнопка Отменена");
                    dialog.dispose(); // Закрыть диалоговое окно
                }); // Кнопка "Отмена"
                buttonPanel.add(okButton);
                buttonPanel.add(cancelButton);

                dialog.add(buttonPanel, BorderLayout.SOUTH);
                dialog.setLocationRelativeTo(frame); // Расположить относительно основного окна
                dialog.setVisible(true); // Показываем диалог
            }
        }); // Кнопка ввода описания доп.работ Арматурщик
        but.getDopWorksPainterDescriptionButton().addActionListener(e -> {
            if (dopWorksPainterButtonPushed != null) {
                logManager.log("Нажата кнопка описания доп.работ Маляр");
                // Запуск задачи проверки грамматики в отдельном потоке
                executor.submit(() -> {
                    // Проверка грамматики
                    CustomDocumentHandler handler2 = new CustomDocumentHandler(dopWorksPainterDescription, 100);
                    ((AbstractDocument) dopWorksPainterDescription.getDocument()).setDocumentFilter(handler2);
                    dopWorksPainterDescription.getDocument().addDocumentListener(handler2);
                });
                // Создаем модальное окно
                JDialog dialog = new JDialog(frame, "Описание доп.работ", true); // true - модальное окно
                dialog.setSize(300, 300);
                dialog.setLayout(new BorderLayout());

                JPanel textPanel = new JPanel();
                textPanel.setLayout(new BorderLayout());
                logManager.log("Создана и открыто окно ввода текста описания доп.работ Маляр");

                dopWorksPainterDescription.setWrapStyleWord(true); // Включить перенос по словам
                dopWorksPainterDescription.setLineWrap(true); // Перенос строк
                // Если описание есть в памяти, то его и прописываем
                if (inputDopWorksPainterDescription != null) {
                    dopWorksPainterDescription.setText(inputDopWorksPainterDescription);
                    logManager.log("Произведена проверка текста описания доп.работ Маляр из памяти: он уже вводился и был сохранен в Map работ.");
                } else {
                    // Если же не было отправляем пустой текст
                    dopWorksPainterDescription.setText("");
                    logManager.log("Произведена проверка текста описания доп.работ Маляр из памяти: в памяти нет ранее введенного текст, отправлен пустой символ.");
                }

                textPanel.add(dopWorksPainterDescription, BorderLayout.CENTER);
                textPanel.setPreferredSize(new Dimension(200, 220));
                dialog.add(textPanel, BorderLayout.NORTH);

                // Панель для кнопок
                JPanel buttonPanel = new JPanel();
                buttonPanel.setLayout(new FlowLayout());

                JButton okButton = new JButton("OK");
                okButton.addActionListener(e1 -> {
                    dopWorksPainterDescriptionPushed = but.getDopWorksPainterDescriptionButton();
                    but.getDopWorksPainterDescriptionButton().setBorder(BorderFactory.createLineBorder(Color.red, 1));
                    inputDopWorksPainterDescription = dopWorksPainterDescription.getText();
                    logManager.log("Описание доп.работ Маляр выбрана кнопка ОК. Текст: " + dopWorksPainterDescription.getText());
                    dialog.dispose(); // Закрыть диалоговое окно
                }); // Кнопка "OK"

                JButton cancelButton = new JButton("Отмена");
                cancelButton.addActionListener(e1 -> {
                    dopWorksPainterDescription.setText("");
                    inputDopWorksPainterDescription = "";
                    but.getDopWorksPainterDescriptionButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
                    dopWorksPainterDescriptionPushed = null;
                    logManager.log("Описание доп.работ Маляр выбрана кнопка Отменена");
                    dialog.dispose(); // Закрыть диалоговое окно
                }); // Кнопка "Отмена"

                buttonPanel.add(okButton);
                buttonPanel.add(cancelButton);

                dialog.add(buttonPanel, BorderLayout.SOUTH);
                dialog.setLocationRelativeTo(frame); // Расположить относительно основного окна
                dialog.setVisible(true); // Показываем диалог
            }
        }); // Кнопка ввода описания доп.работ Маляр
        but.getDopWorksKuzovchikDescriptionButton().addActionListener(e -> {
            if (dopWorksKuzovchikButtonPushed != null) {
                logManager.log("Нажата кнопка описания доп.работ Кузовщик");
                // Запуск задачи проверки грамматики в отдельном потоке
                executor.submit(() -> {
                    // Проверка грамматики
                    CustomDocumentHandler handler3 = new CustomDocumentHandler(dopWorksKuzovchikDescription, 100);
                    ((AbstractDocument) dopWorksKuzovchikDescription.getDocument()).setDocumentFilter(handler3);
                    dopWorksKuzovchikDescription.getDocument().addDocumentListener(handler3);
                });
                // Создаем модальное окно
                JDialog dialog = new JDialog(frame, "Описание доп.работ", true); // true - модальное окно
                dialog.setSize(300, 300);
                dialog.setLayout(new BorderLayout());

                JPanel textPanel = new JPanel();
                textPanel.setLayout(new BorderLayout());
                logManager.log("Создана и открыто окно ввода текста описания доп.работ Кузовщик");

                dopWorksKuzovchikDescription.setWrapStyleWord(true); // Включить перенос по словам
                dopWorksKuzovchikDescription.setLineWrap(true); // Перенос строк
                // Если описание есть в памяти, то его и прописываем
                if (inputDopWorksKuzovchikDescription != null) {
                    dopWorksKuzovchikDescription.setText(inputDopWorksKuzovchikDescription);
                    logManager.log("Произведена проверка текста описания доп.работ Кузовщик из памяти: он уже вводился и был сохранен в Map работ.");
                } else {
                    // Если же не было отправляем пустой текст
                    dopWorksKuzovchikDescription.setText("");
                    logManager.log("Произведена проверка текста описания доп.работ Кузовщик из памяти: в памяти нет ранее введенного текст, отправлен пустой символ.");
                }
                textPanel.add(dopWorksKuzovchikDescription, BorderLayout.CENTER);
                textPanel.setPreferredSize(new Dimension(200, 220));
                dialog.add(textPanel, BorderLayout.NORTH);

                // Панель для кнопок
                JPanel buttonPanel = new JPanel();
                buttonPanel.setLayout(new FlowLayout());

                JButton okButton = new JButton("OK");
                okButton.addActionListener(e1 -> {
                    dopWorksKuzovchikDescriptionPushed = but.getDopWorksKuzovchikDescriptionButton();
                    but.getDopWorksKuzovchikDescriptionButton().setBorder(BorderFactory.createLineBorder(Color.red, 1));
                    inputDopWorksKuzovchikDescription = dopWorksKuzovchikDescription.getText();
                    logManager.log("Описание доп.работ Кузовщик выбрана кнопка ОК. Текст: " + dopWorksKuzovchikDescription.getText());
                    dialog.dispose(); // Закрыть диалоговое окно
                }); // Кнопка "OK"

                JButton cancelButton = new JButton("Отмена");
                cancelButton.addActionListener(e1 -> {
                    dopWorksKuzovchikDescription.setText("");
                    inputDopWorksKuzovchikDescription = "";
                    but.getDopWorksKuzovchikDescriptionButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
                    dopWorksKuzovchikDescriptionPushed = null;
                    logManager.log("Описание доп.работ Кузовщик выбрана кнопка Отменена");
                    dialog.dispose(); // Закрыть диалоговое окно
                }); // Кнопка "Отмена"

                buttonPanel.add(okButton);
                buttonPanel.add(cancelButton);

                dialog.add(buttonPanel, BorderLayout.SOUTH);
                dialog.setLocationRelativeTo(frame); // Расположить относительно основного окна
                dialog.setVisible(true); // Показываем диалог
            }
        }); // Кнопка ввода описания доп.работ Кузовщик


        checkEarlyPushedButtonsWorksPanel();
        correctionPanelArmatureSide.updateValue(elementArmatureSide);
        correctionPanelPaintSide.updateValue(elementPaintSide);
        panelAdd.updateUI();
    } // Панель с работами средней, левой и правой стороны авто

    private void choiseNumberNotNormWorkPanel(JPanel panelAdd) {
        logManager.log("Запущен метод choiseNumberNotNormWorkPanel.");
        JPanel panelXY = new JPanel();
        JPanel panelXY2 = new JPanel();
        JPanel panelNumber123 = new JPanel();
        JPanel panelNumber456 = new JPanel();
        JPanel panelNumber789 = new JPanel();

        panelNotNormWork.setLayout(new GridBagLayout());
        panelXY.setLayout(new BorderLayout());
        panelXY2.setLayout(new BorderLayout());
        panelNumber123.setLayout(new GridBagLayout());
        panelNumber456.setLayout(new GridBagLayout());
        panelNumber789.setLayout(new GridBagLayout());

        panelNotNormWork.add(new JLabel("Выбери №работ."), new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 10));


        panelNumber123.add(addActionListonerForNumberNotNormWork(takeColorOfButtons(removeActionListener(but.getNumber1notNormWork()), 2), panelAdd), new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        panelNumber123.add(addActionListonerForNumberNotNormWork(takeColorOfButtons(removeActionListener(but.getNumber2notNormWork()), 2), panelAdd), new GridBagConstraints(1, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        panelNumber123.add(addActionListonerForNumberNotNormWork(takeColorOfButtons(removeActionListener(but.getNumber3notNormWork()), 2), panelAdd), new GridBagConstraints(2, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        panelNumber456.add(addActionListonerForNumberNotNormWork(takeColorOfButtons(removeActionListener(but.getNumber4notNormWork()), 2), panelAdd), new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        panelNumber456.add(addActionListonerForNumberNotNormWork(takeColorOfButtons(removeActionListener(but.getNumber5notNormWork()), 2), panelAdd), new GridBagConstraints(1, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        panelNumber456.add(addActionListonerForNumberNotNormWork(takeColorOfButtons(removeActionListener(but.getNumber6notNormWork()), 2), panelAdd), new GridBagConstraints(2, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        panelNumber789.add(addActionListonerForNumberNotNormWork(takeColorOfButtons(removeActionListener(but.getNumber7notNormWork()), 2), panelAdd), new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        panelNumber789.add(addActionListonerForNumberNotNormWork(takeColorOfButtons(removeActionListener(but.getNumber8notNormWork()), 2), panelAdd), new GridBagConstraints(1, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        panelNumber789.add(addActionListonerForNumberNotNormWork(takeColorOfButtons(removeActionListener(but.getNumber9notNormWork()), 2), panelAdd), new GridBagConstraints(2, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));


        panelNotNormWork.add(panelNumber123, new GridBagConstraints(0, 1, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 2, 0, 2), 0, 0));
        panelNotNormWork.add(panelNumber456, new GridBagConstraints(0, 2, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 2, 0, 2), 0, 0));
        panelNotNormWork.add(panelNumber789, new GridBagConstraints(0, 3, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(0, 2, 0, 2), 0, 0));


        panelXY.add(panelNotNormWork, BorderLayout.NORTH);
        panelXY2.add(panelXY, BorderLayout.WEST);
        panelAdd.add(panelXY2, BorderLayout.WEST);
        panelAdd.updateUI();
    } // Панель с выбором номера не нормативных работ

    private void addAndRemovePanel(JPanel panel) {
        logManager.log("Запущен метод addAndRemovePanel.");
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
        // Добавляем не активный выбор механика для симметрии кроме панели с полировкой и остеклением
        if (!sideButtonPushed.getText().equals("Полировка") && !"Остекление".equals(sideButtonPushed.getText())) {
            addAndRemovePanel.add(remontComboBox, new GridBagConstraints(0, 4, 1, 1, 1, 1,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(2, 2, 2, 2), 0, 0));
            remontComboBox.setEnabled(false);
        }
        // в первом случае у нас всего 3 основных механика могут выполнять ремонт
        if (remontButtonPushed != null) {
            logManager.log("Добавляем JComboBox для случая если кнопка ремонт прожата.");
            // Добавляем JComboBox только если кнопка remontButtonPushed активирована
            addAndRemovePanel.add(remontComboBox, new GridBagConstraints(0, 4, 1, 1, 1, 1,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(2, 2, 2, 2), 30, 0));
            remontComboBox.setEditable(false);
            remontComboBox.setEnabled(true);
        }
        // во втором случае у нас так же 3 основных и окно для ввода 4го неучтенного механика
        if (notNormWorkDescriptionPushed != null) {
            logManager.log("Добавляем JComboBox для не нормативных работ с возможностью ввода.");
            addAndRemovePanel.add(remontComboBox, new GridBagConstraints(0, 4, 1, 1, 1, 1,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                    new Insets(2, 2, 2, 2), 30, 0));
            // Отправляем на проверку для правильного выставления механика если он есть в памяти или если нет делаем дополнительное редактируемое поле
            remontComboBox.setEnabled(true);
            checkRemontComboBox(notNormWorkHoDoString, 5);
        }
        if (!"Полировка".equals(sideButtonPushed.getText()) && !"Ненормативные".equals(sideButtonPushed.getText()) && !"Остекление".equals(sideButtonPushed.getText())) {
            // Добавляем чекбокс финальной полировки элемента
            addAndRemovePanel.add(finalPolirovkaElementCheckBox, new GridBagConstraints(0, 5, 1, 1, 1, 0.3,
                    GridBagConstraints.WEST, GridBagConstraints.NONE,
                    new Insets(0, 0, 2, 2), 0, 0));
            addAndRemovePanel.add(new JLabel("     Ф.полировка"), new GridBagConstraints(0, 5, 1, 1, 1, 0.7,
                    GridBagConstraints.EAST, GridBagConstraints.NONE,
                    new Insets(0, 2, 2, 2), 0, 0));
        }

        panelXY.add(addAndRemovePanel, BorderLayout.NORTH);
        panelXY2.add(panelXY, BorderLayout.WEST);
        panel.add(panelXY2, BorderLayout.CENTER);
        panel.updateUI();

        but.getAddButton().addActionListener(o -> {
            logManager.log("Нажата кнопка Добавить.");
            Element element = createElement();
            logManager.log("Элемент создан.");
            Element elementFinalPolirovka = null;
            if (finalPolirovkaElementCheckBox.isSelected()) {
                elementFinalPolirovka = createElementFinalPolirovka();
                logManager.log("Установленна галка Ф.Полировки, создан дополнительный элемент полировки.");
            }

            // Если список элементов чист, то
            if (elementList.isEmpty()) {
                logManager.log("elementList isEmpty.");
                // Если Элемент меняется, то к имени добавляем "замена".
                // Применяем здесь, а не в createElement() по причине метода elementList.get(i).getName().contains(element.getName())
                // Если применить в createElement() то при contains "**** ****.**** замена" getName с "**** ****.****" без замена он его не засчитает и вернет false
                elementList.add(addToNameZamenaIfZamenaButtonPushed(element));
                logManager.log("Добавляем в список элементов элемент: elementList.add(addToNameZamenaIfZamenaButtonPushed(element)).");
                if (finalPolirovkaElementCheckBox.isSelected()) {
                    elementList.add(elementFinalPolirovka);
                    logManager.log("Добавляем в список элементов элемент c Ф.Полировкой.");
                }
                // Добавляем в lineBorderColorMap сначала - сторона(ключ) -> (значение)элемент(он же ключ) -> (значение)список работ
                if (lineBorderColorMap.get(sideButtonPushed.getText()) != null) {
                    logManager.log("sideButtonPushed.getText()) не равен null.");
                    lineBorderColorMap.get(sideButtonPushed.getText()).put(elementButtonPushed.getText(), addColorOfButtonsWorks());
                    logManager.log("Добавляем в lineBorderColorMap сначала - сторона(ключ) -> (значение)элемент(он же ключ) -> (значение)список работ: lineBorderColorMap.get(sideButtonPushed.getText()).put(elementButtonPushed.getText(), addColorOfButtonsWorks()).");
                } else {
                    logManager.log("sideButtonPushed.getText()) равен null. Вторым ключом будет имя выбранного элемента через нажатую кнопку");
                    // Если не нажата кнопка полировки то
                    if (!sideButtonPushed.getText().equals("Полировка")) {
                        logManager.log("sideButtonPushed.getText()) не Полировка.");
                        // Вторым ключом будет имя выбранного элемента через нажатую кнопку
                        lineBorderColorMap.put(sideButtonPushed.getText(), new HashMap<>(Map.of(elementButtonPushed.getText(), addColorOfButtonsWorks())));
                        logManager.log("Добавляем в lineBorderColorMap сначала - сторона(ключ) -> (значение)элемент(он же ключ) -> (значение)список работ: lineBorderColorMap.put(sideButtonPushed.getText(), new HashMap<>(Map.of(elementButtonPushed.getText(), addColorOfButtonsWorks()))).");
                    } else {
                        logManager.log("sideButtonPushed.getText())  Полировка. Проверяем есть ли галочка хоть на одном checkBox");
                        // Если кнопка выбранной стороны является полировка, то проверяем есть ли галочка хоть на одном checkBox
                        if (isAnyCheckBoxSelected(checkBoxes)) {
                            logManager.log("есть галочка хоть на одном checkBox. Вторым ключом будет так же кнопка выбранной стороны, то есть Полировка");
                            // Если выбран, то вторым ключом будет так же кнопка выбранной стороны, то есть "Полировка"
                            lineBorderColorMap.put(sideButtonPushed.getText(), new HashMap<>(Map.of(sideButtonPushed.getText(), addColorOfButtonsWorks())));
                            logManager.log("Добавляем в lineBorderColorMap сначала - сторона(ключ) -> (значение)элемент(он же ключ) -> (значение)список работ: lineBorderColorMap.put(sideButtonPushed.getText(), new HashMap<>(Map.of(sideButtonPushed.getText(), addColorOfButtonsWorks()))).");
                        } else {
                            logManager.log("нет галочки хоть на одном checkBox. Следует удалить вовсе пункт о полировке");
                            // Если же ни оди checkBox ни выбран следует удалить вовсе пункт о полировке
                            lineBorderColorMap.remove(sideButtonPushed.getText());
                            logManager.log("lineBorderColorMap.remove(sideButtonPushed.getText())");
                            // Так же требуется удалить и из списка элементов упоминание о полировке
                            removeElementFromList(element.getName());
                            logManager.log("Так же требуется удалить и из списка элементов упоминание о полировке: removeElementFromList(element.getName())");
                        }
                    }
                }
                // Если же в списке элементов уже что, то есть, то
            } else {
                logManager.log("elementList не пуст. Сначала будет произведена проверка на то есть ли такой элемент в списке, если есть он удалится и заново далее добавится новый.");
                // Сначала удаляем элемент из List
                removeElementFromList(element.getName());
                // Если Элемент меняется, то к имени добавляем "замена".
                // Применяем здесь, а не в createElement() по причине метода elementList.get(i).getName().contains(element.getName())
                // Если применить в createElement() то при contains "**** ****.**** замена" getName с "**** ****.****" без замена он его не засчитает и вернет false*/
                elementList.add(addToNameZamenaIfZamenaButtonPushed(element));
                logManager.log("Добавляем в список элементов элемент: elementList.add(addToNameZamenaIfZamenaButtonPushed(element)).");
                if (finalPolirovkaElementCheckBox.isSelected()) {
                    //removeElementFromList(elementFinalPolirovka.getName());
                    elementList.add(elementFinalPolirovka);
                    logManager.log("Добавляем в список элементов элемент c Ф.Полировкой.");
                }
                if (lineBorderColorMap.get(sideButtonPushed.getText()) != null) {
                    logManager.log("sideButtonPushed.getText()) не равен null.");
                    // Если не нажата кнопка полировки то
                    if (!sideButtonPushed.getText().equals("Полировка")) {
                        logManager.log("sideButtonPushed.getText()) не Полировка.");
                        // Вторым ключом будет имя выбранного элемента через нажатую кнопку
                        lineBorderColorMap.get(sideButtonPushed.getText()).put(elementButtonPushed.getText(), addColorOfButtonsWorks());
                        logManager.log("Добавляем в lineBorderColorMap сначала - сторона(ключ) -> (значение)элемент(он же ключ) -> (значение)список работ: lineBorderColorMap.put(sideButtonPushed.getText(), new HashMap<>(Map.of(elementButtonPushed.getText(), addColorOfButtonsWorks()))).");
                    } else {
                        logManager.log("sideButtonPushed.getText())  Полировка. Проверяем есть ли галочка хоть на одном checkBox");
                        // Если кнопка выбранной стороны является полировка, то проверяем есть ли галочка хоть на одном checkBox
                        if (isAnyCheckBoxSelected(checkBoxes)) {
                            logManager.log("есть галочка хоть на одном checkBox. Вторым ключом будет так же кнопка выбранной стороны, то есть Полировка");
                            // Если выбран, то вторым ключом будет так же кнопка выбранной стороны, то есть "Полировка"
                            lineBorderColorMap.get(sideButtonPushed.getText()).put(sideButtonPushed.getText(), addColorOfButtonsWorks());
                            logManager.log("Добавляем в lineBorderColorMap сначала - сторона(ключ) -> (значение)элемент(он же ключ) -> (значение)список работ: lineBorderColorMap.put(sideButtonPushed.getText(), new HashMap<>(Map.of(sideButtonPushed.getText(), addColorOfButtonsWorks()))).");
                        } else {
                            logManager.log("нет галочки хоть на одном checkBox. Следует удалить вовсе пункт о полировке");
                            // Если же ни оди checkBox ни выбран следует удалить вовсе пункт о полировке
                            lineBorderColorMap.remove(sideButtonPushed.getText());
                            logManager.log("lineBorderColorMap.remove(sideButtonPushed.getText())");
                            // Так же требуется удалить и из списка элементов упоминание о полировке
                            removeElementFromList(element.getName());
                            logManager.log("Так же требуется удалить и из списка элементов упоминание о полировке: removeElementFromList(element.getName())");
                        }
                    }
                } else {
                    logManager.log("sideButtonPushed.getText()) равен null. Вторым ключом будет имя выбранного элемента через нажатую кнопку");
                    // Проверяем не нажата кнопка полировки.
                    if (!sideButtonPushed.getText().equals("Полировка")) {
                        logManager.log("sideButtonPushed.getText()) не Полировка.");
                        // Вторым ключом будет имя выбранного элемента через нажатую кнопку
                        lineBorderColorMap.put(sideButtonPushed.getText(), new HashMap<>(Map.of(elementButtonPushed.getText(), addColorOfButtonsWorks())));
                        logManager.log("Добавляем в lineBorderColorMap сначала - сторона(ключ) -> (значение)элемент(он же ключ) -> (значение)список работ: lineBorderColorMap.put(sideButtonPushed.getText(), new HashMap<>(Map.of(elementButtonPushed.getText(), addColorOfButtonsWorks()))).");
                    } else {
                        logManager.log("sideButtonPushed.getText())  Полировка. Проверяем есть ли галочка хоть на одном checkBox");
                        // Если кнопка выбранной стороны является полировка, то проверяем есть ли галочка хоть на одном checkBox
                        if (isAnyCheckBoxSelected(checkBoxes)) {
                            logManager.log("есть галочка хоть на одном checkBox. Вторым ключом будет так же кнопка выбранной стороны, то есть Полировка");
                            // Если выбран, то вторым ключом будет так же кнопка выбранной стороны, то есть "Полировка"
                            lineBorderColorMap.put(sideButtonPushed.getText(), new HashMap<>(Map.of(sideButtonPushed.getText(), addColorOfButtonsWorks())));
                            logManager.log("Добавляем в lineBorderColorMap сначала - сторона(ключ) -> (значение)элемент(он же ключ) -> (значение)список работ: lineBorderColorMap.put(sideButtonPushed.getText(), new HashMap<>(Map.of(sideButtonPushed.getText(), addColorOfButtonsWorks()))).");
                        } else {
                            logManager.log("нет галочки хоть на одном checkBox. Следует удалить вовсе пункт о полировке");
                            // Если же ни оди checkBox ни выбран следует удалить вовсе пункт о полировке
                            lineBorderColorMap.remove(sideButtonPushed.getText());
                            logManager.log("lineBorderColorMap.remove(sideButtonPushed.getText())");
                            // Так же требуется удалить и из списка элементов упоминание о полировке
                            removeElementFromList(element.getName());
                            logManager.log("Так же требуется удалить и из списка элементов упоминание о полировке: removeElementFromList(element.getName())");
                        }
                    }
                }
            }
            // Так как у остекления всего 2 панели то добавляем ей после добавления зеленую рамку тут!
            if (sideButtonPushed.getText().equals("Остекление")) {
                logManager.log("Элемент Остекление. Так как у остекления всего 2 панели то добавляем ей после добавления зеленую рамку");
                elementButtonPushed.setBorder(BorderFactory.createLineBorder(Color.green, 1));
                logManager.log("elementButtonPushed.setBorder(BorderFactory.createLineBorder(Color.green, 1))");
                elementButtonPushed = null;
                logManager.log("Убираем значение у elementButtonPushed = null");
            }
            // Затем обнуляем ЛКМ
            logManager.log("Обнуляем ЛКМ.");
            clearLkm();
            // Пересчитываем ЛКМ оставшихся элементов
            logManager.log("Пересчитываем ЛКМ оставшихся элементов.");
            reCalcLkmPrice();
            // Отправляем новый список на отображение в окошке.
            logManager.log("Отправляем новый список на отображение в окошке просмотра.");
            sendToStringInElementListTextViewing();
            // Убираем панель addAndRemovePanel
            clearCenter(panel).updateUI();

        });   // Добавить
        but.getRemoveButton().addActionListener(e -> {
            logManager.log("Нажата кнопка Удалить.");
            // Сначала удаляем элемент из List
            logManager.log("Удаляем элемент из List через removeElementFromList(createName()).");
            removeElementFromList(createName());
            if (finalPolirovkaElementCheckBox.isSelected()) {
                removeElementFromList(createElementFinalPolirovka().getName());
                logManager.log("Удаляем элемент c Ф.Полировкой из List ");
            }
            // Затем обнуляем ЛКМ
            logManager.log("Обнуляем ЛКМ.");
            clearLkm();
            // Пересчитываем ЛКМ оставшихся элементов
            logManager.log("Пересчитываем ЛКМ оставшихся элементов.");
            reCalcLkmPrice();
            // Отправляем новый список на отображение в окошке.
            logManager.log("Отправляем новый список на отображение в окошке просмотра.");
            sendToStringInElementListTextViewing();
            // Удаляем из Map элемент
            logManager.log("Удаляем из Map элемент.");
            if (lineBorderColorMap.get(sideButtonPushed.getText()) != null) {
                // Проверяем не нажата кнопка полировки.
                if (!sideButtonPushed.getText().equals("Полировка")) {
                    lineBorderColorMap.get(sideButtonPushed.getText()).remove(elementButtonPushed.getText());
                    // Прожимаем кнопку выбранного элемента для обновления кнопок
                    elementButtonPushed.doClick();
                } else {
                    lineBorderColorMap.remove(sideButtonPushed.getText());
                }
            }
            if (sideButtonPushed.getText().equals("Полировка")) {
                for (JCheckBox checkBox : checkBoxes) {
                    checkBox.setSelected(false);
                }
            }

            // Убираем панель addAndRemovePanel
            clearCenter(panel).updateUI();
        });// Удалить
    } // Панель добавить\удалить элемент

    private void notNormWorkAddWork(JPanel panelNotNormWork, JPanel panelAdd) {
        logManager.log("Запущен метод notNormWorkAddWork.");
        clearPushedButtonAfterElementAdd();
        // Используется для того что бы удалить new JLabel("Механик.") после повторного нажатия на номер работы.
        if (panelNotNormWork.getComponents().length > 6) {
            for (int i = panelNotNormWork.getComponents().length - 1; i >= 6; i--) {
                panelNotNormWork.remove(i);
            }
            panelNotNormWork.updateUI();
        }

        panelNotNormWork.add(new JLabel("Описание."), new GridBagConstraints(1, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        panelNotNormWork.add(addDocumentListener(notNormWorkJTextField), new GridBagConstraints(1, 1, 1, 1, 0.7, 1,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(2, 2, 2, 2), 0, 0));
        panelNotNormWork.add(takeColorOfButtons(removeActionListener(addGearIconToButton(but.getNotNormWorkDescriptionButton())), 5), new GridBagConstraints(1, 1, 1, 1, 0.3, 1,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(2, 2, 2, 2), 0, 0));


        notNormWorkJTextField.setPreferredSize(new Dimension(35, 20));
        removeActionListener(but.getNotNormWorkDescriptionButton());

        but.getNotNormWorkDescriptionButton().addActionListener(e -> {

            if (notNormWorkJTextField.getBackground() != Color.pink && notNormWorkJTextField.getText().length() > 0) {
                logManager.log("Нажата кнопка описания ненормативных работ №" + elementButtonPushed.getText());

                // Создаем модальное окно
                DescriptionWindow window = new DescriptionWindow(frame, "Описание работ", true);
                // Устанавливаем стартовый текст, если таковой существует
                if (inputNotNormWorkDescription != null) {
                    // Если описание есть в памяти, то его и прописываем
                    window.setInitialText(inputNotNormWorkDescription);
                    logManager.log("Произведена проверка текста описания ненормативных работ из памяти: он уже вводился и был сохранен в Map работ.");
                } else {
                    // Если же не было отправляем пустой текст
                    window.setInitialText("");
                    logManager.log("Произведена проверка текста описания ненормативных работ из памяти: в памяти нет ранее введенного текст, отправлен пустой символ.");
                }
                // Центрирование окна и открытие
                window.setLocationRelativeTo(frame);
                window.setVisible(true);

                // Получаем результат после закрытия окна
                if (window.isCanceled()) {
                    logManager.log("Нажата кнопка отмена");
                    inputNotNormWorkDescription = "";
                    but.getNotNormWorkDescriptionButton().setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
                    notNormWorkDescriptionPushed = null;
                } else {
                    // Если он есть, то кнопку держим в памяти
                    notNormWorkDescriptionPushed = but.getNotNormWorkDescriptionButton();
                    // Заполняем String из окна описания для дальнейшего внесения в элемент
                    inputNotNormWorkDescription = window.getInputNotNormWorkDescription();
                    // Заполняем String из окна норматива для дальнейшего перевода в double и внесения в элемент
                    notNormWorkNormativeString = notNormWorkJTextField.getText();
                    logManager.log("Нажата кнопка ОК. Текст описания: " + inputNotNormWorkDescription + " Норматив: " + notNormWorkNormativeString);
                    // Открываем окно добавить удалить элемент
                    addAndRemovePanel(clearCenter(panelAdd));
                    // меняем цвет рамки на красный
                    but.getNotNormWorkDescriptionButton().setBorder(BorderFactory.createLineBorder(Color.red, 1));
                    // добавляем надпись "механик" для окна добавить удалить сбоку выпадающего окна
                    panelNotNormWork.add(new JLabel("Механик."), new GridBagConstraints(1, 3, 1, 1, 1, 1,
                            GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                            new Insets(2, 2, 2, 2), 0, 0));
                }
            }
        });
        // Данная проверка нужна для того что бы если в Map уже данная позиция внесена, окно ввода описания открывалось автоматом
        if (inputNotNormWorkDescription != null) {
            but.getNotNormWorkDescriptionButton().doClick();
        }
        System.gc();
        panelAdd.updateUI();
    } // Панель с описанием не нормативных работ

    private JButton updateActionListener(JButton button, ActionListener listener) {
        logManager.log("Запущен метод updateActionListener для кнопки " + button.getText());
        ActionListener[] listeners = button.getActionListeners();
        for (ActionListener l : listeners) {
            button.removeActionListener(l);
        }
        button.addActionListener(listener);
        return button;
    } // Обновление слушателя для кнопок номера не нормативных работ

    private JButton addActionListonerForNumberNotNormWork(JButton buttonNumber, JPanel panelAdd) {
        logManager.log("Запущен метод addActionListonerForNumberNotNormWork для кнопки " + buttonNumber.getText());
        ActionListener newListener = e -> {
            clearPushedButtonAfterElementAdd();
            elementButtonPushed = changeColorPushedButton(elementButtonPushed, buttonNumber, 2);
            notNormWorkAddWork(panelNotNormWork, clearCenter(panelAdd));
        };
        return updateActionListener(buttonNumber, newListener);
    }  // Добавления слушателя для кнопок номера не нормативных работ

    private JButton addGearIconToButton(JButton button) {
        logManager.log("Запущен метод addGearIconToButton для кнопки " + button.getName());
        button.removeAll(); // Убираем все с кнопки
        button.setPreferredSize(new Dimension(20, 18)); // Выставляем размер кнопки
        button.setIcon(new ImageIcon(new ImageIcon("Системные/gear.png").getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH))); // Наносим изображение
        button.setIconTextGap(-8); // Смещение иконки вправо
        return button; // Возвращаем готовую кнопку
    } // Метод нанесения изображения шестеренки и изменения размера кнопки

    private JCheckBox createCheckBox(String toolTip) {
        logManager.log("Запущен метод createCheckBox.");
        JCheckBox checkBox = new JCheckBox();
        checkBox.setToolTipText(toolTip);
        checkBox.setOpaque(false);
        checkBox.setBackground(null);
        return checkBox;
    } // Создание чекбоксов с их описаниями и позициями

    public static boolean isAnyCheckBoxSelected(JCheckBox[] checkBoxi) {
        logManager.log("Запущен метод isAnyCheckBoxSelected.");
        // Проходим по массиву checkBoxes
        for (JCheckBox checkBox : checkBoxi) {
            if (checkBox.isSelected()) {
                return true; // Если нашли выбранный чекбокс, возвращаем true
            }
        }
        return false; // Если ни один чекбокс не выбран, возвращаем false
    } // Метод для проверки, выбран ли хотя бы один JCheckBox в массиве


    private JPanel setPanelBackground(JPanel panel, String imagePath) {
        logManager.log("Запущен метод setPanelBackground.");
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

    public static int getMaxGridY(JPanel panel, int targetGridX) {
        logManager.log("Запущен метод getMaxGridY.");
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
        logManager.log("Запущен метод addDocumentListener.");
        Dimension fixedSize = new Dimension(25, 20);
        textField.setPreferredSize(fixedSize);
        textField.setMinimumSize(fixedSize);
        textField.setMaximumSize(fixedSize);
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
    } // Добавление слушателя для JTextField-ов, ввода чисел нормативов

    private JButton takeColorOfButtons(JButton button, int panelNumber) {
        logManager.log("Запущен метод takeColorOfButtons для кнопки: " + button.getText() + " и панели №" + panelNumber);
        button.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
        switch (panelNumber) {
            case 1:  // Первая панель сторона автомобиля
                // Находим первую кнопку сторона
                if (lineBorderColorMap.get(button.getText()) != null) {
                    button.setBorder(BorderFactory.createLineBorder(Color.green));
                }
                break;
            case 2: // Вторая панель имя элемента автомобиля
                // Находим по первому ключу(стороне) второй ключ(элемент)
                if (lineBorderColorMap.get(sideButtonPushed.getText()) != null) {
                    if (lineBorderColorMap.get(sideButtonPushed.getText()).get(button.getText()) != null) {
                        button.setBorder(BorderFactory.createLineBorder(Color.green));
                    }
                }
                break;
            case 3: // Третья панель вид работ на уже выбранной стороне и элементе
                // Для начала выставляем в выпадающем окне выбора механика значение 0 - "Маляр" дальше в панели добавить/удалить оно будет изменено на не редактируемое
                if (lineBorderColorMap.get(sideButtonPushed.getText()) != null) {
                    // находим первый ключ кнопку1 (сторона)
                    Map<String, List<String>> panel3 = lineBorderColorMap.get(sideButtonPushed.getText());
                    if (panel3.get(elementButtonPushed.getText()) != null) {
                        // находим в значении ключа второй ключ (имя элемента) и берем его значение лист с работами
                        List<String> listOfAddWorks = panel3.get(elementButtonPushed.getText());
                        if (!listOfAddWorks.isEmpty()) {
                            // проходим по работам и возвращаем кнопки с зеленой рамкой если они есть в списке
                            for (String check : listOfAddWorks) {
                                if (check.equals(finalPolirovkaElementCheckBox.getToolTipText())) {
                                    finalPolirovkaElementCheckBox.setSelected(true);
                                }
                                if (check.equals(button.getText())) {
                                    button.setBorder(BorderFactory.createLineBorder(Color.green));
                                }
                                //Если есть кнопка ремонт, то делим ее на значение "ремонт", "число" и "механик"
                                if (button.getText().equals("Ремонт") && check.startsWith(button.getText())) { // Было button.getText().equals("Ремонт") && check.contains("Ремонт") ****************
                                    button.setBorder(BorderFactory.createLineBorder(Color.green));
                                    String[] split = check.split(" ");
                                    // Проверяем размер разделения для того что бы не было ошибок в назначениях
                                    if (split.length > 1) {
                                        // Возвращаем значение "elementRemont" для того чтобы отобразить сколько у данного элемента это значение норматива
                                        elementRemontString = split[1];
                                    }
                                    if (split.length > 2) {
                                        // отправляем на выставления из памяти того кто делал ремонт
                                        checkRemontComboBox(split[2], 3);
                                    }
                                    //Если есть кнопка доп.работ то необходимо вернуть значение норма/часов
                                    // Кнопка доп работ имеет имя механика таким образом проверяем совпадает ли это имя с первым словом check
                                } else if (check.startsWith(button.getText())) {
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
                                    // Дальше необходимо проверить есть ли описание доп.работ
                                } else if ((button.getText().contains("Описание")) && (check.contains("Маляр") || check.contains("Кузовщик") || check.contains("Арматурщик"))) {
                                    String[] split = check.split(" ");
                                    if (split.length > 2) {
                                        String description = split[2].replaceAll("\u2400", ",").replaceAll("\u2422", " ");
                                        String[] buttonDescriptionName = button.getText().split(" ");
                                        String hoWasDescription = buttonDescriptionName[buttonDescriptionName.length - 1];
                                        if (hoWasDescription.equals(split[0])) {
                                            switch (hoWasDescription) {
                                                case "Арматурщик":
                                                    inputDopWorksArmaturchikDescription = description;
                                                    button.setBorder(BorderFactory.createLineBorder(Color.green));
                                                    break;
                                                case "Маляр":
                                                    inputDopWorksPainterDescription = description;
                                                    button.setBorder(BorderFactory.createLineBorder(Color.green));
                                                    break;
                                                case "Кузовщик":
                                                    inputDopWorksKuzovchikDescription = description;
                                                    button.setBorder(BorderFactory.createLineBorder(Color.green));
                                                    break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                break;
            case 4: // case 4 это панель полировка сложный вариант.
                if (lineBorderColorMap.get(button.getText()) != null && button.getText().equals("Полировка")) {
                    button.setBorder(BorderFactory.createLineBorder(Color.green));
                    // находим первый ключ кнопку1 (сторона)
                    Map<String, List<String>> panel4 = lineBorderColorMap.get(button.getText());
                    if (panel4.get(button.getText()) != null) {
                        // находим в значении ключа второй ключ (имя элемента) и берем его значение лист с работами
                        List<String> listOfAddWorks = panel4.get(button.getText());
                        if (!listOfAddWorks.isEmpty()) {
                            // Проходим по каждому JCheckBox в массиве checkBoxes
                            for (JCheckBox checkBox : checkBoxes) {
                                // Получаем текст подсказки (имя) текущего чекбокса
                                String checkBoxName = checkBox.getToolTipText();
                                // Проверяем, содержит ли список selectedCheckBoxNames имя текущего чекбокса
                                if (listOfAddWorks.contains(checkBoxName)) {
                                    // Если да, то устанавливаем чекбокс как выбранный
                                    checkBox.setSelected(true);
                                } else {
                                    // Если нет, то снимаем выбор с чекбокса
                                    checkBox.setSelected(false);
                                }
                            }
                        }
                    }
                } else {
                    // Проходим по каждому JCheckBox в массиве checkBoxes
                    for (JCheckBox checkBox : checkBoxes) {
                        // Так как в памяти еще нет элемента полировка снимаем выбор со всех чекбоксов
                        checkBox.setSelected(false);
                    }
                }
                break;
            case 5: // case 5 Для панели ненормированных работ
                notNormWorkHoDoString = "";
                if (lineBorderColorMap.get(sideButtonPushed.getText()) != null) {
                    // находим первый ключ кнопку1 (сторона)
                    Map<String, List<String>> panel5 = lineBorderColorMap.get(sideButtonPushed.getText());
                    if (panel5.get(elementButtonPushed.getText()) != null) {
                        // находим в значении ключа второй ключ (имя элемента) и берем его значение лист с работами
                        List<String> listOfAddWorks = panel5.get(elementButtonPushed.getText());
                        if (!listOfAddWorks.isEmpty()) {
                            String check = listOfAddWorks.get(0);
                            String[] split = check.split(" ");
                            if (split.length > 2) {
                                notNormWorkJTextField.setText(split[0]);
                                inputNotNormWorkDescription = split[1].replaceAll("\u2400", ",").replaceAll("\u2422", " ");
                                notNormWorkHoDoString = split[2];
                            }
                        }
                    }
                }
                break;
            default:
                throw new IllegalStateException("Не верное значение панели номер: " + panelNumber);
        }
        return button;
    } // Пробег по Map для оформления цвета рамок кнопок

    private void checkRemontComboBox(String hoDo, int panel) {
        logManager.log("Запущен метод checkRemontComboBox для: " + hoDo + " и панели №" + panel);
        // Первое что
        switch (hoDo) {
            case "Маляр":
                remontComboBox.setSelectedIndex(0);
                break;
            case "Кузовщик":
                remontComboBox.setSelectedIndex(1);
                break;
            case "Арматурщик":
                remontComboBox.setSelectedIndex(2);
                break;
            default:
                if (panel == 5) {
                    // Делаем комбо-бокс редактируемым
                    remontComboBox.setEditable(true);
                    if (hoDo.length() > 0) {
                        // Получаем редактор и приводим его к JTextComponent
                        ((JTextComponent) remontComboBox.getEditor().getEditorComponent()).setText(hoDo);
                    } else {
                        // Устанавливаем пустой элемент по умолчанию
                        ((JTextComponent) remontComboBox.getEditor().getEditorComponent()).setText("");
                    }
                } else {
                    remontComboBox.setEditable(false);
                    remontComboBox.setSelectedIndex(0);
                }
                break;
        }

    }

    private JButton changeColorPushedButton(JButton lastPushedButton, JButton pushedButton,
                                            int panelNumber) {
        logManager.log("Запущен метод changeColorPushedButton.");
        if (pushedButton.getText().equals("Полировка")) {
            // Только тут используем данный костыль так как в полировке нам необходима запустить проверку чекБоксов заранее
            if (lastPushedButton != null) {
                // Если прошлая кнопка была нажата, то мы проверяем ее и так как мы в проверке для кнопки полировка которая идет
                // в первом столбце выбора, это панель "1" а прийти проверка может от другой панели, то мы вручную выставляем проверку 1ой панели.
                takeColorOfButtons(lastPushedButton, 1);
            }
            sideButtonPushed = but.getPolirovkaButton();
            lastPushedButton = pushedButton;
        }
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
        logManager.log("Запущен метод addColorOfButtonsWorks.");
        List<String> color = new ArrayList<>();
        if (!sideButtonPushed.getText().equals("Полировка")) {
            if (zamenaOrRsButtonPushed != null) {
                color.add(zamenaOrRsButtonPushed.getText());
                zamenaOrRsButtonPushed.setBorder(BorderFactory.createLineBorder(Color.green, 1));
            }
            if (paint1xOr2xButtonPushed != null) {
                color.add(paint1xOr2xButtonPushed.getText());
                paint1xOr2xButtonPushed.setBorder(BorderFactory.createLineBorder(Color.green, 1));
            }
            if (remontButtonPushed != null) {
                color.add(remontButtonPushed.getText() + " " + elementRemontString + " " + remontComboBox.getSelectedItem());
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
                // Меняем цвет рамки кнопки "доп.Работы.Арматурщик" на зеленый так она теперь добавлена в работы.
                dopWorksArmaturchikButtonPushed.setBorder(BorderFactory.createLineBorder(Color.green, 1));
                // Если кнопка описания доп работ арматурщика нажата и описания доп работ добавлено то
                if (dopWorksArmaturchikDescriptionPushed != null && inputDopWorksArmaturchikDescription != null) {
                    // добавляем в список и описание доп работ
                    color.add(dopWorksArmaturchikButtonPushed.getText() + " " + dopWorksArmaturchik.getText() + " " + inputDopWorksArmaturchikDescription.replaceAll(",", "\u2400").replaceAll(" ", "\u2422"));
                    dopWorksArmaturchikDescriptionPushed.setBorder(BorderFactory.createLineBorder(Color.green, 1));
                } else {
                    // Если же описания нет, то добавляем только кнопку "доп.работы" и количество норма времени.
                    color.add(dopWorksArmaturchikButtonPushed.getText() + " " + dopWorksArmaturchik.getText());
                }
            }
            if (dopWorksPainterButtonPushed != null) {
                // Меняем цвет рамки кнопки "доп.Работы.Маляр" на зеленый так она теперь добавлена в работы.
                dopWorksPainterButtonPushed.setBorder(BorderFactory.createLineBorder(Color.green, 1));
                // Если кнопка описания доп работ Маляр нажата и описания доп работ добавлено то
                if (dopWorksPainterDescriptionPushed != null && inputDopWorksPainterDescription != null) {
                    // добавляем в список и описание доп работ
                    color.add(dopWorksPainterButtonPushed.getText() + " " + dopWorksPainter.getText() + " " + inputDopWorksPainterDescription.replaceAll(",", "\u2400").replaceAll(" ", "\u2422"));
                    dopWorksPainterDescriptionPushed.setBorder(BorderFactory.createLineBorder(Color.green, 1));
                } else {
                    // Если же описания нет, то добавляем только кнопку "доп.работы" и количество норма времени.
                    color.add(dopWorksPainterButtonPushed.getText() + " " + dopWorksPainter.getText());
                }
            }
            if (dopWorksKuzovchikButtonPushed != null) {
                // Меняем цвет рамки кнопки "доп.Работы.Кузовщик" на зеленый так она теперь добавлена в работы.
                dopWorksKuzovchikButtonPushed.setBorder(BorderFactory.createLineBorder(Color.green, 1));
                // Если кнопка описания доп работ Кузовщик нажата и описания доп работ добавлено то
                if (dopWorksKuzovchikDescriptionPushed != null && inputDopWorksKuzovchikDescription != null) {
                    // добавляем в список и описание доп работ
                    color.add(dopWorksKuzovchikButtonPushed.getText() + " " + dopWorksKuzovchik.getText() + " " + inputDopWorksKuzovchikDescription.replaceAll(",", "\u2400").replaceAll(" ", "\u2422"));
                    dopWorksKuzovchikDescriptionPushed.setBorder(BorderFactory.createLineBorder(Color.green, 1));
                } else {
                    // Если же описания нет, то добавляем только кнопку "доп.работы" и количество норма времени.
                    color.add(dopWorksKuzovchikButtonPushed.getText() + " " + dopWorksKuzovchik.getText());
                }
            }
            if (notNormWorkDescriptionPushed != null) {
                notNormWorkDescriptionPushed.setBorder(BorderFactory.createLineBorder(Color.green, 1));
                color.add(notNormWorkNormativeString + " " + inputNotNormWorkDescription.replaceAll(",", "\u2400").replaceAll(" ", "\u2422") + " " + remontComboBox.getSelectedItem());
            }
            if (finalPolirovkaElementCheckBox.isSelected()) {
                color.add(finalPolirovkaElementCheckBox.getToolTipText());
            }
        } else {
            // Итерируем по каждому JCheckBox в массиве
            for (JCheckBox checkBox : checkBoxes) {
                if (checkBox.isSelected()) { // Если чекбокс выбран
                    color.add(checkBox.getToolTipText());// Добавляем его текст в список
                }
            }
        }
        return color;
    } // Добавление зеленой рамки кнопке и занесение нажатий в Map при добавлении в список и память

    private JPanel clearAll(JPanel panel) {
        logManager.log("Запущен метод clearAll.");
        BorderLayout layout = (BorderLayout) panel.getLayout();
        clearPushedButtonAfterElementAdd();
        elementLeftRightSidePanel.removeAll();
        elementCenterSide.removeAll();
        panelGlass.removeAll();
        panelNotNormWork.removeAll();
        if (layout.getLayoutComponent(BorderLayout.WEST) != null) {
            panel.remove(layout.getLayoutComponent(BorderLayout.WEST));
        }
        return clearCenter(panel);
    } // Удалить все панели кроме начальной с выбором сторон

    private JPanel clearCenter(JPanel panel) {
        logManager.log("Запущен метод clearCenter.");
        BorderLayout layout = (BorderLayout) panel.getLayout();
        if (layout.getLayoutComponent(BorderLayout.CENTER) != null) {
            panel.remove(layout.getLayoutComponent(BorderLayout.CENTER));
        }
        panel.updateUI();
        return panel;
    } // Удаление панели добавить\удалить

    private void clearPushedButtonAfterElementAdd() {
        logManager.log("Запущен метод clearPushedButtonAfterElementAdd.");
        zamenaOrRsButtonPushed = null;  // Кнопка замена
        paint1xOr2xButtonPushed = null; // Кнопка окраска с1 или 2х сторон
        remontButtonPushed = null;      // Кнопка ремонта
        ruchkaButtonPushed = null;      // Кнопка ручка
        moldingButtonPushed = null;     // Кнопка молдинг
        zercaloButtonPushed = null;     // Кнопка зеркало
        expanderButtonPushed = null;    // Кнопка расширитель
        overlayButtonPushed = null;     // Кнопка накладка
        glassButtonPushed = null;       // Кнопка остекление лобовое или заднее
        elementRemontString = null;           // Значение введенного числа норматива на ремонт
        dopWorksArmaturchikButtonPushed = null; // Кнопка доп.работы Арматурщик
        dopWorksPainterButtonPushed = null;     // Кнопка доп.работы Маляр
        dopWorksKuzovchikButtonPushed = null;   // Кнопка доп.работы Кузовщик
        dopWorksArmaturchikDescriptionPushed = null; // Кнопка описания доп.работы Арматурщик
        dopWorksPainterDescriptionPushed = null;     // Кнопка описания доп.работы Маляр
        dopWorksKuzovchikDescriptionPushed = null;   // Кнопка описания доп.работы Кузовщик
        notNormWorkDescriptionPushed = null;         // Кнопка описания ненормативных работ
        dopWorksArmaturchik.setText("");  // Графа ввода норматива доп.работы Арматурщик
        dopWorksPainter.setText("");      // Графа ввода норматива доп.работы Маляр
        dopWorksKuzovchik.setText("");    // Графа ввода норматива доп.работы Кузовщик
        notNormWorkJTextField.setText("");// Графа ввода норматива ненормативных работ
        remont.setText("");               // Графа ввода значения норматива ремонт
        elementKuzDetReplaceSide = 0.0;   // Норматив замены кузовной детали
        elementArmatureSide = 0.0;        // Норматив арматурных работ на детали
        elementPaintSide = 0.0;           // Норматив на окраску детали
        haveGlass = 0;                    // Значение норматив есть ли на детали остекление
        inputDopWorksArmaturchikDescription = null;  // Введенный текст описания доп.работы Арматурщик
        inputDopWorksPainterDescription = null;      // Введенный текст описания доп.работы Маляр
        inputDopWorksKuzovchikDescription = null;    // Введенный текст описания доп.работы Кузовщик
        inputNotNormWorkDescription = null;          // Введенный текст описания ненормативных работ
        notNormWorkHoDoString = null;                // Назначенный механик для ненормативных работ
        finalPolirovkaElementCheckBox.setSelected(false); // Чекбокс финальной полировки конкретного элемента
        remontComboBox.setSelectedIndex(0);               // Чекбокс выбора механика
    } // Стирание (приведя их к null) нажатых в процессе кнопок

    private Element createElement() {
        logManager.log("Инициировано создание элемента.");
        Element element = new Element();
        // С начало выставляем имя.
        element.setName(createName());
        logManager.log("Имя элемента вернулось: " + element.getName() + " Продолжаем создание элемента");
        // От сих пор начинаем проставлять нормативы. Если это стекло.
        if (sideButtonPushed.getText().equals("Остекление")) {
            element.setGlass(2);
            logManager.log("Элемент стекло выставляем element.setGlass(2).");
        }
        // От сих пор начинаем проставлять нормативы. Если это не стекло и не полировка.
        if (!sideButtonPushed.getText().equals("Остекление") && !sideButtonPushed.getText().equals("Полировка") && !sideButtonPushed.getText().equals("Ненормативные")) {
            logManager.log("Элемент не Остекление и Полировка.");
            element.setPaintSide(elementPaintSide);
            logManager.log("выставляем element.setPaintSide(elementPaintSide)." + elementPaintSide);
            element.setArmatureSide(elementArmatureSide);
            logManager.log("выставляем element.setArmatureSide(elementArmatureSide)." + elementArmatureSide);
            element.setKuzDetReplaceSide(elementKuzDetReplaceSide);
            logManager.log("выставляем element.setKuzDetReplaceSide(elementKuzDetReplaceSide)." + elementKuzDetReplaceSide);
            elementPaintAllForLkm += elementPaintSide;  // Добавляем в ЛКМ норматив объем окраски общего для всех Элементов
            logManager.log("Добавляем в ЛКМ норматив объем окраски общего для всех Элементов: elementPaintAllForLkm += elementPaintSide.");
            element.setLkmForElement(element.getPaintSide()); // Добавляем в ЛКМ норматив объем окраски для конкретного Элемента
            logManager.log("Добавляем в ЛКМ норматив объем окраски для конкретного Элемента: element.setLkmForElement(element.getPaintSide()).");
            // Если нажата кнопка ремонта, то добавляем, добавив "." перед последним символом
            // переведя в двоичное число
            if (remontButtonPushed != null) {
                String rem;
                rem = new StringBuilder(elementRemontString).insert(elementRemontString.length() - 1, ".").toString();
                element.setRemont(Double.parseDouble(rem));
                logManager.log("Добавляем в элемент ремонт: " + rem);
                element.setHoDoRemont((String) remontComboBox.getSelectedItem());
                logManager.log("Добавляем в элемент кто делал ремонт: " + remontComboBox.getSelectedItem());
            }
            // Если имя элемента входит в перечень, то добавляем ручки, молдинги и зеркала.
            if (zercaloButtonPushed != null) {
                element.setZerkalo(1);
                logManager.log("Добавляем в элемент окраску зеркала: element.setZerkalo(1)");
                element.setLkmForElement(element.getLkmForElement() + 1);
                logManager.log("Добавляем в ЛКМ норматив объем окраски для конкретного Элемента: element.setLkmForElement(element.getLkmForElement() + 1);.");
                elementPaintAllForLkm += 1; // Добавляем в ЛКМ общее, норматив объем окраски согласно элементу
                logManager.log("Добавляем в ЛКМ норматив объем окраски общего для всех Элементов: elementPaintAllForLkm += 1.");
            }
            if (ruchkaButtonPushed != null) {
                element.setRuchka(1);
                logManager.log("Добавляем в элемент окраску ручки: element.setRuchka(1)");
                element.setLkmForElement(element.getLkmForElement() + 1);
                logManager.log("Добавляем в ЛКМ норматив объем окраски для конкретного Элемента: element.setLkmForElement(element.getLkmForElement() + 1);.");
                elementPaintAllForLkm += 1; // Добавляем в ЛКМ общее, норматив объем окраски согласно элементу
                logManager.log("Добавляем в ЛКМ норматив объем окраски общего для всех Элементов: elementPaintAllForLkm += 1.");
            }
            if (moldingButtonPushed != null) {
                element.setMolding(1);
                logManager.log("Добавляем в элемент окраску молдинга: element.setMolding(1)");
                element.setLkmForElement(element.getLkmForElement() + 1);
                logManager.log("Добавляем в ЛКМ норматив объем окраски для конкретного Элемента: element.setLkmForElement(element.getLkmForElement() + 1);.");
                elementPaintAllForLkm += 1; // Добавляем в ЛКМ общее, норматив объем окраски согласно элементу
                logManager.log("Добавляем в ЛКМ норматив объем окраски общего для всех Элементов: elementPaintAllForLkm += 1.");
            }
            // Если имя элемента входит в перечень добавляем расширитель крыльев
            if (expanderButtonPushed != null) {
                element.setExpander(2);
                logManager.log("Добавляем в элемент окраску расширителя: element.setExpander(2)");
                element.setLkmForElement(element.getLkmForElement() + 2);
                logManager.log("Добавляем в ЛКМ норматив объем окраски для конкретного Элемента: element.setLkmForElement(element.getLkmForElement() + 2);.");
                elementPaintAllForLkm += 2; // Добавляем в ЛКМ общее, норматив объем окраски согласно элементу
                logManager.log("Добавляем в ЛКМ норматив объем окраски общего для всех Элементов: elementPaintAllForLkm += 2.");
            }
            // Если имя элемента входит в перечень, то добавляем накладку
            if (overlayButtonPushed != null) {
                element.setOverlay(2);
                logManager.log("Добавляем в элемент окраску накладки: element.setOverlay(2)");
                element.setLkmForElement(element.getLkmForElement() + 2);
                logManager.log("Добавляем в ЛКМ норматив объем окраски для конкретного Элемента: element.setLkmForElement(element.getLkmForElement() + 2);.");
                elementPaintAllForLkm += 2; // Добавляем в ЛКМ общее, норматив объем окраски согласно элементу
                logManager.log("Добавляем в ЛКМ норматив объем окраски общего для всех Элементов: elementPaintAllForLkm += 2.");
            }
            // Если у элемента есть стекло, то проверяем надо ли снимать.
            if (haveGlass > 0) {
                // Умножаем 2 так как норма на стекло 2
                element.setGlass(haveGlass * 2);
                logManager.log("У элемента есть с/у стекла добавляем: element.setGlass(haveGlass * 2).");
                if (haveGlass == 2) {
                    element.setNameGlass("Лобовое и Заднее" + " стекло c/у");
                    logManager.log("У элемента есть с/у 2х стекол добавляем: element.setNameGlass(Лобовое и Заднее + стекло c/у)");
                } else {
                    element.setNameGlass(glassButtonPushed.getText() + " стекло c/у");
                    logManager.log("У элемента есть с/у 1го стекла добавляем: element.setNameGlass(glassButtonPushed.getText() + стекло c/у)");
                }
            }
            if (dopWorksArmaturchikButtonPushed != null) {
                logManager.log("У элемента есть доп работы Арматурщик.");
                // С начало необходимо перевести норматив в вид текста
                String dopWorks;
                // добавив точку перед последним символом таким образом переведя его двоичное число в виде текста
                dopWorks = new StringBuilder(dopWorksArmaturchik.getText()).insert(dopWorksArmaturchik.getText().length() - 1, ".").toString();
                // Далее присвоить элементу в параметр доп.Работы арматурщик двоичное число переведя его из String в double
                element.setDopWorksArmoturchik(Double.parseDouble(dopWorks));
                logManager.log("Добавляем элементу норматив доп работы Арматурщик: element.setDopWorksArmoturchik(Double.parseDouble(dopWorks)) = " + dopWorks);
                // Дальше если есть описание доп.работ арматурщик
                if (dopWorksArmaturchikDescriptionPushed != null && inputDopWorksArmaturchikDescription != null) {
                    element.setDescriptionDopWorksArmaturchic(inputDopWorksArmaturchikDescription);
                    logManager.log("У элемента есть и описание доп работы Арматурщик. Добавляем: element.setDescriptionDopWorksArmaturchic(inputDopWorksArmaturchikDescription) " + inputDopWorksArmaturchikDescription);
                }
            }
            if (dopWorksPainterButtonPushed != null) {
                logManager.log("У элемента есть доп работы Маляр.");
                // С начало необходимо перевести норматив в вид текста
                String dopWorks;
                // добавив точку перед последним символом таким образом переведя его двоичное число в виде текста
                dopWorks = new StringBuilder(dopWorksPainter.getText()).insert(dopWorksPainter.getText().length() - 1, ".").toString();
                // Далее присвоить элементу в параметр доп.Работы Маляр двоичное число переведя его из String в double
                element.setDopWorksPainter(Double.parseDouble(dopWorks));
                logManager.log("Добавляем элементу норматив доп работы Маляр: element.setDopWorksPainter(Double.parseDouble(dopWorks)) = " + dopWorks);
                // Дальше если есть описание доп.работ Маляр
                if (dopWorksPainterDescriptionPushed != null && inputDopWorksPainterDescription != null) {
                    element.setDescriptionDopWorksPainter(inputDopWorksPainterDescription);
                    logManager.log("У элемента есть и описание доп работы Маляр. Добавляем: element.setDescriptionDopWorksPainter(inputDopWorksPainterDescription) " + inputDopWorksPainterDescription);
                }
            }
            if (dopWorksKuzovchikButtonPushed != null) {
                logManager.log("У элемента есть доп работы Кузовщик.");
                // С начало необходимо перевести норматив в вид текста
                String dopWorks;
                // добавив точку перед последним символом таким образом переведя его двоичное число в виде текста
                dopWorks = new StringBuilder(dopWorksKuzovchik.getText()).insert(dopWorksKuzovchik.getText().length() - 1, ".").toString();
                // Далее присвоить элементу в параметр доп.Работы Кузовщик двоичное число переведя его из String в double
                element.setDopWorksKuzovchik(Double.parseDouble(dopWorks));
                logManager.log("Добавляем элементу норматив доп работы Кузовщик: element.setDopWorksKuzovchik(Double.parseDouble(dopWorks)) = " + dopWorks);
                // Дальше если есть описание доп.работ Кузовщик
                if (dopWorksKuzovchikDescriptionPushed != null && inputDopWorksKuzovchikDescription != null) {
                    element.setDescriptionDopWorksKuzovchik(inputDopWorksKuzovchikDescription);
                    logManager.log("У элемента есть и описание доп работы Кузовщик. Добавляем: element.setDescriptionDopWorksKuzovchik(inputDopWorksKuzovchikDescription) " + inputDopWorksKuzovchikDescription);
                }
            }
            calcLkm(element);
        } else if (sideButtonPushed.getText().equals("Полировка")) {
            element.setPaintSide(elementPaintSide);
            logManager.log("Элемент Полировка. Выставляем element.setPaintSide(elementPaintSide)");
        } else if (sideButtonPushed.getText().equals("Ненормативные")) {
            logManager.log("Элемент Ненормативные работы.");
            String notNorm;
            notNorm = new StringBuilder(notNormWorkNormativeString).insert(notNormWorkNormativeString.length() - 1, ".").toString();
            element.setNotNormWork(Double.parseDouble(notNorm));
            logManager.log("Выставляем element.setNotNormWork(Double.parseDouble(notNorm)) = " + notNorm);
            element.setHoDoRemont((String) remontComboBox.getSelectedItem());
            logManager.log("Выставляем element.setHoDoRemont((String) remontComboBox.getSelectedItem()) = " + (String) remontComboBox.getSelectedItem());
        }
        logManager.log("return element");
        return element;
    }  // Создание элемента основного

    private Element createElementFinalPolirovka() {
        Element element = new Element();
        element.setName(createName() + " " + finalPolirovkaElementCheckBox.getToolTipText());
        element.setPaintSide(0.8);
        return element;
    }

    private JButton removeActionListener(JButton button) {
        logManager.log("Запущен метод removeActionListener.");
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
        logManager.log("Запущен метод sendToStringInElementListTextViewing.");
        elementListTextViewing.setText("ЛКМ Итого: " + lkmTotalPrice + "руб.\n" + Arrays.toString(elementList.toArray()).replaceAll("^\\[|\\]$", ""));
    }  // Вывод на окно просмотра добавленных элементов

    private Element addToNameZamenaIfZamenaButtonPushed(Element element) {
        logManager.log("Запущен метод addToNameZamenaIfZamenaButtonPushed.");
        // Если Элемент меняется, то к имени добавляем "замена".
        if (zamenaOrRsButtonPushed != null) {
            if (zamenaOrRsButtonPushed.getText().equals("Замена")) {
                element.setName(element.getName() + " " + zamenaOrRsButtonPushed.getText());
                logManager.log("К имени элемента добавлена обозначение Замена");
            }
        }
        return element;
    }  // Добавление к имени элемента "замена" если элемент меняется

    private String createName() {
        logManager.log("Запущен метод createName.");
        String name;
        // Оформляем имя если это не полировка
        if (!sideButtonPushed.getText().equals("Полировка") && !sideButtonPushed.getText().equals("Ненормативные")) {
            logManager.log("Элемент не является Полировка и Ненормативные.");
            // Первое оформляем имя (левая правая сторона)
            if (sideButtonPushed.getText().equals("Левая") || sideButtonPushed.getText().equals("Правая")) {
                logManager.log("Элемент является Левая или Правая часть авто.");
                if (elementButtonPushed.getText().equals("Пер.Крыло") || elementButtonPushed.getText().equals("Зад.Крыло")) {
                    name = sideButtonPushed.getText().substring(0, sideButtonPushed.getText().length() - 2) + "ое " + elementButtonPushed.getText();
                    logManager.log("Элемент является Пер или Зад. Крыло.");
                } else if (elementButtonPushed.getText().equals("Пер.Дверь") || elementButtonPushed.getText().equals("Зад.Дверь")) {
                    name = sideButtonPushed.getText() + " " + elementButtonPushed.getText();
                    logManager.log("Элемент является Пер или Зад. Дверь.");
                } else {
                    name = sideButtonPushed.getText().substring(0, sideButtonPushed.getText().length() - 2) + "ый " + elementButtonPushed.getText();
                    logManager.log("Элемент не является Пер или Зад. Крыло или Дверь.");
                }
                // Второе оформление имени остекление
            } else if (sideButtonPushed.getText().equals("Остекление")) {
                name = elementButtonPushed.getText() + " " + sideButtonPushed.getText();
                logManager.log("Элемент является Остекление.");
                // Третье оформление имени центральная часть авто (сторону упускаем, оставляем только элемент)
            } else {
                name = elementButtonPushed.getText();
                logManager.log("Элемент является центральная часть авто.");
            }
            // Если полировка, то нужно проверить все чекбоксы
        } else if (sideButtonPushed.getText().equals("Полировка")) {
            logManager.log("Элемент является Полировка.");
            name = createNamePolirovka();
        } else { // В случае не нормативных работ
            logManager.log("Элемент является Ненормативные работы.");
            name = createNameNotNormWork();
        }
        logManager.log("Создали и вернули имя элемента: " + name);
        return name;
    }  // Создание имени элемента

    private String createNamePolirovka() {
        logManager.log("Запущен метод createNamePolirovka.");
        String name;
        int painter = 0;
        // Список для хранения имен выбранных чекбоксов
        List<String> selectedCheckBoxNames = new ArrayList<>();
        // Итерируем по каждому JCheckBox в массиве
        for (JCheckBox checkBox : checkBoxes) {
            if (checkBox.isSelected()) { // Если чекбокс выбран
                selectedCheckBoxNames.add(checkBox.getToolTipText()); // Добавляем его текст в список
                painter++;
            }
        }
        // Преобразуем список в строку с именами выбранных чекбоксов
        String selectedNames = String.join("' ", selectedCheckBoxNames);
        name = sideButtonPushed.getText() + " " + selectedNames;
        elementPaintSide = painter * 0.8;
        logManager.log("Создали имя для элемента полировка: " + name);
        return name;
    }

    private String createNameNotNormWork() {
        String name = elementButtonPushed.getText() + " " + inputNotNormWorkDescription;
        logManager.log("Создали имя для не нормативных работ: " + name);
        return name;
    }

    private void checkEarlyPushedButtonsWorksPanel() {
        logManager.log("Запущен метод checkEarlyPushedButtonsWorksPanel.");
        /*
        Проверяем по цвету рамки какие кнопки были нажаты ранее.
         */
        if (!elementButtonPushed.getText().contains("Проём")) {
            LineBorder colorReplaceButton = (LineBorder) but.getReplaceButton().getBorder();
            if (colorReplaceButton.getLineColor().equals(Color.GREEN)) {
                but.getReplaceButton().doClick();
            }
        }
        LineBorder colorDisassembleButton = (LineBorder) but.getDisassembleButton().getBorder();
        LineBorder colorPaint1xButton = (LineBorder) but.getPaint1xButton().getBorder();
        LineBorder colorPaint2xButton = (LineBorder) but.getPaint2xButton().getBorder();
        LineBorder colorRepair = (LineBorder) but.getRepairButton().getBorder();
        LineBorder colorDopWorksArmaturchik = (LineBorder) but.getDopWorksArmaturchikButton().getBorder();
        LineBorder colorDopWorksArmaturchikDescription = (LineBorder) but.getDopWorksArmaturchikDescriptionButton().getBorder();
        LineBorder colorDopWorksPainter = (LineBorder) but.getDopWorksPainterButton().getBorder();
        LineBorder colorDopWorksPainterDescription = (LineBorder) but.getDopWorksPainterDescriptionButton().getBorder();
        LineBorder colorDopWorksKuzovchik = (LineBorder) but.getDopWorksKuzovchikButton().getBorder();
        LineBorder colorDopWorksKuzovchikDescription = (LineBorder) but.getDopWorksKuzovchikDescriptionButton().getBorder();


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
            if (colorDopWorksArmaturchikDescription.getLineColor().equals(Color.GREEN)) {
                dopWorksArmaturchikDescriptionPushed = but.getDopWorksArmaturchikDescriptionButton();
                but.getDopWorksArmaturchikDescriptionButton().setBorder(BorderFactory.createLineBorder(Color.red, 1));
            }
        }
        if (colorDopWorksPainter.getLineColor().equals(Color.GREEN)) {
            but.getDopWorksPainterButton().doClick();
            if (colorDopWorksPainterDescription.getLineColor().equals(Color.GREEN)) {
                dopWorksPainterDescriptionPushed = but.getDopWorksPainterDescriptionButton();
                but.getDopWorksPainterDescriptionButton().setBorder(BorderFactory.createLineBorder(Color.red, 1));
            }
        }
        if (colorDopWorksKuzovchik.getLineColor().equals(Color.GREEN)) {
            but.getDopWorksKuzovchikButton().doClick();
            if (colorDopWorksKuzovchikDescription.getLineColor().equals(Color.GREEN)) {
                dopWorksKuzovchikDescriptionPushed = but.getDopWorksKuzovchikDescriptionButton();
                but.getDopWorksKuzovchikDescriptionButton().setBorder(BorderFactory.createLineBorder(Color.red, 1));
            }
        }
       // Проверка для выставления нормативов если они менялись у конкретного элемента.
        String name = createName();
        String polishingText = finalPolirovkaElementCheckBox.getToolTipText();

        for (Element e : elementList) {
            if (e.getName().contains(name) && !e.getName().contains(polishingText)) {
                elementArmatureSide = e.getArmatureSide();
                elementPaintSide = e.getPaintSide();
            }
        }
    } // Проверка и нажатие кнопок добавленного элемента

    private void removeElementFromList(String elementName) {
        logManager.log("Запущен метод removeElementFromList. Для элемента: " + elementName);
        Integer q = null;
        Element el = null;
        List<Element> removeList = new ArrayList<>();
        /*
        Удаление элементов из списка работает просто по имени за исключение полировки в которой нужно всегда удалять
        предыдущую версию элемента с полировкой через дополнительную проверку. Если имя элемента начинается с "Полировка" и
        значение String elementName начинается тоже с "Полировка", то этот элемент подлежит удалению.
        */

        for (int i = 0; i <= elementList.size() - 1; i++) {
            if (elementList.get(i).getName().contains(elementName) || (elementList.get(i).getName().startsWith("Полировка") && elementName.startsWith("Полировка"))) {
                q = i;
                el = elementList.get(i);
                removeList.add(el);
            }
        }
        if (q != null) {
            for (Element del : removeList) {
                elementList.remove(del);
                logManager.log("Удалён элемент: " + el.getName());
            }
        }
    } // Удаление из Списка элементов "element"

    public void load
            (List<Element> elementList, Map<String, Map<String, List<String>>> lineBorderColorMap, Client
                    client, Lkm lkm, JPanel panel) {
        logManager.log("Запущен метод load.");
        this.elementList = elementList;
        this.lineBorderColorMap = lineBorderColorMap;
        reCalcLkmPrice();
        this.lkm = lkm;
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(panel);
        startPanel(panel, client, parentFrame);
        sendToStringInElementListTextViewing();
    }

    private void calcLkm(Element element) {
        logManager.log("Запущен метод calcLkm.");
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
        logManager.log("Запущен метод calcLkmPrice.");
        int circleCount = lkm.getP80() + lkm.getP180() + lkm.getP280() + lkm.getP400() + lkm.getP500();
        double circlesCost = circleCount * lkmPrices.getCircle();

        // Подсчет полос
        int stripCount = lkm.getStripP80() + lkm.getStripP120() + lkm.getStripP180();
        double stripsCost = stripCount * lkmPrices.getStrip();

        // Отдельные позиции
        double scotchBriteCost = lkm.getScotchBrite() * lkmPrices.getScotchBrite();
        double primingCost = lkm.getPriming() * lkmPrices.getPriming();
        double clearCost = lkm.getClear() * lkmPrices.getClear();
        double baseDilutionCost = lkm.getBaseDilution() * lkmPrices.getBaseDilution();
        double siliconRemoverCost = lkm.getSiliconRemover() * lkmPrices.getSiliconRemover();
        double basePaint = lkm.getBasePaint() * lkmPrices.getBasePaint();
        double stickyTapeCost = lkm.getStickyTape() * lkmPrices.getStickyTape();
        double coveringFilmCost = lkm.getCoveringFilm() * lkmPrices.getCoveringFilm();
        double puttyFiberCost = lkm.getPuttyFiber() * lkmPrices.getPuttyFiber();
        double puttyUniversalCost = lkm.getPuttyUniversal() * lkmPrices.getPuttyUniversal();
        double napkinCost = lkm.getNapkin() * lkmPrices.getNapkin();
        double hermeticCost = lkm.getHermetic() * lkmPrices.getHermetic();

        // Итоговая сумма с надбавкой 10%
        lkmTotalPrice = (int) ((circlesCost + stripsCost + scotchBriteCost + primingCost +
                clearCost + baseDilutionCost + siliconRemoverCost + basePaint + stickyTapeCost +
                coveringFilmCost + puttyFiberCost + puttyUniversalCost + napkinCost +
                hermeticCost) * 1.1);
    } // Метод подсчета стоимости необходимого ЛКМ

    private void reCalcLkmPrice() {
        logManager.log("Запущен метод reCalcLkmPrice.");
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
        logManager.log("Запущен метод clearLkm.");
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