package ru.yandex.practicum.calcRemAuto.storage;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.calcRemAuto.model.*;
import ru.yandex.practicum.calcRemAuto.panelsAndButtons.panels.AddWorkPanel;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Getter
@NoArgsConstructor
public class WorkWithFile {
    private final Prices prices = new Prices();
    NameDirectories directories = new NameDirectories();
    ExcelUpdater exel = new ExcelUpdater();
    private Client client; // Класс с данными клиента, инициируется из конструктора и load (загрузка).
    private Map<String, Map<String, List<String>>> lineBorderColorMap; // Таблица с нажатыми кнопками, инициируется из конструктора и load (загрузка).
    private Total total; // Класс итого, инициируется из конструктора и load (загрузка).
    private List<Element> elements; // Список добавленных элементов, инициируется из конструктора и load (загрузка).
    private Lkm lkm;
    String GOS_NUMBER; // директория начальная/госНомер
    String DATE_DIRECTORY; // директория начальная/госНомер/дата_расчета
    String OFFICIAL_DIRECTORY; // директория начальная/госНомер/дата_расчета/служебная Для записи служебных данных.

    public WorkWithFile(Client client, Total total, List<Element> elements, Map<String, Map<String, List<String>>> lineBorderColorMap, Lkm lkm) {
        this.client = client;
        this.lineBorderColorMap = lineBorderColorMap;
        this.total = total;
        this.elements = elements;
        this.lkm = lkm;
        GOS_NUMBER = directories.getNAME_START_DIRECTORY() + directories.getSlash() + client.getNumberAuto().toUpperCase();
    } // Конструктор

    @SneakyThrows
    public void save(String text,int lkmTotalPrice) {
        DATE_DIRECTORY = GOS_NUMBER + directories.getSlash() + directories.getDATE_DIRECTORY();
        OFFICIAL_DIRECTORY = DATE_DIRECTORY + directories.getSlash() + directories.getOFFICIAL_DIRECTORY();

        createDirectories();
        OpenFolder openFolder = new OpenFolder();
        openFolder.open(DATE_DIRECTORY);
        exel.createOrderExelFile(client, elements,lkmTotalPrice, DATE_DIRECTORY);

        try (PrintWriter out = new PrintWriter(DATE_DIRECTORY + directories.getSlash() + directories.getSMETA() + directories.getTxt());
             PrintWriter dataClient = new PrintWriter(OFFICIAL_DIRECTORY + directories.getSlash() + directories.getCLIENT() + directories.getTxt());
             PrintWriter dataTotal = new PrintWriter(OFFICIAL_DIRECTORY + directories.getSlash() + directories.getITOGO() + directories.getTxt());
             PrintWriter dataLkm = new PrintWriter(OFFICIAL_DIRECTORY + directories.getSlash() + directories.getLKM() + directories.getTxt())) {

            out.println(text); // Запись файла сметы
            dataClient.write(toStringClient(client)); // Запись файла данных клиента
            dataTotal.write(toStringTotal(total)); // Запись данных класса итого
            dataLkm.write(toStringLkm(lkm)); // запись данных класса ЛКМ
            writeLkmSmeta(lkm);             // Запись файла сметы ЛКМ необходимых для данного ремонта
            saveListElements(DATE_DIRECTORY, OFFICIAL_DIRECTORY); // Запись Списка элементов в файл
            saveMap(OFFICIAL_DIRECTORY);                         // Запись Таблицы работ(нажатых кнопок на элементах) в файл
        }
    } // Метод сохранения который через другие методы сохраняет все в файлы .txt

    public void createDirectories() throws IOException {
        createDirectoryIfNeeded(directories.getNAME_START_DIRECTORY());
        createDirectoryIfNeeded(GOS_NUMBER);
        createDirectoryIfNeeded(DATE_DIRECTORY);
        createDirectoryIfNeeded(OFFICIAL_DIRECTORY);
    } // Метод для распределения создания папок

    private void createDirectoryIfNeeded(String directory) throws IOException {
        if (!Files.exists(Path.of(directory))) {
            Files.createDirectory(Path.of(directory));
        }
    } // Проверка и создание папок

    public void load(File file, JPanel panel) {
        client = loadClient(String.valueOf(file));
        total = loadTotal(String.valueOf(file));
        lineBorderColorMap = loadMap(String.valueOf(file));
        lkm = loadLkm(String.valueOf(file));
        elements = loadElementsList(String.valueOf(file));
        AddWorkPanel addWorkPanel = new AddWorkPanel();
        addWorkPanel.load(elements, lineBorderColorMap, client, lkm, panel);
    } // Загрузка из файлов

    private String toStringElement(Element element) {
        String[] words = {element.getName(), String.valueOf(element.getPaintSide()), String.valueOf(element.getArmatureSide()),
                String.valueOf(element.getKuzDetReplaceSide()), String.valueOf(element.getGlass()), element.getNameGlass(),
                String.valueOf(element.getZerkalo()), String.valueOf(element.getMolding()), String.valueOf(element.getRuchka()),
                String.valueOf(element.getOverlay()), String.valueOf(element.getExpander()), String.valueOf(element.getRemont()),
                String.valueOf(element.getTotal()),
                element.getHoDoRemont()};
        return String.join(",", words);
    } // Метод разбития Элемента в строки для сохранения в файл

    @SneakyThrows
    public void saveListElements(String dateDirectory, String officialDirectory) {
        try (PrintWriter dataElementList = new PrintWriter(officialDirectory + directories.getSlash() + directories.getLIST_OF_ELEMENTS() + directories.getTxt());
             PrintWriter malyr = new PrintWriter(dateDirectory + directories.getSlash() + directories.getMALYAR() + directories.getTxt());
             PrintWriter armoterchik = new PrintWriter(dateDirectory + directories.getSlash() + directories.getARMOTURCHIK() + directories.getTxt());
             PrintWriter kuzovchik = new PrintWriter(dateDirectory + directories.getSlash() + directories.getKUZOVCHIK() + directories.getTxt())) {

            for (Element element : elements) {
                malyr.write(element.getName() + " " + element.getPaintSide() + "н/ч");
                armoterchik.write(element.getName() + " " + element.getArmatureSide() + "н/ч");

                dataElementList.write(toStringElement(element) + "\n");

                if (element.getKuzDetReplaceSide() > 0 || element.getHoDoRemont().equals("кузовщик")) {
                    String line = element.getName();
                    if (element.getHoDoRemont().equals("кузовщик")) {
                        line = line + (" ремонт: " + element.getRemont() + "н/ч " + element.getRemont() * prices.getMechanicHourlyRate()) + "руб.\n";
                    } else if (element.getKuzDetReplaceSide() > 0) {
                        line = line + (" замена: " + element.getKuzDetReplaceSide() + "н/ч " + element.getKuzDetReplaceSide() * prices.getMechanicHourlyRate()) + "руб.\n";
                    }
                    kuzovchik.write(line);
                }
                if (element.getRemont() > 0) {
                    if (element.getHoDoRemont().equals("маляр")) {
                        malyr.write(" ремонт: " + element.getRemont() + "н/ч");
                    }
                    if (element.getHoDoRemont().equals("арматурщик")) {
                        armoterchik.write(" ремонт: " + element.getRemont() + "н/ч");
                    }
                }
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
                int line = (int) ((element.getPaintSide() + element.getRuchka() +
                        element.getZerkalo() + element.getMolding() + element.getOverlay() + element.getExpander()) *
                        prices.getMechanicHourlyRate());
                if (element.getHoDoRemont().equals("маляр")) {
                    line = line + (int) (element.getRemont() * prices.getMechanicHourlyRate());
                }
                malyr.write(" итого: " + line + "руб.\n");
                if (element.getGlass() > 0) {
                    if (element.getNameGlass() != null) {
                        armoterchik.write(element.getNameGlass());
                    } else {
                        armoterchik.write(element.getName());
                    }
                }
                line = 0;
                line = (int) ((element.getArmatureSide() + element.getGlass()) *
                        prices.getMechanicHourlyRate());
                if (element.getHoDoRemont().equals("арматурщик")) {
                    line = line + (int) (element.getRemont() * prices.getMechanicHourlyRate());
                }
                armoterchik.write(" итого: " + line + "руб.\n");
            }
            malyr.write("Итог: " + total.getMalyr());
            armoterchik.write("Итог: " + total.getArmatyrchik());
            kuzovchik.write("Итог: " + total.getKuzovchik());
        }
    } // Метод сохранения List<Element> elements

    @SneakyThrows
    private List<Element> loadElementsList(String nameStartDirectory) {
        List<Element> elements = new ArrayList<>();
        FileReader fileReader = new FileReader(nameStartDirectory + directories.getSlash() + directories.getOFFICIAL_DIRECTORY() +
                directories.getSlash() + directories.getLIST_OF_ELEMENTS() + directories.getTxt());
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
            element.setHoDoRemont(parts[13]);
            element.setTotal(0);

            elements.add(element);
        }
        return elements;
    } // Метод загрузки из файла List<Element> elements

    private String toStringTotal(Total total) {
        return String.format("%s,%s,%s,%s,%s", total.getMalyr(), total.getArmatyrchik(), total.getKuzovchik(),
                total.getMaster(), total.getTotal());
    } // Метод разбития Total в строки для сохранения в файл

    @SneakyThrows
    private Total loadTotal(String officialDirectory) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(officialDirectory +
                directories.getSlash() + directories.getOFFICIAL_DIRECTORY() +
                directories.getSlash() + directories.getITOGO() + directories.getTxt()))) {
            String[] parts = bufferedReader.readLine().split(",");
            if (parts.length == 5) {
                return new Total(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]),
                        Double.parseDouble(parts[2]), Double.parseDouble(parts[3]), Double.parseDouble(parts[4]));
            } else {
                return new Total();
            }
        }
    } // Метод загрузки из файла Total

    private String toStringClient(Client client) {
        return String.format("%s,%s,%s,%s", client.getName(), client.getFoneNumber(), client.getNumberAuto(), client.getModelAuto());
    } // Метод разбития Client в строки для сохранения в файл

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
    } // Метод загрузки из файла Client

    @SneakyThrows
    public void writeLkmSmeta(Lkm lkm) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATE_DIRECTORY + directories.getSlash() + directories.getLKM_NEEDED() + directories.getTxt()))) {
            writer.write("Круг p80 " + lkm.getP80() + "шт.");
            writer.newLine();
            writer.write("Круг p180 " + lkm.getP180() + "шт.");
            writer.newLine();
            writer.write("Круг p280 " + lkm.getP280() + "шт.");
            writer.newLine();
            writer.write("Круг p400 " + lkm.getP400() + "шт.");
            writer.newLine();
            writer.write("Круг p500 " + lkm.getP500() + "шт.");
            writer.newLine();
            writer.write("Полоска P80 " + lkm.getStripP80() + "шт.");
            writer.newLine();
            writer.write("Полоска P120 " + lkm.getStripP120() + "шт.");
            writer.newLine();
            writer.write("Полоска P180 " + lkm.getStripP180() + "шт.");
            writer.newLine();
            writer.write("scotchBrite " + String.format("%.2f", lkm.getScotchBrite()) + "м.");
            writer.newLine();
            writer.write("Грунт " + String.format("%.2f", lkm.getPriming()) + "кг.");
            writer.newLine();
            writer.write("Лак HS " + String.format("%.2f", lkm.getClear()) + "кг.");
            writer.newLine();
            writer.write("Разбавитель " + String.format("%.2f", lkm.getBaseDilution()) + "кг.");
            writer.newLine();
            writer.write("База " + String.format("%.2f", lkm.getBasePaint()) + "кг.");
            writer.newLine();
            writer.write("Обезжириватель " + String.format("%.2f", lkm.getSiliconRemover()) + "кг.");
            writer.newLine();
            writer.write("Скотч " + String.format("%.2f", lkm.getStickyTape()) + "шт.");
            writer.newLine();
            writer.write("Пленка " + String.format("%.2f", lkm.getCoveringFilm()) + "м.");
            writer.newLine();
            writer.write("Шпатлевка Волокнистая " + String.format("%.2f", lkm.getPuttyFiber()) + "кг.");
            writer.newLine();
            writer.write("Шпатлевка Универсальная " + String.format("%.2f", lkm.getPuttyUniversal()) + "кг.");
            writer.newLine();
            writer.write("Салфетки " + lkm.getNapkin() + "шт.");
            writer.newLine();
            writer.write("Герметик " + lkm.getHermetic() + "туб");
        }
    } // Метод записывает файл необходимых ЛКМ на данный расчет

    private String toStringLkm(Lkm lkm) {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                lkm.getP80(), lkm.getP180(), lkm.getP280(), lkm.getP400(), lkm.getP500(),
                lkm.getStripP80(), lkm.getStripP120(), lkm.getStripP180(),
                lkm.getScotchBrite(), lkm.getPriming(), lkm.getClear(),
                lkm.getBaseDilution(), lkm.getBasePaint(), lkm.getSiliconRemover(),
                lkm.getStickyTape(), lkm.getCoveringFilm(), lkm.getPuttyFiber(),
                lkm.getPuttyUniversal(), lkm.getNapkin(), lkm.getHermetic());
    } // Метод разбития ЛКМ в строки для сохранения в файл

    @SneakyThrows
    private Lkm loadLkm(String officialDirectory) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(officialDirectory + "/служебная/Лкм.txt"))) {
            String[] parts = bufferedReader.readLine().split(",");
            if (parts.length == 20) {
                Lkm lkm = new Lkm();
                lkm.setP80(Integer.parseInt(parts[0]));
                lkm.setP180(Integer.parseInt(parts[1]));
                lkm.setP280(Integer.parseInt(parts[2]));
                lkm.setP400(Integer.parseInt(parts[3]));
                lkm.setP500(Integer.parseInt(parts[4]));
                lkm.setStripP80(Integer.parseInt(parts[5]));
                lkm.setStripP120(Integer.parseInt(parts[6]));
                lkm.setStripP180(Integer.parseInt(parts[7]));
                lkm.setScotchBrite(Double.parseDouble(parts[8]));
                lkm.setPriming(Double.parseDouble(parts[9]));
                lkm.setClear(Double.parseDouble(parts[10]));
                lkm.setBaseDilution(Double.parseDouble(parts[11]));
                lkm.setBasePaint(Double.parseDouble(parts[12]));
                lkm.setSiliconRemover(Double.parseDouble(parts[13]));
                lkm.setStickyTape(Double.parseDouble(parts[14]));
                lkm.setCoveringFilm(Double.parseDouble(parts[15]));
                lkm.setPuttyFiber(Double.parseDouble(parts[16]));
                lkm.setPuttyUniversal(Double.parseDouble(parts[17]));
                lkm.setNapkin(Integer.parseInt(parts[18]));
                lkm.setHermetic(Integer.parseInt(parts[19]));
                return lkm;
            } else {
                return new Lkm();
            }
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
            return new Lkm();
        }
    } // Метод загрузки из файла Lkm

    @SneakyThrows
    private void saveMap(String officialDirectory) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(officialDirectory +
                directories.getSlash() + directories.getMAP_OF_WORKS() + directories.getTxt()))) {
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
    } // Метод сохранения Map<String, Map<String, List<String>>> lineBorderColorMap в файл

    @SneakyThrows
    private Map<String, Map<String, List<String>>> loadMap(String officialDirectory) {
        Map<String, Map<String, List<String>>> loadedMap = new HashMap<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(officialDirectory +
                directories.getSlash() + directories.getOFFICIAL_DIRECTORY() +
                directories.getSlash() + directories.getMAP_OF_WORKS() + directories.getTxt()))) {
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
    } // Метод загрузки из файла Map<String, Map<String, List<String>>> lineBorderColorMap
}