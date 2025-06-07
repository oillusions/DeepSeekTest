package org.example.deepseek;

import java.util.ArrayList;
import java.util.List;

public class DeepSeekException extends Exception {
    private final ErrorCode errorCode;

    public static enum ErrorCode {
        API_INVALID_FORMAT(400, "Invalid Format"),
        API_AUTHENTICATION_FAILS(401, "Authentication Fails"),
        API_INSUFFICIENT_BALANCE(402, "Insufficient Balance"),
        API_INVALID_PARAMETERS(422, "Invalid Parameters"),
        API_RATE_LIMIT_REACHED(429, " Rate Limit Reached"),
        API_SERVER_ERROR(500, "Server Error"),
        API_SERVER_OVERLOADED(503, "Server Overloaded")
        ;
        private final int errorCode;
        private final String errorReason;

        ErrorCode(int errorCode, String errorReason) {
            this.errorCode = errorCode;
            this.errorReason = errorReason;
        }

        @Override
        public String toString() {
            return "ErrorCode %s , %s".formatted(errorCode, errorReason);
        }

        public int getErrorCode() {
            return errorCode;
        }

        public String getErrorReason() {
            return errorReason;
        }
    }

    @Override
    public String getMessage() {
        return "%s \n%s".formatted(errorCode, super.getMessage());
    }

    public DeepSeekException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }
}
