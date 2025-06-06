package org.example.logger;

public interface Logger {
    void log(LogLevel level, String message);
    void log(LogLevel level, String message, Throwable throwable);

    void debug(String message);
    void info(String message);
    void warn(String message);
    void error(String message);
}
