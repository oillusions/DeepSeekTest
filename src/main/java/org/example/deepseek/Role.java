package org.example.deepseek;

public enum Role {
    SYSTEM("system"),
    USER("user"),
    ASSISTANT("assistant")
    ;
    private final String value;
    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
