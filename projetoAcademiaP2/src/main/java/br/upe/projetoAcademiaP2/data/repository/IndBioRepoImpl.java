package br.upe.projetoAcademiaP2.data.repository;

import br.upe.projetoAcademiaP2.data.beans.IndicadorBiomedico;
import br.upe.projetoAcademiaP2.data.repository.interfaces.IIndBioRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class IndBioRepoImpl implements IIndBioRepository {

    private final Map<String, IndicadorBiomedico> indicadoresBiomedicos = new HashMap<>();

    @Override
    public IndicadorBiomedico save(IndicadorBiomedico indicadorBiomedico) {

        if (indicadorBiomedico.getId() == null || indicadorBiomedico.getId().isEmpty()) {
            System.err.println("Não é possível salvar Indicador Biomédico sem um ID.");
            return null;
        }
        indicadoresBiomedicos.put(indicadorBiomedico.getId(), indicadorBiomedico);
        return indicadorBiomedico;
    }

    @Override
    public Optional<IndicadorBiomedico> findById(String id) {
        return Optional.ofNullable(indicadoresBiomedicos.get(id));
    }

    @Override
    public List<IndicadorBiomedico> findAll() {
        return new ArrayList<>(indicadoresBiomedicos.values());
    }

    @Override
    public IndicadorBiomedico update(IndicadorBiomedico indicadorBiomedico) {

        if (indicadorBiomedico.getId() == null || !indicadoresBiomedicos.containsKey(indicadorBiomedico.getId())) {
            System.err.println("Indicador Biomédico com ID " + indicadorBiomedico.getId() + " não encontrado para atualização.");
            return null;
        }
        indicadoresBiomedicos.put(indicadorBiomedico.getId(), indicadorBiomedico); // Sobrescreve o indicador existente
        return indicadorBiomedico;
    }

    @Override
    public void deleteById(String id) {
        indicadoresBiomedicos.remove(id);
    }

}

