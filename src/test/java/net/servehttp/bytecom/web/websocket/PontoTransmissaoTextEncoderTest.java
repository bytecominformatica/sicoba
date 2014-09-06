package net.servehttp.bytecom.web.websocket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.servehttp.bytecom.persistence.entity.pingtest.PontoTransmissao;

import org.junit.Test;

public class PontoTransmissaoTextEncoderTest {

  @Test
  public void testEncode() throws Exception {
    PontoTransmissaoTextEncoder encoder = new PontoTransmissaoTextEncoder();
    List<PontoTransmissao> pontos = new ArrayList<>();
    
    PontoTransmissao p1 = null;
    for(int i = 0; i < 10; i++){
      PontoTransmissao p = new PontoTransmissao();
      p.setId(i);
      p.setIp1(192);
      p.setIp2(168);
      p.setIp3(33);
      p.setIp4(i);
      p.setOnline(i % 2 == 0);
      if(p1 ==  null) {
        p1 = p;
      } else {
        
        p1 = p;
      }
    }
    pontos.add(p1);
    System.out.println(encoder.encode(pontos));
  }

}
