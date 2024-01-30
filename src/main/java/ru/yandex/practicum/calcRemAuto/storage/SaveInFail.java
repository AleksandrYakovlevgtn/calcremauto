package ru.yandex.practicum.calcRemAuto.storage;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.calcRemAuto.model.Client;
import ru.yandex.practicum.calcRemAuto.model.Element;
import ru.yandex.practicum.calcRemAuto.model.Total;
import ru.yandex.practicum.calcRemAuto.panelsAndButtons.buttons.Buttons;

import javax.swing.*;
import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

@Slf4j
public class SaveInFail {
    Buttons but = new Buttons();
    Client client;
    Map<String, Map<String, List<String>>> lineBorderColorMap;
    Total total;
    List<Element> elements;
    static String nameStartDirectory = ("Works"); // Начальная директория
    String nameAutomobileDirectory; // директория начальная/госНомер

    public SaveInFail(Client client, Total total, List<Element> elements, Map<String, Map<String, List<String>>> lineBorderColorMap) {
        this.client = client;
        this.lineBorderColorMap = lineBorderColorMap;
        this.total = total;
        this.elements = elements;
        nameAutomobileDirectory = (nameStartDirectory + "/" + client.getNumberAuto().toUpperCase());
    } // Конструктор

    @SneakyThrows
    public void save(String text) {
        String dateDirectory = (nameAutomobileDirectory + "/" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd"))); // директория начальная/госНомер/датаОсмотра
        String officialDirectory = (dateDirectory + "/служебная"); // директория начальная/госНомер/датаОсмотра/служебная. Для записи файлов для загрузки из файлов.

        if (!Files.exists(Path.of(nameStartDirectory))) {
            // Если стартовой директории нет, то необходимо её создать.
            Files.createDirectory(Path.of(nameStartDirectory));
        }
        try {
            if (!Files.exists(Path.of(nameAutomobileDirectory))) {
                // Если папки с госНомером не существует, то создаем её.
                Files.createDirectory(Path.of(nameAutomobileDirectory));
            }
            if (!Files.exists(Path.of(dateDirectory))) {
                // Если в папке с госНомером папки с датой осмотра не существует, то создаем её.
                Files.createDirectory(Path.of(dateDirectory));
            }
            if (Files.exists(Path.of(dateDirectory + "/смета.txt"))) {
                // Если в папке с госНомером с датой осмотра существует файл сметы, то удаляем его для замены новым.
                Files.delete(Path.of(dateDirectory + "/смета.txt"));
            }
            if (!Files.exists(Path.of(officialDirectory))) {
                // Если в папке с госНомером папки с датой осмотра не существует, то создаем её.
                Files.createDirectory(Path.of(officialDirectory));
            }
            PrintWriter out = new PrintWriter(dateDirectory + "/смета.txt"); // Основная смета для мастера
            PrintWriter dataClient = new PrintWriter(officialDirectory + "/клиент.txt"); // файл с клиентом.
            PrintWriter dataTotal = new PrintWriter(officialDirectory + "/итого.txt"); // файл с итого.

            out.println(text);
            dataClient.write(toStringClient(client));
            dataTotal.write(toStringTotal(total));

            saveListElements(dateDirectory, officialDirectory);
            saveMap(officialDirectory);

            out.close();
            dataClient.close();
            dataTotal.close();
            //searchAndDeleteFolder(client.getNumberAuto().toUpperCase());

        } catch (FileNotFoundException | FileAlreadyExistsException o) {
            log.error(o.getMessage());
        }
    }

    private String toStringElement(Element element) {
        String[] words = {element.getName(),
                String.valueOf(element.getPaintSide()),
                String.valueOf(element.getArmatureSide()),
                String.valueOf(element.getKuzDetReplaceSide()),
                String.valueOf(element.getGlass()),
                element.getNameGlass(),
                String.valueOf(element.getZerkalo()),
                String.valueOf(element.getMolding()),
                String.valueOf(element.getRuchka()),
                String.valueOf(element.getOverlay()),
                String.valueOf(element.getExpander()),
                String.valueOf(element.getRemont()),
                String.valueOf(element.getTotal())};
        return String.join(",", words);
    }

    private String toStringTotal(Total total) {
        String[] words = {String.valueOf(total.getMalyr()),
                String.valueOf(total.getArmatyrchik()),
                String.valueOf(total.getKuzovchik()),
                String.valueOf(total.getMaster()),
                String.valueOf(total.getTotal())};
        return String.join(",", words);
    }

    private String toStringClient(Client client) {
        String[] words = {client.getName(),
                client.getFoneNumber(),
                client.getNumberAuto(),
                client.getModelAuto()};
        return String.join(",", words);
    }

    @SneakyThrows
    private void saveListElements(String dateDirectory, String officialDirectory) throws FileNotFoundException, FileAlreadyExistsException {
        PrintWriter dataElementList = new PrintWriter(officialDirectory + "/список_элементов.txt"); // файл с сохраненным списком элементов
        PrintWriter malyr = new PrintWriter(dateDirectory + "/маляр.txt"); // смета упрощенная для маляра
        PrintWriter armoterchik = new PrintWriter(dateDirectory + "/арматурщик.txt"); // смета упрощенная для арматурщика

        for (Element element : elements) {
            /*
            Производим запись в файлы смет для разных исполнителей.
             */
            malyr.write(element.getName() + " " + element.getPaintSide() + "н/ч");
            armoterchik.write(element.getName() + " " + element.getArmatureSide() + "н/ч");
            // Записываем в файл (рабочий) "список_элементов" элементы через ","
            dataElementList.write(toStringElement(element) + "\n");

            if (total.getKuzovchik() > 0) {
                PrintWriter kuzovchik = new PrintWriter(dateDirectory + "/кузовщик.txt"); // смета упрощенная для кузовщика
                kuzovchik.write(element.getName() + " " + element.getKuzDetReplaceSide() + "н/ч " + (element.getKuzDetReplaceSide() * 750) + "руб.\n");
                kuzovchik.close();
            }
            if (element.getRemont() > 0) malyr.write(" ремонт: " + element.getRemont() + "н/ч");
            if (element.getRuchka() > 0) {
                malyr.write(" ручка");
                armoterchik.write(" ручка");
            }
            if (element.getZerkalo() > 0) {
                malyr.write(" зеркало");
                armoterchik.write(" зеркало");
            }
            if (element.getMolding() > 0) {
                malyr.write(" молдинг");
                armoterchik.write(" молдинг");
            }
            if (element.getOverlay() > 0) {
                malyr.write(" накладка");
                armoterchik.write(" накладка");
            }
            if (element.getExpander() > 0) {
                malyr.write(" расширитель");
                armoterchik.write(" расширитель");
            }
            if (element.getMolding() > 0 || element.getRuchka() > 0 || element.getZerkalo() > 0 || element.getOverlay() > 0 || element.getExpander() > 0) {
                malyr.write(" окраска");
                armoterchik.write(" c/у под окрас");
            }
            malyr.write(" итого: " + (int) ((element.getPaintSide()
                    + element.getRemont()
                    + element.getRuchka()
                    + element.getZerkalo()
                    + element.getMolding()
                    + element.getOverlay()
                    + element.getExpander()) * 750) + "руб.\n");
            if (element.getGlass() > 0) {
                armoterchik.write(element.getNameGlass());
            }
            armoterchik.write(" итого: " + (int) ((element.getArmatureSide() + element.getGlass()) * 750) + "руб.\n");
        }
        malyr.close();
        armoterchik.close();
        dataElementList.close();
    }

    private void saveMap(String officialDirectory) {
        try {
            // Блок кода написан при помощи chatGPD
            FileWriter fileWriter = new FileWriter(officialDirectory + "/таблица_работ.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (Map.Entry<String, Map<String, List<String>>> entry : lineBorderColorMap.entrySet()) {
                bufferedWriter.write(entry.getKey() + "\n");
                for (Map.Entry<String, List<String>> innerEntry : entry.getValue().entrySet()) {
                    bufferedWriter.write(innerEntry.getKey() + "\n");
                    for (String color : innerEntry.getValue()) {
                        bufferedWriter.write(color + "\n");
                    }
                }
                bufferedWriter.write("---\n");
            }
            bufferedWriter.close();
            // Конец блока кода написанного при помощи chatGPD
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private Map<String, Map<String, List<String>>> loudMap(String officialDirectory) {
        // Блок кода написан при помощи chatGPD
        Map<String, Map<String, List<String>>> restoredMap = new HashMap<>();
        try {
            FileReader fileReader = new FileReader(officialDirectory + "/таблица_работ.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            String mapKey = null;
            String innerMapKey = null;
            List<String> colorList = new ArrayList<>();
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.equals("---")) {
                    if (innerMapKey != null) {
                        colorList.add(line);
                    } else if (mapKey != null) {
                        innerMapKey = line;
                        colorList = new ArrayList<>();
                    } else {
                        mapKey = line;
                    }
                } else {
                    if (mapKey != null && innerMapKey != null) {
                        restoredMap.put(mapKey, Collections.singletonMap(innerMapKey, colorList));
                    }
                    mapKey = null;
                    innerMapKey = null;
                    colorList = null;
                }
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return restoredMap;
    }

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
            JOptionPane.showMessageDialog(null, folderName + " не верное имя папки.");
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
                        JOptionPane.QUESTION_MESSAGE, null, new String[]{options[1], options[2]}, options[0]); // окно действия

                if (selections == 0) { // Код, который выполнится при нажатии "Удалить"
                    System.out.println("********************");
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