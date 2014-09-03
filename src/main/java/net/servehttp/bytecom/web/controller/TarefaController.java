package net.servehttp.bytecom.web.controller;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import net.servehttp.bytecom.ejb.MailEJB;
import net.servehttp.bytecom.util.DateUtil;

@Singleton
@Startup
public class TarefaController {

  private static final Logger LOGGER = Logger.getLogger(TarefaController.class.getName());
  private static final String DESTINATARIO = "clairton.c.l@gmail.com";
  @EJB
  private MailEJB mail;

  /**
   * Esse metodo é executado a cada 1 minuto de cada hora
   */
  @Schedule(dayOfWeek = "Sun - Sat", hour = "3", persistent = false)
  public void backup() {
    LOGGER.info("\n######################################################"
        + "\n#                                                    #"
        + "\n#                 EFETUANDO BACKUP                   #"
        + "\n#                                                    #"
        + "\n######################################################");

    executar("/opt/script/./backup.sh");
    
    try {
      Thread.sleep(10000);
    } catch (InterruptedException e) {
      LOGGER.log(Level.SEVERE, null, e);
    }
    enviarBackupPorEmail();
  }

  private void executar(String command) {
    try {
      Runtime.getRuntime().exec(command);
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, null, e);
    }
  }

  private void enviarBackupPorEmail() {
    String dataBackup = DateUtil.INSTANCE.formataAnoMesDia(new Date());
    String assunto = "Backup " + dataBackup;
    String fileName = "bytecom" + dataBackup + ".sql";
    String file = "/opt/backup/" + fileName;

    mail.sendAttachment(DESTINATARIO, assunto, "", file, fileName);
  }

}
