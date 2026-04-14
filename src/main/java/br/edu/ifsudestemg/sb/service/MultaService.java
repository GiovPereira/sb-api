package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.model.entity.Autor;
import br.edu.ifsudestemg.sb.model.entity.Idioma;
import br.edu.ifsudestemg.sb.model.entity.Multa;
import br.edu.ifsudestemg.sb.model.repository.AutorRepository;
import br.edu.ifsudestemg.sb.model.repository.MultaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MultaService {
    private MultaRepository repository;

    public MultaService(MultaRepository repository) {
        this.repository = repository;
    }

    public List<Multa> getMultas() {
        return repository.findAll();
    }

    public Optional<Multa> getMultaById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Multa salvar(Multa Multa) {
        validar(Multa);
        return repository.save(Multa);
    }

    @Transactional
    public void excluir(Multa Multa) {
        Objects.requireNonNull(Multa.getId());
        repository.delete(Multa);
    }

    public void validar(Multa Multa) {

//        if (Multa.getNome() == null || Multa.getNome().trim().equals("")) {
//            throw new RegraNegocioException("Nome inválido");
//        }
//        if (Multa.getCurso() == null || Multa.getCurso().getId() == null || Multa.getCurso().getId() == 0) {
//            throw new RegraNegocioException("Curso inválido");
//        }
    }
}
