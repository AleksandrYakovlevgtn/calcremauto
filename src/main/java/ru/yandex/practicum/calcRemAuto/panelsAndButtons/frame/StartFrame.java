package ru.yandex.practicum.calcRemAuto.panelsAndButtons.frame;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.TimeUnit;

@Getter
public class StartFrame {
    private static StartFrame currentInstance;
    JFrame frame = new JFrame("CalcRemAuto");
    ExecutorService executor = Executors.newSingleThreadExecutor(); // Для распараллеливания задач

    public StartFrame() {
        currentInstance = this;
    }

    public static StartFrame getCurrentInstance() {
        return currentInstance;
    }

    public JPanel createFrame(JPanel panel) {
        JMenuFrame menuFrame = new JMenuFrame(frame);
        frame.setSize(825, 605);
        frame.setLayout(new BorderLayout());
        frame.add(panel);
        frame.setJMenuBar(menuFrame.getMenuBar());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                shutdownExecutor();
                System.exit(0);
            }
        });
        return panel;
    }

    public void shutdownExecutor() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            executor.shutdownNow();
        }
    }
}
