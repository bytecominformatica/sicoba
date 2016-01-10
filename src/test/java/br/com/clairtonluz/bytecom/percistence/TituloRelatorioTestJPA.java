package br.com.clairtonluz.bytecom.percistence;

import java.time.LocalDate;

import javax.persistence.EntityManager;

import br.com.clairtonluz.bytecom.util.CreateEntityManager;
import br.com.clairtonluz.bytecom.model.jpa.financeiro.TituloRelatorioJPA;
import br.com.clairtonluz.bytecom.model.jpa.entity.financeiro.StatusTitulo;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TituloRelatorioTestJPA {
  private static EntityManager em;
  private static TituloRelatorioJPA tituloRelatorioJPA;

  @BeforeClass
  public static void setUp() {
    em = CreateEntityManager.INSTANCE.getEntityManager();
    tituloRelatorioJPA = new TituloRelatorioJPA();
    tituloRelatorioJPA.setEntityManager(em);
  }

  @Test
  public void deveriaBuscasTitulosPorStatusEDataOcorrencia() {
    LocalDate inicio = LocalDate.now();
    LocalDate fim = LocalDate.now();
    boolean buscarPorDataOcorrencia = true;
    tituloRelatorioJPA.buscarPorDataStatus(inicio, fim, StatusTitulo.PAGO_NO_BOLETO, buscarPorDataOcorrencia);
  }

  @Test
  public void deveriaBuscasTitulosPorStatusEDataVencimento() {
    LocalDate inicio = LocalDate.now();
    LocalDate fim = LocalDate.now();
    boolean buscarPorDataOcorrencia = false;
    tituloRelatorioJPA.buscarPorDataStatus(inicio, fim, StatusTitulo.PAGO_NO_BOLETO, buscarPorDataOcorrencia);
  }
 
  @AfterClass
  public static void closeUp() {
    em.close();
  }

}
