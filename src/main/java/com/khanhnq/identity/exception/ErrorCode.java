package com.khanhnq.identity.exception;

public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999,"UNCATEGORIZED_EXCEPTION"),
    USER_EXISTED(1001, "User existed"),
    USERNAME_INVALID(1003, "User name must be at least 3 char")
    ;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
