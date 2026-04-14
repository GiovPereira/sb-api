package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.model.entity.Multa;
import br.edu.ifsudestemg.sb.model.entity.Reserva;
import br.edu.ifsudestemg.sb.model.repository.ReservaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReservaService
{

    private ReservaRepository repository;

    public ReservaService(ReservaRepository repository) {
        this.repository = repository;
    }

    public List<Reserva> getReservas() {
        return repository.findAll();
    }

    public Optional<Reserva> getReservaById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Reserva salvar(Reserva Reserva) {
        validar(Reserva);
        return repository.save(Reserva);
    }

    @Transactional
    public void excluir(Reserva Reserva) {
        Objects.requireNonNull(Reserva.getId());
        repository.delete(Reserva);
    }

    public void validar(Reserva Reserva) {

//        if (Reserva.getNome() == null || Reserva.getNome().trim().equals("")) {
//            throw new RegraNegocioException("Nome inválido");
//        }
//        if (Reserva.getCurso() == null || Reserva.getCurso().getId() == null || Reserva.getCurso().getId() == 0) {
//            throw new RegraNegocioException("Curso inválido");
//        }
    }
}
