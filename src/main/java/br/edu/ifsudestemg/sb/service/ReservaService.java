package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.model.entity.Obra;
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

    private final ReservaRepository repository;

    public ReservaService(ReservaRepository repository) {
        this.repository = repository;
    }

    public List<Reserva> listarTodos() {
        return repository.findAll();
    }

    public Optional<Reserva> buscarPorId(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Reserva salvar(Reserva reserva) {
        validar(reserva);

        reserva.setDataReserva(LocalDate.now());

        if (repository.existsByClienteIdAndObraId(
                reserva.getCliente().getId(),
                reserva.getObra().getId())) {

            throw new RuntimeException("Cliente já possui reserva para esta obra");
        }

        Integer posicao = repository.countByObra(reserva.getObra()) + 1;
        reserva.setPosicaoFila(posicao);

        return repository.save(reserva);
    }

    @Transactional
    public void excluir(Reserva reserva) {

        Objects.requireNonNull(reserva.getId(), "ID não pode ser nulo");

        repository.delete(reserva);
    }

    private void validar(Reserva reserva) {

        if (reserva == null) {
            throw new RuntimeException("Reserva não pode ser nula");
        }

        if (reserva.getCliente() == null || reserva.getCliente().getId() == null) {
            throw new RuntimeException("Cliente é obrigatório");
        }

        if (reserva.getObra() == null || reserva.getObra().getId() == null) {
            throw new RuntimeException("Obra é obrigatória");
        }

        if (reserva.getStatus() == null) {
            throw new RuntimeException("Status é obrigatório");
        }
    }
}