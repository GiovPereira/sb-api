package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.model.entity.Multa;
import br.edu.ifsudestemg.sb.model.entity.Reserva;
import br.edu.ifsudestemg.sb.model.entity.Secao;
import br.edu.ifsudestemg.sb.model.repository.SecaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SecaoService
{

    private SecaoRepository repository;

    public SecaoService(SecaoRepository repository) {
        this.repository = repository;
    }

    public List<Secao> getSecoes() {
        return repository.findAll();
    }

    public Optional<Secao> getSecaoById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Secao salvar(Secao Secao) {
        validar(Secao);
        return repository.save(Secao);
    }

    @Transactional
    public void excluir(Secao Secao) {
        Objects.requireNonNull(Secao.getId());
        repository.delete(Secao);
    }

    public void validar(Secao Secao) {

//        if (Secao.getNome() == null || Secao.getNome().trim().equals("")) {
//            throw new RegraNegocioException("Nome inválido");
//        }
//        if (Secao.getCurso() == null || Secao.getCurso().getId() == null || Secao.getCurso().getId() == 0) {
//            throw new RegraNegocioException("Curso inválido");
//        }
    }
}
