package org.example.logger;

public interface Handler {
    void publish(LogRecord record);
}
