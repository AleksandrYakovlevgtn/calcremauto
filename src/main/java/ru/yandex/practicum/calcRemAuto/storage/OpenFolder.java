package ru.yandex.practicum.calcRemAuto.storage;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.calcRemAuto.logToFail.LogToFailManager;

import javax.swing.*;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

@Slf4j
public class OpenFolder {
    static LogToFailManager logManager = new LogToFailManager();

    public void open(String folderPath) {
        logManager.log("Запущен метод open в классе OpenFolder");
        try {
            // Получение экземпляра класса Desktop
            Desktop desktop = Desktop.getDesktop();
            // Создание объекта File для указанной папки
            File folder = new File(folderPath);
            // Проверка существования папки.
            if (!folder.exists()) {
                logManager.log("Папка по пути " + folderPath + " не существует.");
                JOptionPane.showMessageDialog(null, "Папка не существует.", "Предупреждение", JOptionPane.WARNING_MESSAGE);
                return;
            }
            // Открытие папки
            desktop.open(folder);
            logManager.log("Открылась папка по пути: " + folderPath);
        } catch (IOException e) {
            logManager.log(e.getMessage());
        }
    }
}