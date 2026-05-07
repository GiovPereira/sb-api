package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.model.entity.ValorDiarioMulta;
import br.edu.ifsudestemg.sb.model.repository.ValorDiarioMultaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ValorDiarioMultaService {

    private final ValorDiarioMultaRepository repository;

    public ValorDiarioMultaService(ValorDiarioMultaRepository repository) {
        this.repository = repository;
    }

    public List<ValorDiarioMulta> listarTodos() {
        return repository.findAll();
    }

    public Optional<ValorDiarioMulta> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public ValorDiarioMulta salvar(ValorDiarioMulta valorDiarioMulta) {
        validar(valorDiarioMulta);
        return repository.save(valorDiarioMulta);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public ValorDiarioMulta obterValorAtual() {
        return repository.findTopByOrderByDataHoraAlteracaoDesc()
                .orElseThrow(() -> new RuntimeException("Nenhum valor de multa cadastrado"));
    }

    private void validar(ValorDiarioMulta valorDiarioMulta) {

        if (valorDiarioMulta == null) {
            throw new RuntimeException("Objeto não pode ser nulo");
        }

        if (valorDiarioMulta.getValorDia() == null) {
            throw new RuntimeException("Valor da multa é obrigatório");
        }

        if (valorDiarioMulta.getValorDia().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Valor da multa deve ser maior que zero");
        }
    }
}