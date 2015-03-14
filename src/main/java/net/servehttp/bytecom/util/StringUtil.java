package net.servehttp.bytecom.util;

import java.text.Normalizer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

/**
 * 
 * @author clairton
 */
public final class StringUtil {

  private static final DateTimeFormatter FORMATTER_DATA = DateTimeFormatter.ofPattern("ddMMyyyy");
  private static final DateTimeFormatter FORMATTER_DATA_HORA = DateTimeFormatter
      .ofPattern("ddMMyyyyHHmmss");


  public static String removeCaracterEspecial(String str) {
    str = Normalizer.normalize(str, Normalizer.Form.NFD);
    str = str.replaceAll("[^\\p{ASCII}]", "");
    return str;
  }


  public static String gerarSenha(int tamanho) {
    StringBuilder senha = new StringBuilder();
    Random random = new Random();
    for (int i = 0; i < tamanho; i++) {
      char c = (char) (random.nextInt(89) + 33);
      senha.append(c);
    }
    return senha.toString();
  }

  public static String get(String line, int inicio, int fim) {
    return line.substring(inicio, fim);
  }

  public static char getChar(String line, int index) {
    return line.charAt(index);
  }

  public static int getInt(String line, int inicio, int fim) {
    return Integer.parseInt(get(line, inicio, fim));
  }

  public static double getDouble2Decimal(String line, int inicio, int fim) {
    return Double.parseDouble(get(line, inicio, fim)) / 100;
  }

  public static LocalDateTime getDataHora(String line, int inicio, int fim) {
    String data = get(line, inicio, fim);
    return LocalDateTime.parse(data, FORMATTER_DATA_HORA);
  }

  public static LocalDate getData(String line, int inicio, int fim) {
    String data = get(line, inicio, fim);
    return LocalDate.parse(data, FORMATTER_DATA);
  }

  public static String formatCurrence(double value) {
    Locale brasil = new Locale("pt", "BR");
    return String.format(brasil, "%1$,.2f", value);
  }

}
