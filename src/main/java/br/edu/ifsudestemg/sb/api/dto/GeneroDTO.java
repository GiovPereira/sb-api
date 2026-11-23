package br.edu.ifsudestemg.sb.api.dto;

import br.edu.ifsudestemg.sb.model.entity.Genero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class GeneroDTO {
    private Long id;
    private String nome;

    public static GeneroDTO create(Genero genero) {
        ModelMapper modelMapper = new ModelMapper();
        GeneroDTO dto = modelMapper.map(genero, GeneroDTO.class);
        return dto;
    }
}
