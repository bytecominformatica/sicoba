package net.servehttp.bytecom.web.websocket;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import net.servehttp.bytecom.ejb.PingTestController;

@ServerEndpoint("/pingtest")
public class PingTestEndpoint {

  private static final Logger logger = Logger.getLogger("PingTestEndpoint");
  static Queue<Session> queue = new ConcurrentLinkedQueue<>();
  @Inject
  private PingTestController pingTestController;
  

  public static void send(String msg) {
    try {
      for (Session session : queue) {
        session.getBasicRemote().sendText(msg);
      }
    } catch (IOException e) {
      logger.log(Level.INFO, e.toString());
    }
  }

  @OnOpen
  public void openConnection(Session session) {
    queue.add(session);
    logger.log(Level.INFO, "Connection opened.");
    send(pingTestController.getUltimoHtml());
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
