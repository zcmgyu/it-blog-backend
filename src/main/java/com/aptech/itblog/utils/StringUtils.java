package com.aptech.itblog.utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class StringUtils {

    public static String removeAccent(String s) {

        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }

    public static String convertToHyphenCase(String rawText) {
        // String without accent
        String withoutAccent = StringUtils.removeAccent(rawText);

        // Replace space with hyphen
        String transliterated = withoutAccent.replaceAll("\\s", "-");

        return transliterated;
    }
}
