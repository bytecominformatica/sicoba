package net.servehttp.bytecom.controller;

import java.io.IOException;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class ServidorController {

  private static final Logger logger = Logger.getLogger(ServidorController.class.getName());

  public void atualizarAcesso() {
    logger.info("\n######################################################"
        + "\n#                                                    #"
        + "\n#         ATUALIZANDO ACESSO DOS CLIENTES            #"
        + "\n#                                                    #"
        + "\n######################################################");

    executar("/opt/script/./SICOBA.sh start");
  }

  private void executar(String command) {
    try {
      Runtime.getRuntime().exec(command);
    } catch (IOException e) {
      logger.info("O ACESSO DOS CLIENTES NÃO FOI ATUALIZADO PORQUE VOCÊ NÃO ESTA NO SERVIDOR, DEVIDO A ISSO VOCÊ NÃO TEM O SCRIPT DE ATUALIZAÇÃO /opt/script/startServer.sh");
    }
  }

}
