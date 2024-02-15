package ru.yandex.practicum.calcRemAuto.telegram;

import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.File;

public class TelegramFileSenderBot extends TelegramLongPollingBot {

    private static final String BOT_USERNAME = "KuzovnoyCehBot";
    private static final String BOT_TOKEN = "6740921975:AAHJpf5b0itEboDQkAPSFaMudKE7ak-2low";
    private static final String USER_ID = "203509655";
    private static final String FILE_PATH = "Расчеты\\Q111QQ111\\2024_02_15\\маляр.txt";

    public void send() {
        TelegramFileSenderBot bot = new TelegramFileSenderBot();
        bot.sendFile();
    }

    public void sendFile() {
        File fileToSend = new File(FILE_PATH);
        String caption = "Расчет автомобиля";

        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(USER_ID);
        sendDocument.setDocument(new InputFile(fileToSend));
        sendDocument.setCaption(caption);

        try {
            execute(sendDocument);
            System.out.println("File sent successfully!");
        } catch (TelegramApiException e) {
            e.printStackTrace();
            System.out.println("Failed to send file.");
        }
    }
    @Override
    public void onUpdateReceived(Update update) {
        // Обработка входящих обновлений (например, сообщений от пользователей)
    }
    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }
}
