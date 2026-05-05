package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.model.entity.ObraAutor;
import br.edu.ifsudestemg.sb.model.repository.ObraAutorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ObraAutorService
{

    private ObraAutorRepository repository;

    public ObraAutorService(ObraAutorRepository repository) {
        this.repository = repository;
    }

    public List<ObraAutor> getObraAutores() {
        return repository.findAll();
    }

    public Optional<ObraAutor> getObraAutorById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public ObraAutor salvar(ObraAutor ObraAutor) {
        validar(ObraAutor);
        return repository.save(ObraAutor);
    }

    @Transactional
    public void excluir(ObraAutor ObraAutor) {
        Objects.requireNonNull(ObraAutor.getId());
        repository.delete(ObraAutor);
    }

    public void validar(ObraAutor ObraAutor) {

//        if (ObraAutor.getNome() == null || ObraAutor.getNome().trim().equals("")) {
//            throw new RegraNegocioException("Nome inválido");
//        }
//        if (ObraAutor.getCurso() == null || ObraAutor.getCurso().getId() == null || ObraAutor.getCurso().getId() == 0) {
//            throw new RegraNegocioException("Curso inválido");
//        }
    }
}
