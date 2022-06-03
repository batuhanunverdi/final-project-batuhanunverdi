package org.patikadev.finalprojectbatuhanunverdi.exception;

/**
 * @author Mert Batuhan UNVERDI
 * @since 18.05.2022
 */
public class BaseException extends RuntimeException {
    private final String message;

    public BaseException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}