package com.es.phoneshop.Locker;

import java.util.Map;
import java.util.HashMap;

public class Locker {
    private Map<String, Object> locks;

    public Locker() {
        locks = new HashMap<>();
    }

    public Object getLock(String id) {
        if (!locks.containsKey(id)){
            locks.put(id, new Object());
        }
        return locks.get(id);
    }
}
