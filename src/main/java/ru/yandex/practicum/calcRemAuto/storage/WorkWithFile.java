package ru.yandex.practicum.calcRemAuto.storage;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.calcRemAuto.logToFail.LogToFailManager;
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
import java.util.stream.Collectors;
import java.util.Set;

@Slf4j
@Getter
@NoArgsConstructor
public class WorkWithFile {
    private static final Set<String> EXCLUDED_PROFESSIONS = Set.of(
            "Маляр",
            "Кузовщик",
            "Арматурщик",
            "null"
    );
    static LogToFailManager logManager = new LogToFailManager();
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
        logManager.log("Запущен Конструктор WorkWithFile в классе WorkWithFile");
        this.client = client;
        this.lineBorderColorMap = lineBorderColorMap;
        this.total = total;
        this.elements = elements;
        this.lkm = lkm;
        GOS_NUMBER = directories.getNAME_START_DIRECTORY() + directories.getSlash() + client.getNumberAuto().toUpperCase() + "_" + client.getModelAuto().toUpperCase();
    } // Конструктор

    @SneakyThrows
    public void save(String text, int lkmTotalPrice) {
        logManager.log("Запущен метод save в классе WorkWithFile");
        DATE_DIRECTORY = GOS_NUMBER + directories.getSlash() + directories.getDATE_DIRECTORY();
        OFFICIAL_DIRECTORY = DATE_DIRECTORY + directories.getSlash() + directories.getOFFICIAL_DIRECTORY();

        createDirectories();
        OpenFolder openFolder = new OpenFolder();
        openFolder.open(DATE_DIRECTORY);
        exel.createOrderExelFile(client, elements, lkmTotalPrice, DATE_DIRECTORY);

        try (PrintWriter out = new PrintWriter(DATE_DIRECTORY + directories.getSlash() + directories.getSMETA() + directories.getTxt());
             PrintWriter dataClient = new PrintWriter(OFFICIAL_DIRECTORY + directories.getSlash() + directories.getCLIENT() + directories.getTxt());
             PrintWriter dataTotal = new PrintWriter(OFFICIAL_DIRECTORY + directories.getSlash() + directories.getITOGO() + directories.getTxt());
             PrintWriter dataLkm = new PrintWriter(OFFICIAL_DIRECTORY + directories.getSlash() + directories.getLKM() + directories.getTxt())) {

            logManager.log("Начата процедура поэтапной записи в файлы в классе WorkWithFile");

            out.println(text);                        // Запись файла сметы
            dataClient.write(toStringClient(client)); // Запись файла данных клиента
            dataTotal.write(toStringTotal(total));    // Запись данных класса итого
            dataLkm.write(toStringLkm(lkm));          // запись данных класса ЛКМ
            writeLkmSmeta(lkm);                       // Запись файла сметы ЛКМ необходимых для данного ремонта
            saveListElements(OFFICIAL_DIRECTORY);     // Запись Списка элементов в файл
            saveMalyar(DATE_DIRECTORY);               // Запись сметы маляр
            saveArmoturchik(DATE_DIRECTORY);          // Запись сметы арматурщик
            saveKuzovchik(DATE_DIRECTORY);            // Запись сметы кузовщик
            saveNonStaffMechanic(DATE_DIRECTORY);     // Запись в файл не фиксированных(сторонних) механиков.
            saveMap(OFFICIAL_DIRECTORY);              // Запись Таблицы работ(нажатых кнопок на элементах) в файл
        }
    } // Метод сохранения который через другие методы сохраняет все в файлы .txt

    public void createDirectories() throws IOException {
        logManager.log("Запущен метод createDirectories в классе WorkWithFile");
        createDirectoryIfNeeded(directories.getNAME_START_DIRECTORY());
        createDirectoryIfNeeded(GOS_NUMBER);
        createDirectoryIfNeeded(DATE_DIRECTORY);
        createDirectoryIfNeeded(OFFICIAL_DIRECTORY);
    } // Метод для распределения создания папок

    private void createDirectoryIfNeeded(String directory) throws IOException {
        logManager.log("Запущен метод createDirectoryIfNeeded в классе WorkWithFile для пути: " + directory);
        if (!Files.exists(Path.of(directory))) {
            Files.createDirectory(Path.of(directory));
            logManager.log("Директория создана для пути: " + directory);
        }
    } // Проверка и создание папок

    public void load(File file, JPanel panel) {
        logManager.log("Запущен метод load в классе WorkWithFile.");
        client = loadClient(String.valueOf(file));
        total = loadTotal(String.valueOf(file));
        lineBorderColorMap = loadMap(String.valueOf(file));
        lkm = loadLkm(String.valueOf(file));
        elements = loadElementsList(String.valueOf(file));
        AddWorkPanel addWorkPanel = new AddWorkPanel();
        addWorkPanel.load(elements, lineBorderColorMap, client, lkm, panel);
    } // Загрузка из файлов

    private String toStringElement(Element element) {
        logManager.log("Запущен метод toStringElement в классе WorkWithFile.");
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
                String.valueOf(element.getTotal()),
                element.getHoDoRemont(),
                String.valueOf(element.getDopWorksArmoturchik()),
                String.valueOf(element.getDopWorksPainter()),
                String.valueOf(element.getDopWorksKuzovchik()),
                String.valueOf(element.getLkmForElement()),
// Присвоить описание к параметру заменив "," на "\u2400" и " " на "\u2422" для дальнейшей правильной записи в файл. Так же дальше при извлечении из файла будет обратная замена
                String.valueOf(element.getDescriptionDopWorksArmaturchic().replaceAll(",", "\u2400").replaceAll(" ", "\u2422").replaceAll("\n", "\u2028")),
                String.valueOf(element.getDescriptionDopWorksPainter().replaceAll(",", "\u2400").replaceAll(" ", "\u2422").replaceAll("\n", "\u2028")),
                String.valueOf(element.getDescriptionDopWorksKuzovchik().replaceAll(",", "\u2400").replaceAll(" ", "\u2422").replaceAll("\n", "\u2028")),
                String.valueOf(element.getNotNormWork()),
                String.valueOf(element.getDescriptionDopNotNormWork().replaceAll(",", "\u2400").replaceAll(" ", "\u2422").replaceAll("\n", "\u2028"))};
        return String.join(",", words);
    } // Метод разбития Элемента в строки для сохранения в файл

    @SneakyThrows
    public void saveListElements(String officialDirectory) {
        logManager.log("Запущен метод saveListElements в классе WorkWithFile.");
        try (PrintWriter writer = new PrintWriter(officialDirectory + directories.getSlash() + directories.getLIST_OF_ELEMENTS() + directories.getTxt())) {
            for (Element element : elements) {
                writer.write(toStringElement(element) + "\n");
            }
        }
    } // Метод сохранения List<Element> elements

    @SneakyThrows
    public void saveMalyar(String dateDirectory) {
        logManager.log("Запущен метод saveMalyar в классе WorkWithFile.");
        try (PrintWriter writer = new PrintWriter(dateDirectory + directories.getSlash() + directories.getMALYAR() + directories.getTxt())) {
            logManager.log("Начата итерация по элементам из списка.");
            for (Element element : elements) {
                // Логика работы с маляром
                if (!element.getName().contains("Остекление")) {
                    logManager.log("Элемент не Остекление.");
                    if (element.getPaintSide() > 0) {
                        writer.write(element.getName() + " " + element.getPaintSide() + "н/ч ");
                        logManager.log("Произведена запись: element.getPaintSide() > 0, записано значение element.getName() + \" \" + element.getPaintSide().");
                    } else if (element.getMolding() > 0 || element.getRuchka() > 0 || element.getZerkalo() > 0 || element.getOverlay() > 0 || element.getExpander() > 0) {
                        writer.write(element.getName() + " ");
                        logManager.log("У элемента есть окраска навесного, записано значение element.getName() + \" \".");
                    }
                    logManager.log("Начинается проверка на, то что элемент имеет ремонт или в графе исполнитель ремонта указан \"Маляр\".");
                    // Если элемент имеет ремонт или в графе исполнитель ремонта указан "Маляр" то
                    if (element.getRemont() > 0 || element.getHoDoRemont().toLowerCase().equals("маляр")) {
                        // Проверяем что у элемента есть норматив на ремонт тем самым отсекаем, что он является не нормативной работой
                        if (element.getHoDoRemont().toLowerCase().equals("маляр") && element.getRemont() != 0) {
                            writer.write("ремонт: " + element.getRemont() + "н/ч ");
                            logManager.log("У элемента есть ремонт и исполнитель Маляр. Произведена запись: \"ремонт: \" + element.getRemont() + \"н/ч \"");
                        } else if (element.getHoDoRemont().toLowerCase().equals("маляр") && element.getNotNormWork() != 0) {
                            // Если же в графе исполнитель ремонта указан "Маляр" и при этом есть норматив не нормативных работ то
                            // мы отсекаем ремонт и прописываем в файл не нормативные работы
                            writer.write(element.getName() + " " + element.getNotNormWork() + "н/ч " + "итого: " + ((int) (element.getNotNormWork() * prices.getMechanicHourlyRate())) + "руб.\n");
                            logManager.log("У элемента нет ремонта, но есть не нормативные работы для Маляра. Произведена запись: element.getName() + \" \" + element.getNotNormWork() + \"н/ч \" + \"итого: \" + ((int) (element.getNotNormWork() * prices.getMechanicHourlyRate())) + \"руб.\\n\"");
                        } else if (element.getHoDoRemont().toLowerCase().equals("кузовщик") &&
                                !(element.getName().contains("Моторный отсек") || element.getName().contains("Задняя панель"))
                                && element.getRemont() != 0) {
                            // Если же в графе исполнитель ремонта указан "Кузовщик", есть норматив ремонта и элемент не входит в перечень
                            // то отнимаем у кузовщика для маляра 30% за ремонт
                            writer.write("ремонт 30%: " + (element.getRemont() * 0.3) + "н/ч ");
                            logManager.log("У элемента есть ремонт но исполнитель Кузовщик. Произведена запись: \"ремонт 30%: \" + (element.getRemont() * 0.3) + \"н/ч \"");
                        }
                    }

                    boolean hasItem = false; // Флаг для проверки наличия предыдущих элементов

                    if (element.getRuchka() > 0) {
                        writer.write("ручка");
                        logManager.log("У элемента есть окраска навесного - ручка, записано значение \"ручка\".");
                        hasItem = true;
                    }
                    if (element.getZerkalo() > 0) {
                        if (hasItem) { // Если уже было написано предыдущее значение, ставим запятую
                            writer.write(", ");
                        }
                        writer.write("зеркало");
                        logManager.log("У элемента есть окраска навесного - зеркало, записано значение \"зеркало\".");
                        hasItem = true;
                    }
                    if (element.getMolding() > 0) {
                        if (hasItem) {
                            writer.write(", ");
                        }
                        writer.write("молдинг");
                        logManager.log("У элемента есть окраска навесного - молдинг, записано значение \"молдинг\".");
                        hasItem = true;
                    }
                    if (element.getOverlay() > 0) {
                        if (hasItem) {
                            writer.write(", ");
                        }
                        writer.write("накладка");
                        logManager.log("У элемента есть окраска навесного - накладка, записано значение \"накладка\".");
                        hasItem = true;
                    }
                    if (element.getExpander() > 0) {
                        if (hasItem) {
                            writer.write(", ");
                        }
                        writer.write("расширитель");
                        logManager.log("У элемента есть окраска навесного - расширитель, записано значение \"расширитель\".");
                    }
                    if (element.getMolding() > 0 || element.getRuchka() > 0 || element.getZerkalo() > 0 || element.getOverlay() > 0 || element.getExpander() > 0) {
                        writer.write(" окраска ");
                        logManager.log("У элемента есть окраска навесного, добавлена запись  \" окраска\".");
                    }
                    if (element.getDopWorksPainter() > 0) {
                        if (element.getDescriptionDopWorksPainter().equals("null")) {
                            writer.write("доп.работы: " + element.getDopWorksPainter() + "н/ч ");
                            logManager.log("У элемента есть доп работы для Маляр но нет их описания произведена запись: \"доп.работы: \" + element.getDopWorksPainter() + \"н/ч \"");
                        } else {
                            writer.write((element.getName().replace("Замена", "")) + " " + element.getDescriptionDopWorksPainter() + " " + element.getDopWorksPainter() + "н/ч ");
                            logManager.log("У элемента есть доп работы для Маляр и есть описания, произведена запись: element.getName().replace(\"Замена\", \"\")) + \" \" + element.getDescriptionDopWorksPainter() + \" \" + element.getDopWorksPainter() + \"н/ч \"");
                        }
                    }
                    logManager.log("Начинается расчет стоимости работ за элемент для Маляр.");
                    // Расчеты оплаты за элемент механику
                    int line = (int) ((element.getPaintSide() + element.getRuchka() +
                            element.getZerkalo() + element.getMolding() + element.getOverlay() +
                            element.getExpander() + element.getDopWorksPainter()) * prices.getMechanicHourlyRate());

                    if (element.getHoDoRemont().toLowerCase().equals("маляр") ||
                            (element.getHoDoRemont().toLowerCase().equals("кузовщик") &&
                                    !(element.getName().contains("Моторный отсек") || element.getName().contains("Задняя панель")))) {
                        if (element.getHoDoRemont().toLowerCase().equals("маляр")) {
                            line = line + (int) (element.getRemont() * prices.getMechanicHourlyRate());
                        } else if (element.getHoDoRemont().toLowerCase().equals("кузовщик") &&
                                !(element.getName().contains("Моторный отсек") || element.getName().contains("Задняя панель"))) {
                            line = line + (int) ((element.getRemont() * 0.3) * prices.getMechanicHourlyRate());
                        }
                    }
                    if (line > 0) {
                        writer.write("итого: " + line + "руб.\n");
                        logManager.log("Расчет завершен произведена запись: \"итого: \" + line + \"руб.\\n\"");
                    }
                }
            }
            logManager.log("Итерация по элементам из списка завершена.");
            writer.write("Итог: " + (int) total.getMalyr() + " руб.");
            logManager.log("Произведена запись итоговой стоимости работ за все элементы из списка для Маляр: \"Итог: \" + (int) total.getMalyr() + \" руб.\".");
        }
    } // Запись в файл сметы для маляра

    @SneakyThrows
    public void saveArmoturchik(String dateDirectory) {
        logManager.log("Запущен метод saveArmoturchik в классе WorkWithFile.");
        try (PrintWriter writer = new PrintWriter(dateDirectory + directories.getSlash() + directories.getARMOTURCHIK() + directories.getTxt())) {
            logManager.log("Начата итерация по элементам из списка.");
            for (Element element : elements) {
                // Логика работы с арматурщиком
                if (!element.getName().contains("Полировка")) {
                    logManager.log("Элемент не Полировка.");
                    if (element.getArmatureSide() > 0) {
                        writer.write(element.getName() + " " + element.getArmatureSide() + "н/ч ");
                        logManager.log("element.getName() + \" \" + element.getArmatureSide() + \"н/ч ");
                    } else if (element.getName().contains("Остекление")) {
                        logManager.log("Элемент Остекление.");
                        writer.write(element.getName() + " " + element.getGlass() + "н/ч ");
                        logManager.log("Произведена запись: element.getName() + \" \" + element.getGlass() + \"н/ч \"");
                    }
                }

                logManager.log("Начинается проверка на, то что элемент имеет ремонт или в графе исполнитель ремонта указан \"арматурщик\".");

                if (element.getRemont() > 0 && element.getHoDoRemont().toLowerCase().equals("арматурщик")) {
                    writer.write("ремонт: " + element.getRemont() + "н/ч ");
                    logManager.log("Произведена запись: \"ремонт: \" + element.getRemont() + \"н/ч ");
                }

                boolean hasItem = false; // Флаг для проверки наличия предыдущих элементов

                if (element.getRuchka() > 0) {
                    writer.write("ручка");
                    logManager.log("У элемента есть окраска навесного - ручка, записано значение \"ручка\".");
                    hasItem = true;
                }
                if (element.getZerkalo() > 0) {
                    if (hasItem) { // Если уже было написано предыдущее значение, ставим запятую
                        writer.write(", ");
                    }
                    writer.write("зеркало");
                    logManager.log("У элемента есть окраска навесного - зеркало, записано значение \"зеркало\".");
                    hasItem = true;
                }
                if (element.getMolding() > 0) {
                    if (hasItem) {
                        writer.write(", ");
                    }
                    writer.write("молдинг");
                    logManager.log("У элемента есть окраска навесного - молдинг, записано значение \"молдинг\".");
                    hasItem = true;
                }
                if (element.getOverlay() > 0) {
                    if (hasItem) {
                        writer.write(", ");
                    }
                    writer.write("накладка");
                    logManager.log("У элемента есть окраска навесного - накладка, записано значение \"накладка\".");
                    hasItem = true;
                }
                if (element.getExpander() > 0) {
                    if (hasItem) {
                        writer.write(", ");
                    }
                    writer.write("расширитель");
                    logManager.log("У элемента есть окраска навесного - расширитель, записано значение \"расширитель\".");
                }
                if (element.getMolding() > 0 || element.getRuchka() > 0 || element.getZerkalo() > 0 || element.getOverlay() > 0 || element.getExpander() > 0) {
                    writer.write(" c/у под окрас ");
                    logManager.log("У элемента есть окраска навесного, добавлена запись  \" c/у под окрас\".");
                }
                if (element.getDopWorksArmoturchik() > 0) {
                    if (element.getDescriptionDopWorksArmaturchic().equals("null")) {
                        writer.write("доп.работы " + element.getDopWorksArmoturchik() + "н/ч ");
                        logManager.log("У элемента есть доп работы для Арматурщик но нет их описания произведена запись: \"доп.работы: \" + element.getDopWorksArmoturchik() + \"н/ч \"");
                    } else {
                        writer.write((element.getName().replace("Замена", "")) + " " + element.getDescriptionDopWorksArmaturchic() + " " + element.getDopWorksArmoturchik() + "н/ч ");
                        logManager.log("У элемента есть доп работы для Арматурщик и есть описания, произведена запись: (element.getName().replace(\"Замена\", \"\")) + \" \" + element.getDescriptionDopWorksArmaturchic() + \" \" + element.getDopWorksArmoturchik() + \"н/ч \")");
                    }
                }

                if (element.getGlass() > 0) {
                    if (!element.getNameGlass().equals("null")) {
                        writer.write(" " + element.getNameGlass());
                        logManager.log("У элемента есть остекление, произведена запись: \" \" + element.getNameGlass()");
                    }
                }

                logManager.log("Начинается расчет стоимости работ за элемент для Арматурщик.");

                int line = (int) ((element.getArmatureSide() + element.getGlass() + element.getDopWorksArmoturchik()) * prices.getMechanicHourlyRate());
                if (element.getHoDoRemont().toLowerCase().equals("арматурщик")) {
                    if (element.getRemont() != 0) {
                        line = line + (int) (element.getRemont() * prices.getMechanicHourlyRate());
                    } else if (element.getRemont() == 0 && element.getNotNormWork() != 0) {
                        line = line + (int) (element.getNotNormWork() * prices.getMechanicHourlyRate());
                        writer.write(element.getName());
                    }
                }
                if (line > 0) {
                    writer.write(" итого: " + line + "руб.\n");
                    logManager.log("Расчет завершен произведена запись: \"итого: \" + line + \"руб.\\n\"");
                }
            }

            logManager.log("Итерация по элементам из списка завершена.");
            writer.write("Итог: " + (int) total.getArmatyrchik() + " руб.");
            logManager.log("Произведена запись итоговой стоимости работ за все элементы из списка для Арматурщик: \"Итог: \" + (int) total.getArmatyrchik() + \" руб.\".");
        }
    } // Запись в файл сметы для арматурщика

    @SneakyThrows
    public void saveKuzovchik(String dateDirectory) {
        logManager.log("Запущен метод saveKuzovchik в классе WorkWithFile.");
        try (PrintWriter writer = new PrintWriter(dateDirectory + directories.getSlash() + directories.getKUZOVCHIK() + directories.getTxt())) {
            logManager.log("Начата итерация по элементам из списка.");
            for (Element element : elements) {
                // Логика работы с кузовщиком
                if (element.getKuzDetReplaceSide() > 0 || element.getHoDoRemont().toLowerCase().equals("кузовщик") || element.getDopWorksKuzovchik() > 0) {
                    logManager.log("Элемент имеет кузовные работы или ремонт выполняет кузовщик либо присутствуют доп работы для кузовщика.");
                    double kuz = 0;
                    String kuzline = element.getName();
                    if (element.getHoDoRemont().toLowerCase().equals("кузовщик") && element.getRemont() != 0) {
                        kuzline = kuzline.replace("замена", "") + (" ремонт: " + element.getRemont() + "н/ч ");
                        if (element.getName().contains("Моторный отсек") || element.getName().contains("Задняя панель")) {
                            kuz += element.getRemont();
                        } else {
                            kuz += (element.getRemont() * 0.7);
                        }

                    } else if (element.getHoDoRemont().toLowerCase().equals("кузовщик") && element.getNotNormWork() != 0) {
                        kuzline = kuzline + " " + element.getNotNormWork() + "н/ч ";
                        kuz += element.getNotNormWork();
                    }
                    if (element.getKuzDetReplaceSide() > 0) {
                        kuzline = kuzline + (": " + element.getKuzDetReplaceSide() + "н/ч ");
                        kuz += element.getKuzDetReplaceSide();
                    }
                    if (element.getDopWorksKuzovchik() > 0) {
                        if (element.getDescriptionDopWorksKuzovchik().equals("null")) {
                            kuzline = kuzline + (" доп.работы: " + element.getDopWorksKuzovchik() + "н/ч");
                        } else {
                            kuzline = kuzline + (" доп.работы: " + element.getDescriptionDopWorksKuzovchik() + " " + element.getDopWorksKuzovchik() + "н/ч");
                        }
                        kuz += element.getDopWorksKuzovchik();
                    }
                    kuzline = kuzline + (" Итого: " + ((int) (kuz * prices.getMechanicHourlyRate()) + " руб\n"));
                    writer.write(kuzline);
                    logManager.log("Расчет завершен произведена запись: " + kuzline);
                }
            }
            logManager.log("Итерация по элементам из списка завершена.");
            writer.write("Итог: " + (int) total.getKuzovchik() + " руб.");
            logManager.log("Произведена запись итоговой стоимости работ за все элементы из списка для Кузовщика: \"Итог: \" + (int) total.getKuzovchik() + \" руб.\"");
        }
    }   // Запись в файл сметы для кузовщика

    @SneakyThrows
    public void saveNonStaffMechanic(String dateDirectory) {
        logManager.log("Запущен метод saveNonStaffMechanic в классе WorkWithFile.");
        // Фильтруем список, удаляя нежелательные профили
        List<Element> filteredElements = elements.stream()
                .filter(e -> !EXCLUDED_PROFESSIONS.contains(e.getHoDoRemont()))
                .collect(Collectors.toList());

        // Теперь группируем отфильтрованные элементы по полю 'HoDoRemont'
        Map<String, List<Element>> groupedElements = filteredElements.stream()
                .collect(Collectors.groupingBy(Element::getHoDoRemont));

        // Проходим по каждому уникальному имени группы
        for (Map.Entry<String, List<Element>> entry : groupedElements.entrySet()) {

            String hoDoRemontName = entry.getKey();
            List<Element> groupElements = entry.getValue();

            // Формируем имя файла исходя из каталога и имени группы
            String filePath = dateDirectory + directories.getSlash() + hoDoRemontName + directories.getTxt();

            try (PrintWriter writer = new PrintWriter(filePath)) {
                double totalNotNormWork = 0;
                // Записываем каждую строку с данными элемента
                for (Element element : groupElements) {
                    writer.write(element.getName() + " " + element.getNotNormWork() + " н/ч итого: " + (int) (element.getNotNormWork() * prices.getMechanicHourlyRate()) + "руб.\n");
                    totalNotNormWork += element.getNotNormWork();
                }
                writer.write("Итог: " + (int) (totalNotNormWork * prices.getMechanicHourlyRate()) + "руб.");
                logManager.log("Расчет завершен произведена запись: \"Итог: \" " + (int) (totalNotNormWork * prices.getMechanicHourlyRate()) + "руб.");
            } catch (Exception e) {
                logManager.log("Ошибка записи файла: " + e.getMessage());
            }
        }
    } // Запись в файл не фиксированных(сторонних) механиков.

    @SneakyThrows
    private List<Element> loadElementsList(String nameStartDirectory) {
        logManager.log("Запущен метод loadElementsList в классе WorkWithFile.");
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
            // Проверка для совместимости со старыми расчетами!!!
            if (parts.length > 14) {
                element.setDopWorksArmoturchik(Double.parseDouble(parts[14]));
                element.setDopWorksPainter(Double.parseDouble(parts[15]));
                element.setDopWorksKuzovchik(Double.parseDouble(parts[16]));
                element.setLkmForElement(Double.parseDouble(parts[17]));
            }
            if (parts.length >= 19) {
                element.setDescriptionDopWorksArmaturchic(parts[18].replaceAll("\u2422", " ").replaceAll("\u2400", ",").replaceAll("\u2028", "\n"));
            }
            if (parts.length >= 20) {
                element.setDescriptionDopWorksPainter(parts[19].replaceAll("\u2422", " ").replaceAll("\u2400", ",").replaceAll("\u2028", "\n"));
            }
            if (parts.length >= 21) {
                element.setDescriptionDopWorksKuzovchik(parts[20].replaceAll("\u2422", " ").replaceAll("\u2400", ",").replaceAll("\u2028", "\n"));
            }
            if (parts.length >= 22) {
                element.setNotNormWork(Double.parseDouble(parts[21]));
            }
            if (parts.length >= 23) {
                element.setDescriptionDopNotNormWork(parts[22].replaceAll("\u2422", " ").replaceAll("\u2400", ",").replaceAll("\u2028", "\n"));
            }
            elements.add(element);
        }
        return elements;
    } // Метод загрузки из файла List<Element> elements

    private String toStringTotal(Total total) {
        logManager.log("Запущен метод toStringTotal в классе WorkWithFile.");
        return String.format("%s,%s,%s,%s,%s", total.getMalyr(), total.getArmatyrchik(), total.getKuzovchik(),
                total.getMaster(), total.getTotal(), total.getThirdPartyMechanic());
    } // Метод разбития Total в строки для сохранения в файл

    @SneakyThrows
    private Total loadTotal(String officialDirectory) {
        logManager.log("Запущен метод loadTotal в классе WorkWithFile.");
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(officialDirectory +
                directories.getSlash() + directories.getOFFICIAL_DIRECTORY() +
                directories.getSlash() + directories.getITOGO() + directories.getTxt()))) {
            String[] parts = bufferedReader.readLine().split(",");
            if (parts.length == 5) {
                // Данная реализация для возможности считывать старые расчеты из базы.
                return new Total(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]),
                        Double.parseDouble(parts[2]), Double.parseDouble(parts[3]), Double.parseDouble(parts[4]), 0);
            } else if (parts.length == 6) {
                // Данная реализация для новых расчетов, тех что после добавления не нормативных работ
                return new Total(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]),
                        Double.parseDouble(parts[2]), Double.parseDouble(parts[3]), Double.parseDouble(parts[4]), Double.parseDouble(parts[5]));
            } else {
                return new Total();
            }
        }
    } // Метод загрузки из файла Total

    private String toStringClient(Client client) {
        logManager.log("Запущен метод toStringClient в классе WorkWithFile.");
        return String.format("%s,%s,%s,%s", client.getName(), client.getFoneNumber(), client.getNumberAuto(), client.getModelAuto());
    } // Метод разбития Client в строки для сохранения в файл

    @SneakyThrows
    private Client loadClient(String officialDirectory) {
        logManager.log("Запущен метод loadClient в классе WorkWithFile.");
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
        logManager.log("Запущен метод writeLkmSmeta в классе WorkWithFile.");
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
        logManager.log("Запущен метод toStringLkm в классе WorkWithFile.");
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
        logManager.log("Запущен метод loadLkm в классе WorkWithFile.");
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
        logManager.log("Запущен метод saveMap в классе WorkWithFile.");
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
        logManager.log("Запущен метод loadMap в классе WorkWithFile.");
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