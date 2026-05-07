package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.model.entity.StatusExemplar;
import br.edu.ifsudestemg.sb.model.repository.StatusExemplarRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StatusExemplarService {

    private final StatusExemplarRepository repository;

    public StatusExemplarService(StatusExemplarRepository repository) {
        this.repository = repository;
    }

    public List<StatusExemplar> listarTodos() {
        return repository.findAll();
    }

    public Optional<StatusExemplar> buscarPorId(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public StatusExemplar salvar(StatusExemplar statusExemplar) {
        validar(statusExemplar);

        String nomeNormalizado = statusExemplar.getNome().trim().toUpperCase();
        statusExemplar.setNome(nomeNormalizado);

        if (repository.existsByNomeIgnoreCase(nomeNormalizado)) {
            throw new RuntimeException("Já existe um status com esse nome");
        }

        return repository.save(statusExemplar);
    }

    @Transactional
    public void excluir(StatusExemplar statusExemplar) {

        Objects.requireNonNull(statusExemplar.getId(), "ID não pode ser nulo");
        repository.delete(statusExemplar);
    }

    private void validar(StatusExemplar statusExemplar) {

        if (statusExemplar == null) {
            throw new RuntimeException("StatusExemplar não pode ser nulo");
        }

        if (statusExemplar.getNome() == null || statusExemplar.getNome().trim().isEmpty()) {
            throw new RuntimeException("Nome é obrigatório");
        }

        if (statusExemplar.getNome().length() > 50) {
            throw new RuntimeException("Nome muito longo");
        }
    }
}