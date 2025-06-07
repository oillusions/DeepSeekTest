package cn.oillusions.test.logger;

public interface Filter {
    boolean isLogAble(LogRecord record);
}
