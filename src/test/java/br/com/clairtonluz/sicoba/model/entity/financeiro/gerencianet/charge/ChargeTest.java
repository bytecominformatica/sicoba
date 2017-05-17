package br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge;

import br.com.clairtonluz.sicoba.util.DateUtil;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;

/**
 * Created by clairton on 16/05/17.
 */
public class ChargeTest {

    @Test
    public void testValidarPagamentoAtrazadoSemJuros() throws Exception {
        Charge charge = new Charge();
        charge.setValue(35d);
        charge.setPaidValue(35d);
        LocalDate expireAt = LocalDate.of(2017, Month.APRIL, 5);
        LocalDate paidAt = LocalDate.of(2017, Month.APRIL, 6);
        charge.setExpireAt(DateUtil.toDate(expireAt));
        charge.setPaidAt(DateUtil.toDate(paidAt));

        String status = charge.verifyPayment();

        Assert.assertEquals("pagamento deveria ser inválido", Charge.LATE_PAYMENT_WITHOUT_INTEREST, status);
    }

    @Test
    public void testValidarPagamentoAtrazadoPorCausaDoDomingo() throws Exception {
        Charge charge = new Charge();
        charge.setValue(35d);
        charge.setPaidValue(35d);
        LocalDate expireAt = LocalDate.of(2017, Month.APRIL, 9);
        LocalDate paidAt = LocalDate.of(2017, Month.APRIL, 10);
        charge.setExpireAt(DateUtil.toDate(expireAt));
        charge.setPaidAt(DateUtil.toDate(paidAt));

        String status = charge.verifyPayment();

        Assert.assertEquals("pagamento atrazado por causa do domingo não deveria ser inválido", Charge.VALID_PAYMENT, status);
    }

    @Test
    public void testValidarPagamentoAtrazadoPorCausaDoSabado() throws Exception {
        Charge charge = new Charge();
        charge.setValue(35d);
        charge.setPaidValue(35d);
        LocalDate expireAt = LocalDate.of(2017, Month.APRIL, 8);
        LocalDate paidAt = LocalDate.of(2017, Month.APRIL, 10);
        charge.setExpireAt(DateUtil.toDate(expireAt));
        charge.setPaidAt(DateUtil.toDate(paidAt));

        String status = charge.verifyPayment();

        Assert.assertEquals("pagamento atrazado por causa sábado não deveria ser inválido", Charge.VALID_PAYMENT, status);
    }

    @Test
    public void testValidarPagamentoAtrazadoPorCausaDoSexta() throws Exception {
        Charge charge = new Charge();
        charge.setValue(35d);
        charge.setPaidValue(35d);
        LocalDate expireAt = LocalDate.of(2017, Month.APRIL, 7);
        LocalDate paidAt = LocalDate.of(2017, Month.APRIL, 10);
        charge.setExpireAt(DateUtil.toDate(expireAt));
        charge.setPaidAt(DateUtil.toDate(paidAt));

        String status = charge.verifyPayment();
        Assert.assertEquals("pagamento atrazado por causa sexta deveria cobrar juros", Charge.LATE_PAYMENT_WITHOUT_INTEREST, status);
    }

    @Test
    public void testValidarPagamentoEfetuadoNoDiaDoVencimento() throws Exception {
        Charge charge = new Charge();
        charge.setValue(35d);
        charge.setPaidValue(35d);
        LocalDate expireAt = LocalDate.of(2017, Month.APRIL, 5);
        LocalDate paidAt = LocalDate.of(2017, Month.APRIL, 5);
        charge.setExpireAt(DateUtil.toDate(expireAt));
        charge.setPaidAt(DateUtil.toDate(paidAt));

        String status = charge.verifyPayment();
        Assert.assertEquals("pagamento no dia do vencimento não deveria ser inválido", Charge.VALID_PAYMENT, status);
    }

    @Test
    public void testValidarPagamentoEfetuadoNoDiaDoVencimentoComDesconto() throws Exception {
        Charge charge = new Charge();
        charge.setValue(35d);
        charge.setPaidValue(30d);
        charge.setDiscount(5d);
        LocalDate expireAt = LocalDate.of(2017, Month.APRIL, 5);
        LocalDate paidAt = LocalDate.of(2017, Month.APRIL, 5);
        charge.setExpireAt(DateUtil.toDate(expireAt));
        charge.setPaidAt(DateUtil.toDate(paidAt));

        String status = charge.verifyPayment();
        Assert.assertEquals("pagamento no dia do vencimento com desconto não deveria ser inválido", Charge.VALID_PAYMENT, status);
    }

    @Test
    public void testValidarPagamentoEfetuadoAntesDoVencimento() throws Exception {
        Charge charge = new Charge();
        charge.setValue(35d);
        charge.setPaidValue(35d);
        LocalDate expireAt = LocalDate.of(2017, Month.APRIL, 5);
        LocalDate paidAt = LocalDate.of(2017, Month.APRIL, 4);
        charge.setExpireAt(DateUtil.toDate(expireAt));
        charge.setPaidAt(DateUtil.toDate(paidAt));

        String status = charge.verifyPayment();
        Assert.assertEquals("pagamento adiantado não deveria ser inválido", Charge.VALID_PAYMENT, status);
    }

    @Test
    public void testValidarPagamentoEfetuadoAntesDoVencimentoComDesconto() throws Exception {
        Charge charge = new Charge();
        charge.setValue(35d);
        charge.setPaidValue(30d);
        charge.setDiscount(5d);
        LocalDate expireAt = LocalDate.of(2017, Month.APRIL, 5);
        LocalDate paidAt = LocalDate.of(2017, Month.APRIL, 4);
        charge.setExpireAt(DateUtil.toDate(expireAt));
        charge.setPaidAt(DateUtil.toDate(paidAt));

        String status = charge.verifyPayment();
        Assert.assertEquals("pagamento adiantado com desconto não deveria ser inválido", Charge.VALID_PAYMENT, status);
    }

    @Test
    public void testValidarDescontoMaiorDoQueDeveria() throws Exception {
        Charge charge = new Charge();
        charge.setValue(35d);
        charge.setPaidValue(29d);
        charge.setDiscount(5d);
        LocalDate expireAt = LocalDate.of(2017, Month.APRIL, 5);
        LocalDate paidAt = LocalDate.of(2017, Month.APRIL, 4);
        charge.setExpireAt(DateUtil.toDate(expireAt));
        charge.setPaidAt(DateUtil.toDate(paidAt));

        String status = charge.verifyPayment();
        Assert.assertEquals("pagamento com desconto maior do que foi definido deveria ser inválido", Charge.INVALID_DISCOUNT_PAYMENT, status);
    }

}