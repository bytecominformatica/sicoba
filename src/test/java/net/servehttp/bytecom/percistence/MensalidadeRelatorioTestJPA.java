package net.servehttp.bytecom.percistence;

import net.servehttp.bytecom.facede.CreateEntityManager;
import net.servehttp.bytecom.persistence.jpa.financeiro.MensalidadeRelatorioJPA;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import java.time.LocalDate;

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
        mensalidadeRelatorioJPA.buscarPorData(inicio, fim, buscarPorDataOcorrencia);
    }

    @Test
    public void deveriaBuscasMensalidadesPorStatusEDataVencimento() {
        LocalDate inicio = LocalDate.now();
        LocalDate fim = LocalDate.now();
        boolean buscarPorDataOcorrencia = false;
        mensalidadeRelatorioJPA.buscarPorData(inicio, fim, buscarPorDataOcorrencia);
    }

    @AfterClass
    public static void closeUp() {
        em.close();
    }

}
