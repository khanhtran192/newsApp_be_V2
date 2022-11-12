package com.newsApp_be.security.response;

public enum ResponseStatus {
    SUCCESS(1), FAIL(0);

    private final Integer value;

    ResponseStatus(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }
}
