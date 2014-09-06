package net.servehttp.bytecom.web.websocket;

import java.io.IOException;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import net.servehttp.bytecom.ejb.PingTestController;
import net.servehttp.bytecom.persistence.PontoTransmissaoJPA;
import net.servehttp.bytecom.persistence.entity.pingtest.PontoTransmissao;

@ServerEndpoint(value = "/pingtest", encoders = {PontoTransmissaoTextEncoder.class})
public class PingTestEndpoint {

  private static final Logger logger = Logger.getLogger("PingTestEndpoint");
  static Queue<Session> queue = new ConcurrentLinkedQueue<>();

  public static void send(List<PontoTransmissao> pontos) {
    try {
      if (pontos != null) {
        for (Session session : queue) {
          session.getBasicRemote().sendObject(pontos);
        }
      }
    } catch (IOException e) {
      logger.log(Level.INFO, e.toString());
    } catch (EncodeException e) {
      e.printStackTrace();
    }
  }

  @OnOpen
  public void openConnection(Session session) {
    queue.add(session);
    logger.log(Level.INFO, "Connection opened.");
    send(PingTestController.PONTOS);
  }

  @OnClose
  public void closedConnection(Session session) {
    queue.remove(session);
    logger.log(Level.INFO, "Connection closed.");
  }

  @OnError
  public void error(Session session, Throwable t) {
    queue.remove(session);
    logger.log(Level.INFO, t.toString());
    logger.log(Level.INFO, "Connection error.");
  }
}
