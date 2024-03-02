package ru.yandex.practicum.calcRemAuto.panelsAndButtons.frame;

import lombok.Getter;
import ru.yandex.practicum.calcRemAuto.model.Mechanics;
import ru.yandex.practicum.calcRemAuto.model.Prices;
import ru.yandex.practicum.calcRemAuto.model.TelegramBotInfo;

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
    private int hourlyRate; // Стоимость общего н/ч
    private int mechanicHourlyRate; // Стоимость норматива механика
    private int masterHourlyRate;  // Стоимость норматива мастера
    private String malyr;
    private String armoturchik;
    private String kuzovchik;
    private String botUserName;
    private String botToken;
    private String chatId;

    Prices prices = new Prices();  // Класс с ценами
    Mechanics mechanics = new Mechanics(); // Класс с фамилиями механиков
    TelegramBotInfo botInfo = new TelegramBotInfo();

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

        botUserName = botInfo.getBotUserName();
        botToken = botInfo.getBotToken();
        chatId = botInfo.getChatId();

        changeRatesItem.addActionListener(e -> openRatesFrame(parentFrame));
        setMechanicsItem.addActionListener(e -> openMechanicsFrame(parentFrame));
        telegramSettings.addActionListener(e -> openTelegramSettingsFrame(parentFrame));

        settingsMenu.add(changeRatesItem);
        settingsMenu.add(setMechanicsItem);
        settingsMenu.add(telegramSettings);
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

        JButton saveMechanicsButton = new JButton("Сохранить");
        JButton cancelMechanicsButton = new JButton("Отмена");

        add(new JLabel("Маляр:"), createGridBagConstraints(1, 0, 1, 1));
        add(painterField, createGridBagConstraints(2, 0, 1, 1));
        add(new JLabel("Арматурщик:"), createGridBagConstraints(1, 1, 1, 1));
        add(reinforcementWorkerField, createGridBagConstraints(2, 1, 1, 1));
        add(new JLabel("Кузовщик:"), createGridBagConstraints(1, 2, 1, 1));
        add(bodyWorkerField, createGridBagConstraints(2, 2, 1, 1));
        add(saveMechanicsButton, createGridBagConstraints(1, 3, 1, 1));
        add(cancelMechanicsButton, createGridBagConstraints(2, 3, 1, 1));

        saveMechanicsButton.addActionListener(saveEvent -> {
            malyr = painterField.getText();
            armoturchik = reinforcementWorkerField.getText();
            kuzovchik = bodyWorkerField.getText();
            mechanics.writeRatesToFile(malyr, armoturchik, kuzovchik);
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

    private void openTelegramSettingsFrame(Frame parentFrame){
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