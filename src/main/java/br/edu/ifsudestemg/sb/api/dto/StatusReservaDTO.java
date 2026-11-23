package br.edu.ifsudestemg.sb.api.dto;

import br.edu.ifsudestemg.sb.model.entity.StatusReserva;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class StatusReservaDTO {
    private Long id;
    private String nome;

    public static StatusReservaDTO create(StatusReserva statusReserva) {
        ModelMapper modelMapper = new ModelMapper();
        StatusReservaDTO dto = modelMapper.map(statusReserva, StatusReservaDTO.class);
        return dto;
    }
}
