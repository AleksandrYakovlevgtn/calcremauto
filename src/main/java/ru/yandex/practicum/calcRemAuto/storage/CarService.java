package ru.yandex.practicum.calcRemAuto.storage;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.calcRemAuto.model.Element;
import ru.yandex.practicum.calcRemAuto.model.Total;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarService {
    public void addCar(String gosNumber, List<Object> elements) {


    }

    public List<Object> createWorks(String[] w) {
        List<Object> element = new ArrayList<>();
        Total total = new Total();
        for (String s : w) {
            element.add(createElement(s));
        }
        for (Object works : element) {
            if (works.getClass() == Element.class) {
                double remont = ((Element) works).getRemont();
                int navesnoe = (((Element) works).getMolding() + ((Element) works).getRuchka() + ((Element) works).getZerkalo());
                total.setArmatyrchik((int) (total.getArmatyrchik() + (((Element) works).getPaintSide() * 1500)));
                total.setMalyr((int) (total.getMalyr() + ((((Element) works).getPaintSide() * 2250)) + (750 * navesnoe) + (750 * remont)));
                total.setMaster(total.getMaster() + (1500 + (250 * navesnoe) +(int) ((250 * remont))));
                total.setTotal(total.getTotal() + (total.getMalyr() + total.getArmatyrchik() + total.getMaster()));
            }
        }
        element.add(total);
        return element;
    }

    public Element createElement(String line) {
        String[] b = line.split("");
        Integer[] e = new Integer[b.length];
        for (int i = 0; i < line.length(); i++) {
            e[i] = Integer.parseInt(b[i]);
        }
        Element element = new Element();
        element.setName(new NameOfElement().getFullNameElement(e[0], e[1]));
        if ((e[2] == 2) || element.getName().equals("КРЫША")) {
            if (element.getName().equals("КАПОТ") || element.getName().equals("КРЫША")) {
                element.setPaintSide(2);
            } else if (element.getName().equals("КРЫЛО_ПЕРЕДНЕЕ")
                    || element.getName().equals("БАМПЕР_ПЕРЕДНИЙ")
                    || element.getName().equals("БАМПЕР_ЗАДНИЙ")) {
                element.setPaintSide(1);
            } else {
                element.setPaintSide(1.5);
            }
        } else if (element.getName().equals("КАПОТ")) {
            element.setPaintSide(1.5);
        } else {
            element.setPaintSide(1);
        }
        if (e[2] == 3) {
            element.setRemont(e[3]);
        }
        if (b.length > 3) {
            int ePlus = 3;
            if (e[2] == 3) {
                ePlus++;
            }
            for (int i = 0; i < (b.length - ePlus); i++) {
                if (e[ePlus + i] == 1) {
                    element.setZerkalo(1);
                } else if (e[ePlus + i] == 2) {
                    element.setRuchka(1);
                } else if (e[ePlus + i] == 3) {
                    element.setMolding(1);
                }
            }
        }
        return element;
    }
}
