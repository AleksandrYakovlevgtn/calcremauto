package ru.yandex.practicum.calcRemAuto.menu;

import java.util.Scanner;

public class WorkInMenu {
    boolean t = true;

    Menu menu = new Menu();
    //CarService service = new CarService();
    Scanner scanner = new Scanner(System.in);

    /*public void workInAdd() {
        System.out.println("Гос/номер авто.");
        String gosNumber = scanner.nextLine();
        menu.printAddWorks();
        String s = scanner.nextLine();
        String[] work = s.split(" ");
        while (t) {
            List<Object> elements = service.createWorks(work);
            menu.printWorks(elements);
            System.out.println("1 --- сохранить  ||2 --- повторить  ||q --- выход");
            s = scanner.nextLine();
            switch (s) {
                case "1":
                    t = false;
                    service.addCar(gosNumber ,elements);
                    break;
                case "2":
                    workInAdd();
                    break;
                case "q":
                    t = false;
                    break;
                default:
                    System.out.println("Нет такой команды.");
            }
        }
    }*/
}
