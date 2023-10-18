package ru.yandex.practicum.calcRemAuto.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Element {
    private String name;
    private double side = 0;

    private int zerkalo = 0;
    private int molding = 0;
    private int ruchka = 0;
    private int remont = 0;
    private double total = 0;

    public double getTotal() {
        total = (((2250) * side) + (1000 * remont)) + (1500 * side) + (1000 * (zerkalo + molding + ruchka) + 1500);
        return total;
    }
}
