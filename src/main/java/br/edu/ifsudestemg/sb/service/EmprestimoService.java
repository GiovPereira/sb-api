package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.model.entity.Emprestimo;
import br.edu.ifsudestemg.sb.model.repository.EmprestimoRepository;

import java.util.List;
import java.util.Optional;

public class EmprestimoService {
    private EmprestimoRepository repository;

    public EmprestimoService(EmprestimoRepository repository) {
        this.repository = repository;
    }

    public List<Emprestimo> getEmprestimos() {
        return repository.findAll();
    }

    public Optional<Emprestimo> getAlunoById(Long id) {
        return repository.findById(id);
    }
}
