package br.edu.ifsudestemg.sb.api.dto;

import br.edu.ifsudestemg.sb.model.entity.Idioma;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class IdiomaDTO {
    private Long id;
    private String nome;

    public static IdiomaDTO create(Idioma idioma) {
        ModelMapper modelMapper = new ModelMapper();
        IdiomaDTO dto = modelMapper.map(idioma, IdiomaDTO.class);
        return dto;
    }
}
