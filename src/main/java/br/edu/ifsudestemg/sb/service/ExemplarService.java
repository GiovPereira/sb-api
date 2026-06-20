package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.exception.RegraNegocioException;
import br.edu.ifsudestemg.sb.model.entity.Exemplar;
import br.edu.ifsudestemg.sb.model.entity.StatusExemplar;
import br.edu.ifsudestemg.sb.model.repository.ExemplarRepository;
import br.edu.ifsudestemg.sb.model.repository.StatusExemplarRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExemplarService {

    private final ExemplarRepository repository;
    private final StatusExemplarRepository statusRepository;

    public ExemplarService(ExemplarRepository repository, StatusExemplarRepository statusRepository) {
        this.repository = repository;
        this.statusRepository = statusRepository;
    }

    @Transactional
    public Exemplar alterarStatus(Long idExemplar, Long idNovoStatus) {

        Exemplar exemplar = repository.findById(idExemplar)
                .orElseThrow(() -> new RegraNegocioException("Exemplar não encontrado."));

        StatusExemplar novoStatus = statusRepository.findById(idNovoStatus)
                .orElseThrow(() -> new RegraNegocioException("Status não encontrado."));

        StatusExemplar atual = exemplar.getStatusExemplar();

        validarTransicao(atual.getId(), novoStatus.getId());

        exemplar.setStatusExemplar(novoStatus);

        return repository.save(exemplar);
    }

    private void validarTransicao(Long atual, Long novo) {

        // Em Posse e Em Atraso não podem ser alterados manualmente
        if (atual == 3L || atual == 4L) {
            throw new RegraNegocioException("Exemplar em posse não pode ter status alterado manualmente.");
        }

        // Regras específicas
        if (novo == 1L) { // Disponível
            return;
        }

        if (novo == 5L || novo == 6L) { // Extraviado / Danificado

            if (atual == 2L || atual == 1L) {
                return;
            }

            throw new RegraNegocioException("Transição de status inválida.");
        }

        if (novo == 2L) { // Reservado
            if (atual == 1L) return;
        }

        throw new RegraNegocioException("Transição de status inválida.");
    }
}