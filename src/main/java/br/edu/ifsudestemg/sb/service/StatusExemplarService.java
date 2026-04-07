package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.model.entity.StatusExemplar;
import br.edu.ifsudestemg.sb.model.repository.StatusExemplarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusExemplarService
{

    private StatusExemplarRepository repository;

    public StatusExemplarService(StatusExemplarRepository repository) {
        this.repository = repository;
    }

    public List<StatusExemplar> getStatusExemplares() {
        return repository.findAll();
    }

    public Optional<StatusExemplar> getStatusExemplarById(Long id) {
        return repository.findById(id);
    }
}
