package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.model.entity.DuracaoPadraoEmprestimo;
import br.edu.ifsudestemg.sb.model.entity.Editora;
import br.edu.ifsudestemg.sb.model.repository.DuracaoPadraoEmprestimoRepository;
import br.edu.ifsudestemg.sb.model.repository.EditoraRepository;

import java.util.List;
import java.util.Optional;

public class DuracaoPadraoEmprestimoService {
    private DuracaoPadraoEmprestimoRepository repository;

    public DuracaoPadraoEmprestimoService(DuracaoPadraoEmprestimoRepository repository) {
        this.repository = repository;
    }

    public List<DuracaoPadraoEmprestimo> getDuracaoPadraoEmprestimos() {
        return repository.findAll();
    }

    public Optional<DuracaoPadraoEmprestimo> getDuracaoPadraoEmprestimoById(Long id) {
        return repository.findById(id);
    }
}
