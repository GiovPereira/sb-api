package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.model.entity.Idioma;
import br.edu.ifsudestemg.sb.model.repository.IdiomaRepository;

import java.util.List;
import java.util.Optional;

public class IdiomaService {
    private IdiomaRepository repository;

    public IdiomaService(IdiomaRepository repository) {
        this.repository = repository;
    }

    public List<Idioma> getIdiomas() {
        return repository.findAll();
    }

    public Optional<Idioma> getIdiomaById(Long id) {
        return repository.findById(id);
    }
}
