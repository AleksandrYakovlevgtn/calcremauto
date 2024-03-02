package ru.yandex.practicum.calcRemAuto.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.*;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramBotInfo {
    boolean read = false;
    String botUserName;
    String botToken;
    String chatId;

    public String getBotUserName() {
        readTelegramFile();
        return botUserName;
    }

    public String getBotToken() {
        readTelegramFile();
        return botToken;
    }

    public String getChatId() {
        readTelegramFile();
        return chatId;
    }

    public void readTelegramFile() {
        if (!read) {
            readTelegramFromFile();
            read = true;
        }
    } // При гетерах начальная проверка считывался ли файл, если нет то считываем "readTelegramFromFile" и меняем read = true

    public void readTelegramFromFile() {
        try {
            File directory = new File("Системные");

            if (!directory.exists()) {
                writeTelegramToFile("", "", ""); // Отправляем на создание папки и файла если не существуют.
            }

            File file = new File(directory, "Telegram.txt");
            if (!file.exists()) {
                writeTelegramToFile("", "", ""); // Создаем файл если не существует
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                botUserName = reader.readLine();
                botToken = reader.readLine();
                chatId = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    } // Чтение из файла параметров

    public void writeTelegramToFile(String botUserName, String botToken, String chatId) {
        try {
            File directory = new File("Системные");
            if (!directory.exists()) {
                directory.mkdirs(); // Создаем папку если не существует
            }

            File file = new File(directory, "Telegram.txt");
            if (!file.exists()) {
                file.createNewFile(); // Создаем файл если не существует
            }

            try (FileWriter writer = new FileWriter(file)) {
                this.botUserName = botUserName;
                this.botToken = botToken;
                this.chatId = chatId;
                writer.write(botUserName + "\n");
                writer.write(botToken + "\n");
                writer.write(chatId + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    } // Запись в файл параметры
}