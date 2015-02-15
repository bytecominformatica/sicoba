package net.servehttp.bytecom.util.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import net.servehttp.bytecom.util.validator.CpfCnpjValidator;
 
/**
 * 
 * @author Clairton Luz - clairton.c.l@gmail.com
 *
 */	
@Constraint(validatedBy = {CpfCnpjValidator.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface CpfCnpj {
 
  String message() default "CPF/CNPJ inválido";
 
  Class<?>[] groups() default {};
 
  Class<? extends Payload>[] payload() default {};
}
