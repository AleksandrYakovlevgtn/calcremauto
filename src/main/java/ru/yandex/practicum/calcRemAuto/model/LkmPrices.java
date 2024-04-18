package ru.yandex.practicum.calcRemAuto.model;

import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.*;

@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LkmPrices {
    boolean read = false; // Прочитан файл или нет
    int circle; // круги
    int strip; // полоски
    double scotchBrite; // сотчБрайт рулон
    double priming; // Грунт
    double clear; // Лак HS
    double baseDilution; // Разбавитель 5л
    double basePaint; // эмаль базовая за 1гр.
    double siliconRemover; // обезжириватель
    double stickyTape; // Малярный бумажный скотч
    double coveringFilm; // пленка укрывачная
    int napkin; // Салфетки химостойкие
    double puttyFiber; // Шпатлевка волокнистая
    double puttyUniversal; // Шпатлевка универсальная
    int hermetic; // Герметик кузовной

    public void readOrNotReadItFile() {
        if (!read) {
            readPricesFromFile();
            read = true;
        }
    } // При гетерах начальная проверка считывался ли файл, если нет то считываем "readPricesFromFile" и меняем read = true

    public void readPricesFromFile() {
        try {
            File directory = new File("Системные");

            if (!directory.exists()) {
                writePricesToFile(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0); // Отправляем на создание папки и файла если не существуют.
            }
            File file = new File(directory, "Стоимость_ЛКМ.txt");
            if (!file.exists()) {
                writePricesToFile(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0); // Отправляем на создание папки и файла если не существуют.
            }
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                circle = Integer.parseInt(reader.readLine());
                strip = Integer.parseInt(reader.readLine());
                scotchBrite = Double.parseDouble(reader.readLine());
                priming = Double.parseDouble(reader.readLine());
                clear = Double.parseDouble(reader.readLine());
                baseDilution = Double.parseDouble(reader.readLine());
                basePaint = Double.parseDouble(reader.readLine());
                siliconRemover = Double.parseDouble(reader.readLine());
                stickyTape = Double.parseDouble(reader.readLine());
                coveringFilm = Double.parseDouble(reader.readLine());
                napkin = Integer.parseInt(reader.readLine());
                puttyFiber = Double.parseDouble(reader.readLine());
                puttyUniversal = Double.parseDouble(reader.readLine());
                hermetic = Integer.parseInt(reader.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    } // Чтение из файла параметров

    public void writePricesToFile(int circle,
                                  int strip,
                                  double scotchBrite,
                                  double priming,
                                  double clear,
                                  double baseDilution,
                                  double basePaint,
                                  double siliconRemover,
                                  double stickyTape,
                                  double coveringFilm,
                                  int napkin,
                                  double puttyFiber,
                                  double puttyUniversal,
                                  int hermetic) {
        try {
            File directory = new File("Системные");
            if (!directory.exists()) {
                directory.mkdirs(); // Создаем папку если не существует
            }

            File file = new File(directory, "Стоимость_ЛКМ.txt");
            if (!file.exists()) {
                file.createNewFile(); // Создаем файл если не существует
            }
            try (FileWriter writer = new FileWriter(file)) {
                this.circle = circle;
                this.strip = strip;
                this.scotchBrite = scotchBrite;
                this.priming = priming;
                this.clear = clear;
                this.baseDilution = baseDilution;
                this.basePaint = basePaint;
                this.siliconRemover = siliconRemover;
                this.stickyTape = stickyTape;
                this.coveringFilm = coveringFilm;
                this.napkin = napkin;
                this.puttyFiber = puttyFiber;
                this.puttyUniversal = puttyUniversal;
                this.hermetic = hermetic;
                writer.write(circle + "\n");
                writer.write(strip + "\n");
                writer.write(scotchBrite + "\n");
                writer.write(priming + "\n");
                writer.write(clear + "\n");
                writer.write(baseDilution + "\n");
                writer.write(basePaint + "\n");
                writer.write(siliconRemover + "\n");
                writer.write(stickyTape + "\n");
                writer.write(coveringFilm + "\n");
                writer.write(napkin + "\n");
                writer.write(puttyFiber + "\n");
                writer.write(puttyUniversal + "\n");
                writer.write(hermetic + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    } // Запись в файл параметры

    public int getCircle() {
        readOrNotReadItFile();
        return circle;
    }

    public int getStrip() {
        readOrNotReadItFile();
        return strip;
    }

    public Double getScotchBrite() {
        readOrNotReadItFile();
        return scotchBrite;
    }

    public Double getPriming() {
        readOrNotReadItFile();
        return priming;
    }

    public Double getClear() {
        readOrNotReadItFile();
        return clear;
    }

    public Double getBaseDilution() {
        readOrNotReadItFile();
        return baseDilution;
    }

    public Double getBasePaint() {
        readOrNotReadItFile();
        return basePaint;
    }

    public Double getSiliconRemover() {
        readOrNotReadItFile();
        return siliconRemover;
    }

    public Double getStickyTape() {
        readOrNotReadItFile();
        return stickyTape;
    }

    public Double getCoveringFilm() {
        readOrNotReadItFile();
        return coveringFilm;
    }

    public int getNapkin() {
        readOrNotReadItFile();
        return napkin;
    }

    public Double getPuttyFiber() {
        readOrNotReadItFile();
        return puttyFiber;
    }

    public Double getPuttyUniversal() {
        readOrNotReadItFile();
        return puttyUniversal;
    }

    public int getHermetic() {
        readOrNotReadItFile();
        return hermetic;
    }
}
