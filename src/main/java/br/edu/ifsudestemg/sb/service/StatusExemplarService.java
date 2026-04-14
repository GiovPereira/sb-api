package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.model.entity.Secao;
import br.edu.ifsudestemg.sb.model.entity.StatusExemplar;
import br.edu.ifsudestemg.sb.model.repository.StatusExemplarRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StatusExemplarService
{

    private StatusExemplarRepository repository;

    public StatusExemplarService(StatusExemplarRepository repository) {
        this.repository = repository;
    }

    public List<StatusExemplar> getStatusExemplares() {
        return repository.findAll();
    }

    public Optional<StatusExemplar> getStatusExemplarById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public StatusExemplar salvar(StatusExemplar StatusExemplar) {
        validar(StatusExemplar);
        return repository.save(StatusExemplar);
    }

    @Transactional
    public void excluir(StatusExemplar StatusExemplar) {
        Objects.requireNonNull(StatusExemplar.getId());
        repository.delete(StatusExemplar);
    }

    public void validar(StatusExemplar StatusExemplar) {

//        if (StatusExemplar.getNome() == null || StatusExemplar.getNome().trim().equals("")) {
//            throw new RegraNegocioException("Nome inválido");
//        }
//        if (StatusExemplar.getCurso() == null || StatusExemplar.getCurso().getId() == null || StatusExemplar.getCurso().getId() == 0) {
//            throw new RegraNegocioException("Curso inválido");
//        }
    }
}
