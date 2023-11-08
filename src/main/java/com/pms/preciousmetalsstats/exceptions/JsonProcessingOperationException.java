package com.pms.preciousmetalsstats.exceptions;

public class JsonProcessingOperationException extends RuntimeException {
    public JsonProcessingOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}