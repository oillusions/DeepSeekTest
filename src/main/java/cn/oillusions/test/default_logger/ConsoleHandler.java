package cn.oillusions.test.default_logger;

import cn.oillusions.test.logger.Formatter;
import cn.oillusions.test.logger.Handler;
import cn.oillusions.test.logger.LogLevel;
import cn.oillusions.test.logger.LogRecord;

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
