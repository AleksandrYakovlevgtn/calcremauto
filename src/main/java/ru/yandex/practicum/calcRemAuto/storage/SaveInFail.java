package ru.yandex.practicum.calcRemAuto.storage;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.calcRemAuto.model.Client;
import ru.yandex.practicum.calcRemAuto.model.Element;
import ru.yandex.practicum.calcRemAuto.model.Prices;
import ru.yandex.practicum.calcRemAuto.model.Total;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

@Slf4j
@NoArgsConstructor
public class SaveInFail {
    Prices prices = new Prices();
    Client client;
    Map<String, Map<String, List<String>>> lineBorderColorMap;
    Total total;
    List<Element> elements;
    public static String nameStartDirectory = ("Works"); // Начальная директория
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

        } catch (FileNotFoundException | FileAlreadyExistsException o) {
            log.error(o.getMessage());
        }
    }

    public void loud(File file) {
        client = loadClient(String.valueOf(file));
        total = loadTotal(String.valueOf(file));
        lineBorderColorMap = loadMap(String.valueOf(file));
        elements = loadElementsList(String.valueOf(file));
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

    @SneakyThrows
    private List<Element> loadElementsList(String officialDirectory) {
        List<Element> elements = new ArrayList<>();
        //BufferedReader br = new BufferedReader(new FileReader(officialDirectory + "officialDirectory + /служебная/список_элементов.txt"));
        FileReader fileReader = new FileReader(officialDirectory + "/служебная/список_элементов.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] parts = line.split(",");
            Element element = new Element();
            element.setName(parts[0]);
            element.setPaintSide(Double.parseDouble(parts[1]));
            element.setArmatureSide(Double.parseDouble(parts[2]));
            element.setKuzDetReplaceSide(Double.parseDouble(parts[3]));
            element.setGlass(Integer.parseInt(parts[4]));
            element.setNameGlass(parts[5]);
            element.setZerkalo(Integer.parseInt(parts[6]));
            element.setMolding(Integer.parseInt(parts[7]));
            element.setRuchka(Integer.parseInt(parts[8]));
            element.setOverlay(Integer.parseInt(parts[9]));
            element.setExpander(Integer.parseInt(parts[10]));
            element.setRemont(Double.parseDouble(parts[11]));
            element.setTotal(0);

            elements.add(element);
        }
        return elements;
    }

    private String toStringTotal(Total total) {
        String[] words = {String.valueOf(total.getMalyr()),
                String.valueOf(total.getArmatyrchik()),
                String.valueOf(total.getKuzovchik()),
                String.valueOf(total.getMaster()),
                String.valueOf(total.getTotal())};
        return String.join(",", words);
    }

    @SneakyThrows
    private Total loadTotal(String officialDirectory) {
        FileReader fileReader = new FileReader(officialDirectory + "/служебная/итого.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String[] parts = bufferedReader.readLine().split(",");
        if (parts.length == 5) {
            double malyr = Double.parseDouble(parts[0]);
            double armatyrchik = Double.parseDouble(parts[1]);
            double kuzovchik = Double.parseDouble(parts[2]);
            double master = Double.parseDouble(parts[3]);
            double total = Double.parseDouble(parts[4]);
            return new Total(malyr, armatyrchik, kuzovchik, master, total);
        } else {
            return new Total();
        }
    }

    private String toStringClient(Client client) {
        String[] words = {client.getName(),
                client.getFoneNumber(),
                client.getNumberAuto(),
                client.getModelAuto()};
        return String.join(",", words);
    }

    @SneakyThrows
    private Client loadClient(String officialDirectory) {
        FileReader fileReader = new FileReader(officialDirectory + "/служебная/клиент.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String[] parts = bufferedReader.readLine().split(",");
        if (parts.length == 4) {
            String name = parts[0];
            String phoneNumber = parts[1];
            String numberAuto = parts[2];
            String modelAuto = parts[3];
            return new Client(name, phoneNumber, numberAuto, modelAuto);
        } else {
            return new Client("", "", "", "");
        }
    }

    @SneakyThrows
    private void saveListElements(String dateDirectory, String officialDirectory) throws FileNotFoundException, FileAlreadyExistsException {
        PrintWriter dataElementList = new PrintWriter(officialDirectory + "/список_элементов.txt"); // файл с сохраненным списком элементов
        PrintWriter malyr = new PrintWriter(dateDirectory + "/маляр.txt"); // смета упрощенная для маляра
        PrintWriter armoterchik = new PrintWriter(dateDirectory + "/арматурщик.txt"); // смета упрощенная для арматурщика
        PrintWriter kuzovchik = new PrintWriter(dateDirectory + "/кузовщик.txt"); // смета упрощенная для кузовщика

        for (Element element : elements) {
            /*
            Производим запись в файлы смет для разных исполнителей.
             */
            malyr.write(element.getName() + " " + element.getPaintSide() + "н/ч");
            armoterchik.write(element.getName() + " " + element.getArmatureSide() + "н/ч");
            // Записываем в файл (рабочий) "список_элементов" элементы через ","
            dataElementList.write(toStringElement(element) + "\n");

            if (total.getKuzovchik() > 0) {
                kuzovchik.write(element.getName() + " " + element.getKuzDetReplaceSide() + "н/ч " + (element.getKuzDetReplaceSide() * prices.getMechanicHourlyRate()) + "руб.\n");
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
                    + element.getExpander()) * prices.getMechanicHourlyRate()) + "руб.\n");
            if (element.getGlass() > 0) {
                if (element.getNameGlass() != null) {
                    armoterchik.write(element.getNameGlass());
                } else {
                    armoterchik.write(element.getName());
                }
            }
            armoterchik.write(" итого: " + (int) ((element.getArmatureSide() + element.getGlass()) * prices.getMechanicHourlyRate()) + "руб.\n");
        }
        malyr.write("Итог: " + total.getMalyr());
        armoterchik.write("Итог: " + total.getArmatyrchik());
        kuzovchik.write("Итог: " + total.getKuzovchik());
        malyr.close();
        armoterchik.close();
        kuzovchik.close();
        dataElementList.close();
    }

    @SneakyThrows
    private void saveMap(String officialDirectory) {
        FileWriter fileWriter = new FileWriter(officialDirectory + "/таблица_работ.txt");
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        for (Map.Entry<String, Map<String, List<String>>> entry : lineBorderColorMap.entrySet()) {
            bufferedWriter.write("---\n");
            bufferedWriter.write(entry.getKey() + "\n");
            for (Map.Entry<String, List<String>> innerEntry : entry.getValue().entrySet()) {
                bufferedWriter.write("----\n");
                bufferedWriter.write(innerEntry.getKey() + "\n");
                bufferedWriter.write("-----\n");
                for (String color : innerEntry.getValue()) {
                    bufferedWriter.write(color + "\n");
                }
            }
        }
        bufferedWriter.close();
    }

    @SneakyThrows
    private Map<String, Map<String, List<String>>> loadMap(String officialDirectory) {
        Map<String, Map<String, List<String>>> loadedMap = new HashMap<>();
        FileReader fileReader = new FileReader(officialDirectory + "/служебная/таблица_работ.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        String key1 = null;
        String key2 = null;
        List<String> values = new ArrayList<>();
        while ((line = bufferedReader.readLine()) != null) {
            if (line.equals("---")) {
                key1 = bufferedReader.readLine();
                loadedMap.put(key1, new HashMap<>());
            } else if (line.equals("----")) {
                key2 = bufferedReader.readLine();
                loadedMap.get(key1).put(key2, new ArrayList<>());
            } else if (line.equals("-----")) {
                values = loadedMap.get(key1).get(key2);
            } else {
                values.add(line);
            }
        }
        bufferedReader.close();
        return loadedMap;
    }
}