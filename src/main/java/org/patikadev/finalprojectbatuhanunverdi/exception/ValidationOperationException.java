package org.patikadev.finalprojectbatuhanunverdi.exception;

/**
 * @author Mert Batuhan UNVERDI
 * @since 18.05.2022
 */
public class ValidationOperationException {
    private ValidationOperationException() {
    }

    public static class UserNotValidException extends BaseException {
        public UserNotValidException(String message) {
            super(message);
        }
    }

    public static class AccountNotValid extends BaseException {
        public AccountNotValid(String message) {
            super(message);
        }
    }
}
