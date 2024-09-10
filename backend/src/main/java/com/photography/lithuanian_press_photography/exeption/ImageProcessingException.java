package com.photography.lithuanian_press_photography.exeption;

public class ImageProcessingException extends RuntimeException{

    public ImageProcessingException() {
    }

    public ImageProcessingException(String message) {
        super(message);
    }
}
