package ru.yandex.practicum.calcRemAuto.telegram;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import ru.yandex.practicum.calcRemAuto.model.NameDirectories;
import ru.yandex.practicum.calcRemAuto.model.TelegramBotInfo;

import javax.swing.*;
import java.io.File;

@Slf4j
public class TelegramFileSenderBot extends TelegramLongPollingBot {
    TelegramBotInfo telegramBotInfo = new TelegramBotInfo();
    NameDirectories directories = new NameDirectories();
    private final String BOT_USERNAME = telegramBotInfo.getBotUserName();
    private final String BOT_TOKEN = telegramBotInfo.getBotToken();
    private final String USER_ID = telegramBotInfo.getChatId();

    public void sendFile(String filePath) {
        String[] parts = filePath.split("/");
        String[] filePaths = {
                filePath + directories.getSlash() + directories.getMALYAR() + directories.getTxt(),
                filePath + directories.getSlash() + directories.getARMOTURCHIK() + directories.getTxt(),
                filePath + directories.getSlash() + directories.getKUZOVCHIK() + directories.getTxt()
        };

        sendMessage("Расчет автомобиля " + parts[1] + " от " + parts[2]);

        for (String path : filePaths) {
            File fileToSend = new File(path);

            SendDocument sendDocument = new SendDocument();
            sendDocument.setChatId(USER_ID);
            sendDocument.setDocument(new InputFile(fileToSend));

            try {
                execute(sendDocument);
                log.info("Сметы отправлены удачно!");
            } catch (TelegramApiException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
                log.error("Ошибка отправки смет.");
            }
        }
    }

    private void sendMessage(String text) {
        SendMessage message = new SendMessage();
        message.setChatId(USER_ID);
        message.setText(text);
        try {
            execute(message);
            log.info("Заголовок отправлен!");
        } catch (TelegramApiException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            log.error("Ошибка отправки заголовка.");
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.info("Что то хотели но не реализованно" + update.getMessage());
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