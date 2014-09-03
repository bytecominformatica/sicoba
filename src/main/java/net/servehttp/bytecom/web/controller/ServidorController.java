package net.servehttp.bytecom.web.controller;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class ServidorController {

  private static final Logger LOGGER = Logger.getLogger(ServidorController.class.getName());

  public void atualizarAcesso() {
    StringBuilder sb = new StringBuilder();
    sb.append("[").append(new Date()).append("] ATUALIZANDO ACESSO DOS CLIENTES");
    LOGGER.info(sb.toString());

    executar("/opt/script/./SICOBA.sh start");
  }

  private void executar(String command) {
    try {
      Runtime.getRuntime().exec(command);
    } catch (IOException e) {
      LOGGER
          .info("O ACESSO DOS CLIENTES NÃO FOI ATUALIZADO PORQUE VOCÊ NÃO ESTA NO SERVIDOR, DEVIDO A ISSO VOCÊ NÃO TEM O SCRIPT DE ATUALIZAÇÃO /opt/script/startServer.sh");
    }
  }

}
