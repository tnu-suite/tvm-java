package com.techzealot.tvm.hotspot.src.share.vm.utilities;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class PrintUtils {

    public static <K, V> void printMap(Map<K, V> map) {
        StringBuilder sb = new StringBuilder("\n");
        map.forEach((k, v) -> {
            sb.append("k: ").append(k).append(" , v: ").append(v).append("\n");
        });
        log.info("{}", sb);
    }
}
