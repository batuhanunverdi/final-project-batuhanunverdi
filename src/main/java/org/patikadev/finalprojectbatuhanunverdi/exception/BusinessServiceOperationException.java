package org.patikadev.finalprojectbatuhanunverdi.exception;

/**
 * @author Mert Batuhan UNVERDI
 * @since 18.05.2022
 */
public class BusinessServiceOperationException {
    public static class UserAlreadyHaveException extends BaseException {
        public UserAlreadyHaveException(String message) {
            super(message);
        }
    }

    public static class UserNotFound extends BaseException {
        public UserNotFound(String message) {
            super(message);
        }
    }

    public static class AccountNotFound extends BaseException {
        public AccountNotFound(String message) {
            super(message);
        }
    }

    public static class AccountHasMoneyException extends BaseException {
        public AccountHasMoneyException(String message) {
            super(message);
        }
    }

    public static class AccountHasDebtException extends BaseException {
        public AccountHasDebtException(String message) {
            super(message);
        }
    }

    public static class BalanceException extends BaseException {
        public BalanceException(String message) {
            super(message);
        }
    }

    public static class AccountTypeException extends BaseException {
        public AccountTypeException(String message) {
            super(message);
        }
    }

    public static class ExchangeNotFound extends BaseException {
        public ExchangeNotFound(String message) {
            super(message);
        }
    }
}
