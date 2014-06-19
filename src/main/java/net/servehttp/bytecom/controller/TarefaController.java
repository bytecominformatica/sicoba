package net.servehttp.bytecom.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class TarefaController {

  private static final Logger logger = Logger.getLogger(TarefaController.class.getName());

  /**
   * Esse metodo é executado a cada 1 minuto de cada hora
   */
  @Schedule(dayOfWeek = "Sun - Sat", hour = "3", persistent = false)
  public void backup() {
    logger.info("\n######################################################"
        + "\n#                                                    #"
        + "\n#                 EFETUANDO BACKUP                   #"
        + "\n#                                                    #"
        + "\n######################################################");

    executar("/opt/script/./backup.sh");
  }

  private void executar(String command) {
    try {
      Runtime.getRuntime().exec(command);
    } catch (IOException e) {
      logger.log(Level.SEVERE, null, e);
    }
  }

}
