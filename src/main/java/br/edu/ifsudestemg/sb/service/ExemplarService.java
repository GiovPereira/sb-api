package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.model.entity.Exemplar;
import br.edu.ifsudestemg.sb.model.entity.Genero;
import br.edu.ifsudestemg.sb.model.repository.ExemplarRepository;
import br.edu.ifsudestemg.sb.model.repository.GeneroRepository;

import java.util.List;
import java.util.Optional;

public class ExemplarService {
    private ExemplarRepository repository;

    public ExemplarService(ExemplarRepository repository) {
        this.repository = repository;
    }

    public List<Exemplar> getExemplares() {
        return repository.findAll();
    }

    public Optional<Exemplar> getExemplarById(Long id) {
        return repository.findById(id);
    }
}
