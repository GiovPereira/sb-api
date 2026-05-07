package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.model.entity.DuracaoPadraoEmprestimo;
import br.edu.ifsudestemg.sb.model.repository.DuracaoPadraoEmprestimoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DuracaoPadraoEmprestimoService {

    private final DuracaoPadraoEmprestimoRepository repository;

    public DuracaoPadraoEmprestimoService(DuracaoPadraoEmprestimoRepository repository) {
        this.repository = repository;
    }

    public List<DuracaoPadraoEmprestimo> listarTodos() {
        return repository.findAll();
    }

    public Optional<DuracaoPadraoEmprestimo> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public DuracaoPadraoEmprestimo obterAtual() {
        return repository.findTopByOrderByDataHoraAlteracaoDesc()
                .orElseThrow(() -> new RuntimeException("Nenhuma duração cadastrada"));
    }

    @Transactional
    public DuracaoPadraoEmprestimo salvar(DuracaoPadraoEmprestimo duracao) {
        validar(duracao);
        return repository.save(duracao);
    }

    @Transactional
    public void excluir(DuracaoPadraoEmprestimo duracao) {

        Objects.requireNonNull(duracao.getId(), "ID não pode ser nulo");
        repository.delete(duracao);
    }

    private void validar(DuracaoPadraoEmprestimo duracao) {

        if (duracao == null) {
            throw new RuntimeException("Duração não pode ser nula");
        }

        if (duracao.getDiasUteis() == null) {
            throw new RuntimeException("Dias úteis é obrigatório");
        }

        if (duracao.getDiasUteis() <= 0) {
            throw new RuntimeException("Dias úteis deve ser maior que zero");
        }
    }
}