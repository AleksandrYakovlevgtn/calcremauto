package ru.yandex.practicum.calcRemAuto.panelsAndButtons.frame;

import ru.yandex.practicum.calcRemAuto.model.Client;
import ru.yandex.practicum.calcRemAuto.model.Element;
import ru.yandex.practicum.calcRemAuto.model.Total;
import ru.yandex.practicum.calcRemAuto.panelsAndButtons.buttons.Buttons;
import ru.yandex.practicum.calcRemAuto.storage.SaveInFail;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;


public class SaveDialog extends JDialog {
    JTextArea previewTextArea = new JTextArea();
    String probels = "_____________________________"; // уравнивание текста через нижнее подчеркивание
    Client client;
    List<Element> elements;
    Total total = new Total();
    Map<String, Map<String, List<String>>> lineBorderColorMap;
    Buttons but = new Buttons();

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
            SaveInFail saveInFail = new SaveInFail(client,total, elements, lineBorderColorMap);
            saveInFail.save(line);
            dispose();
        });    // Кнопка да "сохранить" отправляет в класс SaveInFail уже на оформление папок и файлов.

        but.getNoButton().addActionListener(e -> dispose()); // Кнопка нет "не сохранять" закрывает диалоговое окно.
    }

    /*public Client takeClient() {
        return new Client("Алексей", "89602819781", "х316со198", "mazda");
    }

    public List<Element> takeElements() {
        Element element1 = new Element("Левое Пер.Крыло", 3.4, 2, 0, 0, "", 0, 0, 0, 0, 0, 5.5, 0);
        Element element2 = new Element("Правая Пер.Дверь Замена", 3.4, 2, 0, 0, "", 1, 0, 0, 0, 0, 0, 0);
        List<Element> elements = new ArrayList<>();
        elements.add(element1);
        elements.add(element2);
        return elements;
    }

    public Map<String, Map<String, List<String>>> takeMap() {
        return new HashMap<>(Map.of("Левое", new HashMap<>(Map.of("Пер.Дверь", new ArrayList<>(List.of("Замена", "Окраска с 1х")))), "Правое", new HashMap<>(Map.of("Пер.Дверь", new ArrayList<>(List.of("Замена", "Окраска с 1х"))))));
    }*/



    /* public JFrame getSaveFrame() {

        saveFrame.setSize(800, 600);
        saveFrame.setLayout(new BorderLayout());
        saveFrame.setLocationRelativeTo(null);
        saveFrame.setVisible(true);
        saveFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Не забудь УБРАТЬ КАК ОТРЕДАКТИРУЮЕШЬ!!!!!!!!!!!!!!!!!!!

        previewTextArea.setLineWrap(true);
        previewTextArea.setEditable(false);

        JPanel yesNoPanel = new JPanel(new GridBagLayout());
        JPanel yesNoPanel2 = new JPanel(new GridBagLayout());
        JPanel yesNoXYZPanel = new JPanel(new BorderLayout());
        JPanel panelABC = new JPanel(new BorderLayout());

        yesNoPanel.add(new JLabel("Сохранить? Или вернутся к редактированию? "), new GridBagConstraints(1, 0, 6, 1, 0, 0,
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
        saveFrame.add(panelABC, BorderLayout.CENTER);
        String line = previewAddText(takeClient(), takeElements());
        but.getYesButton().addActionListener(e -> {
            SaveInFail saveInFail = new SaveInFail(takeClient(), total, takeElements(), takeMap());
            saveInFail.save(line);
        }); // Кнопка "да" при нажатии сохраняет расчет в файлы и закрывает окно.
        but.getNoButton().addActionListener(e -> {
            saveFrame.dispose();
        }); // Кнопка "нет" при нажатии закрываем окно и возвращаемся к выбору работ

        return saveFrame;
    }*/

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
                    + elements.get(i).getOverlay()) * 750));
            total.setArmatyrchik((total.getArmatyrchik())
                    + ((elements.get(i).getArmatureSide()
                    + elements.get(i).getGlass()) * 750));
            total.setKuzovchik(total.getKuzovchik() + (elements.get(i).getKuzDetReplaceSide() * 750));
            total.setMaster((total.getMaster())
                    + ((elements.get(i).getRemont()
                    + elements.get(i).getMolding()
                    + elements.get(i).getRuchka()
                    + elements.get(i).getZerkalo()
                    + elements.get(i).getExpander()
                    + elements.get(i).getOverlay()) * 500)
                    + (elements.get(i).getPaintSide() * 750)
                    + (elements.get(i).getGlass() * 250)
                    + (elements.get(i).getKuzDetReplaceSide() * 500));
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