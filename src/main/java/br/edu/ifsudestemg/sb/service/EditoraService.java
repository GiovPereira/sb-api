package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.model.entity.DuracaoPadraoEmprestimo;
import br.edu.ifsudestemg.sb.model.entity.Editora;
import br.edu.ifsudestemg.sb.model.entity.Emprestimo;
import br.edu.ifsudestemg.sb.model.repository.EditoraRepository;
import br.edu.ifsudestemg.sb.model.repository.EmprestimoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class EditoraService {
    private EditoraRepository repository;

    public EditoraService(EditoraRepository repository) {
        this.repository = repository;
    }

    public List<Editora> getEditoras() {
        return repository.findAll();
    }

    public Optional<Editora> getEditoraById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Editora salvar(Editora Editora) {
        validar(Editora);
        return repository.save(Editora);
    }

    @Transactional
    public void excluir(Editora Editora) {
        Objects.requireNonNull(Editora.getId());
        repository.delete(Editora);
    }

    public void validar(Editora Editora) {

//        if (Editora.getNome() == null || Editora.getNome().trim().equals("")) {
//            throw new RegraNegocioException("Nome inválido");
//        }
//        if (Editora.getCurso() == null || Editora.getCurso().getId() == null || Editora.getCurso().getId() == 0) {
//            throw new RegraNegocioException("Curso inválido");
//        }
    }
}
