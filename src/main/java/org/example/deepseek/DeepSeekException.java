package org.example.deepseek;

public class DeepSeekException extends Exception {
    private final int statusCode;

    public DeepSeekException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
