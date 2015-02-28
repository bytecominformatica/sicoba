package net.servehttp.bytecom.util;

public class MensagemException extends Exception {

  private static final long serialVersionUID = -3510033546292743108L;

  public MensagemException(String message) {
    super(message);
  }

  public MensagemException(String messageFormat, Object...objects) {
    this(String.format(messageFormat, objects));
  }

}
