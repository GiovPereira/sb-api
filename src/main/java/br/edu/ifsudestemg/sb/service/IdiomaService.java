package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.model.entity.Idioma;
import br.edu.ifsudestemg.sb.model.repository.IdiomaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class IdiomaService {

    private final IdiomaRepository repository;

    public IdiomaService(IdiomaRepository repository) {
        this.repository = repository;
    }

    public List<Idioma> listarTodos() {
        return repository.findAll();
    }

    public Optional<Idioma> buscarPorId(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Idioma salvar(Idioma idioma) {
        validar(idioma);

        String nomeNormalizado = idioma.getNome().trim().toUpperCase();
        idioma.setNome(nomeNormalizado);

        if (repository.existsByNomeIgnoreCase(nomeNormalizado)) {
            throw new RuntimeException("Já existe um idioma com esse nome");
        }

        return repository.save(idioma);
    }

    @Transactional
    public void excluir(Idioma idioma) {

        Objects.requireNonNull(idioma.getId(), "ID não pode ser nulo");
        repository.delete(idioma);
    }

    private void validar(Idioma idioma) {

        if (idioma == null) {
            throw new RuntimeException("Idioma não pode ser nulo");
        }

        if (idioma.getNome() == null || idioma.getNome().trim().isEmpty()) {
            throw new RuntimeException("Nome é obrigatório");
        }

        if (idioma.getNome().length() > 50) {
            throw new RuntimeException("Nome muito longo");
        }
    }
}