package net.servehttp.bytecom.ejb;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.DependsOn;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import net.servehttp.bytecom.persistence.PontoTransmissaoJPA;
import net.servehttp.bytecom.persistence.entity.pingtest.PontoTransmissao;
import net.servehttp.bytecom.web.websocket.PingTestEndpoint;

@Startup
@Singleton
@DependsOn("LiquibaseEJB")
public class PingTestController implements Serializable {

  private static final long serialVersionUID = 9080885798882188070L;
  private List<PontoTransmissao> pontos;
  private StringBuilder html;
  private String ultimoHtml;
  @Inject
  private PontoTransmissaoJPA pontoTransmissaoJPA;

  @PostConstruct
  public void init() {
    pontos = pontoTransmissaoJPA.buscarTodosPontoTransmissaoInicial();
    System.out.println("SIZE = " + pontos.size());
  }

  @Schedule(hour = "*", minute = "*", second = "*/5", persistent = false)
  public void pingAll() {
    html = new StringBuilder();

    html.append("<li> <label>");
    html.append("INTERNET");
    html.append("</label>");
    if (pontos != null && !pontos.isEmpty()) {
      html.append("<ul>");
    }
    for (PontoTransmissao p : pontos) {
      gerarHTML(p);
    }
    if (pontos != null && !pontos.isEmpty()) {
      html.append("</ul>");
    }
    html.append("</li>");
    
    ultimoHtml = html.toString();
    PingTestEndpoint.send(html.toString());
  }

  public String getUltimoHtml() {
    return ultimoHtml;
  }

  private void gerarHTML(PontoTransmissao p) {
    if (p.getRecebeDe() == null) {
      html.append("<li> <label id='");
      html.append(p.getId());
      html.append("'>");
      html.append(getIp(p));
      html.append("</label>");
      if (p.getTransmitePara() != null && !p.getTransmitePara().isEmpty()) {
        html.append("<ul>");
      }
      for (PontoTransmissao p1 : p.getTransmitePara()) {
        gerarHTML(p1);
      }
      if (p.getTransmitePara() != null && !p.getTransmitePara().isEmpty()) {
        html.append("</ul>");
      }
      html.append("</li>");
    } else {
      html.append("<li> <label id='");
      html.append(p.getId());
      html.append("'>");
      html.append(getIp(p));
      html.append("</label>");
      if (p.getTransmitePara() != null && !p.getTransmitePara().isEmpty()) {
        html.append("<ul>");
      }
      for (PontoTransmissao p1 : p.getTransmitePara()) {
        gerarHTML(p1);
      }
      if (p.getTransmitePara() != null && !p.getTransmitePara().isEmpty()) {
        html.append("</ul>");
      }
      html.append("</li>");
    }
  }

  private String getIp(PontoTransmissao p) {
    return new StringBuilder().append(p.getIp1()).append(".").append(p.getIp2()).append(".")
        .append(p.getIp3()).append(".").append(p.getIp4()).toString();
  }
}
