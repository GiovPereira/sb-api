package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.exception.RegraNegocioException;
import br.edu.ifsudestemg.sb.model.entity.*;
import br.edu.ifsudestemg.sb.model.repository.ExemplarRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
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

    @Transactional
    public Exemplar salvar(Exemplar exemplar) {
        validar(exemplar);
        return repository.save(exemplar);
    }

    @Transactional
    public void excluir(Exemplar exemplar) {
        Objects.requireNonNull(exemplar.getId());
        repository.delete(exemplar);
    }

    public void validar(Exemplar exemplar) {
//        if (exemplar.getDataAquisicao() == null || exemplar.getDataAquisicao().trim().equals("")) {
//            throw new RegraNegocioException("Nome inválido");
//        }
    }

}
