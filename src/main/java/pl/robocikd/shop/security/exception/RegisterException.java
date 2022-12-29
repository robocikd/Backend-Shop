package pl.robocikd.shop.security.exception;

import pl.robocikd.shop.common.exception.BusinessException;

public class RegisterException extends BusinessException {
    public RegisterException(String message) {
        super(message);
    }
}
