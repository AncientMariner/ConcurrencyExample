package org.xander.collections;

import java.util.concurrent.*;

public class Scalable {
    public static void main(String[] args) {
        int initialCapacity = 5;
        float loadFactor = 3;
        int concurrencyLevel = 3;

        ConcurrentMap<String, String> concurrentMap = new ConcurrentHashMap<>(initialCapacity,
                                                                                loadFactor,
                                                                                concurrencyLevel);
        concurrentMap.put("key", "value");
        concurrentMap.remove("key", "value");

        concurrentMap.putIfAbsent("key", "value");

        concurrentMap.replace("key", "oldValue", "newValue");
        concurrentMap.replace("key", "oldValue-another one");

        System.out.println(concurrentMap);

        ConcurrentNavigableMap<String, String> skipListMap = new ConcurrentSkipListMap<>();
        skipListMap.put("key", "value2");
        skipListMap.put("a", "value");
        String key = skipListMap.floorKey("b");
        System.out.println(key);


        ConcurrentSkipListSet<String> skipListSet = new ConcurrentSkipListSet<>();
        skipListSet.add("value");
        skipListSet.add("another value");

        System.out.println(skipListSet);

    }
}
