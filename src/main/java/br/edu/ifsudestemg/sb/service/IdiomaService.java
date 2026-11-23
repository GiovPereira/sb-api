package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.model.entity.Idioma;
import br.edu.ifsudestemg.sb.exception.RegraNegocioException;
import br.edu.ifsudestemg.sb.model.repository.IdiomaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class IdiomaService {

    private IdiomaRepository repository;

    public IdiomaService(IdiomaRepository repository) {
        this.repository = repository;
    }

    public List<Idioma> getIdiomas() {
        return repository.findAll();
    }

    public Optional<Idioma> getIdiomaById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Idioma salvar(Idioma autor) {

        validar(autor);

        String nomeNormalizado = autor.getNome().trim().toLowerCase();
        autor.setNome(nomeNormalizado);

        Optional<Idioma> existente = repository.findByNomeIgnoreCase(nomeNormalizado);

        if (existente.isPresent()
                && autor.getId() != null
                && !existente.get().getId().equals(autor.getId())) {

            throw new RegraNegocioException("Já existe um autor com esse nome");
        }

        return repository.save(autor);
    }

    @Transactional
    public void excluir(Idioma idioma) {
        Objects.requireNonNull(idioma.getId());
        repository.delete(idioma);
    }

    public void validar(Idioma idioma) {
        if (idioma == null) {
            throw new RegraNegocioException("Idioma inválido");
        }
        if (idioma.getNome() == null || idioma.getNome().trim().equals("") || idioma.getNome().length() > 50) {
            throw new RegraNegocioException("Nome inválido");
        }
    }
}