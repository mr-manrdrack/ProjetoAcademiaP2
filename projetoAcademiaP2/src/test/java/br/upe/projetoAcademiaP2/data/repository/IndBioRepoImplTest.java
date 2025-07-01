package br.upe.projetoAcademiaP2.data.repository;

import br.upe.projetoAcademiaP2.data.beans.IndicadorBiomedico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IndBioRepoImplTest {

    private IndBioRepoImpl indBioRepository;

    @BeforeEach
    public void setUp() {
        indBioRepository = new IndBioRepoImpl();
    }

    @Test
    public void testSaveIndicadorBiomedico() {
        IndicadorBiomedico indicador = new IndicadorBiomedico(
                "teste@email.com", 70.0, 1.75, 15.0, 40.0, 22.86, new Date()
        );

        boolean result = indBioRepository.save(indicador);

        assertTrue(result);

        List<IndicadorBiomedico> allIndicators = indBioRepository.findAll();
        assertNotNull(allIndicators);
        assertFalse(allIndicators.isEmpty());
        assertEquals(1, allIndicators.size());
        assertEquals(indicador, allIndicators.get(0));
    }

    @Test
    public void testSaveNullIndicadorBiomedico() {
        boolean result = indBioRepository.save(null);

        assertFalse(result);

        List<IndicadorBiomedico> allIndicators = indBioRepository.findAll();
        assertTrue(allIndicators.isEmpty());
    }

    @Test
    public void testFindAllEmpty() {
        List<IndicadorBiomedico> allIndicators = indBioRepository.findAll();

        assertNotNull(allIndicators);
        assertTrue(allIndicators.isEmpty());
    }

    @Test
    public void testFindAllWithMultipleIndicators() {
        IndicadorBiomedico indicador1 = new IndicadorBiomedico(
                "teste1@email.com", 70.0, 1.75, 15.0, 40.0, 22.86, new Date()
        );
        IndicadorBiomedico indicador2 = new IndicadorBiomedico(
                "teste2@email.com", 75.0, 1.80, 18.0, 42.0, 23.15, new Date()
        );
        IndicadorBiomedico indicador3 = new IndicadorBiomedico(
                "teste3@email.com", 65.0, 1.70, 12.0, 38.0, 22.49, new Date()
        );

        indBioRepository.save(indicador1);
        indBioRepository.save(indicador2);
        indBioRepository.save(indicador3);

        List<IndicadorBiomedico> allIndicators = indBioRepository.findAll();

        assertNotNull(allIndicators);
        assertEquals(3, allIndicators.size());
        assertTrue(allIndicators.contains(indicador1));
        assertTrue(allIndicators.contains(indicador2));
        assertTrue(allIndicators.contains(indicador3));
    }
}
