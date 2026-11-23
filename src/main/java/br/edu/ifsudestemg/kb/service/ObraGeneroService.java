package br.edu.ifsudestemg.kb.service;

import br.edu.ifsudestemg.kb.model.entity.*;
import br.edu.ifsudestemg.kb.model.repository.ObraGeneroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ObraGeneroService {
    private ObraGeneroRepository repository;

    public ObraGeneroService(ObraGeneroRepository repository) {
        this.repository = repository;
    }

    public List<ObraGenero> getObraGeneros() {
        return repository.findAll();
    }

    public Optional<ObraGenero> getObraGeneroById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public ObraGenero salvar(ObraGenero obraGenero) {
        validar(obraGenero);
        return repository.save(obraGenero);
    }

    @Transactional
    public void excluir(ObraGenero obraGenero) {
        Objects.requireNonNull(obraGenero.getId());
        repository.delete(obraGenero);
    }

    public void validar(ObraGenero obraGenero) {
//        if (ObraGenero.getNome() == null || ObraGenero.getNome().trim().equals("")) {
//            throw new RegraNegocioException("Nome inválido");
//        }
//        if (ObraGenero.getCurso() == null || ObraGenero.getCurso().getId() == null || ObraGenero.getCurso().getId() == 0) {
//            throw new RegraNegocioException("Curso inválido");
//        }
    }

}
