package com.example.telephony.service.espeak;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public class Translit {
    private final Map<Character, String> letters;

    public Translit() {
        this.letters = new HashMap<>() {
            {
                put('а', "a");
                put('б', "b");
                put('в', "v");
                put('г', "g");
                put('д', "d");
                put('е', "e");
                put('ё', "e");
                put('ж', "z");
                put('з', "z");
                put('и', "i");
                put('й', "y");
                put('к', "k");
                put('л', "l");
                put('м', "m");
                put('н', "n");
                put('о', "o");
                put('п', "p");
                put('р', "r");
                put('с', "s");
                put('т', "t");
                put('у', "u");
                put('ф', "f");
                put('х', "h");
                put('ц', "c");
                put('ч', "ch");
                put('ш', "sh");
                put('щ', "sh");
                put('ъ', "");
                put('ы', "i");
                put('ь', "");
                put('э', "e");
                put('ю', "u");
                put('я', "ya");
                put(' ', " ");
            }
        };
    }

    public String getTranslit(String text){
        StringBuilder sb = new StringBuilder(text.length()*2);
        for(char letter: text.toCharArray()){
            if(!Character.isLetter(letter)) {
                sb.append(letter);
                continue;
            }
            sb.append(letters.get(Character.toLowerCase(letter)));
        }
        return sb.toString();
    }
}
