package br.com.clairtonluz.sicoba.util;

import javax.swing.text.MaskFormatter;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * @author clairton
 */
public final class StringUtil {

    public static final Locale LOCALE_BRAZIL = new Locale("pt", "BR");
    private static final SimpleDateFormat FORMATTER_DATA = new SimpleDateFormat("ddMMyyyy");
    private static final SimpleDateFormat FORMATTER_DATA_HORA = new SimpleDateFormat("ddMMyyyyHHmmss");
    public static final int CPF_SIZE = 11;
    public static final int CNPJ_SIZE = 14;

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

    public static Date getDataHora(String line, int inicio, int fim) {
        Date date = null;
        try {
            String data = get(line, inicio, fim);
            date = FORMATTER_DATA_HORA.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date getData(String line, int inicio, int fim) {
        Date date = null;
        try {
            String data = get(line, inicio, fim);
            date = FORMATTER_DATA.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String formatCurrence(double value) {
        return String.format(LOCALE_BRAZIL, "%1$,.2f", value);
    }

    public static boolean isEmpty(String notificationUrl) {
        return notificationUrl == null || notificationUrl.isEmpty();
    }
}
