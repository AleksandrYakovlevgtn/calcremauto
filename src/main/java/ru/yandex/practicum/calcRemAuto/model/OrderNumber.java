package ru.yandex.practicum.calcRemAuto.model;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class OrderNumber {

    private static final String DIRECTORY_PATH = "Системные";   // Директория хранения файла
    private static final String FILE_NAME = "Номер заказа.txt"; // Имя файла

    private int number;

    public synchronized void increment() throws IOException {
        ensureDirectoryExists();       // Обеспечиваем наличие директории
        this.number = readFromFile();  // Читаем текущее значение
        this.number++;                 // Увеличиваем значение
        writeToFile();                 // Записываем обновленное значение
    }

    // Получение текущего значения номера заказа
    public synchronized int getCurrentValue() throws IOException {
        ensureDirectoryExists();       // Обеспечиваем наличие директории
        return readFromFile();         // Читаем текущее значение
    }

    // Чтение числа из файла. Если файл пустой или отсутствует, возвращается значение по умолчанию равное нулю
    private int readFromFile() throws IOException {
        File file = new File(DIRECTORY_PATH, FILE_NAME);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            if (line != null && !line.isEmpty()) {
                return Integer.parseInt(line.trim()); // Преобразование строки в число
            }
        } catch (FileNotFoundException ignored) {
            // Игнорируем ошибку, если файл не найден
            log.error("Ошибка чтения файла " + DIRECTORY_PATH + "/" + FILE_NAME + ", при получении текущего значения числа:", ignored);
        }
        // Если файл не найден или пустое значение => возвращаем 0
        return 0;
    }

    // Запись нового значения в файл. Создаем файл, если его нет
    private void writeToFile() throws IOException {
        File file = new File(DIRECTORY_PATH, FILE_NAME);
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.println(number); // Пишем значение в файл
        }
    }

    // Гарантирует создание директории, если её нет
    private void ensureDirectoryExists() {
        File directory = new File(DIRECTORY_PATH);
        if (!directory.exists()) {          // Проверка наличия директории
            boolean created = directory.mkdirs(); // Создаем директорию рекурсивно
            if (!created) {
                throw new RuntimeException("Ошибка при создании директории");
            }
        }
    }
}