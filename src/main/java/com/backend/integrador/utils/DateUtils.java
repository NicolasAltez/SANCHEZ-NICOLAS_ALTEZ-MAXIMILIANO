package com.backend.integrador.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private DateUtils() {
    }

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String obtenerFechaHoraActualFormateada() {
        return LocalDateTime.now(ZoneOffset.UTC).format(DATE_TIME_FORMATTER);
    }




}
