package ru.yandex.practicum.calcRemAuto.storage;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import ru.yandex.practicum.calcRemAuto.model.Client;
import ru.yandex.practicum.calcRemAuto.model.Element;
import ru.yandex.practicum.calcRemAuto.model.Mechanics;
import ru.yandex.practicum.calcRemAuto.model.Prices;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
public class ExcelUpdater {

    Prices prices = new Prices(); // Класс с ценами
    Mechanics mechanics = new Mechanics(); // Класс с фамилиями механиков
    int sourceRowNum = 22; // Номер строки (нумерация с 0) с которой начинаются работы
    int targetRowNum = 22; // Номер строки, под которой вставить скопированную строку (нумерация с 0)
    int total = 0; // Значение для подсчета итого и дальнейшей вставки

    public void createOrderExelFile(Client client, List<Element> elements, int lkmTotalPrice, String path) {
        try {
            String originalFilePath = "Системные/Болванка.xlsx"; // путь по которому лежит болванка
            String newFilePath = path + "/ЗаказНаряд.xlsx "; // Путь по которому новый файл сохраняется. Путь калькуляции
            // Чтение из оригинального файла
            FileInputStream originalFileInputStream = new FileInputStream(originalFilePath);
            Workbook workbook = new XSSFWorkbook(originalFileInputStream);
            Sheet sheet = workbook.getSheetAt(0);
            writeMaster(sheet); // заполняем мастера
            writeDate(sheet);   // заполняем дату расчета
            writeClient(sheet, client); // заполняем данные по клиенту
            calkNeededRows(sheet, elements); // высчитываем необходимое количество строк для работ и отправляем в заполнение этих строк работами.
            writeTotal(sheet, lkmTotalPrice);  // заполняем итоговую сумму ремонта
            Row row = sheet.getRow(targetRowNum); // берем строку для лкм
            writeLkm(row, lkmTotalPrice); // Записываем стоимость Лакокрасочных материалов

            // Сохранение в новый файл
            FileOutputStream newFileOutputStream = new FileOutputStream(newFilePath);
            workbook.write(newFileOutputStream);

            // Закрываем потоки
            originalFileInputStream.close();
            newFileOutputStream.close();
            workbook.close();

            log.info("Файл успешно обновлен и сохранен по новому пути: " + newFilePath);
        } catch (IOException e) {
            log.error(e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    } // метод формирования заказ наряда через меньшие методы.

    private void writeDate(Sheet sheet) {
        int dateRow = 7;
        int dateCell = 33;
        Row row = sheet.getRow(dateRow);
        Cell cell = row.getCell(dateCell);
        cell.setCellValue((LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
    } // Метод заполнения даты от которой оформлен заказ

    private void writeClient(Sheet sheet, Client client) {
        int clientFirstRow = 10;
        int clientTwoRow = 12;
        int clientTwoColum = 32;
        int cellFirstColum = 8;

        Row row = sheet.getRow(clientFirstRow);
        Cell cell = row.getCell(cellFirstColum);
        cell.setCellValue(client.getName());
        cell = row.getCell(clientTwoColum);
        cell.setCellValue(client.getModelAuto());
        row = sheet.getRow(clientTwoRow);
        cell = row.getCell(cellFirstColum);
        cell.setCellValue(client.getFoneNumber());
        cell = row.getCell(clientTwoColum);
        cell.setCellValue(client.getNumberAuto());
    } // метод заполнения данных клиента

    private void calkNeededRows(Sheet sheet, List<Element> elements) {
        // Копируем и вставляем строку
        int numOfWork = 1;
        Row sourceRow = sheet.getRow(sourceRowNum);
        for (Element element : elements) {
            int elementNeededRow = 0;
            elementNeededRow = element.getRuchka() + element.getMolding() + element.getZerkalo();
            if (element.getPaintSide() != 0) {
                elementNeededRow++;
            }
            if (element.getArmatureSide() != 0) {
                elementNeededRow++;
            }
            if (element.getRemont() != 0) {
                elementNeededRow++;
            }
            if (element.getKuzDetReplaceSide() != 0) {
                elementNeededRow++;
            }
            if (element.getGlass() != 0) {
                elementNeededRow++;
            }
            if (element.getOverlay() != 0) {
                elementNeededRow++;
            }
            if (element.getExpander() != 0) {
                elementNeededRow++;
            }
            if (element.getDopWorksArmoturchik() != 0) {
                elementNeededRow++;
            }
            if (element.getDopWorksPainter() != 0) {
                elementNeededRow++;
            }
            if (element.getDopWorksKuzovchik() != 0) {
                elementNeededRow++;
            }
            if (element.getNotNormWork() != 0) {
                elementNeededRow++;
            }
            for (int i = 0; i < elementNeededRow; i++) {
                copyRow(sheet, sourceRow, targetRowNum, numOfWork);
                targetRowNum++;
                numOfWork++;
            }
            writeInCalculatedRowsElement(element, sheet, sourceRowNum);
            sourceRowNum += elementNeededRow;
        }
    } // метод высчитывает необходимое количество строк для работ

    private void writeInCalculatedRowsElement(Element element, Sheet sheet, int rowForePaste) {
        int nameOfWork = 8; // Ячейка с названием работ
        int narmotive = 25; // Ячейка с нормативом работ
        int price = 29;     // Ячейка с ценой норма часа
        int totalPrice = 39;// Ячейка с ценой работ
        Row row = sheet.getRow(rowForePaste);
        Cell cell = row.getCell(nameOfWork);

        // Первое прописываем арматурные работы элемента в Ячейку с названием работ. Но если работы "полировка" или "не нормативные" то их пишем сразу одной строкой!!!
        // Сначала пишем правильно имя элемента для этого
        if (element.getName().contains("Замена")) {
            // Если работы по замене кузовной детали, то к имени прибавляем р/с для замены куз. Детали
            cell.setCellValue(element.getName().replace("Замена", "р/с для замены куз.детали"));
        } else if (!element.getName().contains("Остекление") && element.getArmatureSide() != 0 && !element.getName().contains("Полировка") && element.getNotNormWork() == 0) {
            // Проверяем, что элемент не "Остекление","Полировка" и "не нормативный" так как этот элемент мы сразу пишем одной строкой!!!
            // Если работы не замена, то добавляем к имени р/с
            cell.setCellValue((element.getName() + " р/с "));
        } else if (element.getName().contains("Остекление") && !element.getName().contains("Полировка") && element.getNotNormWork() == 0) {
            // Если элемент остекление, то добавляем к имени с/у
            cell.setCellValue((element.getName() + " c/у "));
        } else if (element.getName().contains("Полировка") && element.getNotNormWork() == 0) {
            // Если элемент "Полировка" записываем в ячейку имя которое является списком имен элементов кузова под полировку
            cell.setCellValue((element.getName()));
            // Так как этот список может быть внушительный, отправляем ячейку на растяжку под это имя, что бы все влезло!
            adjustRowHeightPolirovka(row, element);
        } else if (element.getNotNormWork() != 0){
            // Если элемент "не нормативный" так же записываем в ячейку его имя
            cell.setCellValue((element.getName()));
            // И так же на всякий случай отправляем на растяжку ячейки. Имя не нормированных работ ограничивается 100 символами.
            adjustRowHeightPolirovka(row, element);
        }

        // Далее прописываем Ячейку норматива работ

        // Если элемент это остекление то
        if (element.getName().contains("Остекление")) {
            cell = row.getCell(narmotive); // Берем ячейку норматив работ
            cell.setCellValue(element.getGlass()); // Вставляем значение работ по остеклению
            cell = row.getCell(price); // Берем ячейку стоимость норма часа
            cell.setCellValue(prices.getHourlyRate()); // Вставляем значение стоимости норма часа
            cell = row.getCell(totalPrice); // Берем ячейку стоимость работы
            cell.setCellValue(element.getGlass() * prices.getHourlyRate()); // Вставляем значение стоимости данной работы (норматив * цену норма часа)
            total += (element.getGlass() * prices.getHourlyRate()); // Добавляем к итоговой стоимости работ стоимость данной работы
        } else { // Если элемент не стекло
            cell = row.getCell(narmotive); // Берем ячейку норматив работ
            if (!element.getName().contains("Полировка") && element.getNotNormWork() == 0) {
                cell.setCellValue(element.getArmatureSide()); // !!!!!!!!Вставляем значение арматурных работ если элемент не полировка и не нормативный!!!!!!!!!!!
            } else if (element.getName().contains("Полировка")){
                cell.setCellValue(element.getPaintSide()); // !!!!!!!!Если элемент полировка сразу вставляем значение малярных работ!!!!!!!!!!
            } else {
                cell.setCellValue(element.getNotNormWork()); // Если элемент не стекло и не полировка, то он не нормативные работы!!!
            }
            // Эти три строки стандартные ко всем так как стоимость норма часа к одному заказу не может изменятся.
            cell = row.getCell(price);// Берем ячейку стоимость норма часа
            cell.setCellValue(prices.getHourlyRate());// Вставляем значение стоимости норма часа
            cell = row.getCell(totalPrice);// Берем ячейку стоимость работы

            // Тут прописываем ячейку стоимости конкретной работы. Пример: р/с = 2н/ч таким образом вставляем значение (2 * на ставку)
            // Проверяем на ниши однострочные элементы. (полировка и не нормативные)
            if (!element.getName().contains("Полировка") && element.getNotNormWork() == 0) {
                cell.setCellValue(element.getArmatureSide() * prices.getHourlyRate()); // Вставляем значение стоимости данной работы (норматив * цену норма часа)
                total += (element.getArmatureSide() * prices.getHourlyRate()); // Добавляем к итоговой стоимости работ стоимость данной работы
            } else if (element.getName().contains("Полировка")){
                // Если полировка
                cell.setCellValue(element.getPaintSide() * prices.getHourlyRate()); // Вставляем значение стоимости данной работы (норматив * цену норма часа)
                total += (element.getPaintSide() * prices.getHourlyRate()); // Добавляем к итоговой стоимости работ стоимость данной работы
            } else if (element.getNotNormWork() != 0) {
                // И наконец "не нормативные работы". Больше мы с однострочными элементами не встретимся.
                cell.setCellValue(element.getNotNormWork() * prices.getHourlyRate()); // Вставляем значение стоимости данной работы (норматив * цену норма часа)
                total += (element.getNotNormWork() * prices.getHourlyRate()); // Добавляем к итоговой стоимости работ стоимость данной работы
            }
        }

        // От сих пор начинается заполнение всех вспомогательных работ.

        if (element.getKuzDetReplaceSide() != 0) { // Если работы по замене кузовной приварной детали, то прописываем работы кузовщика по замене
            row = sheet.getRow(++rowForePaste); // Берем новую строку
            cell = row.getCell(nameOfWork); // Берем ячейку имени работ
            cell.setCellValue(element.getName().replace("Замена", "") + " Замена.куз.дет."); // Вставляем имя элемента и вид работ
            cell = row.getCell(narmotive);  // Берем ячейку норматив работ
            cell.setCellValue(element.getKuzDetReplaceSide()); // Вставляем значение кузовных работ
            cell = row.getCell(price); // Берем ячейку стоимость норма часа
            cell.setCellValue(prices.getHourlyRate()); // Вставляем значение стоимости норма часа
            cell = row.getCell(totalPrice); // Берем ячейку стоимость работы
            cell.setCellValue(element.getKuzDetReplaceSide() * prices.getHourlyRate()); // Вставляем значение стоимости данной работы (норматив * цену норма часа)
            total += (element.getKuzDetReplaceSide() * prices.getHourlyRate()); // Добавляем к итоговой стоимости работ стоимость данной работы
        }
        if (element.getRemont() != 0) { // Если элемент имеет ремонт
            row = sheet.getRow(++rowForePaste); // Берем новую строку
            cell = row.getCell(nameOfWork);  // Берем ячейку имени работ
            cell.setCellValue(element.getName().replace("Замена", "") + " ремонт"); // Вставляем имя элемента и вид работ
            cell = row.getCell(narmotive);  // Берем ячейку норматив работ
            cell.setCellValue(element.getRemont()); // Вставляем значение ремонтных работ
            cell = row.getCell(price);  // Берем ячейку стоимость норма часа
            cell.setCellValue(prices.getHourlyRate()); // Вставляем значение стоимости норма часа
            cell = row.getCell(totalPrice); // Берем ячейку стоимость работы
            cell.setCellValue(element.getRemont() * prices.getHourlyRate()); // Вставляем значение стоимости данной работы (норматив * цену норма часа)
            total += (element.getRemont() * prices.getHourlyRate()); // Добавляем к итоговой стоимости работ стоимость данной работы
        }
        if (element.getDopWorksArmoturchik() != 0) { // Если на элементе есть арматурные Доп.работы прописываем их
            // Если в элементе отсутствуют работы для арматурщика, то не берем новую строку
            if (element.getArmatureSide() != 0) {
                // Если есть работы для арматурщика, то
                row = sheet.getRow(++rowForePaste); // Берем новую строку
            }
            cell = row.getCell(nameOfWork);  // Берем ячейку имени работ
            // Если элемент не имеет описания доп.работ
            if (element.getDescriptionDopWorksArmaturchic().equals("null")) {
                // то вставляем готовую заглушку
                cell.setCellValue(element.getName().replace("Замена", "") + " арматурные Доп.работы"); // Вставляем имя элемента и вид работ
            } else {
                // В противном случае вставляем описания доп.работ
                cell.setCellValue(element.getName().replace("Замена", "") + " " + element.getDescriptionDopWorksArmaturchic()); // Вставляем имя элемента и вид работ
                adjustRowHeightForLongWords(row, element.getDescriptionDopWorksArmaturchic());
            }
            cell = row.getCell(narmotive);  // Берем ячейку норматив работ
            cell.setCellValue(element.getDopWorksArmoturchik()); // Вставляем значение Доп.работ
            cell = row.getCell(price);  // Берем ячейку стоимость норма часа
            cell.setCellValue(prices.getHourlyRate()); // Вставляем значение стоимости норма часа
            cell = row.getCell(totalPrice); // Берем ячейку стоимость работы
            cell.setCellValue(element.getDopWorksArmoturchik() * prices.getHourlyRate()); // Вставляем значение стоимости данной работы (норматив * цену норма часа)
            total += (element.getDopWorksArmoturchik() * prices.getHourlyRate()); // Добавляем к итоговой стоимости работ стоимость данной работы
        }
        if (element.getDopWorksPainter() != 0) { // Если на элементе есть малярные Доп.работы прописываем их
            // Если в элементе отсутствуют работы для арматурщика или отсутствуют доп.работы для арматурщика, то не берем новую строку
            if (element.getArmatureSide() != 0 || element.getDopWorksArmoturchik() != 0) {
                // Если есть то
                row = sheet.getRow(++rowForePaste); // Берем новую строку
            }
            cell = row.getCell(nameOfWork);  // Берем ячейку имени работ
            // Если элемент не имеет описания доп.работ
            if (element.getDescriptionDopWorksPainter().equals("null")) {
                // то вставляем готовую заглушку
                cell.setCellValue(element.getName().replace("Замена", "") + " покрасочные Доп.работы"); // Вставляем имя элемента и вид работ
            } else {
                // В противном случае вставляем описания доп.работ
                cell.setCellValue(element.getName().replace("Замена", "") + " " + element.getDescriptionDopWorksPainter()); // Вставляем имя элемента и вид работ
                adjustRowHeightForLongWords(row, element.getDescriptionDopWorksPainter());
            }
            cell = row.getCell(narmotive);  // Берем ячейку норматив работ
            cell.setCellValue(element.getDopWorksPainter()); // Вставляем значение Доп.работ
            cell = row.getCell(price);  // Берем ячейку стоимость норма часа
            cell.setCellValue(prices.getHourlyRate()); // Вставляем значение стоимости норма часа
            cell = row.getCell(totalPrice); // Берем ячейку стоимость работы
            cell.setCellValue(element.getDopWorksPainter() * prices.getHourlyRate()); // Вставляем значение стоимости данной работы (норматив * цену норма часа)
            total += (element.getDopWorksPainter() * prices.getHourlyRate()); // Добавляем к итоговой стоимости работ стоимость данной работы
        }
        if (element.getDopWorksKuzovchik() != 0) { // Если на элементе есть кузовные Доп.работы прописываем их
            // Если в элементе отсутствуют работы для арматурщика или отсутствуют доп.работы для арматурщика или отсутствуют доп.работы для маляра, то не берем новую строку
            if (element.getArmatureSide() != 0 || element.getDopWorksArmoturchik() != 0 || element.getDopWorksPainter() != 0) {
                // Если есть то
                row = sheet.getRow(++rowForePaste); // Берем новую строку
            }
            cell = row.getCell(nameOfWork);  // Берем ячейку имени работ
            // Если элемент не имеет описания доп.работ
            if (element.getDescriptionDopWorksKuzovchik().equals("null")) {
                // то вставляем готовую заглушку
                cell.setCellValue(element.getName().replace("Замена", "") + " кузовные Доп.работы"); // Вставляем имя элемента и вид работ
            } else {
                // В противном случае вставляем описания доп.работ
                cell.setCellValue(element.getName().replace("Замена", "") + " " + element.getDescriptionDopWorksKuzovchik()); // Вставляем имя элемента и вид работ
                adjustRowHeightForLongWords(row, element.getDescriptionDopWorksKuzovchik());
            }
            cell = row.getCell(narmotive);  // Берем ячейку норматив работ
            cell.setCellValue(element.getDopWorksKuzovchik()); // Вставляем значение Доп.работ
            cell = row.getCell(price);  // Берем ячейку стоимость норма часа
            cell.setCellValue(prices.getHourlyRate()); // Вставляем значение стоимости норма часа
            cell = row.getCell(totalPrice); // Берем ячейку стоимость работы
            cell.setCellValue(element.getDopWorksKuzovchik() * prices.getHourlyRate()); // Вставляем значение стоимости данной работы (норматив * цену норма часа)
            total += (element.getDopWorksKuzovchik() * prices.getHourlyRate()); // Добавляем к итоговой стоимости работ стоимость данной работы
        }
        if (element.getPaintSide() != 0 && !element.getName().contains("Полировка")) { // Далее прописываем работы по окраске
            row = sheet.getRow(++rowForePaste); // Берем новую строку
            cell = row.getCell(nameOfWork); // Берем ячейку имени работ
            cell.setCellValue(element.getName().replace("Замена", "") + " окраска"); // Вставляем имя элемента и вид работ
            cell = row.getCell(narmotive); // Берем ячейку норматив работ
            cell.setCellValue(element.getPaintSide()); // Вставляем значение окраски
            cell = row.getCell(price); // Берем ячейку стоимость норма часа
            cell.setCellValue(prices.getHourlyRate()); // Вставляем значение стоимости норма часа
            cell = row.getCell(totalPrice); // Берем ячейку стоимость работы
            cell.setCellValue(element.getPaintSide() * prices.getHourlyRate()); // Вставляем значение стоимости данной работы (норматив * цену норма часа)
            total += (element.getPaintSide() * prices.getHourlyRate()); // Добавляем к итоговой стоимости работ стоимость данной работы
        }
        // Далее прописываем Все работы по навесным деталям на элементе
        if (element.getRuchka() != 0) {
            row = sheet.getRow(++rowForePaste);
            cell = row.getCell(nameOfWork);
            cell.setCellValue(element.getName().replace("Замена", "") + " ручка окраска");
            cell = row.getCell(narmotive);
            cell.setCellValue(element.getRuchka());
            cell = row.getCell(price);
            cell.setCellValue(prices.getHourlyRate());
            cell = row.getCell(totalPrice);
            total += (element.getRuchka() * prices.getHourlyRate());
            cell.setCellValue(element.getRuchka() * prices.getHourlyRate());
        }
        if (element.getMolding() != 0) {
            row = sheet.getRow(++rowForePaste);
            cell = row.getCell(nameOfWork);
            cell.setCellValue(element.getName().replace("Замена", "") + " молдинг окраска");
            cell = row.getCell(narmotive);
            cell.setCellValue(element.getMolding());
            cell = row.getCell(price);
            cell.setCellValue(prices.getHourlyRate());
            cell = row.getCell(totalPrice);
            total += (element.getMolding() * prices.getHourlyRate());
            cell.setCellValue(element.getMolding() * prices.getHourlyRate());
        }
        if (element.getZerkalo() != 0) {
            row = sheet.getRow(++rowForePaste);
            cell = row.getCell(nameOfWork);
            cell.setCellValue(element.getName().replace("Замена", "") + " зеркало окраска");
            cell = row.getCell(narmotive);
            cell.setCellValue(element.getZerkalo());
            cell = row.getCell(price);
            cell.setCellValue(prices.getHourlyRate());
            cell = row.getCell(totalPrice);
            total += (element.getZerkalo() * prices.getHourlyRate());
            cell.setCellValue(element.getZerkalo() * prices.getHourlyRate());
        }
        if (element.getOverlay() != 0) {
            row = sheet.getRow(++rowForePaste);
            cell = row.getCell(nameOfWork);
            cell.setCellValue(element.getName().replace("Замена", "") + " накладка окраска");
            cell = row.getCell(narmotive);
            cell.setCellValue(element.getOverlay());
            cell = row.getCell(price);
            cell.setCellValue(prices.getHourlyRate());
            cell = row.getCell(totalPrice);
            total += (element.getOverlay() * prices.getHourlyRate());
            cell.setCellValue(element.getOverlay() * prices.getHourlyRate());
        }
        if (element.getExpander() != 0) {
            row = sheet.getRow(++rowForePaste);
            cell = row.getCell(nameOfWork);
            cell.setCellValue(element.getName().replace("Замена", "") + " расширитель окраска");
            cell = row.getCell(narmotive);
            cell.setCellValue(element.getExpander());
            cell = row.getCell(price);
            cell.setCellValue(prices.getHourlyRate());
            cell = row.getCell(totalPrice);
            total += (element.getExpander() * prices.getHourlyRate());
            cell.setCellValue(element.getExpander() * prices.getHourlyRate());
        }
        // Здесь прописываем работы по стеклам если они добавлены к работам по элементу
        if (!element.getNameGlass().contains("null")) {
            row = sheet.getRow(++rowForePaste);
            cell = row.getCell(nameOfWork);
            cell.setCellValue(element.getNameGlass());
            cell = row.getCell(narmotive);
            cell.setCellValue(element.getGlass());
            cell = row.getCell(price);
            cell.setCellValue(prices.getHourlyRate());
            cell = row.getCell(totalPrice);
            total += (element.getGlass() * prices.getHourlyRate());
            cell.setCellValue(element.getGlass() * prices.getHourlyRate());
        }
        // Здесь прописываем не нормативные работы
        /*if(element.getNotNormWork() != 0){
            row = sheet.getRow(++rowForePaste);
            cell = row.getCell(nameOfWork);
            cell.setCellValue(element.getName());
            cell = row.getCell(narmotive);
            cell.setCellValue(element.getNotNormWork());
            cell = row.getCell(price);
            cell.setCellValue(prices.getHourlyRate());
            cell = row.getCell(totalPrice);
            total += (element.getNotNormWork() * prices.getHourlyRate());
            cell.setCellValue(element.getNotNormWork() * prices.getHourlyRate());
        }*/
    } // заполняет строки работами

    private void writeLkm(Row row, int lkmTotalPrice) {
        int numWork = 1;
        int nameOfWork = 8;
        int narmotive = 25;
        int totalPrice = 39;
        Cell cell = row.getCell(numWork);
        cell.setCellValue("-");
        cell = row.getCell(nameOfWork);
        cell.setCellValue("Лакокрасочные материалы");
        cell = row.getCell(narmotive);
        cell.setCellValue("1");
        cell = row.getCell(totalPrice);
        cell.setCellValue(lkmTotalPrice);
    } // Заполняем строку ЛКМ

    private void writeTotal(Sheet sheet, int lkmTotalPrice) {
        int totalPrice = 40; // Номер ячейки под работами итого
        int totalTotalPrice = 32; // Номер ячейки итого итоговое
        Row row = sheet.getRow(targetRowNum + 1);
        Cell cell = row.getCell(totalPrice);
        cell.setCellValue(total + lkmTotalPrice);
        row = sheet.getRow(targetRowNum + 4);
        cell = row.getCell(totalTotalPrice);
        cell.setCellValue(total + lkmTotalPrice);
    } // заполняет итоговою сумму ремонта

    private void writeMaster(Sheet sheet) {
        int row1 = 15;
        int cel1 = 32;
        int row2 = 42;
        int cel2 = 22;
        Row row = sheet.getRow(row1);
        Cell cell = row.getCell(cel1);
        cell.setCellValue(mechanics.getMaster());
        row = sheet.getRow(row2);
        cell = row.getCell(cel2);
        cell.setCellValue(mechanics.getMaster());
    } // заполняет параметры мастера

    private void copyRow(Sheet sheet, Row sourceRow, int targetRowNum, int numOfWork) {
        // Задает высоту строки по умолчанию в пунктах
        float defaultRowHeightInPoints = 20.0f;
        // Устанавливает высоту исходной строки
        sourceRow.setHeightInPoints(defaultRowHeightInPoints);
        // Создает или получает строку назначения
        Row newRow = sheet.getRow(targetRowNum);
        if (newRow != null) {
            // Если строка уже существует, сдвигает строки вниз, чтобы освободить место для новой строки
            sheet.shiftRows(targetRowNum, sheet.getLastRowNum(), 1);
            newRow = sheet.createRow(targetRowNum);
        } else {
            // Если строки не существует, создает новую строку
            newRow = sheet.createRow(targetRowNum);
        }
        // Копирует ячейки из исходной строки в строку назначения
        for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
            Cell oldCell = sourceRow.getCell(i);
            Cell newCell = newRow.createCell(i);
            // Если исходная ячейка пуста, пропускает её
            if (oldCell == null) {
                newCell = null;
                continue;
            }
            // Копирует стиль и содержимое ячейки
            newCell.setCellStyle(oldCell.getCellStyle());
            newCell.setCellType(oldCell.getCellType());
            // Копирует значение ячейки в зависимости от типа данных
            switch (oldCell.getCellType()) {
                case STRING:
                    newCell.setCellValue(oldCell.getStringCellValue());
                    break;
                case NUMERIC:
                    newCell.setCellValue(oldCell.getNumericCellValue());
                    break;
                case BOOLEAN:
                    newCell.setCellValue(oldCell.getBooleanCellValue());
                    break;
                case FORMULA:
                    newCell.setCellFormula(oldCell.getCellFormula());
                    break;
                case BLANK:
                    newCell.setCellType(CellType.BLANK);
                    break;
                case ERROR:
                    newCell.setCellErrorValue(oldCell.getErrorCellValue());
                    break;
            }
        }
        // Копирует объединенные ячейки
        for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
            CellRangeAddress cellRangeAddress = sheet.getMergedRegion(i);
            if (cellRangeAddress.getFirstRow() == sourceRow.getRowNum()) {
                // Создает новую область объединения ячеек для строки назначения
                CellRangeAddress newCellRangeAddress = new CellRangeAddress(newRow.getRowNum(),
                        (newRow.getRowNum() +
                                (cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow())),
                        cellRangeAddress.getFirstColumn(),
                        cellRangeAddress.getLastColumn());
                sheet.addMergedRegion(newCellRangeAddress);
            }
        }
        // Если передано значение numOfWork, устанавливает его в соответствующей ячейке
        if (numOfWork != 0) {
            Cell numWorkCell = newRow.getCell(1);
            numWorkCell.setCellValue(numOfWork);
        }
        //*******************************************
        // Устанавливает высоту строки назначения
        newRow.setHeightInPoints(defaultRowHeightInPoints);
    } // метод копирования строк со всеми параметрами

    private void adjustRowHeightPolirovka(Row row, Element element) {
        String[] points = element.getName().split("'");
        int i = (points.length / 3) * 30; // Сколько нужно
        float b = row.getHeightInPoints(); // Сколько стандарт
        if (i > b) { // Если нужно больше чем стандарт
            row.setHeightInPoints(i); // То выставляем размер ячейки
        }// Если нет, то оставляем стандартный размер ячейки
    } // метод выставления высоты строки для длинных имен элемента полировки.

    private static void adjustRowHeightForLongWords(Row row, String text) {
        String[] points = text.split("\n");
        double i = points.length + text.length();
        if (i < 50) {
            i = i * 1.2;
        } else if (i > 50 && i < 75) {
            i = 50;
        } else {
            i = 65;
        }
        row.setHeightInPoints((int) i);
    } // метод выставления высоты строки для длинных описаний доп.работ
}