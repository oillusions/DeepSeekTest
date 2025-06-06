package org.example.default_logger;

import org.example.logger.Formatter;
import org.example.logger.LogRecord;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TestFormatter implements Formatter {
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");


    @Override
    public String format(LogRecord record) {
        return String.format("[%s] [%s] %s",
                timeFormatter.format((LocalDateTime.ofInstant(Instant.ofEpochMilli(record.getTimeStamp()), ZoneId.systemDefault()))),
                record.getLogLevel().name(),
                record.getMessage());
    }
}
