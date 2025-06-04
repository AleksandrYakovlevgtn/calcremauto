package ru.yandex.practicum.calcRemAuto.panelsAndButtons.frame;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.calcRemAuto.grammar.CustomDocumentHandler;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class DescriptionWindow extends JDialog implements ActionListener {

    private JTextArea notNormWorkDescription;
    private JButton okButton;
    private JButton cancelButton;
    private String inputNotNormWorkDescription;
    private boolean isCanceled = false;
    private final ExecutorService localExecutor = Executors.newSingleThreadExecutor();

    public DescriptionWindow(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        setSize(300, 300);
        setLayout(new BorderLayout());

        initComponents();
    }

    private void initComponents() {
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BorderLayout());

        notNormWorkDescription = new JTextArea();
        notNormWorkDescription.setWrapStyleWord(true); // Включить перенос по словам
        notNormWorkDescription.setLineWrap(true);      // Перенос строк
        textPanel.add(notNormWorkDescription, BorderLayout.CENTER);
        textPanel.setPreferredSize(new Dimension(200, 220));
        add(textPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        okButton = new JButton("OK");
        okButton.addActionListener(this);
        cancelButton = new JButton("Отмена");
        cancelButton.addActionListener(this);

        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Запуск проверки грамматики
        setupGrammarCheck();
        log.info("Создана и открыто окно ввода текста описания ненормативных работ");
    }

    private void setupGrammarCheck() {
        localExecutor.submit(() -> {
            // Логика проверки грамматик
            CustomDocumentHandler handler1 = new CustomDocumentHandler(notNormWorkDescription, 50);
            ((AbstractDocument) notNormWorkDescription.getDocument()).setDocumentFilter(handler1);
            notNormWorkDescription.getDocument().addDocumentListener(handler1);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == okButton) {
            // Сначала проверяем что описание заполнено и можно продолжать обрабатывать нажатия
            if (notNormWorkDescription.getText().length() > 0) {
                inputNotNormWorkDescription = notNormWorkDescription.getText();
                log.info("Описание ненормативных работ выбрана кнопка OK, текст: " + inputNotNormWorkDescription);
                dispose();
            }
        } else if (source == cancelButton) {
            inputNotNormWorkDescription = null;
            isCanceled = true;
            log.info("Описание ненормативных работ выбрана кнопка Отменена");
            dispose();
        }
    }

    @Override
    public void dispose() {
        try {
            if (!localExecutor.awaitTermination(50, TimeUnit.MILLISECONDS)) {
                localExecutor.shutdownNow(); // Принудительно останавливаем потоки
            }
        } catch (InterruptedException ex) {
            localExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        super.dispose(); // Закрытие окна
    }

    public String getInputNotNormWorkDescription() {
        return inputNotNormWorkDescription;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void resetFields() {
        notNormWorkDescription.setText("");
        inputNotNormWorkDescription = null;
        isCanceled = false;
    }

    public void setInitialText(String initialText) {
        notNormWorkDescription.setText(initialText);
    }
}