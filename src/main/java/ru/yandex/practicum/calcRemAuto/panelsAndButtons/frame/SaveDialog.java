package ru.yandex.practicum.calcRemAuto.panelsAndButtons.frame;

import ru.yandex.practicum.calcRemAuto.model.*;
import ru.yandex.practicum.calcRemAuto.panelsAndButtons.buttons.Buttons;
import ru.yandex.practicum.calcRemAuto.storage.WorkWithFile;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;


public class SaveDialog extends JDialog {
    Prices prices = new Prices(); // Класс с ценами нормативов.
    Mechanics mechanics = new Mechanics();
    JTextArea previewTextArea = new JTextArea(); // Поле отображающие развернутую смету
    private boolean answer; // Возвращаемое значение для выполнения действия в родительском окне (закрыть или оставить)
    String probels = "_____________________________"; // уравнивание текста через нижнее подчеркивание
    Client client;
    List<Element> elements;  // Список элементов авто требующих ремонта.
    Total total = new Total(); // Класс итого посчитанный из List<Element> elements
    Map<String, Map<String, List<String>>> lineBorderColorMap; // Рабочая таблица с нажатыми кнопками для данного расчета
    Buttons but = new Buttons(); // Кнопки

    public SaveDialog(Frame parentFrame, Client client, List<Element> elements, Map<String, Map<String, List<String>>> lineBorderColorMap) {
        super(parentFrame, "Проверка перед сохранением!", true);

        this.client = client;
        this.elements = elements;
        this.lineBorderColorMap = lineBorderColorMap;

        setLayout(new BorderLayout());
        setSize(800, 600);
        setLocationRelativeTo(parentFrame);

        previewTextArea.setLineWrap(true);
        previewTextArea.setEditable(false);

        JPanel yesNoPanel = new JPanel(new GridBagLayout());
        JPanel yesNoPanel2 = new JPanel(new GridBagLayout());
        JPanel yesNoXYZPanel = new JPanel(new BorderLayout());
        JPanel panelABC = new JPanel(new BorderLayout());

        yesNoPanel.add(new JLabel("Сохранить? Или вернуться к редактированию? "), new GridBagConstraints(1, 0, 6, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 1, 1));
        yesNoPanel2.add(but.getYesButton(), new GridBagConstraints(0, 0, 1, 0, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 4, 0));
        yesNoPanel2.add(but.getNoButton(), new GridBagConstraints(1, 0, 1, 0, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 0, 0));
        yesNoPanel.add(yesNoPanel2, new GridBagConstraints(8, 0, 1, 1, 0, 0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 1, 1));

        yesNoXYZPanel.add(yesNoPanel, BorderLayout.NORTH);
        panelABC.add(yesNoXYZPanel, BorderLayout.NORTH);
        panelABC.add(previewTextArea, BorderLayout.CENTER);
        add(panelABC, BorderLayout.CENTER);

        String line = previewAddTextAndCreateTotal(client, elements); // Запуск метода отображения сметы

        but.getYesButton().addActionListener(e -> {
            WorkWithFile workWithFaile = new WorkWithFile(client, total, elements, lineBorderColorMap);
            workWithFaile.save(line);
            answer = true;
            dispose();
        });    // Кнопка да "сохранить" отправляет в класс WorkWithFile уже на оформление папок и файлов.

        but.getNoButton().addActionListener(e -> {
            answer = false;
            dispose();
        }); // Кнопка нет "не сохранять" закрывает диалоговое окно.
    }

    public boolean getAnswer() {
        return answer;
    } // метод возвращающий значение для выполнения действия в родительском окне (закрыть или оставить панель на окне)

    private String previewAddTextAndCreateTotal(Client client, List<Element> elements) {
        // StringBuilder для хранения строк
        StringBuilder line = new StringBuilder();

        // Итерация по элементам
        for (Element element : elements) {
            // Обновление total на основе текущего элемента и добавление строки для текущего элемента
            updateTotalValues(element);
            line.append(takeLine(element));
        }

        // Обновление общего значения total
        updateTotal();

        // Установка текста в previewTextArea на основе клиента, total и строк
        previewTextArea.setText(buildPreviewText(client, line));
        return previewTextArea.getText();
    }

    private double calculateMalyr(Element element) {
        Double total = (element.getPaintSide()  + element.getMolding()
                + element.getRuchka() + element.getZerkalo() + element.getExpander()
                + element.getOverlay()) * prices.getMechanicHourlyRate();
        if(element.getHoDoRemont().equals("маляр")){
            total = total + (element.getRemont() * prices.getMechanicHourlyRate());
        }
        return total;

    }// Метод для расчета значения Маляр на основе текущего элемента

    private double calculateArmatyrchik(Element element) {
        Double total =(element.getArmatureSide() + element.getGlass()) * prices.getMechanicHourlyRate();
        if(element.getHoDoRemont().equals("арматурщик")){
            total = total + (element.getRemont() * prices.getMechanicHourlyRate());
        }
        return total;
    } // Метод для расчета значения Арматурщик на основе текущего элемента

    private double calculateKuzovchik(Element element) {
        Double total = element.getKuzDetReplaceSide() * prices.getMechanicHourlyRate();
        if(element.getHoDoRemont().equals("кузовщик")){
            total = total + (element.getRemont() * prices.getMechanicHourlyRate());
        }
        return total;
    }// Метод для расчета значения Кузовщик на основе текущего элемента

    private double calculateMaster(Element element) {
        return (element.getRemont() + element.getMolding() + element.getRuchka()
                + element.getZerkalo() + element.getExpander() + element.getOverlay()
                + element.getPaintSide() + element.getGlass() + element.getKuzDetReplaceSide()
                + element.getArmatureSide()) * prices.getMasterHourlyRate();
    }// Метод для расчета значения Мастер на основе текущего элемента

    private void updateTotalValues(Element element) {
        total.setMalyr(total.getMalyr() + calculateMalyr(element));
        total.setArmatyrchik(total.getArmatyrchik() + calculateArmatyrchik(element));
        total.setKuzovchik(total.getKuzovchik() + calculateKuzovchik(element));
        total.setMaster(total.getMaster() + calculateMaster(element));
    } // Метод для обновления значений total на основе текущего элемента

    private void updateTotal() {
        total.setTotal(total.getTotal() + total.getArmatyrchik() + total.getMalyr() + total.getKuzovchik() + total.getMaster());
    }// Метод для обновления общего значения total

    private String buildPreviewText(Client client, StringBuilder line) {
        return client.toString()
                + "\n" + "Автомаляр: " + mechanics.getMalyr() + " Арматурщик: " + mechanics.getArmoturchik() + " Кузовщик: " + mechanics.getKuzovchik()
                + "\n" + total.toString() + "\n" + line.toString();
    }// Метод для построения окончательной строки текста для previewTextArea

    private String takeLine(Element element) {
        StringBuilder line = new StringBuilder(element.getName());
        line.append(probels.substring(element.getName().length()));
        if (!element.getName().contains("Остекление")) {
            line.append(" окраска: ").append(element.getPaintSide()).append(" н/ч.")
                    .append(" ремонт: ").append(element.getRemont()).append(" н/ч.")
                    .append(" замена кузовной детали: ").append(element.getKuzDetReplaceSide()).append(" н/ч.")
                    .append(" р/с: ").append(element.getArmatureSide()).append(" н/ч. ");
            if (element.getGlass() > 0) line.append(element.getNameGlass());
            if (element.getZerkalo() > 0) line.append(" зеркало ");
            if (element.getMolding() > 0) line.append(" молдинг ");
            if (element.getRuchka() > 0) line.append(" ручка ");
            if (element.getZerkalo() > 0 || element.getMolding() > 0 || element.getRuchka() > 0)
                line.append(" окраска.");
            if (element.getExpander() > 0)
                line.append(" расширитель окраска: ").append(element.getExpander()).append(" н/ч.");
            if (element.getOverlay() > 0)
                line.append(" накладка окраска: ").append(element.getOverlay()).append(" н/ч.");
        }
        return line.append("\n").toString();
    } // Создание текста отображения сметы
}