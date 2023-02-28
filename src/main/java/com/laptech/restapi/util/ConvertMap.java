package com.laptech.restapi.util;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Nhat Phi
 * @since 2023-02-28
 */
public class ConvertMap {
    public static Map<String, String> changeAllValueFromObjectToString(Map<String, Object> params) {
        return params.entrySet()
                .stream()
                .map(e -> new AbstractMap.SimpleEntry<>(e.getKey(), e.getValue().toString()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
