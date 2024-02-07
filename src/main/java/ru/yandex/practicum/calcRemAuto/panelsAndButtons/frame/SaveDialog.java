package ru.yandex.practicum.calcRemAuto.panelsAndButtons.frame;

import ru.yandex.practicum.calcRemAuto.model.Client;
import ru.yandex.practicum.calcRemAuto.model.Element;
import ru.yandex.practicum.calcRemAuto.model.Prices;
import ru.yandex.practicum.calcRemAuto.model.Total;
import ru.yandex.practicum.calcRemAuto.panelsAndButtons.buttons.Buttons;
import ru.yandex.practicum.calcRemAuto.storage.SaveInFail;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;


public class SaveDialog extends JDialog {
    Prices prices = new Prices(); // Класс с ценами нормативов.
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
            SaveInFail saveInFail = new SaveInFail(client, total, elements, lineBorderColorMap);
            saveInFail.save(line);
            answer = true;
            dispose();
        });    // Кнопка да "сохранить" отправляет в класс SaveInFail уже на оформление папок и файлов.

        but.getNoButton().addActionListener(e -> {
            answer = false;
            dispose();
        }); // Кнопка нет "не сохранять" закрывает диалоговое окно.
    }

    public boolean getAnswer() {
        return answer;
    } // метод возвращающий значение для выполнения действия в родительском окне (закрыть или оставить панель на окне)

    private String previewAddTextAndCreateTotal(Client client, List<Element> elements) {
        String line = "";
        for (int i = 0; i < elements.size(); i++) {
            total.setMalyr((total.getMalyr())
                    + ((elements.get(i).getPaintSide()
                    + elements.get(i).getRemont()
                    + elements.get(i).getMolding()
                    + elements.get(i).getRuchka()
                    + elements.get(i).getZerkalo()
                    + elements.get(i).getExpander()
                    + elements.get(i).getOverlay()) * prices.getMechanicHourlyRate()));
            total.setArmatyrchik((total.getArmatyrchik())
                    + ((elements.get(i).getArmatureSide()
                    + elements.get(i).getGlass()) * prices.getMechanicHourlyRate()));
            total.setKuzovchik(total.getKuzovchik() + (elements.get(i).getKuzDetReplaceSide() * prices.getMechanicHourlyRate()));
            total.setMaster((total.getMaster())
                    + ((elements.get(i).getRemont()
                    + elements.get(i).getMolding()
                    + elements.get(i).getRuchka()
                    + elements.get(i).getZerkalo()
                    + elements.get(i).getExpander()
                    + elements.get(i).getOverlay()
                    + elements.get(i).getPaintSide()
                    + elements.get(i).getGlass()
                    + elements.get(i).getKuzDetReplaceSide()
                    + elements.get(i).getArmatureSide()) * prices.getMasterHourlyRate()));
            line += takeLine(elements.get(i));
        }
        total.setTotal(total.getTotal()
                + total.getArmatyrchik()
                + total.getMalyr()
                + total.getKuzovchik()
                + total.getMaster());

        previewTextArea.setText(client.toString() + "\n");
        previewTextArea.setText(previewTextArea.getText() + total.toString() + "\n");
        previewTextArea.setText(previewTextArea.getText() + line);
        return previewTextArea.getText();
    } // Отображение созданной через метод "takeLine" сметы.

    private String takeLine(Element element) {
        String line = element.getName();
        line = line + probels.substring(element.getName().length());
        if (!element.getName().contains("Остекление")) {
            line += " окраска: " + element.getPaintSide() + " н/ч."
                    + " ремонт: " + element.getRemont() + " н/ч."
                    + " замена кузовной детали: " + element.getKuzDetReplaceSide() + " н/ч."
                    + " р/с: " + element.getArmatureSide() + " н/ч. ";
            if (element.getGlass() > 0) line += element.getNameGlass();
            if (element.getZerkalo() > 0) line += " зеркало ";
            if (element.getMolding() > 0) line += " молдинг ";
            if (element.getRuchka() > 0) line += " ручка ";
            if (element.getZerkalo() > 0 || element.getMolding() > 0 || element.getRuchka() > 0) line += " окраска.";
            if (element.getExpander() > 0) line += " расширитель окраска: " + element.getExpander() + " н/ч.";
            if (element.getOverlay() > 0) line += " накладка окраска: " + element.getOverlay() + " н/ч.";
        }
        return line += "\n";
    } // Создание текста отображения сметы
}