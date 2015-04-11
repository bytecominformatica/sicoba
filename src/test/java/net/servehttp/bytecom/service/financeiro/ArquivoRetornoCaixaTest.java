package net.servehttp.bytecom.service.financeiro;

import java.io.InputStream;

import net.servehttp.bytecom.persistence.jpa.entity.financeiro.retorno.Header;
import net.servehttp.bytecom.service.financeiro.ArquivoRetornoCaixa;

import org.junit.Assert;
import org.junit.Test;

public class ArquivoRetornoCaixaTest {

  @Test
  public void testTratarArquivo() throws Exception {
    ArquivoRetornoCaixa retornoCaixa = new ArquivoRetornoCaixa();
    InputStream in = getClass().getClassLoader().getResourceAsStream("files/ret000149.ret");
    Header header = retornoCaixa.lerArquivoRetornoCaixa(in, "ret000149.ret");
    Assert.assertNotNull(header);
  }

}
