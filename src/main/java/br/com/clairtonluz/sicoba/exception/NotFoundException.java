package br.com.clairtonluz.sicoba.exception;

/**
 * Created by clairtonluz<clairton.c.l@gmail.com> on 02/04/16.
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
