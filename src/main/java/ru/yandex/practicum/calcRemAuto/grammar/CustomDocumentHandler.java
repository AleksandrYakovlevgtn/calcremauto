package ru.yandex.practicum.calcRemAuto.grammar;

import lombok.extern.slf4j.Slf4j;
import org.languagetool.language.Russian;
import org.languagetool.rules.RuleMatch;

import javax.swing.text.*;
import javax.swing.event.DocumentEvent;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentListener;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class CustomDocumentHandler extends DocumentFilter implements DocumentListener {
    private JTextComponent component;
    private int maxLength;
    private Highlighter highlighter;
    private GrammarChecker grammarChecker;
    private ExecutorService executor;

    public CustomDocumentHandler(JTextComponent component, int maxLength) {
        this.component = component;
        this.maxLength = maxLength;
        highlighter = component.getHighlighter();
        try {
            grammarChecker = new GrammarChecker(new Russian());
        } catch (IOException e) {
            e.printStackTrace();
        }
        executor = Executors.newSingleThreadExecutor();
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if (fb.getDocument().getLength() + string.length() <= maxLength) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (fb.getDocument().getLength() + text.length() - length <= maxLength) {
            super.replace(fb, offset, length, text, attrs);
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        executor.submit(() -> checkGrammar(component.getText()));
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        executor.submit(() -> checkGrammar(component.getText()));
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }

    private void checkGrammar(final String text) {
        try {
            final List<RuleMatch> matches = grammarChecker.check(text);

            // Все манипуляции с UI должны выполняться в EDT
            SwingUtilities.invokeLater(() -> {
                highlighter.removeAllHighlights();

                for (RuleMatch match : matches) {
                    int start = match.getFromPos();
                    int end = match.getToPos();

                    // Подсвечиваем слово с ошибкой красным подчеркиванием
                    try {
                        highlighter.addHighlight(start, end, new RedUnderlinePainter());
                    } catch (BadLocationException ex) {
                        ex.printStackTrace();
                        log.error("Ошибка при добавлении подсветки: " + ex.getMessage());
                    }
                }
            });
        } catch (IOException o) {
            o.printStackTrace();
            log.error("Ошибка в проверке грамматики: " + o.getMessage());
        }
    }
}