package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.exception.RegraNegocioException;
import br.edu.ifsudestemg.sb.model.entity.Reserva;
import br.edu.ifsudestemg.sb.model.repository.ReservaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReservaService {

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
    public Reserva salvar(Reserva reserva) {

        validar(reserva);

        reserva.setDataReserva(LocalDate.now());

        if (repository.existsByClienteIdAndObraId(
                reserva.getCliente().getId(),
                reserva.getObra().getId())) {

            throw new RegraNegocioException(
                    "Cliente já possui reserva para esta obra");
        }

        Integer posicao =
                repository.countByObra(reserva.getObra()) + 1;

        reserva.setPosicaoFila(posicao);

        return repository.save(reserva);
    }

    @Transactional
    public void excluir(Reserva reserva) {
        Objects.requireNonNull(reserva.getId());
        repository.delete(reserva);
    }

    public void validar(Reserva reserva) {

        if (reserva == null) {
            throw new RegraNegocioException("Reserva inválida");
        }

        if (reserva.getCliente() == null ||
                reserva.getCliente().getId() == null) {

            throw new RegraNegocioException("Cliente inválido");
        }

        if (reserva.getObra() == null ||
                reserva.getObra().getId() == null) {

            throw new RegraNegocioException("Obra inválida");
        }

        if (reserva.getStatus() == null) {
            throw new RegraNegocioException("Status inválido");
        }
    }
}