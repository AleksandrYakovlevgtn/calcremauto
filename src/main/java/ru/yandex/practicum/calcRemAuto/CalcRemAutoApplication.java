package ru.yandex.practicum.calcRemAuto;

import ru.yandex.practicum.calcRemAuto.telegram.TelegramFileSenderBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.yandex.practicum.calcRemAuto.panelsAndButtons.panels.FirstPanel;
import ru.yandex.practicum.calcRemAuto.panelsAndButtons.frame.StartFrame;

import javax.swing.*;
import java.awt.*;

@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = "ru.yandex.practicum.calcRemAuto")
public class CalcRemAutoApplication {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SpringApplication.run(CalcRemAutoApplication.class, args);
            StartFrame frame = new StartFrame();
            JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    // Загрузка изображения
                    Image image = new ImageIcon("Системные/первая_страница.png").getImage();
                    // Рисование изображения как фона
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                }
            };
            panel = frame.createFrame(panel);
            FirstPanel firstPanel = new FirstPanel();
            firstPanel.createFirstPanel(panel);
            try {
                TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
                botsApi.registerBot(new TelegramFileSenderBot());
            } catch (TelegramApiException e) {
                JOptionPane.showMessageDialog(null, "Нет связи с интернетом! \nTelegram не доступен!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                log.error("Нет связи с интернетом! Telegram не доступен!" + e.getMessage());
                //throw new RuntimeException(e);
            }
        });
    }
}