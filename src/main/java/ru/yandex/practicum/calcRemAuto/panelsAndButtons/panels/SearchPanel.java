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

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.calcRemAuto.logToFail.LogToFailManager;
import ru.yandex.practicum.calcRemAuto.model.NameDirectories;
import ru.yandex.practicum.calcRemAuto.panelsAndButtons.buttons.Buttons;
import ru.yandex.practicum.calcRemAuto.storage.OpenFolder;
import ru.yandex.practicum.calcRemAuto.storage.WorkWithFile;
import ru.yandex.practicum.calcRemAuto.telegram.TelegramFileSenderBot;

@Slf4j
public class SearchPanel {
    static LogToFailManager logManager = new LogToFailManager();
    NameDirectories directories = new NameDirectories();
    private final String FILE_NAME = directories.getSMETA() + directories.getTxt();  //  Имя файла "смета.txt"
    private static final String[] OPTIONS = {"Открыть папку", "Пересчитать", "Удалить", "Назад", "Telegram", "Отмена"};  // Кнопки
    JPanel panel;
    private final Buttons but = new Buttons(); // Класс с кнопками
    private final JTextField search = new JTextField(10); // Поле ввода гос/номера
    private String AUTOMOBILE_DIRECTORY; // Директория до расчетов "расчеты/госНомер"

    public void createSearchPanel(JPanel panel) {
        logManager.log("Запущен метод createSearchPanel в классе SearchPanel");
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
            logManager.log("Нажата кнопка Назад");
            FirstPanel firstPanel = new FirstPanel();
            panel.removeAll();
            firstPanel.createFirstPanel(panel);
        });

        but.getButtonSearch().addActionListener(e -> {
            logManager.log("Нажата кнопка Поиск. текст в графе гос/номера: " + search.getText());
            try {
                boolean allBordersAreGreen = Stream.of(search)
                        .map(component -> (LineBorder) component.getBorder())
                        .allMatch(border -> border.getLineColor().equals(Color.green));

                if (allBordersAreGreen) {
                    AUTOMOBILE_DIRECTORY = directories.getNAME_START_DIRECTORY() + directories.getSlash() + search.getText().toUpperCase();
                    searchAndDeleteFolder(search.getText());
                } else {
                    showErrorDialog("Неверный формат гос/номера!");
                    logManager.log("Открылось окно ошибки: Неверный формат гос/номера!");
                }
            } catch (Exception o) {
                showErrorDialog("Ошибка: " + o.getMessage());
                logManager.log("Отловили ошибку:" + o.getMessage());
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

            // Добавляем обработку нажатия клавиши Enter
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    but.getButtonSearch().doClick(); // Нажимаем кнопку программно
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
        logManager.log("Запущен метод searchAndDeleteFolder");
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
        logManager.log("Запущен метод selectAction.");
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
                    logManager.log("Произведен выбор: Открыть папку.");
                    OpenFolder openFolder = new OpenFolder();
                    openFolder.open(AUTOMOBILE_DIRECTORY + "/" + chosenFolderPath);
                }

                if (selection == 1) {
                    logManager.log("Произведен выбор: Пересчитать.");
                    WorkWithFile workWithFaile = new WorkWithFile();
                    File fileX = new File(AUTOMOBILE_DIRECTORY + "/" + chosenFolderPath);
                    workWithFaile.load(fileX, panel);
                }
                if (selection == 2) {
                    logManager.log("Произведен выбор: Удалить.");
                    String filePath = file.getPath();
                    Path path = Paths.get(filePath);
                    Path parentPath = path.getParent();

                    int confirm = showConfirmDialog(chosenFolder, "Вы уверенны что хотите удалить расчет от  ");

                    if (confirm == JOptionPane.YES_OPTION) {
                        boolean success = deleteFileOrDirectory(parentPath.toFile());
                        // Проверяем последний ли мы удалили расчет если да то удаляем и папку авто так как там нет расчетов
                        checkAndDeleteParentFolderIfEmpty(new File(AUTOMOBILE_DIRECTORY));

                        if (success) {
                            showInformationDialog("Расчет от " + chosenFolder + " удален.");
                            logManager.log("Расчет от " + chosenFolder + " удален.");
                        } else {
                            logManager.log("Не удалось удалить расчет от " + chosenFolder + ".");
                            showErrorDialog("Не удалось удалить расчет от " + chosenFolder + ".");
                        }
                    }
                }
                if (selection == 3) {
                    logManager.log("Произведен выбор: Назад.");
                    searchAndDeleteFolder(search.getText());
                }
                if (selection == 4) {
                    logManager.log("Произведен выбор: Тelegram.");
                    int result = showConfirmDialog(chosenFolder, "Отправить смету в telegram?");
                    if (result == JOptionPane.YES_OPTION) {
                        logManager.log("Произведен выбор: Да.");
                        sendSmeta(chosenFolderPath);
                    } else {
                        logManager.log("Произведен выбор: Нет.");
                        searchAndDeleteFolder(search.getText());
                    }
                }
            } catch (FileNotFoundException e) {
                logManager.log("Отловлена ошибка FileNotFoundException" + e.getMessage());
                showErrorDialog("Ошибка: Файл не найден.");
            }
        }
    }  // Обработка выбора метода "searchAndDeleteFolder"

    private void sendSmeta(String path) {
        logManager.log("Запущен метод sendSmeta.");
        TelegramFileSenderBot telegramFileSenderBot = new TelegramFileSenderBot();
        telegramFileSenderBot.sendFile(AUTOMOBILE_DIRECTORY + "/" + path);
    } // Отправка в телеграм смет

    private static boolean deleteFileOrDirectory(File fileOrDirectory) {
        logManager.log("Запущен метод deleteFileOrDirectory.");
        if (fileOrDirectory.exists()) {
            if (fileOrDirectory.isDirectory()) {
                // Получаем список файлов и поддиректорий
                File[] files = fileOrDirectory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        // Рекурсивно удаляем файлы и поддиректории
                        deleteFileOrDirectory(file);
                    }
                }
                // Проверяем, осталась ли директория пустой после удаления всего содержимого
                if (fileOrDirectory.list().length == 0) {
                    // Удаляем пустую директорию
                    return fileOrDirectory.delete();
                }
            } else {
                // Если это файл, просто удаляем его
                return fileOrDirectory.delete();
            }

            // Директория не была пустой, поэтому возвращаем true (удаление прошло успешно)
            return true;
        } else {
            showErrorDialog("Файл или папка не существует: " + fileOrDirectory.getPath());
            return false;
        }
    } // Метод удаления конкретной папки с расчетом

    private static void checkAndDeleteParentFolderIfEmpty(File parentFolder) {
        logManager.log("Запущен метод checkAndDeleteParentFolderIfEmpty.");
        if (parentFolder.exists() && parentFolder.isDirectory()) {
            // Получаем список файлов и поддиректорий в родительской папке
            File[] contents = parentFolder.listFiles();
            if (contents != null && contents.length == 0) {
                // Родительская папка пуста, удаляем её
                if (parentFolder.delete()) {
                    log.info("Удалена и родительская папка: " + parentFolder);
                    logManager.log("Удалена и родительская папка: " + parentFolder);
                } else {
                    log.error("Не удалось удалить родительскую папку: " + parentFolder);
                    logManager.log("Не удалось удалить родительскую папку: " + parentFolder);
                }
            }
        }
    } // Метод для проверки и возможного удаления родительской папки

    private Object showInputDialog(String message, String[] folderNames, File defaultFolder) {
        logManager.log("Запущен метод showInputDialog.Папка:" + folderNames.toString());
        return JOptionPane.showInputDialog(null, message, "Просмотр", JOptionPane.PLAIN_MESSAGE, null,
                folderNames, defaultFolder);
    } // Показ диалогового окна выбора авто и даты расчета

    private int showOptionDialog(String fileContent) {
        logManager.log("Запущен метод showOptionDialog.Показ диалогового окна со сметой");
        // Показываем JOptionPane с заданными размерами и позицией
        JOptionPane optionPane = new JOptionPane(
                wrapTextInScrollPane(fileContent),
                JOptionPane.PLAIN_MESSAGE,
                JOptionPane.DEFAULT_OPTION,
                null,
                OPTIONS,
                OPTIONS[0]
        );

        // Получаем родительское окно (JFrame)
        Window window = SwingUtilities.getWindowAncestor(panel);
        if (!(window instanceof JFrame)) {
            throw new IllegalStateException("Родительский component не является JFrame.");
        }
        JFrame frame = (JFrame) window;

        Dimension frameSize = frame.getSize();
        Point framePosition = frame.getLocationOnScreen();

        // Создаем JDialog для показа сообщения
        final JDialog dialog = optionPane.createDialog(panel, "Содержимое файла.");

        // Устанавливаем размеры и позицию окна
        dialog.setBounds(framePosition.x, framePosition.y, frameSize.width, frameSize.height);

        // Отображение окна
        dialog.setVisible(true);
        // Получаем выбранный пользователем вариант
        Object selectedOption = optionPane.getValue();
        // Находим индекс выбранного варианта
        for (int i = 0; i < OPTIONS.length; i++) {
            if (OPTIONS[i].equals(selectedOption)) {
                return i;
            }
        }
        // Если ничего не выбрано, возвращаем -1
        return -1;
    } // Показ диалогового окна со сметой

    private int showConfirmDialog(Object chosenFolder, String text) {
        logManager.log("Запущен метод showConfirmDialog.Выбор:" + text + chosenFolder);
        return JOptionPane.showConfirmDialog(null, text + chosenFolder + "?",
                "Подтверждение удаления.", JOptionPane.YES_NO_OPTION);
    } // Показ диалогового

    private static void showMessageDialog(String message) {
        logManager.log("Запущен метод showMessageDialog.Сообщение: " + message);
        JOptionPane.showMessageDialog(null, message);
    } // Показ диалогового

    private static void showInformationDialog(String message) {
        logManager.log("Запущен метод showInformationDialog.Сообщение: " + message);
        JOptionPane.showMessageDialog(null, message, "Успех", JOptionPane.INFORMATION_MESSAGE);
    } // Показ диалогового

    private static void showErrorDialog(String message) {
        logManager.log("Запущен метод showErrorDialog.Сообщение: " + message);
        JOptionPane.showMessageDialog(null, message, "Ошибка", JOptionPane.ERROR_MESSAGE);
    } // Показ диалогового

    private Component wrapTextInScrollPane(String text) {
        logManager.log("Запущен метод wrapTextInScrollPane.Список: " + text);
        JTextArea textArea = new JTextArea(text);
        textArea.setLineWrap(true);      // Включаем перенос строк
        textArea.setWrapStyleWord(true); // Перенос по словам
        textArea.setEditable(false);     // Запрещаем редактирование

        // Определяем максимальную ширину текста
        int maxWidth = panel.getWidth() - 50; // Уменьшаем ширину на 50 пикселей для отступов

        // Создаем JScrollPane с текстовым полем внутри
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(maxWidth, Short.MAX_VALUE)); // Ограничиваем ширину

        return scrollPane;
    } // Оборачивает текст в JScrollPane и устанавливает максимальную ширину для showOptionDialog
}