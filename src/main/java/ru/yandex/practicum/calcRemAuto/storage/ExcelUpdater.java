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

    public void createOrderExelFile(Client client, List<Element> elements, String path) {
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
            writeTotal(sheet);  // заполняем итоговую сумму ремонта
            Row row = sheet.getRow(targetRowNum); // берем строку для удаления
            sheet.removeRow(row); // удаляем строку (мне так нравится)

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
            for (int i = 0; i < elementNeededRow; i++) {
                copyRow(sheet, sourceRow, targetRowNum, numOfWork);
                targetRowNum++;
                numOfWork++;
            }
            writeInCalculatedRowsElement(element, sheet, sourceRowNum);
            sourceRowNum += elementNeededRow;
        }
    } // метод высчитывает необходимые строки для работ

    private void writeInCalculatedRowsElement(Element element, Sheet sheet, int rowForePaste) {
        int nameOfWork = 8;
        int narmotive = 25;
        int price = 29;
        int totalPrice = 39;
        Row row = sheet.getRow(rowForePaste);
        Cell cell = row.getCell(nameOfWork);
        if (element.getName().contains("Замена")) {
            cell.setCellValue(element.getName());
        } else if (!element.getName().contains("Остекление")) {
            cell.setCellValue((element.getName() + " р/с "));
        } else {
            cell.setCellValue((element.getName() + " c/у "));
        }
        if (element.getName().contains("Остекление")) {
            cell = row.getCell(narmotive);
            cell.setCellValue(element.getGlass());
            cell = row.getCell(price);
            cell.setCellValue(prices.getHourlyRate());
            cell = row.getCell(totalPrice);
            total += (element.getGlass() * prices.getHourlyRate());
            cell.setCellValue(element.getGlass() * prices.getHourlyRate());
        } else {
            cell = row.getCell(narmotive);
            cell.setCellValue(element.getArmatureSide());
            cell = row.getCell(price);
            cell.setCellValue(prices.getHourlyRate());
            cell = row.getCell(totalPrice);
            total += (element.getArmatureSide() * prices.getHourlyRate());
            cell.setCellValue(element.getArmatureSide() * prices.getHourlyRate());
        }
        if (element.getKuzDetReplaceSide() != 0) {
            row = sheet.getRow(++rowForePaste);
            cell = row.getCell(nameOfWork);
            cell.setCellValue(element.getName().replace("Замена", "") + " Замена.куз.дет.");
            cell = row.getCell(narmotive);
            cell.setCellValue(element.getKuzDetReplaceSide());
            cell = row.getCell(price);
            cell.setCellValue(prices.getHourlyRate());
            cell = row.getCell(totalPrice);
            total += (element.getKuzDetReplaceSide() * prices.getHourlyRate());
            cell.setCellValue(element.getKuzDetReplaceSide() * prices.getHourlyRate());
        }
        if (element.getRemont() != 0) {
            row = sheet.getRow(++rowForePaste);
            cell = row.getCell(nameOfWork);
            cell.setCellValue(element.getName().replace("Замена", "") + " ремонт");
            cell = row.getCell(narmotive);
            cell.setCellValue(element.getRemont());
            cell = row.getCell(price);
            cell.setCellValue(prices.getHourlyRate());
            cell = row.getCell(totalPrice);
            total += (element.getRemont() * prices.getHourlyRate());
            cell.setCellValue(element.getRemont() * prices.getHourlyRate());
        }
        if (element.getPaintSide() != 0) {
            row = sheet.getRow(++rowForePaste);
            cell = row.getCell(nameOfWork);
            cell.setCellValue(element.getName().replace("Замена", "") + " окраска");
            cell = row.getCell(narmotive);
            cell.setCellValue(element.getPaintSide());
            cell = row.getCell(price);
            cell.setCellValue(prices.getHourlyRate());
            cell = row.getCell(totalPrice);
            total += (element.getPaintSide() * prices.getHourlyRate());
            cell.setCellValue(element.getPaintSide() * prices.getHourlyRate());
        }
        if (element.getRuchka() != 0) {
            row = sheet.getRow(++rowForePaste);
            cell = row.getCell(nameOfWork);
            cell.setCellValue("ручка окраска");
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
            cell.setCellValue("молдинг окраска");
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
            cell.setCellValue("зеркало окраска");
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
            cell.setCellValue("накладка окраска");
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
            cell.setCellValue("накладка окраска");
            cell = row.getCell(narmotive);
            cell.setCellValue(element.getExpander());
            cell = row.getCell(price);
            cell.setCellValue(prices.getHourlyRate());
            cell = row.getCell(totalPrice);
            total += (element.getExpander() * prices.getHourlyRate());
            cell.setCellValue(element.getExpander() * prices.getHourlyRate());
        }
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
    } // заполняет строки работами

    private void writeTotal(Sheet sheet) {
        int totalPrice = 40;
        Row row = sheet.getRow(targetRowNum + 1);
        Cell cell = row.getCell(totalPrice);
        cell.setCellValue(total);
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
        Row newRow = sheet.getRow(targetRowNum);
        if (newRow != null) {
            sheet.shiftRows(targetRowNum, sheet.getLastRowNum(), 1);
            newRow = sheet.createRow(targetRowNum);
        } else {
            newRow = sheet.createRow(targetRowNum);
        }

        for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
            Cell oldCell = sourceRow.getCell(i);
            Cell newCell = newRow.createCell(i);

            if (oldCell == null) {
                newCell = null;
                continue;
            }

            // Копирование стиля и содержимого
            newCell.setCellStyle(oldCell.getCellStyle());
            newCell.setCellType(oldCell.getCellType());

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
        // Копирование объединенных ячеек
        for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
            CellRangeAddress cellRangeAddress = sheet.getMergedRegion(i);
            if (cellRangeAddress.getFirstRow() == sourceRow.getRowNum()) {
                CellRangeAddress newCellRangeAddress = new CellRangeAddress(newRow.getRowNum(),
                        (newRow.getRowNum() +
                                (cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow())),
                        cellRangeAddress.getFirstColumn(),
                        cellRangeAddress.getLastColumn());
                sheet.addMergedRegion(newCellRangeAddress);
            }
        }
        if (numOfWork != 0) {
            Cell numWorkCell = newRow.getCell(1);
            numWorkCell.setCellValue(numOfWork);
        }
    } // метод копирования строк со всеми параметрами
}