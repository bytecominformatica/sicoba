package net.servehttp.bytecom.percistence;

import java.util.Date;

import javax.persistence.EntityManager;

import net.servehttp.bytecom.facede.CreateEntityManager;
import net.servehttp.bytecom.persistence.entity.Mensalidade;
import net.servehttp.bytecom.persistence.relatorios.MensalidadeRelatorioJPA;
import net.servehttp.bytecom.util.DateUtil;

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
    Date inicio = DateUtil.INSTANCE.getHoje();
    Date fim = DateUtil.INSTANCE.getHoje();
    boolean buscarPorDataOcorrencia = true;
    mensalidadeRelatorioJPA.buscarPorDataStatus(inicio, fim, Mensalidade.PAGA, buscarPorDataOcorrencia);
  }

  @Test
  public void deveriaBuscasMensalidadesPorStatusEDataVencimento() {
    Date inicio = DateUtil.INSTANCE.getHoje();
    Date fim = DateUtil.INSTANCE.getHoje();
    boolean buscarPorDataOcorrencia = false;
    mensalidadeRelatorioJPA.buscarPorDataStatus(inicio, fim, Mensalidade.PAGA, buscarPorDataOcorrencia);
  }
 
  @AfterClass
  public static void closeUp() {
    em.close();
  }

}
