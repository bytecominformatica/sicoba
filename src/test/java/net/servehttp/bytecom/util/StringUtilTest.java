package net.servehttp.bytecom.util;

import org.junit.Assert;
import org.junit.Test;

public class StringUtilTest {

  @Test
  public void deveriaGerarSenha() {
    String senha = StringUtil.INSTANCE.gerarSenha(8);
    System.out.println(senha);
    Assert.assertNotNull(senha);
    Assert.assertTrue(senha.length() == 8);
  }

  @Test
  public void deveriaGerarOValorExtensoDeUmNumeroDeCentavos() {
    double valor = 0.69;
    String valorPorExtensoEsperado = "sessenta e nove centavos";
    String valorPorExtensoAtual = StringUtil.INSTANCE.valorPorExtenso(valor);
    Assert.assertEquals(valorPorExtensoEsperado, valorPorExtensoAtual);
  }

  @Test
  public void deveriaGerarOValorExtensoDeUmNumeroUnidade() {
    double valor = 1.69;
    String valorPorExtensoEsperado = "hum real e sessenta e nove centavos";
    String valorPorExtensoAtual = StringUtil.INSTANCE.valorPorExtenso(valor);
    Assert.assertEquals(valorPorExtensoEsperado, valorPorExtensoAtual);
  }

  @Test
  public void deveriaGerarOValorExtensoDeUmNumeroDezenas() {
    double valor = 41.69;
    String valorPorExtensoEsperado = "quarenta e um reais e sessenta e nove centavos";
    String valorPorExtensoAtual = StringUtil.INSTANCE.valorPorExtenso(valor);
    Assert.assertEquals(valorPorExtensoEsperado, valorPorExtensoAtual);
  }

  @Test
  public void deveriaGerarOValorExtensoDeUmNumeroCentenas() {
    double valor = 341.69;
    String valorPorExtensoEsperado = "trezentos e quarenta e um reais e sessenta e nove centavos";
    String valorPorExtensoAtual = StringUtil.INSTANCE.valorPorExtenso(valor);
    Assert.assertEquals(valorPorExtensoEsperado, valorPorExtensoAtual);
  }

  @Test
  public void deveriaGerarOValorExtensoDeUmNumeroMilhar() {
    double valor = 35321.69;
    String valorPorExtensoEsperado =
        "trinta e cinco mil e trezentos e vinte e um reais e sessenta e nove centavos";
    String valorPorExtensoAtual = StringUtil.INSTANCE.valorPorExtenso(valor);
    Assert.assertEquals(valorPorExtensoEsperado, valorPorExtensoAtual);
  }

  @Test
  public void deveriaGerarOValorExtensoDeUmNumeroMilhao() {
    double valor = 1235321.69;
    String valorPorExtensoEsperado =
        "hum milhão e duzentos e trinta e cinco mil e trezentos e vinte e um reais e sessenta e nove centavos";
    String valorPorExtensoAtual = StringUtil.INSTANCE.valorPorExtenso(valor);
    Assert.assertEquals(valorPorExtensoEsperado, valorPorExtensoAtual);
  }

  @Test
  public void deveriaGerarOValorExtensoDeUmNumeroMilhoes() {
    double valor = 4235321.69;
    String valorPorExtensoEsperado =
        "quatro milhões e duzentos e trinta e cinco mil e trezentos e vinte e um reais e sessenta e nove centavos";
    String valorPorExtensoAtual = StringUtil.INSTANCE.valorPorExtenso(valor);
    Assert.assertEquals(valorPorExtensoEsperado, valorPorExtensoAtual);
  }

}
