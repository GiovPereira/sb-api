package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.model.entity.Cliente;
import br.edu.ifsudestemg.sb.model.entity.DuracaoPadraoEmprestimo;
import br.edu.ifsudestemg.sb.model.repository.ClienteRepository;
import br.edu.ifsudestemg.sb.model.repository.DuracaoPadraoEmprestimoRepository;

import java.util.List;
import java.util.Optional;

public class ClienteService {
    private ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public List<Cliente> getClientes() {
        return repository.findAll();
    }

    public Optional<Cliente> getClienteById(Long id) {
        return repository.findById(id);
    }
}
