package com.ddnik.exceptions;

/**
 * Выбрасывается в случае ошибки при расчёте стоимости бронирования.
 */
public class PriceCalculateException extends RuntimeException {
    public PriceCalculateException(String message) {
        super(message);
    }

    public PriceCalculateException(String message, Throwable cause) {
        super(message, cause);
    }

    public PriceCalculateException(Throwable cause) {
        super(cause);
    }

    public PriceCalculateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
