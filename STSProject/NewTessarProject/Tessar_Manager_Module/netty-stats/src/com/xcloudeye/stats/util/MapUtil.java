package com.xcloudeye.stats.util;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MapUtil {
    private static final int CACHE_LENGTH = 14;

    public static void setCache(List<TreeMap<Integer, Integer>> cache, Integer date, Integer value, int index) {
        TreeMap<Integer, Integer> cache2 = new TreeMap<Integer, Integer>();
        if (cache.isEmpty() || cache.size() == 0) {
            cache2.put(date, value);
            cache.add(cache2);
        } else {
            if (cache.size() > index) {
                if (!cache2.containsKey(date) && cache.size() >= CACHE_LENGTH) {
                    cache2.remove(cache2.firstKey());
                    cache2.put(date, value);
                } else {
                    cache2.put(date, value);
                }
                cache.set(index, cache2);
            } else {
                cache2.put(date, value);
                cache.add(cache2);
            }
        }
    }


    public static String getParameter(Map<String, Object> paramMap, String key) {
        if (paramMap == null || paramMap.size() == 0 || !paramMap.containsKey(key)) {
            return null;
        }
        return String.valueOf(paramMap.get(key));
    }

}
