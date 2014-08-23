package net.servehttp.bytecom.util;

import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * 
 * @author clairton
 */
public enum StringUtil {
  INSTANCE;

  private Extenso extenso = new Extenso();

  public String gerarSenha(int tamanho) {
    StringBuilder senha = new StringBuilder();
    Random random = new Random();
    for (int i = 0; i < tamanho; i++) {
      char c = (char) (random.nextInt(89) + 33);
      senha.append(c);
    }
    return senha.toString();
  }

  public String get(String line, int inicio, int fim) {
    return line.substring(inicio, fim);
  }

  public char getChar(String line, int index) {
    return line.charAt(index);
  }

  public int getInt(String line, int inicio, int fim) {
    return Integer.parseInt(get(line, inicio, fim));
  }

  public double getDouble2Decimal(String line, int inicio, int fim) {
    return Double.parseDouble(get(line, inicio, fim)) / 100;
  }

  public Date getDataHora(String line, int inicio, int fim) {
    String data = get(line, inicio, fim);
    return DateUtil.INSTANCE.parse(data, "ddMMyyyyHHmmss");
  }

  public Date getData(String line, int inicio, int fim) {
    String data = get(line, inicio, fim);
    return DateUtil.INSTANCE.parse(data, "ddMMyyyy");
  }

  public String formatCurrence(double value) {
    Locale brasil = new Locale("pt", "BR");
    return String.format(brasil, "%1$,.2f", value);
  }

  public String valorPorExtenso(double valor) {
    extenso.setNumber(valor);
    return extenso.toString();
  }
}
