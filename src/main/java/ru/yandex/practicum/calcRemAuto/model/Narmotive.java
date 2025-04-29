package ru.yandex.practicum.calcRemAuto.model;

import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.nio.file.Files;
import java.util.List;
import java.io.File;

import java.io.FileWriter;
import java.io.IOException;


@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Narmotive {
    NameDirectories d = new NameDirectories();
    boolean read = false; // Прочитан файл или нет
    // Переднее крыло
    double FrontWingDisassemble; //Пер.Крыло р/с
    double FrontWingReplace;     //Пер.Крыло замена
    double FrontWingPaint1x;     //Пер.Крыло окраска с одной стороны
    double FrontWingPaint2x;     //Пер.Крыло окраска с двух сторон
    double FrontWingPolish;      //Пер.Крыло полировка
    double FrontWingExpanderDisassemble;// Расширитель Пер.Крыла р/с
    double FrontWingExpanderPaint;      // Расширитель Пер.Крыла окраска
    // Передняя дверь
    double FrontDoorDisassemble; //Пер.Дверь р/с
    double FrontDoorReplace;     //Пер.Дверь замена
    double FrontDoorPaint1x;     //Пер.Дверь окраска с одной стороны
    double FrontDoorPaint2x;     //Пер.Дверь окраска с двух сторон
    double FrontDoorPolish;      //Пер.Дверь  полировка
    // Задняя дверь
    double BackDoorDisassemble; //Зад.Дверь р/с
    double BackDoorReplace;     //Зад.Дверь замена
    double BackDoorPaint1x;     //Зад.Дверь окраска с одной стороны
    double BackDoorPaint2x;     //Зад.Дверь окраска с двух сторон
    double BackDoorPolish;      //Зад.Дверь полировка
    // Зад.Крыло
    double BackWingDisassemble;           // Зад.Крыло р/с
    double BackWingReplace;               // Зад.Крыло р/с для замена Expander
    double BackWingReplaceKuzDet;         // Зад.Крыло замена кузовной детали
    double BackWingPaint1x;               // Зад.Крыло окраска с одной стороны
    double BackWingPaint2x;               // Зад.Крыло окраска с двух сторон
    double BackWingPolish;                // Зад.Крыло полировка
    double BackWingExpanderDisassemble;   // Расширитель Зад.Крыла р/с
    double BackWingExpanderPaint;         // Расширитель Зад.Крыла окраска
    // Брус
    double BalkDisassemble;               // Брус р/с
    double BalkReplace;                   // Брус р/с для замены
    double BalkReplaceKuzDet;             // Брус замена кузовной детали
    double BalkPaint1x;                   // Брус окраска с одной стороны
    double BalkPaint2x;                   // Брус окраска с двух сторон
    double BalkPolish;                    // Брус полировка
    // Порог
    double DoorStepDisassemble;           // Порог р/с
    double DoorStepReplace;               // Порог р/с для замены
    double DoorStepReplaceKuzDet;         // Порог замена кузовной детали
    double DoorStepPaint1x;               // Порог окраска с одной стороны
    double DoorStepPaint2x;               // Порог окраска с двух сторон
    double DoorStepOverlayDisassemble;    // Накладка порога р/с
    double DoorStepOverlayPaint;          // Накладка порога окраска
    // Пер.Проём
    double FrontDoorwayDisassemble;       // Пер.Проём р/с
    double FrontDoorwayPaint1x;           // Пер.Проём окраска с одной стороны
    double FrontDoorwayPaint2x;           // Пер.Проём окраска с двух сторон
    double FrontDoorwayPolish;            // Пер.Проём полировка
    // Зад.Проём
    double BackDoorwayDisassemble;        // Зад.Проём р/с
    double BackDoorwayPaint1x;            // Зад.Проём окраска с одной стороны
    double BackDoorwayPaint2x;            // Зад.Проём окраска с двух сторон
    double BackDoorwayPolish;             // Зад.Проём полировка
    // Пер.Бампер
    double frontBumperDisassemble;        // Пер.Бампер р/с
    double frontBumperReplace;            // Пер.Бампер замена
    double frontBumperPaint1x;            // Пер.Бампер окраска с одной стороны
    double frontBumperPaint2x;            // Пер.Бампер окраска с двух сторон
    double frontBumperPolish;             // Пер.Бампер полировка
    double frontBumperOverlayDisassemble; // Накладка Пер.Бампера р/с
    double frontBumperOverlayPaint;       // Накладка Пер.Бампера окраска
    // Зад.Бампер
    double rearBumperDisassemble;         // Зад.Бампер р/с
    double rearBumperReplace;             // Зад.Бампер замена
    double rearBumperPaint1x;             // Зад.Бампер окраска с одной стороны
    double rearBumperPaint2x;             // Зад.Бампер окраска с двух сторон
    double rearBumperPolish;              // Зад.Бампер полировка
    double rearBumperOverlayDisassemble;  // Накладка Зад.Бампера р/с
    double rearBumperOverlayPaint;        // Накладка Зад.Бампера окраска
    // Капот
    double bonnetDisassemble;             // Капот р/с
    double bonnetReplace;                 // Капот замена
    double bonnetPaint1x;                 // Капот окраска с одной стороны
    double bonnetPaint2x;                 // Капот окраска с двух сторон
    double bonnetPolish;                  // Капот полировка
    // Крышка баг.
    double trunkLidDisassemble;           // Крышка баг. р/с
    double trunkLidReplace;               // Крышка баг. замена
    double trunkLidPaint1x;               // Крышка баг. окраска с одной стороны
    double trunkLidPaint2x;               // Крышка баг. окраска с двух сторон
    double trunkLidPolish;                // Крышка баг. полировка
    // Крыша
    double roofDisassemble;               // Крыша р/с
    double roofReplace;                   // Крыша р/с для замены
    double roofReplaceKuzDet;             // Крыша замена кузовной детали
    double roofPaint1x;                   // Крыша окраска с одной стороны
    double roofPaint2x;                   // Крыша окраска с двух сторон
    double roofPolish;                    // Крыша полировка
    // Моторный отсек
    double engineCompartmentDisassemble;  // Моторный отсек р/с
    double engineCompartmentReplace;      // Моторный отсек р/с для замены
    double engineCompartmentReplaceKuzDet;// Моторный отсек замена кузовной детали
    double engineCompartmentPaint1x;      // Моторный отсек окраска с одной стороны
    double engineCompartmentPaint2x;      // Моторный отсек окраска с двух сторон
    double engineCompartmentPolish;       // Моторный отсек полировка
    // Задняя панель
    double backPanelDisassemble;          // Задняя панель р/с
    double backPanelReplace;              // Задняя панель р/с для замены
    double backPanelReplaceKuzDet;        // Задняя панель замена кузовной детали
    double backPanelPaint1x;              // Задняя панель окраска с одной стороны
    double backPanelPaint2x;              // Задняя панель окраска с двух сторон

    double WindshieldReplace;             // Лобовое стекло замена

    double RearWindowReplace;             // Заднее стекло замена

    // Зеркало
    double zerkaloDisassemble;   // Зеркало р/с
    double zerkaloPaint;        // Зеркало окраска
    double zerkaloPolish;       // Зеркало полировка
    // Ручка
    double ruchkaDisassemble;    // Ручка р/с
    double ruchkaPaint;        // Ручка окраска
    // Молдинг
    double moldingDisassemble;   // Молдинг р/с
    double moldingPaint;       // Молдинг окраска

    double opticsPolish;       // Оптика полировка

    public void writeToFileNarmotive(
            double FrontWingDisassemble,
            double FrontWingReplace,
            double FrontWingPaint1x,
            double FrontWingPaint2x,
            double FrontWingPolish,
            double FrontWingExpanderDisassemble,
            double FrontWingExpanderPaint,
            double FrontDoorDisassemble,
            double FrontDoorReplace,
            double FrontDoorPaint1x,
            double FrontDoorPaint2x,
            double FrontDoorPolish,
            double BackDoorDisassemble,
            double BackDoorReplace,
            double BackDoorPaint1x,
            double BackDoorPaint2x,
            double BackDoorPolish,
            double BackWingDisassemble,
            double BackWingReplace,
            double BackWingReplaceKuzDet,
            double BackWingPaint1x,
            double BackWingPaint2x,
            double BackWingPolish,
            double BackWingExpanderDisassemble,
            double BackWingExpanderPaint,
            double BalkDisassemble,
            double BalkReplace,
            double BalkReplaceKuzDet,
            double BalkPaint1x,
            double BalkPaint2x,
            double BalkPolish,
            double DoorStepDisassemble,
            double DoorStepReplace,
            double DoorStepReplaceKuzDet,
            double DoorStepPaint1x,
            double DoorStepPaint2x,
            double DoorStepOverlayDisassemble,
            double DoorStepOverlayPaint,
            double FrontDoorwayDisassemble,
            double FrontDoorwayPaint1x,
            double FrontDoorwayPaint2x,
            double FrontDoorwayPolish,
            double BackDoorwayDisassemble,
            double BackDoorwayPaint1x,
            double BackDoorwayPaint2x,
            double BackDoorwayPolish,
            double frontBumperDisassemble,
            double frontBumperReplace,
            double frontBumperPaint1x,
            double frontBumperPaint2x,
            double frontBumperPolish,
            double frontBumperOverlayDisassemble,
            double frontBumperOverlayPaint,
            double rearBumperDisassemble,
            double rearBumperReplace,
            double rearBumperPaint1x,
            double rearBumperPaint2x,
            double rearBumperPolish,
            double rearBumperOverlayDisassemble,
            double rearBumperOverlayPaint,
            double bonnetDisassemble,
            double bonnetReplace,
            double bonnetPaint1x,
            double bonnetPaint2x,
            double bonnetPolish,
            double trunkLidDisassemble,
            double trunkLidReplace,
            double trunkLidPaint1x,
            double trunkLidPaint2x,
            double trunkLidPolish,
            double roofDisassemble,
            double roofReplace,
            double roofReplaceKuzDet,
            double roofPaint1x,
            double roofPaint2x,
            double roofPolish,
            double engineCompartmentDisassemble,
            double engineCompartmentReplace,
            double engineCompartmentReplaceKuzDet,
            double engineCompartmentPaint1x,
            double engineCompartmentPaint2x,
            double engineCompartmentPolish,
            double backPanelDisassemble,
            double backPanelReplace,
            double backPanelReplaceKuzDet,
            double backPanelPaint1x,
            double backPanelPaint2x,
            double WindshieldReplace,
            double RearWindowReplace,
            double zerkaloDisassemble,
            double zerkaloPaint,
            double zerkaloPolish,
            double ruchkaDisassemble,
            double ruchkaPaint,
            double moldingDisassemble,
            double moldingPaint,
            double opticsPolish
    ) throws IOException {
        // Инициализация полей класса
        this.FrontWingDisassemble = FrontWingDisassemble;
        this.FrontWingReplace = FrontWingReplace;
        this.FrontWingPaint1x = FrontWingPaint1x;
        this.FrontWingPaint2x = FrontWingPaint2x;
        this.FrontWingPolish = FrontWingPolish;
        this.FrontWingExpanderDisassemble = FrontWingExpanderDisassemble;
        this.FrontWingExpanderPaint = FrontWingExpanderPaint;
        this.FrontDoorDisassemble = FrontDoorDisassemble;
        this.FrontDoorReplace = FrontDoorReplace;
        this.FrontDoorPaint1x = FrontDoorPaint1x;
        this.FrontDoorPaint2x = FrontDoorPaint2x;
        this.FrontDoorPolish = FrontDoorPolish;
        this.BackDoorDisassemble = BackDoorDisassemble;
        this.BackDoorReplace = BackDoorReplace;
        this.BackDoorPaint1x = BackDoorPaint1x;
        this.BackDoorPaint2x = BackDoorPaint2x;
        this.BackDoorPolish = BackDoorPolish;
        this.BackWingDisassemble = BackWingDisassemble;
        this.BackWingReplace = BackWingReplace;
        this.BackWingReplaceKuzDet = BackWingReplaceKuzDet;
        this.BackWingPaint1x = BackWingPaint1x;
        this.BackWingPaint2x = BackWingPaint2x;
        this.BackWingPolish = BackWingPolish;
        this.BackWingExpanderDisassemble = BackWingExpanderDisassemble;
        this.BackWingExpanderPaint = BackWingExpanderPaint;
        this.BalkDisassemble = BalkDisassemble;
        this.BalkReplace = BalkReplace;
        this.BalkReplaceKuzDet = BalkReplaceKuzDet;
        this.BalkPaint1x = BalkPaint1x;
        this.BalkPaint2x = BalkPaint2x;
        this.BalkPolish = BalkPolish;
        this.DoorStepDisassemble = DoorStepDisassemble;
        this.DoorStepReplace = DoorStepReplace;
        this.DoorStepReplaceKuzDet = DoorStepReplaceKuzDet;
        this.DoorStepPaint1x = DoorStepPaint1x;
        this.DoorStepPaint2x = DoorStepPaint2x;
        this.DoorStepOverlayDisassemble = DoorStepOverlayDisassemble;
        this.DoorStepOverlayPaint = DoorStepOverlayPaint;
        this.FrontDoorwayDisassemble = FrontDoorwayDisassemble;
        this.FrontDoorwayPaint1x = FrontDoorwayPaint1x;
        this.FrontDoorwayPaint2x = FrontDoorwayPaint2x;
        this.FrontDoorwayPolish = FrontDoorwayPolish;
        this.BackDoorwayDisassemble = BackDoorwayDisassemble;
        this.BackDoorwayPaint1x = BackDoorwayPaint1x;
        this.BackDoorwayPaint2x = BackDoorwayPaint2x;
        this.BackDoorwayPolish = BackDoorwayPolish;
        this.frontBumperDisassemble = frontBumperDisassemble;
        this.frontBumperReplace = frontBumperReplace;
        this.frontBumperPaint1x = frontBumperPaint1x;
        this.frontBumperPaint2x = frontBumperPaint2x;
        this.frontBumperPolish = frontBumperPolish;
        this.frontBumperOverlayDisassemble = frontBumperOverlayDisassemble;
        this.frontBumperOverlayPaint = frontBumperOverlayPaint;
        this.rearBumperDisassemble = rearBumperDisassemble;
        this.rearBumperReplace = rearBumperReplace;
        this.rearBumperPaint1x = rearBumperPaint1x;
        this.rearBumperPaint2x = rearBumperPaint2x;
        this.rearBumperPolish = rearBumperPolish;
        this.rearBumperOverlayDisassemble = rearBumperOverlayDisassemble;
        this.rearBumperOverlayPaint = rearBumperOverlayPaint;
        this.bonnetDisassemble = bonnetDisassemble;
        this.bonnetReplace = bonnetReplace;
        this.bonnetPaint1x = bonnetPaint1x;
        this.bonnetPaint2x = bonnetPaint2x;
        this.bonnetPolish = bonnetPolish;
        this.trunkLidDisassemble = trunkLidDisassemble;
        this.trunkLidReplace = trunkLidReplace;
        this.trunkLidPaint1x = trunkLidPaint1x;
        this.trunkLidPaint2x = trunkLidPaint2x;
        this.trunkLidPolish = trunkLidPolish;
        this.roofDisassemble = roofDisassemble;
        this.roofReplace = roofReplace;
        this.roofReplaceKuzDet = roofReplaceKuzDet;
        this.roofPaint1x = roofPaint1x;
        this.roofPaint2x = roofPaint2x;
        this.roofPolish = roofPolish;
        this.engineCompartmentDisassemble = engineCompartmentDisassemble;
        this.engineCompartmentReplace = engineCompartmentReplace;
        this.engineCompartmentReplaceKuzDet = engineCompartmentReplaceKuzDet;
        this.engineCompartmentPaint1x = engineCompartmentPaint1x;
        this.engineCompartmentPaint2x = engineCompartmentPaint2x;
        this.engineCompartmentPolish = engineCompartmentPolish;
        this.backPanelDisassemble = backPanelDisassemble;
        this.backPanelReplace = backPanelReplace;
        this.backPanelReplaceKuzDet = backPanelReplaceKuzDet;
        this.backPanelPaint1x = backPanelPaint1x;
        this.backPanelPaint2x = backPanelPaint2x;
        this.WindshieldReplace = WindshieldReplace;
        this.RearWindowReplace = RearWindowReplace;
        this.zerkaloDisassemble = zerkaloDisassemble;
        this.zerkaloPaint = zerkaloPaint;
        this.zerkaloPolish = zerkaloPolish;
        this.ruchkaDisassemble = ruchkaDisassemble;
        this.ruchkaPaint = ruchkaPaint;
        this.moldingDisassemble = moldingDisassemble;
        this.moldingPaint = moldingPaint;
        this.opticsPolish = opticsPolish;
        // Проверяем существование директории и создаем ее при необходимости
        File directory = new File(d.SYTEM_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs(); // Создаем папку если не существует
        }
        // Создаем файл "Нормативы.txt" внутри указанной директории
        File file = new File(directory, "Нормативы.txt");
        if (!file.exists()) {
            file.createNewFile(); // Создаем файл если не существует
        }

        try (FileWriter writer = new FileWriter(file)) {
            // Запись всех полей класса в файл

            writer.write(FrontWingDisassemble + "\n");
            writer.write(FrontWingReplace + "\n");
            writer.write(FrontWingPaint1x + "\n");
            writer.write(FrontWingPaint2x + "\n");
            writer.write(FrontWingPolish + "\n");
            writer.write(FrontWingExpanderDisassemble + "\n");
            writer.write(FrontWingExpanderPaint + "\n");

            writer.write(FrontDoorDisassemble + "\n");
            writer.write(FrontDoorReplace + "\n");
            writer.write(FrontDoorPaint1x + "\n");
            writer.write(FrontDoorPaint2x + "\n");
            writer.write(FrontDoorPolish + "\n");

            writer.write(BackDoorDisassemble + "\n");
            writer.write(BackDoorReplace + "\n");
            writer.write(BackDoorPaint1x + "\n");
            writer.write(BackDoorPaint2x + "\n");
            writer.write(BackDoorPolish + "\n");

            writer.write(BackWingDisassemble + "\n");
            writer.write(BackWingReplace + "\n");
            writer.write(BackWingReplaceKuzDet + "\n");
            writer.write(BackWingPaint1x + "\n");
            writer.write(BackWingPaint2x + "\n");
            writer.write(BackWingPolish + "\n");
            writer.write(BackWingExpanderDisassemble + "\n");
            writer.write(BackWingExpanderPaint + "\n");

            writer.write(BalkDisassemble + "\n");
            writer.write(BalkReplace + "\n");
            writer.write(BalkReplaceKuzDet + "\n");
            writer.write(BalkPaint1x + "\n");
            writer.write(BalkPaint2x + "\n");
            writer.write(BalkPolish + "\n");

            writer.write(DoorStepDisassemble + "\n");
            writer.write(DoorStepReplace + "\n");
            writer.write(DoorStepReplaceKuzDet + "\n");
            writer.write(DoorStepPaint1x + "\n");
            writer.write(DoorStepPaint2x + "\n");
            writer.write(DoorStepOverlayDisassemble + "\n");
            writer.write(DoorStepOverlayPaint + "\n");

            writer.write(FrontDoorwayDisassemble + "\n");
            writer.write(FrontDoorwayPaint1x + "\n");
            writer.write(FrontDoorwayPaint2x + "\n");
            writer.write(FrontDoorwayPolish + "\n");

            writer.write(BackDoorwayDisassemble + "\n");
            writer.write(BackDoorwayPaint1x + "\n");
            writer.write(BackDoorwayPaint2x + "\n");
            writer.write(BackDoorwayPolish + "\n");

            writer.write(frontBumperDisassemble + "\n");
            writer.write(frontBumperReplace + "\n");
            writer.write(frontBumperPaint1x + "\n");
            writer.write(frontBumperPaint2x + "\n");
            writer.write(frontBumperPolish + "\n");
            writer.write(frontBumperOverlayDisassemble + "\n");
            writer.write(frontBumperOverlayPaint + "\n");

            writer.write(rearBumperDisassemble + "\n");
            writer.write(rearBumperReplace + "\n");
            writer.write(rearBumperPaint1x + "\n");
            writer.write(rearBumperPaint2x + "\n");
            writer.write(rearBumperPolish + "\n");
            writer.write(rearBumperOverlayDisassemble + "\n");
            writer.write(rearBumperOverlayPaint + "\n");

            writer.write(bonnetDisassemble + "\n");
            writer.write(bonnetReplace + "\n");
            writer.write(bonnetPaint1x + "\n");
            writer.write(bonnetPaint2x + "\n");
            writer.write(bonnetPolish + "\n");

            writer.write(trunkLidDisassemble + "\n");
            writer.write(trunkLidReplace + "\n");
            writer.write(trunkLidPaint1x + "\n");
            writer.write(trunkLidPaint2x + "\n");
            writer.write(trunkLidPolish + "\n");

            writer.write(roofDisassemble + "\n");
            writer.write(roofReplace + "\n");
            writer.write(roofReplaceKuzDet + "\n");
            writer.write(roofPaint1x + "\n");
            writer.write(roofPaint2x + "\n");
            writer.write(roofPolish + "\n");

            writer.write(engineCompartmentDisassemble + "\n");
            writer.write(engineCompartmentReplace + "\n");
            writer.write(engineCompartmentReplaceKuzDet + "\n");
            writer.write(engineCompartmentPaint1x + "\n");
            writer.write(engineCompartmentPaint2x + "\n");
            writer.write(engineCompartmentPolish + "\n");

            writer.write(backPanelDisassemble + "\n");
            writer.write(backPanelReplace + "\n");
            writer.write(backPanelReplaceKuzDet + "\n");
            writer.write(backPanelPaint1x + "\n");
            writer.write(backPanelPaint2x + "\n");

            writer.write(WindshieldReplace + "\n");
            writer.write(RearWindowReplace + "\n");

            writer.write(zerkaloDisassemble + "\n");
            writer.write(zerkaloPaint + "\n");
            writer.write(zerkaloPolish + "\n");

            writer.write(ruchkaDisassemble + "\n");
            writer.write(ruchkaPaint + "\n");

            writer.write(moldingDisassemble + "\n");
            writer.write(moldingPaint + "\n");

            writer.write(opticsPolish + "\n");
        }
    }

    public void readNormativesFromFile(String directoryPath) {
        try {
            File directory = new File(directoryPath);
            File file = new File(directory, "Нормативы.txt");

            if (!file.exists()) {
                writeToFileNarmotive(0,0,0,0,0,0,
                        0,0,0,0,0,0,0,
                        0,0,0,0,0,0,0,
                        0,0,0,0,0,0,0,
                        0,0,0,0,0,0,0,0,
                        0,0,0,0,0,0,
                        0,0,0,0,0,0,
                        0,0,0,0,0,0,
                        0,0,0,0,0,0,
                        0,0,0,0,0,0,0,0,
                        0,0,0,0,0,0,0,0,
                        0,0,0,0,0,0,
                        0,0,0,0,0,0,0,
                        0,0,0,0,0,0,0,0,0);
            }

            List<String> lines = Files.readAllLines(file.toPath());

            int index = 0;
            this.FrontWingDisassemble = Double.parseDouble(lines.get(index++));
            this.FrontWingReplace = Double.parseDouble(lines.get(index++));
            this.FrontWingPaint1x = Double.parseDouble(lines.get(index++));
            this.FrontWingPaint2x = Double.parseDouble(lines.get(index++));
            this.FrontWingPolish = Double.parseDouble(lines.get(index++));
            this.FrontWingExpanderDisassemble = Double.parseDouble(lines.get(index++));
            this.FrontWingExpanderPaint = Double.parseDouble(lines.get(index++));
            this.FrontDoorDisassemble = Double.parseDouble(lines.get(index++));
            this.FrontDoorReplace = Double.parseDouble(lines.get(index++));
            this.FrontDoorPaint1x = Double.parseDouble(lines.get(index++));
            this.FrontDoorPaint2x = Double.parseDouble(lines.get(index++));
            this.FrontDoorPolish = Double.parseDouble(lines.get(index++));
            this.BackDoorDisassemble = Double.parseDouble(lines.get(index++));
            this.BackDoorReplace = Double.parseDouble(lines.get(index++));
            this.BackDoorPaint1x = Double.parseDouble(lines.get(index++));
            this.BackDoorPaint2x = Double.parseDouble(lines.get(index++));
            this.BackDoorPolish = Double.parseDouble(lines.get(index++));
            this.BackWingDisassemble = Double.parseDouble(lines.get(index++));
            this.BackWingReplace = Double.parseDouble(lines.get(index++));
            this.BackWingReplaceKuzDet = Double.parseDouble(lines.get(index++));
            this.BackWingPaint1x = Double.parseDouble(lines.get(index++));
            this.BackWingPaint2x = Double.parseDouble(lines.get(index++));
            this.BackWingPolish = Double.parseDouble(lines.get(index++));
            this.BackWingExpanderDisassemble = Double.parseDouble(lines.get(index++));
            this.BackWingExpanderPaint = Double.parseDouble(lines.get(index++));
            this.BalkDisassemble = Double.parseDouble(lines.get(index++));
            this.BalkReplace = Double.parseDouble(lines.get(index++));
            this.BalkReplaceKuzDet = Double.parseDouble(lines.get(index++));
            this.BalkPaint1x = Double.parseDouble(lines.get(index++));
            this.BalkPaint2x = Double.parseDouble(lines.get(index++));
            this.BalkPolish = Double.parseDouble(lines.get(index++));
            this.DoorStepDisassemble = Double.parseDouble(lines.get(index++));
            this.DoorStepReplace = Double.parseDouble(lines.get(index++));
            this.DoorStepReplaceKuzDet = Double.parseDouble(lines.get(index++));
            this.DoorStepPaint1x = Double.parseDouble(lines.get(index++));
            this.DoorStepPaint2x = Double.parseDouble(lines.get(index++));
            this.DoorStepOverlayDisassemble = Double.parseDouble(lines.get(index++));
            this.DoorStepOverlayPaint = Double.parseDouble(lines.get(index++));
            this.FrontDoorwayDisassemble = Double.parseDouble(lines.get(index++));
            this.FrontDoorwayPaint1x = Double.parseDouble(lines.get(index++));
            this.FrontDoorwayPaint2x = Double.parseDouble(lines.get(index++));
            this.FrontDoorwayPolish = Double.parseDouble(lines.get(index++));
            this.BackDoorwayDisassemble = Double.parseDouble(lines.get(index++));
            this.BackDoorwayPaint1x = Double.parseDouble(lines.get(index++));
            this.BackDoorwayPaint2x = Double.parseDouble(lines.get(index++));
            this.BackDoorwayPolish = Double.parseDouble(lines.get(index++));
            this.frontBumperDisassemble = Double.parseDouble(lines.get(index++));
            this.frontBumperReplace = Double.parseDouble(lines.get(index++));
            this.frontBumperPaint1x = Double.parseDouble(lines.get(index++));
            this.frontBumperPaint2x = Double.parseDouble(lines.get(index++));
            this.frontBumperPolish = Double.parseDouble(lines.get(index++));
            this.frontBumperOverlayDisassemble = Double.parseDouble(lines.get(index++));
            this.frontBumperOverlayPaint = Double.parseDouble(lines.get(index++));
            this.rearBumperDisassemble = Double.parseDouble(lines.get(index++));
            this.rearBumperReplace = Double.parseDouble(lines.get(index++));
            this.rearBumperPaint1x = Double.parseDouble(lines.get(index++));
            this.rearBumperPaint2x = Double.parseDouble(lines.get(index++));
            this.rearBumperPolish = Double.parseDouble(lines.get(index++));
            this.rearBumperOverlayDisassemble = Double.parseDouble(lines.get(index++));
            this.rearBumperOverlayPaint = Double.parseDouble(lines.get(index++));
            this.bonnetDisassemble = Double.parseDouble(lines.get(index++));
            this.bonnetReplace = Double.parseDouble(lines.get(index++));
            this.bonnetPaint1x = Double.parseDouble(lines.get(index++));
            this.bonnetPaint2x = Double.parseDouble(lines.get(index++));
            this.bonnetPolish = Double.parseDouble(lines.get(index++));
            this.trunkLidDisassemble = Double.parseDouble(lines.get(index++));
            this.trunkLidReplace = Double.parseDouble(lines.get(index++));
            this.trunkLidPaint1x = Double.parseDouble(lines.get(index++));
            this.trunkLidPaint2x = Double.parseDouble(lines.get(index++));
            this.trunkLidPolish = Double.parseDouble(lines.get(index++));
            this.roofDisassemble = Double.parseDouble(lines.get(index++));
            this.roofReplace = Double.parseDouble(lines.get(index++));
            this.roofReplaceKuzDet = Double.parseDouble(lines.get(index++));
            this.roofPaint1x = Double.parseDouble(lines.get(index++));
            this.roofPaint2x = Double.parseDouble(lines.get(index++));
            this.roofPolish = Double.parseDouble(lines.get(index++));
            this.engineCompartmentDisassemble = Double.parseDouble(lines.get(index++));
            this.engineCompartmentReplace = Double.parseDouble(lines.get(index++));
            this.engineCompartmentReplaceKuzDet = Double.parseDouble(lines.get(index++));
            this.engineCompartmentPaint1x = Double.parseDouble(lines.get(index++));
            this.engineCompartmentPaint2x = Double.parseDouble(lines.get(index++));
            this.engineCompartmentPolish = Double.parseDouble(lines.get(index++));
            this.backPanelDisassemble = Double.parseDouble(lines.get(index++));
            this.backPanelReplace = Double.parseDouble(lines.get(index++));
            this.backPanelReplaceKuzDet = Double.parseDouble(lines.get(index++));
            this.backPanelPaint1x = Double.parseDouble(lines.get(index++));
            this.backPanelPaint2x = Double.parseDouble(lines.get(index++));
            this.WindshieldReplace = Double.parseDouble(lines.get(index++));
            this.RearWindowReplace = Double.parseDouble(lines.get(index++));
            this.zerkaloDisassemble = Double.parseDouble(lines.get(index++));
            this.zerkaloPaint = Double.parseDouble(lines.get(index++));
            this.zerkaloPolish = Double.parseDouble(lines.get(index++));
            this.ruchkaDisassemble = Double.parseDouble(lines.get(index++));
            this.ruchkaPaint = Double.parseDouble(lines.get(index++));
            this.moldingDisassemble = Double.parseDouble(lines.get(index++));
            this.moldingPaint = Double.parseDouble(lines.get(index++));
            this.opticsPolish = Double.parseDouble(lines.get(index++));

        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
