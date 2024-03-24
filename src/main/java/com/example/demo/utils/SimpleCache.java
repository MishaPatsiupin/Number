package com.example.demo.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SimpleCache {

    private Map<String, Object> cache = new HashMap<>();

    public void put(String key, Object value) {
        cache.put(key, value);
    }

    public Object get(String key) {
        return cache.get(key);
    }

    public void remove(String key) {
        cache.remove(key);
    }

    public void clear() {
        cache.clear();
    }

    public int size() {
        return cache.size();
    }


    public Set<String> keySet() {
        return cache.keySet();
    }

}

