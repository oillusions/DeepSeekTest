package cn.oillusions.test.logger;

public class LogRecord {
    protected final String message;
    protected final Throwable throwable;
    protected final long timeStamp;
    protected final LogLevel LogLevel;
    protected final String loggerName;


    public LogRecord(String message, Throwable throwable, long timeStamp, LogLevel level, String loggerName) {
        this.message = message;
        this.throwable = throwable;
        this.timeStamp = timeStamp;
        this.LogLevel = level;
        this.loggerName = loggerName;
    }

    public LogLevel getLogLevel() {
        return LogLevel;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public String getLoggerName() {
        return loggerName;
    }

    public Throwable getThrowable() {
        return throwable;
    }
}
