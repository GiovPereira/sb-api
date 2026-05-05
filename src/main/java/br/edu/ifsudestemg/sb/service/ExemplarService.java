package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.exception.RegraNegocioException;
import br.edu.ifsudestemg.sb.model.entity.*;
import br.edu.ifsudestemg.sb.model.repository.ExemplarRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
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
