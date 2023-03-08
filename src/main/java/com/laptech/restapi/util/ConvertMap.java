package com.laptech.restapi.util;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Nhat Phi
 * @since 2023-02-28
 */
public class ConvertMap {
    public static Map<String, String> changeAllValueFromObjectToString(Map<String, Object> params) {
        Map<String, String> newMap = new HashMap<>();
        params.forEach((k,v) -> {
            String newValue = (v != null) ? String.valueOf(v) : null;
            newMap.put(k, newValue);
        });
        return newMap;
    }
}
