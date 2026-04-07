package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.model.entity.StatusReserva;
import br.edu.ifsudestemg.sb.model.repository.StatusReservaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusReservaService
{

    private StatusReservaRepository repository;

    public StatusReservaService(StatusReservaRepository repository) {
        this.repository = repository;
    }

    public List<StatusReserva> getStatusReservas() {
        return repository.findAll();
    }

    public Optional<StatusReserva> getStatusReservaById(Long id) {
        return repository.findById(id);
    }
}
