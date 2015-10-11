package net.servehttp.bytecom.controller.extra;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.inject.Inject;

import net.servehttp.bytecom.persistence.jpa.extra.GenericoJPA;
import net.servehttp.bytecom.service.extra.LogService;
import net.servehttp.bytecom.util.MensagemException;
import net.servehttp.bytecom.util.ejb.MailEJB;
import net.servehttp.bytecom.util.extra.EmailAddress;
import net.servehttp.bytecom.util.web.AlertaUtil;

public class GenericoController implements Serializable {

  private static final long serialVersionUID = 3946937417603951392L;
  private static final String ASSUNTO = "ERROR NO SICOBA";

  @EJB
  private MailEJB mail;
  @Inject
  private LogService log;
  @Inject
  protected GenericoJPA jpa;

  protected void log(Exception e) {
    AlertaUtil.error(e.getMessage());
    if (!(e instanceof MensagemException)) {
      Logger.getLogger(GenericoController.class.getName()).log(Level.SEVERE, null, e);
      enviarLogError(e);
    }
  }

  private void enviarLogError(Exception e) {
    mail.send(EmailAddress.ADMIN, ASSUNTO, log.getMensagemLog(e));
  }
}
