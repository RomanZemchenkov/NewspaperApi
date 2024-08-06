package com.roman.service.mapper.date;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class InstantMapper {

    private static final String PATTERN = "dd-MM-yyyy HH:mm:ss";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(PATTERN).withZone(ZoneId.systemDefault());

    public String mapToString(Instant date){
        return FORMATTER.format(date);
    }

    public Instant mapToInstant(String date){
        return Instant.parse(date);
    }
}
