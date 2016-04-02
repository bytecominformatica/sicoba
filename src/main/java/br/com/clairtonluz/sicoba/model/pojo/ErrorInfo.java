package br.com.clairtonluz.sicoba.model.pojo;

/**
 * Created by clairtonluz<clairton.c.l@gmail.com> on 02/04/16.
 */
public class ErrorInfo {
    public final String url;
    public final String message;

    public ErrorInfo(String url, Exception ex) {
        this.url = url;
        this.message = ex.getLocalizedMessage();
    }
}
