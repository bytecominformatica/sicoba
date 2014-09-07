package net.servehttp.bytecom.ejb;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import net.servehttp.bytecom.persistence.PontoTransmissaoJPA;
import net.servehttp.bytecom.persistence.entity.pingtest.PontoTransmissaoPojo;
import net.servehttp.bytecom.util.NetworkUtil;
import net.servehttp.bytecom.web.websocket.PingTestEndpoint;

@Startup
@Singleton
@DependsOn("LiquibaseEJB")
public class PingTestController implements Serializable {

  private static final long serialVersionUID = 9080885798882188070L;
  private static final Logger LOGGER = Logger.getLogger(PingTestController.class.getName());
  public static List<PontoTransmissaoPojo> PONTOS;
  private static final int SLEEP_MINUTES = 1;
  @Inject
  private PontoTransmissaoJPA pontoTransmissaoJPA;

  @PostConstruct
  public void init() {
    PONTOS = pontoTransmissaoJPA.buscarTodosPontoTransmissaoDetachERecebeDeNull();

    new Thread(new Runnable() {
      @Override
      public void run() {
        while (true) {
          pingAll();
          try {
            Thread.sleep(1000 * 60 * SLEEP_MINUTES);
          } catch (InterruptedException e) {
            LOGGER.severe(e.getMessage());
          }
        }

      }
    }).start();
  }

  public void pingAll() {
    for (PontoTransmissaoPojo p : PONTOS) {
      verificarPontosOnline(p);
    }
    PingTestEndpoint.send(PONTOS);
  }

  private void verificarPontosOnline(PontoTransmissaoPojo p) {
    p.setOnline(NetworkUtil.INSTANCE.ping(getIp(p)));
    for (PontoTransmissaoPojo p1 : p.getTransmitePara()) {
      verificarPontosOnline(p1);
    }
  }

  private String getIp(PontoTransmissaoPojo p) {
    return String.format("%d.%d.%d.%d", p.getIp1(), p.getIp2(), p.getIp3(), p.getIp4());
  }
}
