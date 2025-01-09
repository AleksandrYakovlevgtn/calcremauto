package ru.yandex.practicum.calcRemAuto.panelsAndButtons.panels;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Stream;
import java.util.List;

import ru.yandex.practicum.calcRemAuto.model.NameDirectories;
import ru.yandex.practicum.calcRemAuto.panelsAndButtons.buttons.Buttons;
import ru.yandex.practicum.calcRemAuto.storage.OpenFolder;
import ru.yandex.practicum.calcRemAuto.storage.WorkWithFile;
import ru.yandex.practicum.calcRemAuto.telegram.TelegramFileSenderBot;


public class SearchPanel {
    NameDirectories directories = new NameDirectories();
    private final String FILE_NAME = directories.getSMETA() + directories.getTxt();  //  Имя файла "смета.txt"
    private static final String[] OPTIONS = {"Открыть папку", "Пересчитать", "Удалить", "Назад", "Telegram", "Отмена"};  // Кнопки
    JPanel panel;
    private final Buttons but = new Buttons(); // Класс с кнопками
    private final JTextField search = new JTextField(10); // Поле ввода гос/номера
    private String AUTOMOBILE_DIRECTORY; // Директория до расчетов "расчеты/госНомер"

    public void createSearchPanel(JPanel panel) {
        this.panel = panel;
        addKeyListener();
        addDocumentListener();
        panel.removeAll();
        panel.setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel(new GridBagLayout());
        JPanel searchPanelXYZPanel = new JPanel(new BorderLayout());

        JPanel buttonsPanel = new JPanel(new GridBagLayout());
        JPanel buttonsXYZPanel = new JPanel(new BorderLayout());

        searchPanel.add(but.getButtonSearch(), new GridBagConstraints(1, 4, 1, 1, 1, 1,
                GridBagConstraints.EAST, GridBagConstraints.NONE,
                new Insets(2, 2, 2, 2), 30, 1));

        searchPanel.add(search, new GridBagConstraints(2, 4, 1, 1, 1, 1,
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                new Insets(2, 2, 2, 2), 1, 10));

        buttonsPanel.add(but.getButtonBack(), new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 10, 10), 10, 0));

        buttonsXYZPanel.add(buttonsPanel, BorderLayout.EAST);
        panel.add(buttonsXYZPanel, BorderLayout.SOUTH);

        searchPanelXYZPanel.add(searchPanel, BorderLayout.CENTER);
        panel.add(searchPanelXYZPanel, BorderLayout.CENTER);

        panel.updateUI();

        but.getButtonBack().addActionListener(e -> {
            FirstPanel firstPanel = new FirstPanel();
            panel.removeAll();
            firstPanel.createFirstPanel(panel);
        });

        but.getButtonSearch().addActionListener(e -> {
            try {
                boolean allBordersAreGreen = Stream.of(search)
                        .map(component -> (LineBorder) component.getBorder())
                        .allMatch(border -> border.getLineColor().equals(Color.green));

                if (allBordersAreGreen) {
                    AUTOMOBILE_DIRECTORY = directories.getNAME_START_DIRECTORY() + directories.getSlash() + search.getText().toUpperCase();
                    searchAndDeleteFolder(search.getText());
                } else {
                    showErrorDialog("Неверный формат гос/номера!");
                }
            } catch (Exception o) {
                showErrorDialog("Необходимо заполнить поле гос/номера!");
            }
        });
    } // Создание окна поиска

    private void addKeyListener() {
        search.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                String text = search.getText();
                if (text.length() == 0) {
                    if (!Character.isLetter(c)) {
                        e.consume();
                    }
                } else if (text.length() >= 1 && text.length() <= 3) {
                    if (!Character.isDigit(c)) {
                        e.consume();
                    }
                } else if (text.length() >= 4 && text.length() <= 5) {
                    if (!Character.isLetter(c)) {
                        e.consume();
                    }
                } else if (text.length() >= 6) {
                    if (!Character.isDigit(c) || search.getText().length() >= 9) {
                        e.consume();
                    }
                } else {
                    e.consume();
                }
            }
        });
    } // Создание и добавление слушателя поля ввода гос/номера для правильного приема символов

    private void addDocumentListener() {
        search.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void removeUpdate(DocumentEvent e) {
                updateBorderColor();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                updateBorderColor();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateBorderColor();
            }
        });
    } // Создание слушателя поля ввода для отображения цвета рамки

    private void updateBorderColor() {
        if (search.getText().length() < 3) {
            search.setBorder(BorderFactory.createLineBorder(Color.red));
        } else {
            search.setBorder(BorderFactory.createLineBorder(Color.green));
        }
    } // Метод обновления цвета рамки

    private void searchAndDeleteFolder(String partialFolderName) {
        File startDirectory = new File(directories.getNAME_START_DIRECTORY());

        if (startDirectory.exists() && startDirectory.isDirectory()) {
            // Получаем список всех папок в стартовой директории
            File[] allFolders = startDirectory.listFiles(File::isDirectory);

            if (allFolders != null && allFolders.length > 0) {
                List<File> matchingFolders = new ArrayList<>();

                for (File folder : allFolders) {
                    if (folder.getName().toUpperCase().contains(partialFolderName.toUpperCase())) {
                        matchingFolders.add(folder);
                    }
                }

                if (!matchingFolders.isEmpty()) {
                    File selectedFolder = null;

                    if (matchingFolders.size() == 1) {
                        // Если найдена только одна папка
                        selectedFolder = matchingFolders.get(0);
                    } else {
                        // Если найдено несколько папок, предлагаем пользователю выбрать одну
                        String[] matchingFolderNames = matchingFolders.stream()
                                .map(File::getName)
                                .toArray(String[]::new);

                        // Используем метод showInputDialog для выбора папки
                        Object chosenFolderName = showInputDialog(
                                "Найдено несколько папок, выберите нужную:",
                                matchingFolderNames,
                                matchingFolders.get(0)  // передаем первый элемент в качестве значения по умолчанию
                        );

                        if (chosenFolderName != null) {
                            selectedFolder = matchingFolders.stream()
                                    .filter(folder -> folder.getName().equals(chosenFolderName.toString()))
                                    .findFirst()
                                    .orElse(null);
                        }
                    }

                    if (selectedFolder != null) {
                        File[] subFolders = selectedFolder.listFiles(File::isDirectory);
                        if (subFolders != null && subFolders.length > 0) {
                            StringBuilder message = new StringBuilder("На автомобиль " + selectedFolder.getName() + " есть ранние расчеты:\n");

                            String[] folderNames = Arrays.stream(subFolders)
                                    .map(File::getName)
                                    .toArray(String[]::new);

                            // Используем метод showInputDialog для выбора подпапки
                            Object chosenSubFolder = showInputDialog((message + "\nОт какой даты открыть расчет?"), folderNames, subFolders[0]);
                            AUTOMOBILE_DIRECTORY = directories.getNAME_START_DIRECTORY() + directories.getSlash() + selectedFolder.getName().toUpperCase();

                            selectAction(chosenSubFolder);
                        } else {
                            showMessageDialog("Ранних расчетов автомобиля " + selectedFolder.getName() + " не найдено.");
                        }
                    }
                } else {
                    showMessageDialog("Ранних расчетов автомобиля " + partialFolderName.toUpperCase() + " не найдено.");
                }
            } else {
                showMessageDialog("Ранних расчетов автомобиля " + partialFolderName.toUpperCase() + " не найдено.");
            }
        } else {
            showMessageDialog("Ранних расчетов автомобиля " + partialFolderName.toUpperCase() + " не найдено.");
        }
    }// Открытие диалогового поля выбора расчета из найденных по гос/номера

    private void selectAction(Object chosenFolder) {
        String chosenFolderPath = (chosenFolder != null) ? chosenFolder.toString() : "";

        if (chosenFolder != null) {
            try {
                File file = new File(AUTOMOBILE_DIRECTORY + "/" + chosenFolderPath + "/" + FILE_NAME);
                Scanner scanner = new Scanner(file);
                StringBuilder fileContent = new StringBuilder();

                while (scanner.hasNextLine()) {
                    String data = scanner.nextLine();
                    fileContent.append(data).append("\n");
                }

                scanner.close();

                int selection = showOptionDialog(fileContent.toString());
                if (selection == 0) {
                    OpenFolder openFolder = new OpenFolder();
                    openFolder.open(AUTOMOBILE_DIRECTORY + "/" + chosenFolderPath);
                }

                if (selection == 1) {
                    WorkWithFile workWithFaile = new WorkWithFile();
                    File fileX = new File(AUTOMOBILE_DIRECTORY + "/" + chosenFolderPath);
                    workWithFaile.load(fileX, panel);
                }
                if (selection == 2) {
                    String filePath = file.getPath();
                    Path path = Paths.get(filePath);
                    Path parentPath = path.getParent();

                    int confirm = showConfirmDialog(chosenFolder, "Вы уверенны что хотите удалить расчет от  ");

                    if (confirm == JOptionPane.YES_OPTION) {
                        boolean success = deleteFileOrDirectory(parentPath.toFile());

                        if (success) {
                            showInformationDialog("Расчет от " + chosenFolder + " удален.");
                        } else {
                            showErrorDialog("Не удалось удалить расчет от " + chosenFolder + ".");
                        }
                    }
                }
                if (selection == 3) {
                    searchAndDeleteFolder(search.getText());
                }
                if (selection == 4) {
                    int result = showConfirmDialog(chosenFolder, "Отправить смету в telegram?");
                    if (result == JOptionPane.YES_OPTION) {
                        sendSmeta(chosenFolderPath);
                    } else {
                        searchAndDeleteFolder(search.getText());
                    }
                }
            } catch (FileNotFoundException e) {
                showErrorDialog("Ошибка: Файл не найден.");
            }
        }
    }  // Обработка выбора метода "searchAndDeleteFolder"

    private void sendSmeta(String path) {
        TelegramFileSenderBot telegramFileSenderBot = new TelegramFileSenderBot();
        telegramFileSenderBot.sendFile(AUTOMOBILE_DIRECTORY + "/" + path);
    } // Отправка в телеграм смет

    private static boolean deleteFileOrDirectory(File fileOrDirectory) {
        if (fileOrDirectory.exists()) {
            if (fileOrDirectory.isDirectory()) {
                File[] files = fileOrDirectory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        deleteFileOrDirectory(file);
                    }
                }
            }
            return fileOrDirectory.delete();
        } else {
            showErrorDialog("Файл или папка не существует: " + fileOrDirectory.getPath());
            return false;
        }
    } // Метод удаления папки расчета

    private Object showInputDialog(String message, String[] folderNames, File defaultFolder) {
        return JOptionPane.showInputDialog(null, message, "Просмотр", JOptionPane.PLAIN_MESSAGE, null,
                folderNames, defaultFolder);
    } // Показ диалогового окна выбора авто и даты расчета

    /*public static Object showInputDialog(String message, String[] folderNames, File defaultFolder) {
        // Создайте новый JDialog
        JDialog dialog = new JDialog((JFrame) null, "Просмотр", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(null); // Центрируйте окно на экране

        // Создайте панель для отображения сообщения
        JPanel messagePanel = new JPanel();
        JLabel label = new JLabel("<html><div style='text-align: center;'>" + message + "</div></html>");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setPreferredSize(new Dimension(280, 50)); // Настройка размера для переносов
        messagePanel.add(label);

        // Создайте панель для выбора
        JPanel selectionPanel = new JPanel();
        JComboBox<String> comboBox = new JComboBox<>(folderNames);
        comboBox.setSelectedItem(defaultFolder.getName());
        selectionPanel.add(comboBox);

        // Создайте панель для кнопки OK
        JPanel buttonPanel = new JPanel();
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(okButton);

        // Добавьте панели в диалоговое окно
        dialog.add(messagePanel, BorderLayout.NORTH);
        dialog.add(selectionPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        // Закройте диалог при нажатии на крестик
        dialog.setDefaultCloseOperation();

        // Отобразите диалог и дождитесь результата
        dialog.setVisible(true);

        // Получите выбранное значение
        return comboBox.getSelectedItem();
    }*/






    private int showOptionDialog(String fileContent) {
        return JOptionPane.showOptionDialog(null, fileContent, "Содержимое файла.", JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, OPTIONS, OPTIONS[0]);
    } // Показ диалогового окна со сметой

    private int showConfirmDialog(Object chosenFolder, String text) {
        return JOptionPane.showConfirmDialog(null, text + chosenFolder + "?",
                "Подтверждение удаления.", JOptionPane.YES_NO_OPTION);
    } // Показ диалогового

    private static void showMessageDialog(String message) {
        JOptionPane.showMessageDialog(null, message);
    } // Показ диалогового

    private static void showInformationDialog(String message) {
        JOptionPane.showMessageDialog(null, message, "Успех", JOptionPane.INFORMATION_MESSAGE);
    } // Показ диалогового

    private static void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(null, message, "Ошибка", JOptionPane.ERROR_MESSAGE);
    } // Показ диалогового
}