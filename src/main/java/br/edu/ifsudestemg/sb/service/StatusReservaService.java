package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.model.entity.StatusReserva;
import br.edu.ifsudestemg.sb.model.repository.StatusReservaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusReservaService {

    private final StatusReservaRepository repository;

    public StatusReservaService(StatusReservaRepository repository) {
        this.repository = repository;
    }

    public List<StatusReserva> listarTodos() {
        return repository.findAll();
    }

    public Optional<StatusReserva> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public StatusReserva salvar(StatusReserva statusReserva) {
        validar(statusReserva);
        return repository.save(statusReserva);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    private void validar(StatusReserva statusReserva) {

        if (statusReserva == null) {
            throw new RuntimeException("StatusReserva não pode ser nulo");
        }

        if (statusReserva.getNome() == null || statusReserva.getNome().trim().isEmpty()) {
            throw new RuntimeException("Nome do status é obrigatório");
        }

        if (statusReserva.getNome().length() > 50) {
            throw new RuntimeException("Nome do status muito longo");
        }
    }
}