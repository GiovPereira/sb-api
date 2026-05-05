package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.model.entity.Cliente;
import br.edu.ifsudestemg.sb.model.entity.*;
import br.edu.ifsudestemg.sb.model.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
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

    @Transactional
    public Cliente salvar(Cliente cliente) {
        validar(cliente);
        return repository.save(cliente);
    }

    @Transactional
    public void excluir(Cliente cliente) {
        Objects.requireNonNull(cliente.getId());
        repository.delete(cliente);
    }

    public void validar(Cliente cliente) {
        if (cliente.getNome() == null || cliente.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (cliente.getCpf() == null || cliente.getCpf().trim().equals("")) {
            throw new RegraNegocioException("Cpf inválido");
        }
        if (cliente.getDataNascimento() == null) {
            throw new RegraNegocioException("Data de nascimento inválida");
        }
//        if (cliente.getEmail() == null || cliente.getEmail().trim().equals("")) {
//            throw new RegraNegocioException("Email inválido");
//        }
        if (cliente.getTelefone() == null || cliente.getTelefone().trim().equals("")) {
            throw new RegraNegocioException("Telefone inválido");
        }
        if (cliente.getCep() == null || cliente.getCep().trim().equals("")) {
            throw new RegraNegocioException("Cep inválido");
        }
        if (cliente.getLogradouro() == null || cliente.getLogradouro().trim().equals("")) {
            throw new RegraNegocioException("Logradouro inválido");
        }
        if (cliente.getNumero() == null) {
            throw new RegraNegocioException("Número inválido");
        }
        if (cliente.getBairro() == null || cliente.getBairro().trim().equals("")) {
            throw new RegraNegocioException("Bairro inválido");
        }
        if (cliente.getCidade() == null || cliente.getCidade().trim().equals("")) {
            throw new RegraNegocioException("Cidade inválida");
        }
        if (cliente.getEstado() == null || cliente.getEstado().trim().equals("")) {
            throw new RegraNegocioException("Estado inválido");
        }
    }

}
