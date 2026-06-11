package br.edu.ifsudestemg.sb.api.dto;

import br.edu.ifsudestemg.sb.model.entity.Cliente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

    private Long id;

    private String nome;

    private String cpf;

    private LocalDate dataNascimento;

    private String email;

    private String telefone;

    private String cep;

    private String logradouro;

    private Integer numero;

    private String complemento;

    private String bairro;

    private String cidade;

    private String estado;

    private List<ReservaDTO> reservas;

    private List<EmprestimoDTO> emprestimos;

    public static ClienteDTO create(Cliente cliente) {

        ModelMapper modelMapper = new ModelMapper();

        ClienteDTO dto =
                modelMapper.map(
                        cliente,
                        ClienteDTO.class
                );

        if (cliente.getReservas() != null) {

            dto.setReservas(

                    cliente.getReservas()

                            .stream()

                            .map(reserva -> {

                                ReservaDTO reservaDTO =
                                        new ReservaDTO();

                                return reservaDTO.createDTO(reserva);

                            })

                            .collect(Collectors.toList())
            );
        }

        if (cliente.getEmprestimos() != null) {

            dto.setEmprestimos(

                    cliente.getEmprestimos()

                            .stream()

                            .map(EmprestimoDTO::create)

                            .collect(Collectors.toList())
            );
        }

        return dto;
    }

}