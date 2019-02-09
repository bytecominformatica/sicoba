package br.com.clairtonluz.sicoba.config;

import br.com.clairtonluz.sicoba.exception.BadRequestException;
import br.com.clairtonluz.sicoba.exception.ConflitException;
import br.com.clairtonluz.sicoba.exception.NotFoundException;
import br.com.clairtonluz.sicoba.model.pojo.ErrorInfo;
import br.com.clairtonluz.sicoba.service.notification.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by clairtonluz<clairton.c.l@gmail.com> on 02/04/16.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private EmailService emailService;

    @ResponseStatus(HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(ConflitException.class)
    @ResponseBody
    public ErrorInfo handleConflict(HttpServletRequest req, Exception e) {
        return new ErrorInfo(req.getRequestURI(), e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    public ErrorInfo handleBadRequest(HttpServletRequest req, Exception e) {
        return new ErrorInfo(req.getRequestURI(), e);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ErrorInfo handleNotFound(HttpServletRequest req, Exception e) {
        return new ErrorInfo(req.getRequestURI(), e);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorInfo handleException(HttpServletRequest req, Exception e) {
        e.printStackTrace();
        emailService.notificarAdmin(req, e);
        return new ErrorInfo(req.getRequestURI(), e);
    }
}
