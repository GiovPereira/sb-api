package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.model.entity.Editora;
import br.edu.ifsudestemg.sb.model.entity.Emprestimo;
import br.edu.ifsudestemg.sb.model.repository.EditoraRepository;
import br.edu.ifsudestemg.sb.model.repository.EmprestimoRepository;

import java.util.List;
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
}
