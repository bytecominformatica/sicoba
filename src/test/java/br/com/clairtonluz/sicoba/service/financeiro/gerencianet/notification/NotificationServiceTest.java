package br.com.clairtonluz.sicoba.service.financeiro.gerencianet.notification;

import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.GerencianetAccount;
import br.com.clairtonluz.sicoba.model.entity.financeiro.gerencianet.charge.Charge;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class NotificationServiceTest {

    private NotificationService notificationService;

    @Before
    public void setUp() {
        notificationService = new NotificationService(null, null,
                null, null, null, null);
    }

    @Test
    public void getChargeDetails() {
        Charge charge = new Charge();
        charge.setId(1);
        charge.setUrl("http://example.com.br");
        charge.setDescription("Teste");
        charge.setValue(123d);
        charge.setDiscount(13d);
        charge.setPaidValue(110d);
        charge.setExpireAt(LocalDate.of(2018, 1, 20));
        charge.setPaidAt(LocalDate.of(2018, 1, 15).atStartOfDay());
        GerencianetAccount account = new GerencianetAccount();
        account.setName("Conta de teste");
        charge.setGerencianetAccount(account);

        String expected = "Cobrança: " +
                charge.getId() + "\n" +
                "Boleto: " + charge.getUrl() + "\n" +
                "Descrição: " + charge.getDescription() + "\n" +
                "Valor: " + ": " + charge.getValue() + "\n" +
                "Desconto: " + charge.getDiscount() + "\n" +
                "Valor pago: " + charge.getPaidValue() + "\n" +
                "Vencimento: " + charge.getExpireAt() + "\n" +
                "Data Pagamento: " + charge.getPaidAt() + "\n" +
                "Conta: " + charge.getGerencianetAccount().getName() + "\n";

        String actual = notificationService.getChargeDetails(charge);
        System.out.println(actual);

        Assert.assertEquals(expected, actual);

    }
}