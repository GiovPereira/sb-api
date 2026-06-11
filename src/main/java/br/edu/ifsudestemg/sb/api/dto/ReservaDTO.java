package br.edu.ifsudestemg.sb.api.dto;

import br.edu.ifsudestemg.sb.model.entity.Reserva;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDTO {

    private Long id;

    private LocalDateTime dataHoraReserva;

    private LocalDate dataMaximaPrevistaColeta;

    private LocalDateTime dataHoraColetaEfetiva;

    private Integer posicaoFila;

    private Long idCliente;
    private Long idObra;
    private Long idExemplar;
    private Long idStatusReserva;
    private Long idDuracaoPadraoReserva;

    private String nomeCliente;
    private String tituloObra;
    private String nomeStatusReserva;

    private Integer diasUteisDuracao;

    public ReservaDTO createDTO(Reserva reserva) {

        ModelMapper modelMapper = new ModelMapper();

        ReservaDTO dto = modelMapper.map(reserva, ReservaDTO.class);

        if (reserva.getCliente() != null) {

            dto.setIdCliente(reserva.getCliente().getId());
            dto.setNomeCliente(reserva.getCliente().getNome());
        }

        if (reserva.getObra() != null) {

            dto.setIdObra(reserva.getObra().getId());
            dto.setTituloObra(reserva.getObra().getTitulo());
        }

        if (reserva.getExemplar() != null) {

            dto.setIdExemplar(reserva.getExemplar().getId());
        }

        if (reserva.getStatusReserva() != null) {

            dto.setIdStatusReserva(reserva.getStatusReserva().getId());
            dto.setNomeStatusReserva(reserva.getStatusReserva().getNome());
        }

        if (reserva.getDuracaoPadraoReserva() != null) {

            dto.setIdDuracaoPadraoReserva(reserva.getDuracaoPadraoReserva().getId());
            dto.setDiasUteisDuracao(reserva.getDuracaoPadraoReserva().getDiasUteis());
        }

        return dto;
    }
}