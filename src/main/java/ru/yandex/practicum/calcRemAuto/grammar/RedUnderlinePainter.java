package ru.yandex.practicum.calcRemAuto.grammar;

import lombok.SneakyThrows;

import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import java.awt.*;

public class RedUnderlinePainter implements Highlighter.HighlightPainter {

    /**
     * Метод для рисования красного подчеркивания под текстом.
     *
     * @param g      Графический контекст для рисования.
     * @param p0     Начальная позиция текста.
     * @param p1     Конечная позиция текста.
     * @param bounds Границы области отображения компонента.
     * @param c      Компонент, содержащий текст.
     */
    @SneakyThrows
    @Override
    public void paint(Graphics g, int p0, int p1, Shape bounds, JTextComponent c) {
        // Получаем координаты начальной и конечной позиции ошибки
        Rectangle r0 = c.modelToView(p0);
        Rectangle r1 = c.modelToView(p1);

        // Если ошибка находится в одной строке
        if (r0.y == r1.y) {
            // Устанавливаем красный цвет для подчеркивающей линии
            g.setColor(Color.RED);
            // Рисуем прямую линию от начала до конца ошибки
            g.drawLine(r0.x, r0.y + r0.height - 1, r1.x + r1.width, r1.y + r1.height - 1);
        } else { // Если ошибка охватывает несколько строк
            // устанавливаем красный цвет для подчеркивающей линии
            g.setColor(Color.RED);
            // Рисуем линию для первой строки ошибки
            g.drawLine(r0.x, r0.y + r0.height - 1, r0.x + r0.width, r0.y + r0.height - 1);
            // Рисуем линию для последней строки ошибки
            g.drawLine(r1.x, r1.y + r1.height - 1, r1.x + r1.width, r1.y + r1.height - 1);
        }
    }
}