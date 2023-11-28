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
    String nameGlass;
    int zerkalo = 0;
    int molding = 0;
    int ruchka = 0;
    double remont = 0;
    double total = 0;

    @Override
    public String toString() {
        String result = "-------------------------\n";
        if (paintSide > 0 || glass > 0) result = result + name + "\n";
        else if (paintSide <= 0 && glass <= 0) result = result + name + " не красим" + "\n";
        if (nameGlass != null) result = result + nameGlass + "\n";
        if (zerkalo > 0) result = result + "Зеркало окраска." + "\n";
        if (ruchka > 0) result = result + "Ручка окраска." + "\n";
        if (molding > 0) result = result + "Молдинг окраска." + "\n";
        if (remont > 0) result = result + "Ремонт " + remont + "н/ч" + "\n";
        result = result + "paintSide = " +  paintSide + "\n"
                + "armatureSide = " + armatureSide + "\n"
                + "kuzDetReplaceSide = " + kuzDetReplaceSide + "\n"
                + "glass = " + glass + "\n";
        return result;
    }
}
