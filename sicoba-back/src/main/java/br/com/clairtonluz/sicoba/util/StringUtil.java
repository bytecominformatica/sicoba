package br.com.clairtonluz.sicoba.util;

import javax.swing.text.MaskFormatter;
import java.text.Normalizer;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

/**
 * @author clairton
 */
public final class StringUtil {

    public static final Locale LOCALE_BRAZIL = new Locale("pt", "BR");
    public static final int CPF_SIZE = 11;
    public static final int CNPJ_SIZE = 14;
    private static final DateTimeFormatter FORMATTER_DATA = DateTimeFormatter.ofPattern("ddMMyyyy");
    private static final DateTimeFormatter FORMATTER_DATA_HORA = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");

    public static String formatarString(String texto, String mascara) {
        try {
            MaskFormatter mf = null;
            mf = new MaskFormatter(mascara);
            mf.setValueContainsLiteralCharacters(false);
            return mf.valueToString(texto);
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static String formatarCpfCnpj(String cpfCnpj) {
        if (isCpf(cpfCnpj)) {
            cpfCnpj = formatarCpf(cpfCnpj);
        } else if (isCnpj(cpfCnpj)) {
            cpfCnpj = formatarCnpj(cpfCnpj);
        }

        return cpfCnpj;
    }

    public static String formatarCpf(String cpf) {
        cpf = removerFormatacaoCpfCnpj(cpf);
        return formatarString(cpf, "###.###.###-##");
    }

    public static String formatarCnpj(String cnpj) {
        cnpj = removerFormatacaoCpfCnpj(cnpj);
        return formatarString(cnpj, "##.###.###/####-##");
    }

    public static boolean isCpf(String cpf) {
        cpf = removerFormatacaoCpfCnpj(cpf);
        return cpf != null && cpf.length() == CPF_SIZE;
    }

    public static boolean isCnpj(String cnpj) {
        cnpj = removerFormatacaoCpfCnpj(cnpj);
        return cnpj != null && cnpj.length() == CNPJ_SIZE;
    }

    public static String removerFormatacaoFone(String fone) {
        return fone != null ? fone.replaceAll("[() -]", "") : null;
    }

    public static String removerFormatacaoCpfCnpj(String cpfCnpj) {
        return cpfCnpj != null ? cpfCnpj.replaceAll("[. -]", "") : null;
    }

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
        return String.format(LOCALE_BRAZIL, "%1$,.2f", value);
    }

    public static boolean isBlank(String value) {
        return value == null || value.isEmpty();
    }

    public static boolean isNotBlank(String value) {
        return !isBlank(value);
    }

    /**
     * preenche com zeros a esqueda
     *
     * @param value
     * @param size
     * @return
     */
    public static String padLeft(Object value, int size) {
        return padLeft(value, size, "0");
    }

    /**
     * Preenche com o caractere especificado a esquerda
     *
     * @param value
     * @param size
     * @param fill
     * @return
     */
    public static String padLeft(Object value, int size, String fill) {
        if (value == null) return null;

        StringBuilder sb = new StringBuilder(String.valueOf(value));
        while (sb.length() < size) {
            sb.insert(0, fill);
        }
        return sb.toString();
    }
}
