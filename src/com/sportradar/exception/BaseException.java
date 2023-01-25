package com.sportradar.exception;

import java.io.Serializable;

public abstract class BaseException extends RuntimeException implements Serializable {
    protected BaseException(String message) {
        super(message);
    }
}
