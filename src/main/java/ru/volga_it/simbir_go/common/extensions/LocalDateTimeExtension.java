package ru.volga_it.simbir_go.common.extensions;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class LocalDateTimeExtension {

    public static long daysBetweenInclusive(LocalDateTime ld1, LocalDateTime ld2) {
        return Math.abs(ChronoUnit.DAYS.between(ld1, ld2)) + 1;
    }

    public static long minutesBetweenInclusive(LocalDateTime ld1, LocalDateTime ld2) {
        return Math.abs(ChronoUnit.MINUTES.between(ld1, ld2)) + 1;
    }
}
