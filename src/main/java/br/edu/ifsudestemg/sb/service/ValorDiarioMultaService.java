package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.exception.RegraNegocioException;
import br.edu.ifsudestemg.sb.model.entity.ValorDiarioMulta;
import br.edu.ifsudestemg.sb.model.repository.ValorDiarioMultaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ValorDiarioMultaService {

    private ValorDiarioMultaRepository repository;

    public ValorDiarioMultaService(ValorDiarioMultaRepository repository) {
        this.repository = repository;
    }

    public List<ValorDiarioMulta> getValorDiarioMultas() {
        return repository.findAll();
    }

    public Optional<ValorDiarioMulta> getValorDiarioMultaById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public ValorDiarioMulta salvar(ValorDiarioMulta valorDiarioMulta) {
        validar(valorDiarioMulta);
        return repository.save(valorDiarioMulta);
    }

    @Transactional
    public void excluir(ValorDiarioMulta valorDiarioMulta) {
        Objects.requireNonNull(valorDiarioMulta.getId());
        repository.delete(valorDiarioMulta);
    }

    public ValorDiarioMulta obterValorAtual() {
        return repository.findTopByOrderByIdDesc()
                .orElseThrow(() ->
                        new RegraNegocioException("Nenhum valor de multa cadastrado"));
    }

    public void validar(ValorDiarioMulta valorDiarioMulta) {

        if (valorDiarioMulta == null) {
            throw new RegraNegocioException("Objeto inválido");
        }

        if (valorDiarioMulta.getValorDia() == null) {
            throw new RegraNegocioException("Valor da multa inválido");
        }

        if (valorDiarioMulta.getValorDia().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraNegocioException("Valor da multa inválido");
        }
    }
}