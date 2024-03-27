package ru.yandex.practicum.calcRemAuto.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.*;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Mechanics {
    boolean read = false;
    String malyr;
    String armoturchik;
    String kuzovchik;
    String master;

    public String getMalyr() {
        readMechanicsFile();
        return malyr;
    }

    public String getArmoturchik() {
        readMechanicsFile();
        return armoturchik;
    }

    public String getKuzovchik() {
        readMechanicsFile();
        return kuzovchik;
    }

    public String getMaster() {
        readMechanicsFile();
        return master;
    }


    public void readMechanicsFile() {
        if (!read) {
            readRatesFromFile();
            read = true;
        }
    } // При гетерах начальная проверка считывался ли файл, если нет то считываем "readRatesFromFile" и меняем read = true

    public void readRatesFromFile() {
        try {
            File directory = new File("Системные");

            if (!directory.exists()) {
                writeRatesToFile("", "", "", ""); // Отправляем на создание папки и файла если не существуют.
            }

            File file = new File(directory, "Механики.txt");
            if (!file.exists()) {
                writeRatesToFile("", "", "", ""); // Создаем файл если не существует
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                malyr = reader.readLine();
                armoturchik = reader.readLine();
                kuzovchik = reader.readLine();
                master = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    } // Чтение из файла параметров

    public void writeRatesToFile(String malyr, String armoturchik, String kuzovchik, String master) {
        try {
            File directory = new File("Системные");
            if (!directory.exists()) {
                directory.mkdirs(); // Создаем папку если не существует
            }

            File file = new File(directory, "Механики.txt");
            if (!file.exists()) {
                file.createNewFile(); // Создаем файл если не существует
            }

            try (FileWriter writer = new FileWriter(file)) {
                this.malyr = malyr;
                this.armoturchik = armoturchik;
                this.kuzovchik = kuzovchik;
                this.master = master;
                writer.write(malyr + "\n");
                writer.write(armoturchik + "\n");
                writer.write(kuzovchik + "\n");
                writer.write(master + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    } // Запись в файл параметры
}