package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.exception.RegraNegocioException;
import br.edu.ifsudestemg.sb.model.entity.*;
import br.edu.ifsudestemg.sb.model.repository.EmprestimoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
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

    @Transactional
    public Emprestimo salvar(Emprestimo emprestimo) {
        validar(emprestimo);
        return repository.save(emprestimo);
    }

    @Transactional
    public void excluir(Emprestimo emprestimo) {
        Objects.requireNonNull(emprestimo.getId());
        repository.delete(emprestimo);
    }

    public void validar(Emprestimo emprestimo) {
//        if (emprestimo.getNome() == null || emprestimo.getNome().trim().equals("")) {
//            throw new RegraNegocioException("Nome inválido");
//        }
    }

}
