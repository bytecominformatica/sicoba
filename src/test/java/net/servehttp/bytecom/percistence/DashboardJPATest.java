package net.servehttp.bytecom.percistence;

import javax.persistence.EntityManager;

import net.servehttp.bytecom.facede.CreateEntityManager;
import net.servehttp.bytecom.model.jpa.dashboard.DashboadJPA;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class DashboardJPATest {
  private static EntityManager em;
  private static DashboadJPA dashboadJPA;

  @BeforeClass
  public static void setUp() {
    em = CreateEntityManager.INSTANCE.getEntityManager();
    dashboadJPA = new DashboadJPA();
    dashboadJPA.setEntityManager(em);
  }

  @Test
  public void deveriaBuscasQuantidadeDeInstalacoesDoMes() {
    dashboadJPA.getQuantidadeInstalacoesDoMes();
  }
 
  @Test
  public void deveriaBuscasFaturamentoDoMes() {
    dashboadJPA.getFaturamentoDoMes();
  }

  @Test
  public void deveriaBuscasFaturamentoPrevistoDoMes() {
    dashboadJPA.getFaturamentoPrevistoDoMes();
  }
  
  @Test
  public void deveriaBuscasMensalidadesEmAtraso() {
    dashboadJPA.getMensalidadesEmAtraso();
  }
  
  @AfterClass
  public static void closeUp() {
    em.close();
  }

}
