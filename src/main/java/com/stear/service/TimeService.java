package com.stear.service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeService {
    public static String getSystemUTCTime() {
        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        return pattern.format(now);
    }
}
