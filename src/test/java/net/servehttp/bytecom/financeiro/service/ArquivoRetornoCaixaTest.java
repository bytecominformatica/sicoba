package net.servehttp.bytecom.financeiro.service;

import java.io.InputStream;

import net.servehttp.bytecom.financeiro.jpa.entity.retorno.Header;

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
