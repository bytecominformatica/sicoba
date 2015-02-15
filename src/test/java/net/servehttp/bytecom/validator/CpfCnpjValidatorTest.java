package net.servehttp.bytecom.validator;

import net.servehttp.bytecom.util.validator.CpfCnpjValidator;

import org.junit.Assert;
import org.junit.Test;

public class CpfCnpjValidatorTest {

  private CpfCnpjValidator cpfCnpjValidator = new CpfCnpjValidator();

  @Test
  public void deveriaValidarCpfComCaracteres(){
    boolean valid = cpfCnpjValidator.isValid("676.593.401-40", null);
    Assert.assertTrue(valid);
  }
  
  @Test
  public void deveriaValidarCpfSemCaracteres(){
    boolean valid = cpfCnpjValidator.isValid("67659340140", null);
    Assert.assertTrue(valid);
  }
  
  @Test
  public void deveriaValidarCnpjComCaracteres(){
    boolean valid = cpfCnpjValidator.isValid("07.888.331/0001-59", null);
    Assert.assertTrue(valid);
  }

  @Test
  public void deveriaValidarCnpjSemCaracteres(){
    boolean valid = cpfCnpjValidator.isValid("07888331000159", null);
    Assert.assertTrue(valid);
  }

}
