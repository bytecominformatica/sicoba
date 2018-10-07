package br.com.clairtonluz.sicoba.util;

import br.com.caelum.stella.inwords.FormatoDeReal;
import br.com.caelum.stella.inwords.InteiroSemFormato;
import br.com.caelum.stella.inwords.NumericToWordsConverter;

import java.util.Locale;

/**
 * @author clairton
 */
public final class NumberUtil {

    public static final Locale LOCALE_BRAZIL = new Locale("pt", "BR");
    public static final NumericToWordsConverter CONVERTER_TO_WORD = new NumericToWordsConverter(new InteiroSemFormato());
    public static final NumericToWordsConverter CONVERTER_TO_WORD_REAL = new NumericToWordsConverter(new FormatoDeReal());

    public static String numberToWord(double value) {
        return CONVERTER_TO_WORD.toWords(value);
    }

    public static String numberToWordReal(double value) {
        return CONVERTER_TO_WORD_REAL.toWords(value);
    }


    public static String formatCurrence(double value) {
        return String.format(LOCALE_BRAZIL, "%1$,.2f", value);
    }


}
