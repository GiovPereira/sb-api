package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.exception.RegraNegocioException;
import br.edu.ifsudestemg.sb.model.entity.*;
import br.edu.ifsudestemg.sb.model.repository.AutorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AutorService {
    private AutorRepository repository;

    public AutorService(AutorRepository repository) {
        this.repository = repository;
    }

    public List<Autor> getAutores() {
        return repository.findAll();
    }

    public Optional<Autor> getAutorById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Autor salvar(Autor autor) {

        validar(autor);

        String nomeNormalizado = autor.getNome().trim().toLowerCase();
        autor.setNome(nomeNormalizado);

        Optional<Autor> existente = repository.findByNomeIgnoreCase(nomeNormalizado);

        if (existente.isPresent()
                && autor.getId() != null
                && !existente.get().getId().equals(autor.getId())) {

            throw new RegraNegocioException("Já existe um autor com esse nome");
        }

        return repository.save(autor);
    }
    
    @Transactional
    public void excluir(Autor autor) {
        Objects.requireNonNull(autor.getId());
        repository.delete(autor);
    }

    public void validar(Autor autor) {
        if (autor.getNome() == null || autor.getNome().trim().equals("")) {
            throw new RegraNegocioException("Informe o nome");
        }
    }
    
}
