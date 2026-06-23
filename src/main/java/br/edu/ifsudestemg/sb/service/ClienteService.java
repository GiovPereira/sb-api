package br.edu.ifsudestemg.sb.service;

import br.edu.ifsudestemg.sb.api.dto.ClienteDTO;
import br.edu.ifsudestemg.sb.api.dto.EmprestimoDTO;
import br.edu.ifsudestemg.sb.api.dto.ReservaDTO;
import br.edu.ifsudestemg.sb.exception.RegraNegocioException;
import br.edu.ifsudestemg.sb.model.entity.Cliente;
import br.edu.ifsudestemg.sb.model.repository.ClienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService
{

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository)
    {
        this.repository = repository;
    }

    public List<Cliente> getClientes()
    {
        return repository.findAll();
    }

    public List<ClienteDTO> getClientesDTO()
    {
        return repository.findAll().stream()
                .map(this::createDTO)
                .collect(Collectors.toList());
    }

    public Optional<Cliente> getClienteById(Long id)
    {
        return repository.findById(id);
    }

    public ClienteDTO createDTO(Cliente cliente)
    {
        ModelMapper modelMapper = new ModelMapper();
        ClienteDTO dto = modelMapper.map(cliente, ClienteDTO.class);

        if (cliente.getReservas() != null) {
            dto.setReservas(cliente.getReservas().stream()
                    .map(reserva -> {
                        ReservaDTO reservaDTO = new ReservaDTO();
                        return reservaDTO.createDTO(reserva);
                    })
                    .collect(Collectors.toList()));
        }

        if (cliente.getEmprestimos() != null) {
            dto.setEmprestimos(cliente.getEmprestimos().stream()
                    .map(EmprestimoDTO::create)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    @Transactional
    public Cliente salvar(Cliente cliente) {

        validar(cliente);

        String cpfNormalizado = cliente.getCpf().replaceAll("\\D", "");
        cliente.setCpf(cpfNormalizado);

        Optional<Cliente> existente = repository.findByCpf(cpfNormalizado);

        if (existente.isPresent()
                && cliente.getId() != null
                && !existente.get().getId().equals(cliente.getId())) {

            throw new RegraNegocioException("Já existe um cliente com esse CPF");
        }

        String emailNormalizado = cliente.getEmail() != null
                ? cliente.getEmail().trim().toLowerCase()
                : null;

        cliente.setEmail(emailNormalizado);

        String telefoneNormalizado = cliente.getTelefone() != null
                ? cliente.getTelefone().replaceAll("\\D", "")
                : null;

        cliente.setTelefone(telefoneNormalizado);

        String cepNormalizado = cliente.getCep() != null
                ? cliente.getCep().replaceAll("\\D", "")
                : null;

        cliente.setCep(cepNormalizado);

        return repository.save(cliente);
    }

    @Transactional
    public void excluir(Cliente cliente)
    {
        Objects.requireNonNull(cliente.getId());

        try {
            repository.delete(cliente);
            repository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new RegraNegocioException("Não foi possível excluir o cliente.");
        }
    }

    public void validar(Cliente cliente)
    {
        if (cliente == null) {
            throw new RegraNegocioException("Cliente inválido.");
        }

        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new RegraNegocioException("Informe o nome");
        }

        if (cliente.getCpf() == null || cliente.getCpf().trim().isEmpty()) {
            throw new RegraNegocioException("Informe um cpf");
        }

        if (!cliente.getCpf().matches("\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
            throw new RegraNegocioException("Digite um CPF válido");
        }

        if (cliente.getEmail() != null && !cliente.getEmail().trim().isEmpty()) {
            if (!cliente.getEmail().trim().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+(\\.[A-Za-z]{2,})+$")) {
                throw new RegraNegocioException("E-mail inválido");
            }
        }

        if (cliente.getTelefone()!= null && !cliente.getTelefone().trim().isEmpty()) {

            String rawTelefone = cliente.getTelefone();

            if (!rawTelefone.matches("^(\\(\\d{2}\\)\\s?(\\d{4}-\\d{4}|\\d{5}-\\d{4}))$")) {
                throw new RegraNegocioException("Telefone inválido");
            }

            String telefone = rawTelefone.replaceAll("\\D", "");

            boolean fixo = telefone.matches("\\d{10}");
            boolean celular = telefone.matches("\\d{11}") && telefone.charAt(2) == '9';

            if (!(fixo || celular)) {
                throw new RegraNegocioException("Telefone inválido");
            }
        }

        if (cliente.getCep() != null && !cliente.getCep().trim().isEmpty()) {
            if (!cliente.getCep().matches("\\d{8}|\\d{5}-\\d{3}")) {
                throw new RegraNegocioException("Digite um CEP válido");
            }
        }




//        if (cliente.getDataNascimento() == null) {
//            throw new RegraNegocioException("Data de nascimento inválida");
//        }
//
//        if (cliente.getTelefone() == null || cliente.getTelefone().trim().isEmpty()) {
//            throw new RegraNegocioException("Telefone inválido");
//        }
//
//        if (cliente.getCep() == null || cliente.getCep().trim().isEmpty()) {
////            throw new RegraNegocioException("Cep inválido");
////        }
//
//        if (cliente.getLogradouro() == null || cliente.getLogradouro().trim().isEmpty()) {
//            throw new RegraNegocioException("Logradouro inválido");
//        }
//
//        if (cliente.getNumero() == null) {
//            throw new RegraNegocioException("Número inválido");
//        }
//
//        if (cliente.getBairro() == null || cliente.getBairro().trim().isEmpty()) {
//            throw new RegraNegocioException("Bairro inválido");
//        }
//
//        if (cliente.getCidade() == null || cliente.getCidade().trim().isEmpty()) {
//            throw new RegraNegocioException("Cidade inválida");
//        }
//
//        if (cliente.getEstado() == null || cliente.getEstado().trim().isEmpty()) {
//            throw new RegraNegocioException("Estado inválido");
//        }
//
//        if (cliente.getTelefone() != null && !cliente.getTelefone().trim().isEmpty()) {
//
//            String telefone = cliente.getTelefone()
//                    .replaceAll("\\s+", "")
//                    .replaceAll("[^0-9+]", "");
//
//            if (telefone.startsWith("+")) {
//                telefone = telefone.substring(1);
//            }
//
//            telefone = telefone.replaceAll("\\D", "");
//
//            if (!telefone.matches("^[1-9]\\d{9,14}$")) {
//                throw new RegraNegocioException("Digite um telefone válido");
//            }
//
//            telefone = "+" + telefone;
//
//            cliente.setTelefone(telefone);
//        }
    }
}