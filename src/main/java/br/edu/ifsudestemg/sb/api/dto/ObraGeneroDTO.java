package br.edu.ifsudestemg.sb.api.dto;

import br.edu.ifsudestemg.sb.model.entity.ObraGenero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ObraGeneroDTO {
    private Long id;
    private String nome;
    private Long idObra;
    private Long idGenero;

    public static ObraGeneroDTO create(ObraGenero autor) {
        ModelMapper modelMapper = new ModelMapper();
        ObraGeneroDTO dto = modelMapper.map(autor, ObraGeneroDTO.class);
        return dto;
    }
}
