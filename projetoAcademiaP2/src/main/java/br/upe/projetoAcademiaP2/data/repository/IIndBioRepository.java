package br.upe.projetoAcademiaP2.data.repository;

import br.upe.projetoAcademiaP2.data.beans.IndicadorBio;
import java.util.List;
import java.util.Optional;

public interface IIndBioRepository {
    IndicadorBiomedico save(IndicadorBiomedico indicadorBiomedico);

    Optional<IndicadorBiomedico> findById(String id);
    List<IndicadorBiomedico> findAll();
    IndicadorBiomedico update(IndicadorBiomedico indicadorBiomedico);
    void deleteById(String id);
}

