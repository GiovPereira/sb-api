package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.model.entity.Secao;
import br.edu.ifsudestemg.sb.model.repository.SecaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SecaoService
{

    private SecaoRepository repository;

    public SecaoService(SecaoRepository repository) {
        this.repository = repository;
    }

    public List<Secao> getSecoes() {
        return repository.findAll();
    }

    public Optional<Secao> getSecaoById(Long id) {
        return repository.findById(id);
    }
}
