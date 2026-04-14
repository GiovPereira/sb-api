package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.exception.RegraNegocioException;
import br.edu.ifsudestemg.sb.model.entity.DuracaoPadraoEmprestimo;
import br.edu.ifsudestemg.sb.model.entity.Editora;
import br.edu.ifsudestemg.sb.model.repository.DuracaoPadraoEmprestimoRepository;
import br.edu.ifsudestemg.sb.model.repository.EditoraRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
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

    @Transactional
    public DuracaoPadraoEmprestimo salvar(DuracaoPadraoEmprestimo DuracaoPadraoEmprestimo) {
        validar(DuracaoPadraoEmprestimo);
        return repository.save(DuracaoPadraoEmprestimo);
    }

    @Transactional
    public void excluir(DuracaoPadraoEmprestimo DuracaoPadraoEmprestimo) {
        Objects.requireNonNull(DuracaoPadraoEmprestimo.getId());
        repository.delete(DuracaoPadraoEmprestimo);
    }

    public void validar(DuracaoPadraoEmprestimo DuracaoPadraoEmprestimo) {

//        if (DuracaoPadraoEmprestimo.getNome() == null || DuracaoPadraoEmprestimo.getNome().trim().equals("")) {
//            throw new RegraNegocioException("Nome inválido");
//        }
//        if (DuracaoPadraoEmprestimo.getCurso() == null || DuracaoPadraoEmprestimo.getCurso().getId() == null || DuracaoPadraoEmprestimo.getCurso().getId() == 0) {
//            throw new RegraNegocioException("Curso inválido");
//        }
    }
}
