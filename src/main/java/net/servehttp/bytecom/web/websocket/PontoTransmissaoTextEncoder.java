package net.servehttp.bytecom.web.websocket;

import java.util.List;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import net.servehttp.bytecom.persistence.entity.pingtest.PontoTransmissao;

import com.google.gson.Gson;

public class PontoTransmissaoTextEncoder implements Encoder.Text<List<PontoTransmissao>> {

  private Gson gson = new Gson();

  @Override
  public void init(EndpointConfig config) {}

  @Override
  public void destroy() {}

  @Override
  public String encode(List<PontoTransmissao> pontos) throws EncodeException {
    return gson.toJson(pontos);
  }
}
