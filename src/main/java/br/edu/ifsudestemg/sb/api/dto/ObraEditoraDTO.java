package br.edu.ifsudestemg.sb.api.dto;

import br.edu.ifsudestemg.sb.model.entity.ObraEditora;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ObraEditoraDTO
{
    private Long id;
    private String nome;
    private Long idObra;
    private Long idEditora;

    public static ObraEditoraDTO create(ObraEditora autor) {
        ModelMapper modelMapper = new ModelMapper();
        ObraEditoraDTO dto = modelMapper.map(autor, ObraEditoraDTO.class);
        return dto;
    }
}
