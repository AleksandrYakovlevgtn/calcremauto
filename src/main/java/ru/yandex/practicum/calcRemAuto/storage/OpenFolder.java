package ru.yandex.practicum.calcRemAuto.storage;

import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

@Slf4j
public class OpenFolder {
    public void open(String folderPath) {
        try {
            // Получение экземпляра класса Desktop
            Desktop desktop = Desktop.getDesktop();
            // Создание объекта File для указанной папки
            File folder = new File(folderPath);
            // Проверка существования папки.
            if (!folder.exists()) {
                log.warn("Папка по пути " + folderPath + " не существует.");
                JOptionPane.showMessageDialog(null, "Папка не существует.", "Предупреждение", JOptionPane.WARNING_MESSAGE);
                return;
            }
            // Открытие папки
            desktop.open(folder);
            log.info("Открылась папка по пути: " + folderPath);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}