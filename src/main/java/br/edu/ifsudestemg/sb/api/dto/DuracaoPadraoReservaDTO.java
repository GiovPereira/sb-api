package br.edu.ifsudestemg.sb.api.dto;

import br.edu.ifsudestemg.sb.model.entity.DuracaoPadraoReserva;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class DuracaoPadraoReservaDTO
{
    private Long id;
    private Integer diasUteis;
    private LocalDateTime dataHoraAlteracao;

    public static DuracaoPadraoReservaDTO create(DuracaoPadraoReserva duracaoPadraoReserva) {
        ModelMapper modelMapper = new ModelMapper();
        DuracaoPadraoReservaDTO dto = modelMapper.map(duracaoPadraoReserva, DuracaoPadraoReservaDTO.class);
        return dto;
    }
}
