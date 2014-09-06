package net.servehttp.bytecom.ejb;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import net.servehttp.bytecom.persistence.PontoTransmissaoJPA;
import net.servehttp.bytecom.persistence.entity.pingtest.PontoTransmissao;
import net.servehttp.bytecom.util.NetworkUtil;
import net.servehttp.bytecom.web.websocket.PingTestEndpoint;

@Startup
@Singleton
public class PingTestController implements Serializable {

  private static final long serialVersionUID = 9080885798882188070L;
  public static List<PontoTransmissao> PONTOS;
  @Inject
  private PontoTransmissaoJPA pontoTransmissaoJPA;

  @PostConstruct
  public void init() {
    PONTOS = pontoTransmissaoJPA.buscarTodosPontoTransmissaoInicial();
    pingAll();
  }

  @Schedule(hour = "*", minute = "*", second = "*/60", persistent = false)
  public void pingAll() {
    for (PontoTransmissao p : PONTOS) {
      verificarPontosOnline(p);
    }
    PingTestEndpoint.send(PONTOS);
  }

  private void verificarPontosOnline(PontoTransmissao p) {
    p.setOnline(NetworkUtil.INSTANCE.ping(getIp(p)));
    // define null pois sem isso a conversão para JSON quebra
    p.setRecebeDe(null);
    for (PontoTransmissao p1 : p.getTransmitePara()) {
      verificarPontosOnline(p1);
    }
  }

  private String getIp(PontoTransmissao p) {
    return String.format("%d.%d.%d.%d", p.getIp1(), p.getIp2(), p.getIp3(), p.getIp4());
  }
}
