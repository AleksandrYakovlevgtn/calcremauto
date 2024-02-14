package ru.yandex.practicum.calcRemAuto.storage;

import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.calcRemAuto.model.Client;
import ru.yandex.practicum.calcRemAuto.model.Element;
import ru.yandex.practicum.calcRemAuto.model.Prices;
import ru.yandex.practicum.calcRemAuto.model.Total;
import ru.yandex.practicum.calcRemAuto.panelsAndButtons.panels.AddWorkPanel;

import javax.swing.*;
import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@NoArgsConstructor
public class SaveInFail {
    private final Prices prices = new Prices();
    private Client client;
    private Map<String, Map<String, List<String>>> lineBorderColorMap;
    private Total total;
    private List<Element> elements;
    public static final String NAME_START_DIRECTORY = "Расчеты";
    String NAME_AUTOMOBILE_DIRECTORY; // директория начальная/госНомер
    String DATE_DIRECTORY;
    String OFFICIAL_DIRECTORY;

    public SaveInFail(Client client, Total total, List<Element> elements, Map<String, Map<String, List<String>>> lineBorderColorMap) {
        this.client = client;
        this.lineBorderColorMap = lineBorderColorMap;
        this.total = total;
        this.elements = elements;
        NAME_AUTOMOBILE_DIRECTORY = NAME_START_DIRECTORY + "/" + client.getNumberAuto().toUpperCase();
    }

    @SneakyThrows
    public void save(String text) {
        DATE_DIRECTORY = NAME_AUTOMOBILE_DIRECTORY + "/" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd"));
        OFFICIAL_DIRECTORY = DATE_DIRECTORY + "/служебная";

        createDirectories();

        try (PrintWriter out = new PrintWriter(DATE_DIRECTORY + "/смета.txt");
             PrintWriter dataClient = new PrintWriter(OFFICIAL_DIRECTORY + "/клиент.txt");
             PrintWriter dataTotal = new PrintWriter(OFFICIAL_DIRECTORY + "/итого.txt")) {

            out.println(text);
            dataClient.write(toStringClient(client));
            dataTotal.write(toStringTotal(total));

            saveListElements(DATE_DIRECTORY, OFFICIAL_DIRECTORY);
            saveMap(OFFICIAL_DIRECTORY);

        } catch (FileNotFoundException | FileAlreadyExistsException e) {
            log.error(e.getMessage());
        }
    }

    private void createDirectories() throws IOException {
        createDirectoryIfNeeded(NAME_START_DIRECTORY);
        createDirectoryIfNeeded(NAME_AUTOMOBILE_DIRECTORY);
        createDirectoryIfNeeded(DATE_DIRECTORY);
        createDirectoryIfNeeded(OFFICIAL_DIRECTORY);
    }

    private void createDirectoryIfNeeded(String directory) throws IOException {
        if (!Files.exists(Path.of(directory))) {
            Files.createDirectory(Path.of(directory));
        }
    }

    public void load(File file, JPanel panel) {
        client = loadClient(String.valueOf(file));
        total = loadTotal(String.valueOf(file));
        lineBorderColorMap = loadMap(String.valueOf(file));
        elements = loadElementsList(String.valueOf(file));
        AddWorkPanel addWorkPanel = new AddWorkPanel();
        addWorkPanel.load(elements, lineBorderColorMap, client, panel);
    }

    private String toStringElement(Element element) {
        String[] words = {element.getName(), String.valueOf(element.getPaintSide()), String.valueOf(element.getArmatureSide()),
                String.valueOf(element.getKuzDetReplaceSide()), String.valueOf(element.getGlass()), element.getNameGlass(),
                String.valueOf(element.getZerkalo()), String.valueOf(element.getMolding()), String.valueOf(element.getRuchka()),
                String.valueOf(element.getOverlay()), String.valueOf(element.getExpander()), String.valueOf(element.getRemont()),
                String.valueOf(element.getTotal())};
        return String.join(",", words);
    }

    @SneakyThrows
    private List<Element> loadElementsList(String officialDirectory) {
        List<Element> elements = new ArrayList<>();
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
        return String.format("%s,%s,%s,%s,%s", total.getMalyr(), total.getArmatyrchik(), total.getKuzovchik(),
                total.getMaster(), total.getTotal());
    }

    @SneakyThrows
    private Total loadTotal(String officialDirectory) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(officialDirectory + "/служебная/итого.txt"))) {
            String[] parts = bufferedReader.readLine().split(",");
            if (parts.length == 5) {
                return new Total(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]),
                        Double.parseDouble(parts[2]), Double.parseDouble(parts[3]), Double.parseDouble(parts[4]));
            } else {
                return new Total();
            }
        }
    }

    private String toStringClient(Client client) {
        return String.format("%s,%s,%s,%s", client.getName(), client.getFoneNumber(), client.getNumberAuto(), client.getModelAuto());
    }

    @SneakyThrows
    private Client loadClient(String officialDirectory) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(officialDirectory + "/служебная/клиент.txt"))) {
            String[] parts = bufferedReader.readLine().split(",");
            if (parts.length == 4) {
                return new Client(parts[0], parts[1], parts[2], parts[3]);
            } else {
                return new Client("", "", "", "");
            }
        }
    }

    @SneakyThrows
    private void saveListElements(String dateDirectory, String officialDirectory) throws FileNotFoundException, FileAlreadyExistsException {
        try (PrintWriter dataElementList = new PrintWriter(officialDirectory + "/список_элементов.txt");
             PrintWriter malyr = new PrintWriter(dateDirectory + "/маляр.txt");
             PrintWriter armoterchik = new PrintWriter(dateDirectory + "/арматурщик.txt");
             PrintWriter kuzovchik = new PrintWriter(dateDirectory + "/кузовщик.txt")) {

            for (Element element : elements) {
                malyr.write(element.getName() + " " + element.getPaintSide() + "н/ч");
                armoterchik.write(element.getName() + " " + element.getArmatureSide() + "н/ч");

                dataElementList.write(toStringElement(element) + "\n");

                if (total.getKuzovchik() > 0) {
                    kuzovchik.write(element.getName() + " " + element.getKuzDetReplaceSide() + "н/ч " +
                            (element.getKuzDetReplaceSide() * prices.getMechanicHourlyRate()) + "руб.\n");
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
                malyr.write(" итого: " + (int) ((element.getPaintSide() + element.getRemont() + element.getRuchka() +
                        element.getZerkalo() + element.getMolding() + element.getOverlay() + element.getExpander()) *
                        prices.getMechanicHourlyRate()) + "руб.\n");
                if (element.getGlass() > 0) {
                    if (element.getNameGlass() != null) {
                        armoterchik.write(element.getNameGlass());
                    } else {
                        armoterchik.write(element.getName());
                    }
                }
                armoterchik.write(" итого: " + (int) ((element.getArmatureSide() + element.getGlass()) *
                        prices.getMechanicHourlyRate()) + "руб.\n");
            }
            malyr.write("Итог: " + total.getMalyr());
            armoterchik.write("Итог: " + total.getArmatyrchik());
            kuzovchik.write("Итог: " + total.getKuzovchik());
        }
    }

    @SneakyThrows
    private void saveMap(String officialDirectory) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(officialDirectory + "/таблица_работ.txt"))) {
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
        }
    }

    @SneakyThrows
    private Map<String, Map<String, List<String>>> loadMap(String officialDirectory) {
        Map<String, Map<String, List<String>>> loadedMap = new HashMap<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(officialDirectory + "/служебная/таблица_работ.txt"))) {
            String line;
            String key1 = null;
            String key2 = null;
            List<String> values = new ArrayList<>();
            while ((line = bufferedReader.readLine()) != null) {
                switch (line) {
                    case "---":
                        key1 = bufferedReader.readLine();
                        loadedMap.put(key1, new HashMap<>());
                        break;
                    case "----":
                        key2 = bufferedReader.readLine();
                        loadedMap.get(key1).put(key2, new ArrayList<>());
                        break;
                    case "-----":
                        values = loadedMap.get(key1).get(key2);
                        break;
                    default:
                        values.add(line);
                        break;
                }
            }
        }
        return loadedMap;
    }
}