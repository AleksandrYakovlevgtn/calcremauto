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
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Stream;

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
        if (search.getText().length() < 8) {
            search.setBorder(BorderFactory.createLineBorder(Color.red));
        } else {
            search.setBorder(BorderFactory.createLineBorder(Color.green));
        }
    } // Метод обновления цвета рамки

    private void searchAndDeleteFolder(String folderName) {
        File folder = new File(directories.getNAME_START_DIRECTORY() + directories.getSlash() + folderName);

        if (folder.exists() && folder.isDirectory()) {
            File[] subFolders = folder.listFiles(File::isDirectory);

            if (subFolders != null && subFolders.length > 0) {
                StringBuilder message = new StringBuilder("На автомобиль " + folderName + " есть ранние расчеты:\n");

                for (File subFolder : subFolders) {
                    message.append(subFolder.getName()).append("\n");
                }

                String[] folderNames = Arrays.stream(subFolders)
                        .map(folderi -> folderi.getName().substring(folderi.getName().lastIndexOf(File.separator) + 1))
                        .toArray(String[]::new);

                Object chosenFolder = showInputDialog((message + "\nОт какой даты открыть расчет?"), folderNames, subFolders[0]);

                selectAction(chosenFolder);

            } else {
                showMessageDialog("Ранних расчетов автомобиля  " + folderName + " не найдено.");
            }
        } else {
            showMessageDialog("Ранних расчетов автомобиля  " + folderName + " не найдено.");
        }
    } // Открытие диалогового поля выбора расчета из найденных по гос/номера

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
    } // Показ диалогового

    private int showOptionDialog(String fileContent) {
        return JOptionPane.showOptionDialog(null, fileContent, "Содержимое файла.", JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, OPTIONS, OPTIONS[0]);
    } // Показ диалогового

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