package com.conichi.codingchallenge.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ConversionNotSupportedException extends RuntimeException {

    public ConversionNotSupportedException(String message) {
        super(message);
    }
}