package ru.itis.jl.cookweb.security.exceptions;

import org.springframework.security.core.AuthenticationException;

public class RefreshTokenException extends AuthenticationException {
    public RefreshTokenException (String message, Throwable cause) {
        super(message, cause);
    }

    public RefreshTokenException(String msg) {
        super(msg);
    }
}
