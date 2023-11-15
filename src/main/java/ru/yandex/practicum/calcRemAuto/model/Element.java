package ru.yandex.practicum.calcRemAuto.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Element {
    String name;
    double paintSide = 0;
    double armatureSide = 0;
    double kuzDetReplaceSide = 0;
    int glass = 0;
    int zerkalo = 0;
    int molding = 0;
    int ruchka = 0;
    double remont = 0;
    double total = 0;
}
