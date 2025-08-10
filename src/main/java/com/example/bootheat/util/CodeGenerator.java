package com.example.bootheat.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public final class CodeGenerator {
    private static final AtomicInteger SEQ = new AtomicInteger(1);
    private CodeGenerator() {}

    public static String orderCode() {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        int n = SEQ.getAndIncrement();
        return "BE-" + date + "-" + String.format("%04d", n);
    }
}
