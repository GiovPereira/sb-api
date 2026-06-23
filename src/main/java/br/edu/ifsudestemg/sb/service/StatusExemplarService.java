package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.exception.RegraNegocioException;
import br.edu.ifsudestemg.sb.model.entity.StatusExemplar;
import br.edu.ifsudestemg.sb.model.repository.StatusExemplarRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StatusExemplarService {

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

    @Transactional
    public StatusExemplar salvar(StatusExemplar statusExemplar) {

        validar(statusExemplar);

        String nomeNormalizado = statusExemplar.getNome().trim().toLowerCase();
        statusExemplar.setNome(nomeNormalizado);

        Optional<StatusExemplar> existente = repository.findByNomeIgnoreCase(nomeNormalizado);

        if (existente.isPresent()
                && statusExemplar.getId() != null
                && !existente.get().getId().equals(statusExemplar.getId())) {

            throw new RegraNegocioException("Já existe um status com esse nome");
        }

        return repository.save(statusExemplar);
    }

    @Transactional
    public void excluir(StatusExemplar statusExemplar) {
        Objects.requireNonNull(statusExemplar.getId());
        repository.delete(statusExemplar);
    }

    public void validar(StatusExemplar statusExemplar) {
        if (statusExemplar == null) {
            throw new RegraNegocioException("Status exemplar inválido");
        }
        if (statusExemplar.getNome() == null || statusExemplar.getNome().trim().equals("") || statusExemplar.getNome().length() > 50) {
            throw new RegraNegocioException("Nome inválido");
        }
    }
}