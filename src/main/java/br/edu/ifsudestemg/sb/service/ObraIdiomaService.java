package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.model.entity.ObraIdioma;
import br.edu.ifsudestemg.sb.model.repository.ObraIdiomaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ObraIdiomaService
{

    private ObraIdiomaRepository repository;

    public ObraIdiomaService(ObraIdiomaRepository repository) {
        this.repository = repository;
    }

    public List<ObraIdioma> getObraIdiomas() {
        return repository.findAll();
    }

    public Optional<ObraIdioma> getObraIdiomaById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public ObraIdioma salvar(ObraIdioma ObraIdioma) {
        validar(ObraIdioma);
        return repository.save(ObraIdioma);
    }

    @Transactional
    public void excluir(ObraIdioma ObraIdioma) {
        Objects.requireNonNull(ObraIdioma.getId());
        repository.delete(ObraIdioma);
    }

    public void validar(ObraIdioma ObraIdioma) {

//        if (ObraIdioma.getNome() == null || ObraIdioma.getNome().trim().equals("")) {
//            throw new RegraNegocioException("Nome inválido");
//        }
//        if (ObraIdioma.getCurso() == null || ObraIdioma.getCurso().getId() == null || ObraIdioma.getCurso().getId() == 0) {
//            throw new RegraNegocioException("Curso inválido");
//        }
    }
}
