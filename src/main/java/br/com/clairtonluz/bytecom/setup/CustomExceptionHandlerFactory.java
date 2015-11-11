package br.com.clairtonluz.bytecom.setup;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

/**
 * Created by clairtonluz on 07/11/15.
 */
public class CustomExceptionHandlerFactory extends ExceptionHandlerFactory {
    private ExceptionHandlerFactory parent;

    // this injection handles jsf
    public CustomExceptionHandlerFactory(ExceptionHandlerFactory parent) {
        this.parent = parent;
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        ExceptionHandler handler = new CustomExceptionHandler(parent.getExceptionHandler());
        return handler;
    }

}