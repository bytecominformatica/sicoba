package br.com.clairtonluz.sicoba.util;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class NumberUtilTest {

    @Test
    public void deveriaFormatarCurrence() {
        double value = 1_345_432_098.32;
        String atual = NumberUtil.formatCurrence(value);
        String expected = "1.345.432.098,32";
        Assert.assertEquals(expected, atual);
    }
}