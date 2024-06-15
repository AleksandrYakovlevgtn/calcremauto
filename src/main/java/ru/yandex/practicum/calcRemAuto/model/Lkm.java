package ru.yandex.practicum.calcRemAuto.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Lkm {
    int p80 = 0;
    int p180 = 0;
    int p280 = 0;
    int p400 = 0;
    int p500 = 0;
    int stripP80 = 0;
    int stripP120 = 0;
    int stripP180 = 0;
    double scotchBrite = 0.0;
    double priming = 0.0;
    double clear = 0.0;
    double baseDilution = 0.0;
    double basePaint = 0.0;
    double siliconRemover = 0.0;
    double stickyTape = 0.0;
    double coveringFilm = 0.0;
    double puttyFiber = 0.0; // Шпатлевка волокнистая
    double puttyUniversal = 0.0; // Шпатлевка универсальная
    int napkin = 0;
    int hermetic = 0;

    public void plusOneCircles() {
        p80++;
        p180++;
        p280++;
        p400++;
        p500++;
    }
    public void minusOneCircles() {
        p80--;
        p180--;
        p280--;
        p400--;
        p500--;
    }

    public void plusStrip() {
        stripP80++;
        stripP120++;
        stripP180++;
    }
    public void minusStrip() {
        stripP80--;
        stripP120--;
        stripP180--;
    }
}