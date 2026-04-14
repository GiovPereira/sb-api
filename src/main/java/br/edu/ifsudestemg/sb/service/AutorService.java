package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.model.entity.Autor;
import br.edu.ifsudestemg.sb.model.entity.Cliente;
import br.edu.ifsudestemg.sb.model.repository.AutorRepository;
import br.edu.ifsudestemg.sb.model.repository.ClienteRepository;

import java.util.List;
import java.util.Optional;

public class AutorService {
    private AutorRepository repository;

    public AutorService(AutorRepository repository) {
        this.repository = repository;
    }

    public List<Autor> getAutores() {
        return repository.findAll();
    }

    public Optional<Autor> getAutorById(Long id) {
        return repository.findById(id);
    }
}
