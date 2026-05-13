package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.model.entity.DuracaoPadraoEmprestimo;
import br.edu.ifsudestemg.sb.exception.RegraNegocioException;
import br.edu.ifsudestemg.sb.model.repository.DuracaoPadraoEmprestimoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DuracaoPadraoEmprestimoService {

    private DuracaoPadraoEmprestimoRepository repository;

    public DuracaoPadraoEmprestimoService(DuracaoPadraoEmprestimoRepository repository) {
        this.repository = repository;
    }
    public List<DuracaoPadraoEmprestimo> getDuracaoPadraoEmprestimos() {
        return repository.findAll();
    }

    public Optional<DuracaoPadraoEmprestimo>
    getDuracaoPadraoEmprestimoById(Long id) {
        return repository.findById(id);
    }

    public DuracaoPadraoEmprestimo obterAtual() {
        return repository.findTopByOrderByDataHoraAlteracaoDesc()
                .orElseThrow(() ->
                        new RegraNegocioException(
                                "Nenhuma duração cadastrada"));
    }

    @Transactional
    public DuracaoPadraoEmprestimo salvar(
            DuracaoPadraoEmprestimo duracao) {

        validar(duracao);

        return repository.save(duracao);
    }

    @Transactional
    public void excluir(DuracaoPadraoEmprestimo duracao) {
        Objects.requireNonNull(duracao.getId());
        repository.delete(duracao);
    }

    public void validar(DuracaoPadraoEmprestimo duracao) {

        if (duracao == null) {
            throw new RegraNegocioException("Duração inválida");
        }

        if (duracao.getDiasUteis() == null) {
            throw new RegraNegocioException(
                    "Dias úteis inválido");
        }

        if (duracao.getDiasUteis() <= 0) {
            throw new RegraNegocioException(
                    "Dias úteis inválido");
        }
    }
}