package ru.yandex.practicum.calcRemAuto.logToFail;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class LogToFailManager {
    private static final String BASE_DIRECTORY = "Системные"; // Базовая директория задана константой
    private BufferedWriter writer;

    public LogToFailManager() {
        initializeLogFile();
    }

    private void initializeLogFile() {
        // Текущая дата
        LocalDate today = LocalDate.now();
        int currentYear = today.getYear();
        int currentMonth = today.getMonthValue();
        int currentDay = today.getDayOfMonth();

        // Формируем полный путь
        Path fullPath = Paths.get(BASE_DIRECTORY, "Логи", Integer.toString(currentYear), String.format("%02d", currentMonth), String.format("%02d", currentDay));

        // Имя файла логов
        Path logFilePath = fullPath.resolve("логи.txt");

        try {
            // Создаем все необходимые папки рекурсивно
            Files.createDirectories(fullPath);

            // Открываем файл для записи
            writer = new BufferedWriter(new FileWriter(logFilePath.toFile(), true));
        } catch (IOException e) {
            handleException(e, "Ошибка при инициализации файла логов");
        }
    }

    /**
     * Метод для записи сообщения в лог-файл
     *
     * @param message Сообщение для лога
     */
    public synchronized void log(String message) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd.HH-mm-ss");
        String formattedNow = now.format(formatter);
        initializeLogFile();
        try {
            writer.write("[" + formattedNow + "] " + message + "\n");
            log.info(message);
            writer.flush(); // Немедленно сохраняем запись в файл
            close();
        } catch (IOException e) {
            handleException(e, "Ошибка при записи в файл логов");
        }
    }

    /**
     * Завершаем работу менеджера логов и закрываем файл
     */
    public void close() {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                handleException(e, "Ошибка при закрытии файла логов");
            }
        }
    }

    /**
     * Внутренний метод для обработки исключений
     */
    private void handleException(Exception e, String message) {
        System.err.println(message + ": " + e.getMessage());
        e.printStackTrace(System.err);
    }
}