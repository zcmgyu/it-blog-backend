package com.aptech.itblog.utils;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ParseUtils {

    public static HashMap parseMap(String str) {
        return (HashMap<String, String>) Arrays
                .asList(str.split(","))
                .stream()
                .map(s -> s.split(":"))
                .collect(Collectors.toMap(e -> e[0], e -> e[1]));

    }

    public static List<String> parseList(String jsonStr) {
        List<String> jsonList;
        try {
            jsonList = new ObjectMapper().readValue(jsonStr, List.class);
        } catch (IOException e) {
            jsonList = new ArrayList<>();
        }
        return jsonList;
    }
}
