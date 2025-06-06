package org.example.default_logger;

import org.example.logger.Formatter;
import org.example.logger.Handler;
import org.example.logger.LogLevel;
import org.example.logger.LogRecord;

public class ConsoleHandler implements Handler {
    private final Formatter formatter;

    public ConsoleHandler(Formatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public void publish(LogRecord record) {
        if (record.getLogLevel().ordinal() >= LogLevel.WARN.ordinal()) {
            System.err.println(formatter.format(record));
        } else {
            System.out.println(formatter.format(record));
        }
    }
}
