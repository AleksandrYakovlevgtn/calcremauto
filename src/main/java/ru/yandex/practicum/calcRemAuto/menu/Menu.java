package ru.yandex.practicum.calcRemAuto.menu;

import ru.yandex.practicum.calcRemAuto.model.Element;
import ru.yandex.practicum.calcRemAuto.model.Total;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    /*public void printTitle() {
        System.out.println("1 --- Добавить машину.");
        System.out.println("2 --- Обновить машину в ремонте.");
        System.out.println("3 --- Найти машину по гос номеру.");
        System.out.println("q --- Закрыть программу.");
    }

    public void printAddWorks() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1 --- Левая     ||2 --- правая   ||3 --- Центр");
        String command = scanner.nextLine();
        if (command.indexOf("1") == 0 || command.indexOf("2") == 0){
            System.out.println("1 --- Крыло Пер ||2 --- Дверь Пер||3 --- Дверь Зад||4 --- Крыло Зад ||5 --- Порог  ||6 --- Брус");
        }

        System.out.println("1 --- Бампер Пер||2 --- Капот    ||3 --- Крыша    ||4 --- Крышка баг||5 --- Бампер зад");
        System.out.println("1 --- Окраска   ||2 --- Замена   ||3 --- Ремонт(следующий символ сколько н/ч)");
        System.out.println("1 --- Зеркало   ||2 --- Ручка    ||3 --- Молдинг");
    }

    public void printWorks(List<Object> elements) {
       for(Object works : elements){
           if(works.getClass() == Element.class){
               System.out.println(((Element) works).getName());
               System.out.println("Ремонт: " + ((Element) works).getRemont());
               System.out.println("Общее : " + ((Element) works).getTotal());
           } else if (works.getClass() == Total.class){
               System.out.println("Маляр : " + ((Total) works).getMalyr());
               System.out.println("Армат : " + ((Total) works).getArmatyrchik());
               System.out.println("Мастер: " + ((Total) works).getMaster());
               System.out.println("----------------------------------------");
               System.out.println("Итого : " + ((Total) works).getTotal());
           }
       }
    }*/
}
