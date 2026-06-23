package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.exception.RegraNegocioException;
import br.edu.ifsudestemg.sb.model.entity.StatusReserva;
import br.edu.ifsudestemg.sb.model.repository.StatusReservaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StatusReservaService {

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

    @Transactional
    public StatusReserva salvar(StatusReserva statusReserva) {

        validar(statusReserva);

        String nomeNormalizado = statusReserva.getNome().trim().toLowerCase();
        statusReserva.setNome(nomeNormalizado);

        Optional<StatusReserva> existente = repository.findByNomeIgnoreCase(nomeNormalizado);

        if (existente.isPresent()
                && statusReserva.getId() != null
                && !existente.get().getId().equals(statusReserva.getId())) {

            throw new RegraNegocioException("Já existe um status com esse nome");
        }

        return repository.save(statusReserva);
    }

    @Transactional
    public void excluir(StatusReserva statusReserva) {
        Objects.requireNonNull(statusReserva.getId());
        repository.delete(statusReserva);
    }

    public void validar(StatusReserva statusReserva) {
        if (statusReserva == null) {
            throw new RegraNegocioException("Status da reserva inválido");
        }
        if (statusReserva.getNome() == null || statusReserva.getNome().trim().equals("") || statusReserva.getNome().length() > 50) {
            throw new RegraNegocioException("Nome inválido");
        }
    }
}