package br.edu.ifsudestemg.sb.api.dto;

import br.edu.ifsudestemg.sb.model.entity.Editora;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class EditoraDTO {
    private Long id;
    private String nome;

    public static EditoraDTO create(Editora editora) {
        ModelMapper modelMapper = new ModelMapper();
        EditoraDTO dto = modelMapper.map(editora, EditoraDTO.class);
        return dto;
    }
}
