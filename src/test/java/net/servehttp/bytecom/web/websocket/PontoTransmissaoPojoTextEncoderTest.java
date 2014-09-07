package net.servehttp.bytecom.web.websocket;

import java.util.ArrayList;
import java.util.List;

import net.servehttp.bytecom.persistence.entity.pingtest.PontoTransmissaoPojo;

import org.junit.Test;

public class PontoTransmissaoPojoTextEncoderTest {

  @Test
  public void testEncode() throws Exception {
    PontoTransmissaoPojoTextEncoder encoder = new PontoTransmissaoPojoTextEncoder();
    List<PontoTransmissaoPojo> pontos = new ArrayList<>();

    PontoTransmissaoPojo p1 = null;
    for (int i = 0; i < 10; i++) {
      PontoTransmissaoPojo p = new PontoTransmissaoPojo();
      p.setIp1(192);
      p.setIp2(168);
      p.setIp3(33);
      p.setIp4(i);
      p.setOnline(i % 2 == 0);
      if (p1 == null) {
        p1 = p;
      } else {

        p1 = p;
      }
    }
    pontos.add(p1);
    encoder.encode(pontos);
  }

}
