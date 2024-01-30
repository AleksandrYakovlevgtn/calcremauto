package ru.yandex.practicum.calcRemAuto.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor // *********
@NoArgsConstructor
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
    int overlay = 0;
    int expander = 0;
    double remont = 0;
    double total = ((paintSide + armatureSide + kuzDetReplaceSide + glass + zerkalo + molding + ruchka + expander + overlay + remont) * 750);

    @Override
    public String toString() {
        String result = " \n";
        if (paintSide > 0 || glass > 0) result = result + name + "\n";
        else if (paintSide <= 0 && glass <= 0) result = result + name + " не красим" + "\n";
        if (nameGlass != null) result = result + nameGlass + "\n";
        if (zerkalo > 0) result = result + "Зеркало окраска." + "\n";
        if (ruchka > 0) result = result + "Ручка окраска." + "\n";
        if (molding > 0) result = result + "Молдинг окраска." + "\n";
        if (expander > 0) result = result + "Расширитель окраска." + "\n";
        if (overlay > 0) result = result + "Накладка окраска." + "\n";
        if (remont > 0) result = result + "Ремонт " + remont + "н/ч" + "\n";
        result = result + "paintSide = " + paintSide + "\n"
                + "armatureSide = " + armatureSide + "\n"
                + "kuzDetReplaceSide = " + kuzDetReplaceSide + "\n"
                + "glass = " + glass + "\n"
                + ((paintSide + armatureSide + kuzDetReplaceSide + glass + zerkalo + molding + ruchka + expander + overlay + remont) * 750) + " руб.\n";
        return result;
    }
}