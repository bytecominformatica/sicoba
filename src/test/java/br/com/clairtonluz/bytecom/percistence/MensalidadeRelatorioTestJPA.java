package br.com.clairtonluz.bytecom.percistence;

import java.time.LocalDate;

import javax.persistence.EntityManager;

import br.com.clairtonluz.bytecom.util.CreateEntityManager;
import br.com.clairtonluz.bytecom.model.jpa.financeiro.MensalidadeRelatorioJPA;
import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.StatusMensalidade;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class MensalidadeRelatorioTestJPA {
  private static EntityManager em;
  private static MensalidadeRelatorioJPA mensalidadeRelatorioJPA;

  @BeforeClass
  public static void setUp() {
    em = CreateEntityManager.INSTANCE.getEntityManager();
    mensalidadeRelatorioJPA = new MensalidadeRelatorioJPA();
    mensalidadeRelatorioJPA.setEntityManager(em);
  }

  @Test
  public void deveriaBuscasMensalidadesPorStatusEDataOcorrencia() {
    LocalDate inicio = LocalDate.now();
    LocalDate fim = LocalDate.now();
    boolean buscarPorDataOcorrencia = true;
    mensalidadeRelatorioJPA.buscarPorDataStatus(inicio, fim, StatusMensalidade.PAGO_NO_BOLETO, buscarPorDataOcorrencia);
  }

  @Test
  public void deveriaBuscasMensalidadesPorStatusEDataVencimento() {
    LocalDate inicio = LocalDate.now();
    LocalDate fim = LocalDate.now();
    boolean buscarPorDataOcorrencia = false;
    mensalidadeRelatorioJPA.buscarPorDataStatus(inicio, fim, StatusMensalidade.PAGO_NO_BOLETO, buscarPorDataOcorrencia);
  }
 
  @AfterClass
  public static void closeUp() {
    em.close();
  }

}
