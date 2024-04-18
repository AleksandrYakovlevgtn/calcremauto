package ru.yandex.practicum.calcRemAuto.panelsAndButtons.frame;

import lombok.Getter;
import ru.yandex.practicum.calcRemAuto.model.LkmPrices;
import ru.yandex.practicum.calcRemAuto.model.Mechanics;
import ru.yandex.practicum.calcRemAuto.model.Prices;
import ru.yandex.practicum.calcRemAuto.model.TelegramBotInfo;
import ru.yandex.practicum.calcRemAuto.panelsAndButtons.panels.CalculatorPanel;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

@Getter
public class JMenuFrame extends JDialog {
    JMenuBar menuBar = new JMenuBar(); // Верхняя панель с выпадающим окном
    JMenu settingsMenu = new JMenu("Настройки"); // Элемент на выпадающем окне
    JMenuItem changeRatesItem = new JMenuItem("Изменить стоимость"); // Подпункт для указания стоимости н/ч
    JMenuItem setMechanicsItem = new JMenuItem("Указать фамилии механиков"); // Подпункт для указания фамилий механиков
    JMenuItem telegramSettings = new JMenuItem("Telegram Token"); // Настройки телеграм (chat_id,bot_id)
    JMenuItem lkmPricesSettings = new JMenuItem("ЛКМ цены");
    private int hourlyRate; // Стоимость общего н/ч
    private int mechanicHourlyRate; // Стоимость норматива механика
    private int masterHourlyRate;  // Стоимость норматива мастера
    private int circle;  // круги
    private int strip; // полоски
    private double scotchBrite; // сотчБрайт рулон
    private double priming; // Грунт
    private double clear;  // Лак HS
    private double baseDilution; // Разбавитель 5л
    private double basePaint;  // эмаль базовая за 1гр.
    private double siliconRemover; // обезжириватель
    private double stickyTape; // Малярный бумажный скотч
    private double coveringFilm;  // пленка укрывачная
    private int napkin; // Салфетки химостойкие
    private double puttyFiber; // Шпатлевка волокнистая
    private double puttyUniversal; // Шпатлевка универсальная
    private int hermetic;// Герметик кузовной
    private String malyr;
    private String armoturchik;
    private String kuzovchik;
    private String master;
    private String botUserName;
    private String botToken;
    private String chatId;

    Prices prices = new Prices();  // Класс с ценами
    Mechanics mechanics = new Mechanics(); // Класс с фамилиями механиков
    TelegramBotInfo botInfo = new TelegramBotInfo();
    LkmPrices lkmPrices = new LkmPrices();

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

        malyr = mechanics.getMalyr();
        armoturchik = mechanics.getArmoturchik();
        kuzovchik = mechanics.getKuzovchik();
        master = mechanics.getMaster();

        botUserName = botInfo.getBotUserName();
        botToken = botInfo.getBotToken();
        chatId = botInfo.getChatId();

        circle = lkmPrices.getCircle();
        strip = lkmPrices.getStrip();
        scotchBrite = lkmPrices.getScotchBrite();
        priming = lkmPrices.getPriming();
        clear = lkmPrices.getClear();
        baseDilution = lkmPrices.getBaseDilution();
        basePaint = lkmPrices.getBasePaint();
        siliconRemover = lkmPrices.getSiliconRemover();
        stickyTape = lkmPrices.getStickyTape();
        coveringFilm = lkmPrices.getCoveringFilm();
        napkin = lkmPrices.getNapkin();
        puttyFiber = lkmPrices.getPuttyFiber();
        puttyUniversal = lkmPrices.getPuttyUniversal();
        hermetic = lkmPrices.getHermetic();

        changeRatesItem.addActionListener(e -> openRatesFrame(parentFrame));
        setMechanicsItem.addActionListener(e -> openMechanicsFrame(parentFrame));
        telegramSettings.addActionListener(e -> openTelegramSettingsFrame(parentFrame));
        lkmPricesSettings.addActionListener(e -> openLkmPricesSettingsFrame(parentFrame));

        settingsMenu.add(changeRatesItem);
        settingsMenu.add(setMechanicsItem);
        settingsMenu.add(telegramSettings);
        settingsMenu.add(lkmPricesSettings);
        menuBar.add(settingsMenu);
    }  // Начальная настройка параметров

    private void openRatesFrame(Frame parentFrame) {

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
            prices.writeRatesToFile(hourlyRate, mechanicHourlyRate, masterHourlyRate);
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
    } // Создание окна с ценами н/ч

    private void openMechanicsFrame(Frame parentFrame) {
        setLayout(new GridBagLayout());

        JTextField painterField = new JTextField(malyr);
        JTextField reinforcementWorkerField = new JTextField(armoturchik);
        JTextField bodyWorkerField = new JTextField(kuzovchik);
        JTextField masterField = new JTextField(master);

        JButton saveMechanicsButton = new JButton("Сохранить");
        JButton cancelMechanicsButton = new JButton("Отмена");

        add(new JLabel("Маляр:"), createGridBagConstraints(1, 0, 1, 1));
        add(painterField, createGridBagConstraints(2, 0, 1, 1));
        add(new JLabel("Арматурщик:"), createGridBagConstraints(1, 1, 1, 1));
        add(reinforcementWorkerField, createGridBagConstraints(2, 1, 1, 1));
        add(new JLabel("Кузовщик:"), createGridBagConstraints(1, 2, 1, 1));
        add(bodyWorkerField, createGridBagConstraints(2, 2, 1, 1));
        add(new JLabel("Мастер:"), createGridBagConstraints(1, 3, 1, 1));
        add(masterField, createGridBagConstraints(2, 3, 1, 1));
        add(saveMechanicsButton, createGridBagConstraints(1, 4, 1, 1));
        add(cancelMechanicsButton, createGridBagConstraints(2, 4, 1, 1));

        saveMechanicsButton.addActionListener(saveEvent -> {
            malyr = painterField.getText();
            armoturchik = reinforcementWorkerField.getText();
            kuzovchik = bodyWorkerField.getText();
            master = masterField.getText();
            mechanics.writeRatesToFile(malyr, armoturchik, kuzovchik, master);
            deleteComponentsFromJDialog();
            dispose();
        });
        cancelMechanicsButton.addActionListener(cancelEvent -> {
            deleteComponentsFromJDialog();
            dispose();
        });

        pack();
        setLocationRelativeTo(parentFrame);
        setVisible(true);
    } // Окно с фамилиями механиков.

    private void openTelegramSettingsFrame(Frame parentFrame) {
        setLayout(new GridBagLayout());

        JTextField botUserNameField = new JTextField(botUserName);
        JTextField botTokenField = new JTextField(botToken);
        JTextField chatIdField = new JTextField(chatId);

        JButton saveButton = new JButton("Сохранить");
        JButton cancelButton = new JButton("Отмена");

        add(new JLabel("Имя Бота: "), createGridBagConstraints(1, 0, 1, 1));
        add(botUserNameField, createGridBagConstraints(2, 0, 1, 1));
        add(new JLabel("Token бота: "), createGridBagConstraints(1, 1, 1, 1));
        add(botTokenField, createGridBagConstraints(2, 1, 1, 1));
        add(new JLabel("Chat_id: "), createGridBagConstraints(1, 2, 1, 1));
        add(chatIdField, createGridBagConstraints(2, 2, 1, 1));
        add(saveButton, createGridBagConstraints(1, 3, 1, 1));
        add(cancelButton, createGridBagConstraints(2, 3, 1, 1));

        saveButton.addActionListener(saveEvent -> {
            botUserName = botUserNameField.getText();
            botToken = botTokenField.getText();
            chatId = chatIdField.getText();
            botInfo.writeTelegramToFile(botUserName, botToken, chatId);
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
    } // Окно с информацией о боте и чате Телеграм

    private void openLkmPricesSettingsFrame(Frame parentFrame) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;

        CalculatorPanel calc = new CalculatorPanel();

        gbc.gridx = 3; // Устанавливаем столбец
        gbc.gridy = 0; // Устанавливаем строку
        gbc.gridheight = GridBagConstraints.REMAINDER; // Занимаем все оставшиеся строки
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Занимаем все оставшиеся столбцы
        gbc.fill = GridBagConstraints.BOTH; // Растягиваем по обеим осям
        gbc.weightx = 1; // Устанавливаем вес, чтобы компонент занимал всю доступную ширину



        JTextField circleField = new JTextField(String.valueOf(circle));
        JTextField stripField = new JTextField(String.valueOf(strip));
        JTextField scotchBriteField = new JTextField(String.valueOf(scotchBrite));
        JTextField primingField = new JTextField(String.valueOf(priming));
        JTextField clearField = new JTextField(String.valueOf(clear));
        JTextField baseDilutionField = new JTextField(String.valueOf(baseDilution));
        JTextField basePaintField = new JTextField(String.valueOf(basePaint));
        JTextField siliconRemoverField = new JTextField(String.valueOf(siliconRemover));
        JTextField stickyTapeField = new JTextField(String.valueOf(stickyTape));
        JTextField coveringFilmField = new JTextField(String.valueOf(coveringFilm));
        JTextField napkinField = new JTextField(String.valueOf(napkin));
        JTextField puttyFiberField = new JTextField(String.valueOf(puttyFiber));
        JTextField puttyUniversalField = new JTextField(String.valueOf(puttyUniversal));
        JTextField hermeticField = new JTextField(String.valueOf(hermetic));

        JButton saveButton = new JButton("Сохранить");
        JButton cancelButton = new JButton("Отмена");

        add(new JLabel("Круги: "), createGridBagConstraints(1, 0, 1, 1));
        add(circleField, createGridBagConstraints(2, 0, 1, 1));
        add(new JLabel("Полоски: "), createGridBagConstraints(1, 1, 1, 1));
        add(stripField, createGridBagConstraints(2, 1, 1, 1));
        add(new JLabel("СкотчБрайт: "), createGridBagConstraints(1, 2, 1, 1));
        add(scotchBriteField, createGridBagConstraints(2, 2, 1, 1));
        add(new JLabel("Грунт: "), createGridBagConstraints(1, 3, 1, 1));
        add(primingField, createGridBagConstraints(2, 3, 1, 1));
        add(new JLabel("Лак HS: "), createGridBagConstraints(1, 4, 1, 1));
        add(clearField, createGridBagConstraints(2, 4, 1, 1));
        add(new JLabel("Разбавитель: "), createGridBagConstraints(1, 5, 1, 1));
        add(baseDilutionField, createGridBagConstraints(2, 5, 1, 1));
        add(new JLabel("Эмаль: "), createGridBagConstraints(1, 6, 1, 1));
        add(basePaintField, createGridBagConstraints(2, 6, 1, 1));
        add(new JLabel("Обезжириватель: "), createGridBagConstraints(1, 7, 1, 1));
        add(siliconRemoverField, createGridBagConstraints(2, 7, 1, 1));
        add(new JLabel("Скотч: "), createGridBagConstraints(1, 8, 1, 1));
        add(stickyTapeField, createGridBagConstraints(2, 8, 1, 1));
        add(new JLabel("Пленка: "), createGridBagConstraints(1, 9, 1, 1));
        add(coveringFilmField, createGridBagConstraints(2, 9, 1, 1));
        add(new JLabel("Салфетки: "), createGridBagConstraints(1, 10, 1, 1));
        add(napkinField, createGridBagConstraints(2, 10, 1, 1));
        add(new JLabel("Шпатлевка Fiber: "), createGridBagConstraints(1, 11, 1, 1));
        add(puttyFiberField, createGridBagConstraints(2, 11, 1, 1));
        add(new JLabel("Шпатлевка Universal: "), createGridBagConstraints(1, 12, 1, 1));
        add(puttyUniversalField, createGridBagConstraints(2, 12, 1, 1));
        add(new JLabel("Герметик: "), createGridBagConstraints(1, 13, 1, 1));
        add(hermeticField, createGridBagConstraints(2, 13, 1, 1));
        add(saveButton, createGridBagConstraints(1, 14, 1, 1));
        add(cancelButton, createGridBagConstraints(2, 14, 1, 1));
        add(calc, gbc);

        saveButton.addActionListener(saveEvent -> {
            circle = Integer.parseInt(circleField.getText());
            strip = Integer.parseInt(stripField.getText());
            scotchBrite = Double.parseDouble(scotchBriteField.getText());
            priming = Double.parseDouble(primingField.getText());
            clear = Double.parseDouble(clearField.getText());
            baseDilution = Double.parseDouble(baseDilutionField.getText());
            basePaint = Double.parseDouble(basePaintField.getText());
            siliconRemover = Double.parseDouble(siliconRemoverField.getText());
            stickyTape = Double.parseDouble(stickyTapeField.getText());
            coveringFilm = Double.parseDouble(coveringFilmField.getText());
            napkin = Integer.parseInt(napkinField.getText());
            puttyFiber = Double.parseDouble(puttyFiberField.getText());
            puttyUniversal = Double.parseDouble(puttyUniversalField.getText());
            hermetic = Integer.parseInt(hermeticField.getText());

            lkmPrices.writePricesToFile(circle, strip, scotchBrite, priming, clear, baseDilution, basePaint, siliconRemover, stickyTape, coveringFilm, napkin, puttyFiber, puttyUniversal, hermetic);
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
    } // Окно с информацией ценами ЛКМ и калькулятором.


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