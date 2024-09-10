package com.photography.lithuanian_press_photography.exeption;

public class ImageValidationException extends RuntimeException{
    public ImageValidationException() {
    }

    public ImageValidationException(String message) {
        super(message);
    }
}
