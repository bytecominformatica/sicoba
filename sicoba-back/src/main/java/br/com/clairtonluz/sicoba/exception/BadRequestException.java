package br.com.clairtonluz.sicoba.exception;

/**
 * Created by clairtonluz<clairton.c.l@gmail.com> on 02/04/16.
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
