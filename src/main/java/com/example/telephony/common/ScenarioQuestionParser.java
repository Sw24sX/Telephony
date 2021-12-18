package com.example.telephony.common;

import com.example.telephony.domain.scenario.ScenarioQuestion;
import com.example.telephony.domain.scenario.ScenarioQuestionPart;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScenarioQuestionParser {
    private final Pattern pattern;

    public ScenarioQuestionParser() {
        pattern = Pattern.compile("\\*[a-zа-яА-ЯA-Z_]+");
    }

    public ScenarioQuestion parseTextToScenarioQuestion(String text) {
        if(text == null) {
            return null;
        }

        ScenarioQuestion result = new ScenarioQuestion();
        result.setParts(new ArrayList<>());
        Matcher matcher = pattern.matcher(text.trim());

        int startIndex = 0;
        while(matcher.find()) {
            if(startIndex != matcher.start()) {
                String partText = text.substring(startIndex, matcher.start());
                result.getParts().add(new ScenarioQuestionPart(partText, false, result));
            }

            String partVariable = text.substring(matcher.start(), matcher.end());
            result.getParts().add(new ScenarioQuestionPart(partVariable, true, result));
            startIndex = matcher.end();
        }

        if(startIndex != text.length()) {
            result.getParts().add(new ScenarioQuestionPart(text.substring(startIndex), false, result));
        }

        if(!StringUtils.isBlank(text) && result.getParts().size() == 0) {
            result.getParts().add(new ScenarioQuestionPart(text, false, result));
        }

        return result;
    }

    public String getFullText(ScenarioQuestion question) {
        if(question == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        for(ScenarioQuestionPart part : question.getParts()) {
            builder.append(part.getQuestionPart());
        }
        return builder.toString();
    }
}
