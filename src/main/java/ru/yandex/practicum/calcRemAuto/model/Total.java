package ru.yandex.practicum.calcRemAuto.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Total {
    Double malyr = 0.0;
    Double armatyrchik = 0.0;
    Double kuzovchik = 0.0;
    Double master = 0.0;
    Double total = 0.0;

    @Override
    public String toString() {
        return "Маляр: " + malyr +
                " Арматурщик: " + armatyrchik +
                " Кузовщик: " + kuzovchik +
                " Мастер: " + master +
                " Итого: " + total;
    }
}