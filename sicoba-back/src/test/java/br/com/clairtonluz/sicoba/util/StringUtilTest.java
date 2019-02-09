package br.com.clairtonluz.sicoba.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by clairton on 28/11/16.
 */
public class StringUtilTest {

    @Test
    public void deveriaFormatarCurrence() {
        var value = 1_345_432_098.32;
        String atual = StringUtil.formatCurrence(value);
        String expected = "1.345.432.098,32";
        Assert.assertEquals(expected, atual);
    }

    @Test
    public void deveriaCompeltarComZeros() {
        var value = 1234;
        String atual = StringUtil.padLeft(value, 10);
        String expected = "0000001234";
        Assert.assertEquals(expected, atual);
    }

    @Test
    public void deveriaCompeltarComEspacos() {
        var value = 1234;
        String atual = StringUtil.padLeft(value, 10, " ");
        String expected = "      1234";
        Assert.assertEquals(expected, atual);
    }
}
