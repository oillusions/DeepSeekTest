package org.example.default_logger;

import org.example.logger.Handler;
import org.example.logger.LogLevel;
import org.example.logger.LogRecord;
import org.example.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class TestLogger implements Logger {
    private LogLevel logLevel = LogLevel.INFO;
    private final List<Handler> handlers = new ArrayList<>();
    private final String name;


    public TestLogger(String name) {
        this.name = name;
    }

    public TestLogger addHandler(Handler handler) {
        this.handlers.add(handler);
        return this;
    }

    @Override
    public void log(LogLevel level, String message) {
        log(level, message, null);
    }

    @Override
    public void log(LogLevel level, String message, Throwable throwable) {
        if (level.ordinal() < this.logLevel.ordinal()) {
            System.out.println("等级不足, 我扔!: " + level.name());
            return;
        }
        LogRecord record = new LogRecord(
                message,
                throwable,
                System.currentTimeMillis(),
                level,
                this.name
        );

        publish(record);
    }

    private void publish(LogRecord record) {
        for (Handler handler : handlers) {
            handler.publish(record);
        }
    }

    @Override
    public void debug(String message) {
        log(LogLevel.DEBUG, message);
    }

    @Override
    public void info(String message) {
        log(LogLevel.INFO, message);
    }

    @Override
    public void warn(String message) {
        log(LogLevel.WARN, message);
    }

    @Override
    public void error(String message) {
        log(LogLevel.ERROR, message);
    }

    public void setLogLevel(LogLevel level) {
        this.logLevel = level;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }
}
