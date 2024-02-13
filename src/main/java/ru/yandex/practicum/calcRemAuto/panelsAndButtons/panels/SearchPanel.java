package ru.yandex.practicum.calcRemAuto.panelsAndButtons.panels;

import ru.yandex.practicum.calcRemAuto.model.Client;
import ru.yandex.practicum.calcRemAuto.model.Element;
import ru.yandex.practicum.calcRemAuto.model.Total;
import ru.yandex.practicum.calcRemAuto.panelsAndButtons.buttons.Buttons;
import ru.yandex.practicum.calcRemAuto.storage.SaveInFail;

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
import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.Stream;

import static ru.yandex.practicum.calcRemAuto.storage.SaveInFail.nameStartDirectory;

public class SearchPanel {
    Buttons but = new Buttons();
    JTextField search = new JTextField(10);
    String nameAutomobileDirectory;

    public void SearchPanel(JPanel panel) {
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
            firstPanel.firstPanel(panel);
        }); // Кнопка назад возвращает на стартовый экран
        but.getButtonSearch().addActionListener(e -> {
            try {
                boolean allBordersAreGreen = Stream.of(search)
                        .map(component -> (LineBorder) component.getBorder())
                        .allMatch(border -> border.getLineColor().equals(Color.green));

                if (allBordersAreGreen) {
                    nameAutomobileDirectory  = nameStartDirectory + "/" + search.getText().toUpperCase();
                    searchAndDeleteFolder(search.getText());
                }else{
                    JOptionPane.showMessageDialog(null, "Неверный формат гос/номера!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception o) {
                JOptionPane.showMessageDialog(null, "Необходимо заполнить поле гос/номера!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }); // Кнопка поиска производит поиск и открывает диалоговое окна для действий.
    }

    private void addKeyListener() {
        search.addKeyListener(new KeyAdapter() {
            // Ввод гос/номера должен быть в формате
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                String text = search.getText();
                if (text.length() == 0) {
                    // Первый символ буква
                    if (!Character.isLetter(c)) {
                        e.consume();
                    }
                } else if (text.length() >= 1 && text.length() <= 3) {
                    // Следующие 3 это цифры
                    if (!Character.isDigit(c)) {
                        e.consume();
                    }
                } else if (text.length() >= 4 && text.length() <= 5) {
                    // Пятый и шестой символ буквы
                    if (!Character.isLetter(c)) {
                        e.consume();
                    }
                } else if (text.length() >= 6) {
                    // Последние два или три опять цифры
                    if (!Character.isDigit(c) || search.getText().length() >= 9) {
                        e.consume();
                    }
                } else {
                    e.consume();
                }
            }
        });
    }  // Добавление слушателя нажатий клавиш
    private void addDocumentListener(){
        search.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void removeUpdate(DocumentEvent e) {
                // в зависимости от количества символов цвет строки изменяется.
                if (search.getText().length() < 8) {
                    search.setBorder(BorderFactory.createLineBorder(Color.red));
                } else {
                    search.setBorder(BorderFactory.createLineBorder(Color.green));
                }
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                // в зависимости от количества символов цвет строки изменяется.
                if (search.getText().length() < 8) {
                    search.setBorder(BorderFactory.createLineBorder(Color.red));
                } else {
                    search.setBorder(BorderFactory.createLineBorder(Color.green));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // в зависимости от количества символов цвет строки изменяется.
                if (search.getText().length() < 8) {
                    search.setBorder(BorderFactory.createLineBorder(Color.red));
                } else {
                    search.setBorder(BorderFactory.createLineBorder(Color.green));
                }
            }
        });
    } // Добавление слушателя содержимого с добавлением цвета рамки в зависимости от содержания

    public void searchAndDeleteFolder(String folderName) {
        File folder = new File(nameStartDirectory + "/" + folderName); // Здесь указывается начальная директория поиска

        if (folder.exists() && folder.isDirectory()) {
            File[] subFolders = folder.listFiles(File::isDirectory);

            if (subFolders != null && subFolders.length > 0) {
                StringBuilder message = new StringBuilder("На автомобиль " + folderName + " есть ранние расчеты:\n");

                for (File subFolder : subFolders) {
                    message.append(subFolder.getName()).append("\n");
                }

                message.append("\nХотите просмотреть?");
                int option = JOptionPane.showConfirmDialog(null, message, "Поиск", JOptionPane.YES_NO_OPTION);

                if (option == JOptionPane.YES_OPTION) {
                    selectAction(subFolders);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Ранних расчетов автомобиля  " + folderName + " не найдено.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ранних расчетов автомобиля  " + folderName + " не найдено.");
        }
    } // Метод поиска папок !расчетов! для просмотра и дальнейшей возможности удаления.

    public void selectAction(File[] subFolders) {
        String[] folderNames = Arrays.stream(subFolders)
                .map(folder -> folder.getName().substring(0, Math.min(folder.getName().length(), 10))) // Измените крайнее значение на нужную вам длину
                .toArray(String[]::new); // Сокращаем вывод папки до значения даты

        String[] options = new String[]{"Открыть", "Удалить", "Отмена"}; // Кнопки действий

        Object chosenFolder = JOptionPane.showInputDialog(null, "От какой даты открыть расчет?",
                "Просмотр", JOptionPane.PLAIN_MESSAGE, null,
                folderNames, subFolders[0]); // Окно выбора даты расчета
        String chosenFolderPath = (chosenFolder != null) ? chosenFolder.toString() : "";

        if (chosenFolder != null) {
            try {
                // Укажите путь к файлу
                File file = new File(nameAutomobileDirectory + "/" + chosenFolderPath + "/" + "смета.txt");
                // Создать экземпляр Scanner для чтения файла
                Scanner scanner = new Scanner(file);
                // Переменная для хранения содержимого файла
                StringBuilder fileContent = new StringBuilder();

                while (scanner.hasNextLine()) {
                    String data = scanner.nextLine();
                    fileContent.append(data).append("\n");
                }
                // Закрыть Scanner
                scanner.close();
                // Отобразить содержимое файла в диалоговом окне
                int selections = JOptionPane.showOptionDialog(null, fileContent.toString(),
                        "Содержимое файла.", JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, new String[]{options[0],options[1], options[2]}, options[0]); // окно действия
                if (selections == 0){
                    System.out.println("***************************");
                    SaveInFail saveInFail = new SaveInFail();
                    File fileX = new File(nameAutomobileDirectory + "/" + chosenFolderPath);
                    saveInFail.loud(fileX);
                }
                if (selections == 1) { // Код, который выполнится при нажатии "Удалить"
                    String filePath = file.getPath();
                    Path path = Paths.get(filePath);
                    // Получение родительского каталога (без названия файла)
                    Path parentPath = path.getParent();

                    int confirm = JOptionPane.showConfirmDialog(null, "Вы уверенны что хотите удалить расчет от  " + chosenFolder + "?",
                            "Подтверждение удаления.", JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        // Удаление файла или папки
                        boolean success = deleteFileOrDirectory(parentPath.toFile());
                        // Показываем диалоговое окно после успешного удаления
                        if (success) {
                            JOptionPane.showMessageDialog(null, "Расчет от " + chosenFolder + " удален.", "Успех", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Не удалось удалить расчет от " + chosenFolder + ".", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("Ошибка: Файл не найден.");
            }
        }
    } // Метод выбора действий метода !searchAndDeleteFolder!

    private static boolean deleteFileOrDirectory(File fileOrDirectory) {
        if (fileOrDirectory.exists()) {
            if (fileOrDirectory.isDirectory()) {
                // Если это директория, удаляем все файлы внутри
                File[] files = fileOrDirectory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        // Рекурсивно вызываем удаление для каждого файла
                        deleteFileOrDirectory(file);
                    }
                }
            }
            // Удаление файла или папки
            return fileOrDirectory.delete();
        } else {
            System.out.println("Файл или папка не существует: " + fileOrDirectory.getPath());
            return false;
        }
    } // Метод удаления папки расчета конкретного автомобиля по дате(имени папки).
}