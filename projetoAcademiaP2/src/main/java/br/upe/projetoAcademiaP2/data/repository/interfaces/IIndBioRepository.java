package br.upe.projetoAcademiaP2.data.repository.interfaces;

import br.upe.projetoAcademiaP2.data.beans.IndicadorBiomedico;
import java.util.List;
import java.util.Optional;

public interface IIndBioRepository {
    boolean save(IndicadorBiomedico indicadorBiomedico);
    List<IndicadorBiomedico> findAll();
}

