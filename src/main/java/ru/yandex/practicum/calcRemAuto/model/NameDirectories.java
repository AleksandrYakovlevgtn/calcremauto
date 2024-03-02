package ru.yandex.practicum.calcRemAuto.model;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class NameDirectories {
    String slash = "/";
    String NAME_START_DIRECTORY = "Расчеты";
    String DATE_DIRECTORY = (String.valueOf(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd"))));
    String OFFICIAL_DIRECTORY = "служебная";
    String SMETA = "смета";
    String CLIENT = "клиент";
    String ITOGO = "итого";
    String MALYAR = "маляр";
    String ARMOTURCHIK = "арматурщик";
    String KUZOVCHIK = "кузовщик";
    String LIST_OF_ELEMENTS = "список_элементов";
    String MAP_OF_WORKS = "таблица_работ";
    String txt = ".txt";
}