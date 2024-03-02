package ru.yandex.practicum.calcRemAuto.storage;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.yandex.practicum.calcRemAuto.model.Client;
import ru.yandex.practicum.calcRemAuto.model.Element;
import ru.yandex.practicum.calcRemAuto.model.NameDirectories;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExcelUpdater {

    NameDirectories d = new NameDirectories();

    public void updateExcelFile(Client client) {
        try {
            String originalFilePath = "Системные/Болванка.xlsx";
            String newFilePath = "Системные/Test.xlsx ";
            // Чтение из оригинального файла
            FileInputStream originalFileInputStream = new FileInputStream(originalFilePath);
            Workbook workbook = new XSSFWorkbook(originalFileInputStream);
            Sheet sheet = workbook.getSheetAt(0);
            sheet = writeDate(sheet);
            sheet = writeClien(sheet, client);


            // Сохранение в новый файл
            FileOutputStream newFileOutputStream = new FileOutputStream(newFilePath);
            workbook.write(newFileOutputStream);

            // Закрываем потоки
            originalFileInputStream.close();
            newFileOutputStream.close();
            workbook.close();

            System.out.println("Файл успешно обновлен и сохранен по новому пути: " + newFilePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Sheet writeDate(Sheet sheet) {
        int dateRow = 7;
        int dateCell = 33;
        Row row = sheet.getRow(dateRow);
        Cell cell = row.getCell(dateCell);
        cell.setCellValue((LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
        return sheet;
    } // Запись в файл даты открытия заказа

    private Sheet writeClien(Sheet sheet, Client client) {
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
        return sheet;
    } // Запись в файл данных по клиенту в две строки и две колонки (4 значения)

    private static void copyRow(Row sourceRow, Row newRow) {
        for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
            Cell oldCell = sourceRow.getCell(i);
            Cell newCell = newRow.createCell(i);

            if (oldCell != null) {
                newCell.setCellStyle(oldCell.getCellStyle());
                newCell.setCellType(oldCell.getCellType());

                switch (oldCell.getCellType()) {
                    case STRING:
                        newCell.setCellValue(oldCell.getStringCellValue());
                        break;
                    case BOOLEAN:
                        newCell.setCellValue(oldCell.getBooleanCellValue());
                        break;
                    case NUMERIC:
                        newCell.setCellValue(oldCell.getNumericCellValue());
                        break;
                    case FORMULA:
                        newCell.setCellFormula(oldCell.getCellFormula());
                        break;
                    case BLANK:
                        break;
                    default:
                        break;
                }
            }
        }
    }
}