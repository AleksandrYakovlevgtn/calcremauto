package ru.yandex.practicum.calcRemAuto.storage;

public class NameOfElement {
    String[] name = {" ЛЕВОЕ", " ПРАВОЕ", "КРЫЛО_ПЕРЕДНЕЕ", "ДВЕРЬ_ПЕРЕДНЯЯ", "ДВЕРЬ_ЗАДНЯЯ", "КРЫЛО_ЗАДНЕЕ", "ПОРОГ", "БРУС", "БАМПЕР_ПЕРЕДНИЙ", "КАПОТ", "КРЫША", "КРЫШКА_БОГАЖНИКА", "БАМПЕР_ЗАДНИЙ"};

    public String getFullNameElement(int side, int num) {
        if (side == 1 || side == 2) {
            if (side == 1) {
                return name[num + 1] + name[0];
            } else {
                return name[num + 1] + name[1];
            }
        } else {
            return name[num + 7];
        }
    }
}
