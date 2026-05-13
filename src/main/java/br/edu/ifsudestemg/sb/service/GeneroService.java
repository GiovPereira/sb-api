package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.exception.RegraNegocioException;
import br.edu.ifsudestemg.sb.model.entity.*;
import br.edu.ifsudestemg.sb.model.repository.GeneroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class GeneroService {
    private GeneroRepository repository;

    public GeneroService(GeneroRepository repository) {
        this.repository = repository;
    }

    public List<Genero> getGeneros() {
        return repository.findAll();
    }

    public Optional<Genero> getGeneroById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Genero salvar(Genero genero) {
        validar(genero);
        return repository.save(genero);
    }

    @Transactional
    public void excluir(Genero genero) {
        Objects.requireNonNull(genero.getId());
        repository.delete(genero);
    }

    public void validar(Genero genero) {
        if (genero.getNome() == null || genero.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
    }

}
