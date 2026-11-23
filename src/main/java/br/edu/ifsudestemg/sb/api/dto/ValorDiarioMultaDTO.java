package br.edu.ifsudestemg.sb.api.dto;

import br.edu.ifsudestemg.sb.model.entity.ValorDiarioMulta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ValorDiarioMultaDTO {
    private Long id;
    private String nome;
    private Float valorDia;
    private LocalDateTime dataHoraAlteracao;

    public static ValorDiarioMultaDTO create(ValorDiarioMulta valorDiarioMulta) {
        ModelMapper modelMapper = new ModelMapper();
        ValorDiarioMultaDTO dto = modelMapper.map(valorDiarioMulta, ValorDiarioMultaDTO.class);
        return dto;
    }
}
