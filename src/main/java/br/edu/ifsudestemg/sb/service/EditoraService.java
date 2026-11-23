package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.model.entity.Editora;
import br.edu.ifsudestemg.sb.exception.RegraNegocioException;
import br.edu.ifsudestemg.sb.model.repository.EditoraRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
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
    public Editora salvar(Editora editora) {

        validar(editora);

        String nomeNormalizado = editora.getNome().trim().toLowerCase();
        editora.setNome(nomeNormalizado);

        Optional<Editora> existente = repository.findByNomeIgnoreCase(nomeNormalizado);

        if (existente.isPresent()
                && editora.getId() != null
                && !existente.get().getId().equals(editora.getId())) {

            throw new RegraNegocioException("Já existe um editora com esse nome");
        }

        return repository.save(editora);
    }

    @Transactional
    public void excluir(Editora editora) {
        Objects.requireNonNull(editora.getId());
        repository.delete(editora);
    }

    public void validar(Editora editora) {

        if (editora == null) {
            throw new RegraNegocioException("Editora inválida");
        }

        if (editora.getNome() == null || editora.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
    }
}