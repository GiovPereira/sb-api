package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.exception.RegraNegocioException;
import br.edu.ifsudestemg.sb.model.entity.*;
import br.edu.ifsudestemg.sb.model.repository.EmprestimoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


public class EmprestimoService {
    private EmprestimoRepository repository;

    public EmprestimoService(EmprestimoRepository repository) {
        this.repository = repository;
    }

    public List<Emprestimo> getEmprestimos() {
        return repository.findAll();
    }

    public Optional<Emprestimo> getEmprestimoById(Long id) {
        return repository.findById(id);
    }
}
