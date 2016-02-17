package br.com.clairtonluz.sicoba.validator;

import br.com.clairtonluz.sicoba.annotation.CpfCnpj;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;


/**
 * @author clairtonluz
 */
public class CpfCnpjValidator implements ConstraintValidator<CpfCnpj, String> {

    private List<String> cnpjInvalido = Arrays.asList("00000000000000", "11111111111111",
            "22222222222222", "33333333333333", "44444444444444", "55555555555555", "66666666666666",
            "77777777777777", "88888888888888", "99999999999999");

    @Override
    public void initialize(CpfCnpj constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || value.isEmpty() || isCpf(value) || isCnpj(value);
    }

    /**
     * Realiza a validação do CPF.
     *
     * @param cpf número de CPF a ser validado pode ser passado no formado 999.999.999-99 ou
     *            99999999999
     * @return true se o CPF é válido e false se não é válido
     */
    private boolean isCpf(String cpf) {
        cpf = cpf.replace(".", "");
        cpf = cpf.replace("-", "");

        if (!isNumbers(cpf)) {
            return false;
        }

        int d1, d2;
        int digito1, digito2, resto;
        int digitoCPF;
        String nDigResult;

        d1 = d2 = 0;
        digito1 = digito2 = resto = 0;

        for (int nCount = 1; nCount < cpf.length() - 1; nCount++) {
            digitoCPF = Integer.valueOf(cpf.substring(nCount - 1, nCount)).intValue();
            d1 = d1 + (11 - nCount) * digitoCPF;
            d2 = d2 + (12 - nCount) * digitoCPF;
        }
        ;

        resto = (d1 % 11);
        digito1 = resto < 2 ? 0 : 11 - resto;
        d2 += 2 * digito1;
        resto = (d2 % 11);

        digito2 = resto < 2 ? 0 : 11 - resto;
        String nDigVerific = cpf.substring(cpf.length() - 2, cpf.length());
        nDigResult = String.valueOf(digito1) + String.valueOf(digito2);

        return nDigVerific.equals(nDigResult);
    }

    private boolean isNumbers(String value) {
        try {
            Long.parseLong(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Realiza a validação de um cnpj
     *
     * @param cnpj String - o CNPJ pode ser passado no formato 99.999.999/9999-99 ou 99999999999999
     * @return boolean
     */
    private boolean isCnpj(String cnpj) {
        cnpj = cnpj.replace(".", "");
        cnpj = cnpj.replace("-", "");
        cnpj = cnpj.replace("/", "");

        if (!isNumbers(cnpj)) {
            return false;
        }


        if (cnpjInvalido.contains(cnpj) || cnpj.length() != 14) {
            return false;
        }
        char dig13, dig14;
        int sm, i, r, num, peso;

        try {
            sm = 0;
            peso = 2;
            for (i = 11; i >= 0; i--) {
                num = (int) (cnpj.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10) {
                    peso = 2;
                }
            }

            r = sm % 11;
            dig13 = (r == 0) || (r == 1) ? '0' : (char) ((11 - r) + 48);

            sm = 0;
            peso = 2;
            for (i = 12; i >= 0; i--) {
                num = (int) (cnpj.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10) {
                    peso = 2;
                }
            }
            r = sm % 11;
            dig14 = (r == 0) || (r == 1) ? '0' : (char) ((11 - r) + 48);

            return dig13 == cnpj.charAt(12) && dig14 == cnpj.charAt(13);

        } catch (InputMismatchException erro) {
            return false;
        }
    }
}
