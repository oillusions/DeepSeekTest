package org.example.logger;

public interface Filter {
    boolean isLogAble(LogRecord record);
}
