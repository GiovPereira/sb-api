package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.model.entity.Editora;
import br.edu.ifsudestemg.sb.model.entity.Idioma;
import br.edu.ifsudestemg.sb.model.repository.IdiomaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    public Idioma salvar(Idioma Idioma) {
        validar(Idioma);
        return repository.save(Idioma);
    }

    @Transactional
    public void excluir(Idioma Idioma) {
        Objects.requireNonNull(Idioma.getId());
        repository.delete(Idioma);
    }

    public void validar(Idioma Idioma) {

//        if (Idioma.getNome() == null || Idioma.getNome().trim().equals("")) {
//            throw new RegraNegocioException("Nome inválido");
//        }
//        if (Idioma.getCurso() == null || Idioma.getCurso().getId() == null || Idioma.getCurso().getId() == 0) {
//            throw new RegraNegocioException("Curso inválido");
//        }
    }
}
