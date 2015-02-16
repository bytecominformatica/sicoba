package net.servehttp.bytecom.extra.controller;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.inject.Inject;

import net.servehttp.bytecom.extra.service.LogService;
import net.servehttp.bytecom.util.ejb.MailEJB;
import net.servehttp.bytecom.util.web.AlertaUtil;

public class GenericoController implements Serializable {

  private static final long serialVersionUID = 3946937417603951392L;
  private static final String DESTINATARIO = "clairton.c.l@gmail.com";
  private static final String ASSUNTO = "ERROR NO SICOBA";

  @EJB
  private MailEJB mail;

  @Inject
  private LogService log;

  protected void log(Exception e) {
    AlertaUtil.error(String.format("Error: %s%nCausa: %s", e.getMessage(), e.getCause()));
    enviarLogError(e);
  }

  private void enviarLogError(Exception e) {
    mail.send(DESTINATARIO, ASSUNTO, log.getMensagemLog(e));
  }
}
