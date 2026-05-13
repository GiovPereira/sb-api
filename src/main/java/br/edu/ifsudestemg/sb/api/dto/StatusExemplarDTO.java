package br.edu.ifsudestemg.sb.api.dto;

import br.edu.ifsudestemg.sb.model.entity.StatusExemplar;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class StatusExemplarDTO {
    private Long id;
    private String nome;

    public static StatusExemplarDTO create(StatusExemplar statusExemplar) {
        ModelMapper modelMapper = new ModelMapper();
        StatusExemplarDTO dto = modelMapper.map(statusExemplar, StatusExemplarDTO.class);
        return dto;
    }
}
