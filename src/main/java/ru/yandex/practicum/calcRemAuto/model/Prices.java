package ru.yandex.practicum.calcRemAuto.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.*;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class Prices {
    boolean read = false; // Прочитан файл или нет
    int hourlyRate; // Стоимость норма часа
    int mechanicHourlyRate; // Ставка механика
    int masterHourlyRate; // Ставка мастера

    public int getHourlyRate() {
        readOrNotReadItFile();
        return hourlyRate;
    }

    public int getMechanicHourlyRate() {
        readOrNotReadItFile();
        return mechanicHourlyRate;
    }

    public int getMasterHourlyRate() {
        readOrNotReadItFile();
        return masterHourlyRate;
    }

    public void readOrNotReadItFile() {
        if (!read) {
            readRatesFromFile();
            read = true;
        }
    } // При гетерах начальная проверка считывался ли файл, если нет то считываем "readRatesFromFile" и меняем read = true

    public void readRatesFromFile() {
        try {
            File directory = new File("system");

            if (!directory.exists()) {
                writeRatesToFile(0, 0, 0); // Отправляем на создание папки и файла если не существуют.
            }

            File file = new File(directory, "rates.txt");

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                hourlyRate = Integer.parseInt(reader.readLine());
                mechanicHourlyRate = Integer.parseInt(reader.readLine());
                masterHourlyRate = Integer.parseInt(reader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    } // Чтение из файла параметров

    public void writeRatesToFile(int hourlyRate, int mechanicHourlyRate, int masterHourlyRate) {
        try {
            File directory = new File("system");
            if (!directory.exists()) {
                directory.mkdirs(); // Создаем папку если не существует
            }

            File file = new File(directory, "rates.txt");
            if (!file.exists()) {
                file.createNewFile(); // Создаем файл если не существует
            }

            try (FileWriter writer = new FileWriter(file)) {
                this.hourlyRate = hourlyRate;
                this.mechanicHourlyRate = mechanicHourlyRate;
                this.masterHourlyRate = masterHourlyRate;
                writer.write(hourlyRate + "\n");
                writer.write(mechanicHourlyRate + "\n");
                writer.write(masterHourlyRate + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    } // Запись в файл параметры
}