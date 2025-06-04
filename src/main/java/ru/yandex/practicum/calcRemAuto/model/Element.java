package ru.yandex.practicum.calcRemAuto.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Element {
    Prices prices = new Prices();
    int hourlyRate = prices.getHourlyRate();
    String name;
    double paintSide = 0;
    double armatureSide = 0;
    double kuzDetReplaceSide = 0;
    int glass = 0;
    String nameGlass = "null";
    int zerkalo = 0;
    int molding = 0;
    int ruchka = 0;
    int overlay = 0;
    int expander = 0;
    double remont = 0;
    double total;
    String hoDoRemont = "null";
    double dopWorksArmoturchik = 0;
    double dopWorksPainter = 0;
    double dopWorksKuzovchik = 0;
    double lkmForElement = 0.0;
    String descriptionDopWorksArmaturchic = "null";
    String descriptionDopWorksPainter = "null";
    String descriptionDopWorksKuzovchik = "null";
    double notNormWork = 0;
    String descriptionDopNotNormWork = "null";

    {
        calculateTotal();
    }

    private void calculateTotal() {
        total = ((paintSide + armatureSide + kuzDetReplaceSide + glass + zerkalo
                + molding + ruchka + expander + overlay + remont
                + dopWorksArmoturchik + dopWorksPainter + dopWorksKuzovchik + notNormWork) * hourlyRate);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(" \n");
        if (paintSide > 0 || glass > 0) result.append(name).append("\n");
        else if (paintSide <= 0 && glass <= 0 && notNormWork <= 0) result.append(name).append(" не красим").append("\n");
        if (nameGlass != null && !nameGlass.equals("null")) result.append(nameGlass).append("\n");
        if (zerkalo > 0) result.append("Зеркало окраска.").append("\n");
        if (ruchka > 0) result.append("Ручка окраска.").append("\n");
        if (molding > 0) result.append("Молдинг окраска.").append("\n");
        if (expander > 0) result.append("Расширитель окраска.").append("\n");
        if (overlay > 0) result.append("Накладка окраска.").append("\n");
        if (remont > 0) result.append("Ремонт ").append(remont).append("н/ч").append("\n");
        if (dopWorksArmoturchik > 0)
            result.append("Доп работа Арматурщик ").append(dopWorksArmoturchik).append("н/ч").append("\n");
        if (dopWorksPainter > 0) result.append("Доп работа Маляр ").append(dopWorksPainter).append("н/ч").append("\n");
        if (dopWorksKuzovchik > 0)
            result.append("Доп работа Кузовщик ").append(dopWorksKuzovchik).append("н/ч").append("\n");
        if (notNormWork > 0) result.append("ненормативные ").append(notNormWork).append("н/ч").append("\n");
        result.append("малярные работы = ").append(paintSide).append("\n")
                .append("арматурные работы = ").append(armatureSide).append("\n")
                .append("зам.куз.детали = ").append(kuzDetReplaceSide).append("\n")
                .append("работы с остеклением = ").append(glass).append("\n");
        calculateTotal();
        result.append(total).append(" руб.\n");
        return result.toString();
    }
}