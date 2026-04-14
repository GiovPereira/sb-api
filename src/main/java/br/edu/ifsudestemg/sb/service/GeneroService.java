package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.model.entity.Genero;
import br.edu.ifsudestemg.sb.model.entity.Idioma;
import br.edu.ifsudestemg.sb.model.repository.GeneroRepository;
import br.edu.ifsudestemg.sb.model.repository.IdiomaRepository;

import java.util.List;
import java.util.Optional;

public class GeneroService {
    private GeneroRepository repository;

    public GeneroService(GeneroRepository repository) {
        this.repository = repository;
    }

    public List<Genero> getGeneros() {
        return repository.findAll();
    }

    public Optional<Genero> getGeneroById(Long id) {
        return repository.findById(id);
    }
}
