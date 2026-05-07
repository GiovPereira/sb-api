package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.model.entity.Editora;
import br.edu.ifsudestemg.sb.model.repository.EditoraRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EditoraService {

    private final EditoraRepository repository;

    public EditoraService(EditoraRepository repository) {
        this.repository = repository;
    }

    public List<Editora> listarTodos() {
        return repository.findAll();
    }

    public Optional<Editora> buscarPorId(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Editora salvar(Editora editora) {
        validar(editora);

        String nomeNormalizado = editora.getNome().trim().toUpperCase();
        editora.setNome(nomeNormalizado);

        if (repository.existsByNomeIgnoreCase(nomeNormalizado)) {
            throw new RuntimeException("Já existe uma editora com esse nome");
        }

        return repository.save(editora);
    }

    @Transactional
    public void excluir(Editora editora) {

        Objects.requireNonNull(editora.getId(), "ID não pode ser nulo");
        repository.delete(editora);
    }

    private void validar(Editora editora) {

        if (editora == null) {
            throw new RuntimeException("Editora não pode ser nula");
        }

        if (editora.getNome() == null || editora.getNome().trim().isEmpty()) {
            throw new RuntimeException("Nome é obrigatório");
        }

        if (editora.getNome().length() > 100) {
            throw new RuntimeException("Nome muito longo");
        }
    }
}