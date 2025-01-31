package ru.yandex.practicum.calcRemAuto.grammar;

import org.languagetool.JLanguageTool;
import org.languagetool.Language;
import org.languagetool.rules.RuleMatch;

import java.io.IOException;
import java.util.List;
public class GrammarChecker  {
    private final JLanguageTool languageTool;

    public GrammarChecker(Language language) throws IOException {
        this.languageTool = new JLanguageTool(language);
    }

    public List<RuleMatch> check(String text) throws IOException {
        return languageTool.check(text);
    }
}