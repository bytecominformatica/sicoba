package br.com.clairtonluz.sicoba.config;

import br.com.clairtonluz.sicoba.exception.BadRequestException;
import br.com.clairtonluz.sicoba.exception.ConflitException;
import br.com.clairtonluz.sicoba.model.pojo.ErrorInfo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by clairtonluz<clairton.c.l@gmail.com> on 02/04/16.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(ConflitException.class)
    @ResponseBody
    public ErrorInfo handleConflict(HttpServletRequest req, Exception ex) {
        return new ErrorInfo(req.getRequestURI(), ex);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    public ErrorInfo handleEmpty(HttpServletRequest req, Exception ex) {
        return new ErrorInfo(req.getRequestURI(), ex);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorInfo handleException(HttpServletRequest req, Exception ex) {
        ex.printStackTrace();
        return new ErrorInfo(req.getRequestURI(), ex);
    }
}
