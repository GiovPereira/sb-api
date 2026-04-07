package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.model.entity.ValorDiarioMulta;
import br.edu.ifsudestemg.sb.model.repository.ValorDiarioMultaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ValorDiarioMultaService
{

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
}
