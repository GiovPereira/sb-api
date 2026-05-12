package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.exception.RegraNegocioException;
import br.edu.ifsudestemg.sb.model.entity.*;
import br.edu.ifsudestemg.sb.model.repository.ObraGeneroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Service
public class ObraGeneroService
{

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
    public ObraGenero salvar(ObraGenero ObraGenero) {
        validar(ObraGenero);
        return repository.save(ObraGenero);
    }

    @Transactional
    public void excluir(ObraGenero ObraGenero) {
        Objects.requireNonNull(ObraGenero.getId());
        repository.delete(ObraGenero);
    }

    public void validar(ObraGenero ObraGenero) {

//        if (ObraGenero.getNome() == null || ObraGenero.getNome().trim().equals("")) {
//            throw new RegraNegocioException("Nome inválido");
//        }
//        if (ObraGenero.getCurso() == null || ObraGenero.getCurso().getId() == null || ObraGenero.getCurso().getId() == 0) {
//            throw new RegraNegocioException("Curso inválido");
//        }
    }
}
