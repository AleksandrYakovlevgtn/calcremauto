package ru.yandex.practicum.calcRemAuto.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Total {
    double malyr = 0;
    double armatyrchik = 0;
    double kuzovchik = 0;
    double master = 0;
    double total = 0;

    @Override
    public String toString() {
        return "Маляр: " + malyr +
                " Арматурщик: " + armatyrchik +
                " Кузовщик: " + kuzovchik +
                " Мастер: " + master +
                " Итого: " + total;
    }
}