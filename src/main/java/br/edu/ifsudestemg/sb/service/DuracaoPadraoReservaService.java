package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.exception.RegraNegocioException;
import br.edu.ifsudestemg.sb.model.entity.DuracaoPadraoReserva;
import br.edu.ifsudestemg.sb.model.repository.DuracaoPadraoReservaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DuracaoPadraoReservaService
{

    private DuracaoPadraoReservaRepository repository;

    public DuracaoPadraoReservaService(DuracaoPadraoReservaRepository repository) {
        this.repository = repository;
    }
    public List<DuracaoPadraoReserva> getDuracaoPadraoReservas() {
        return repository.findAll();
    }

    public Optional<DuracaoPadraoReserva>
    getDuracaoPadraoReservaById(Long id) {
        return repository.findById(id);
    }

    public DuracaoPadraoReserva obterAtual() {
        return repository.findTopByOrderByDataHoraAlteracaoDesc()
                .orElseThrow(() ->
                        new RegraNegocioException(
                                "Nenhuma duração cadastrada"));
    }

    @Transactional
    public DuracaoPadraoReserva salvar(
            DuracaoPadraoReserva duracao) {

        validar(duracao);

        return repository.save(duracao);
    }

    @Transactional
    public void excluir(DuracaoPadraoReserva duracao) {
        Objects.requireNonNull(duracao.getId());
        repository.delete(duracao);
    }

    public void validar(DuracaoPadraoReserva duracao) {

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