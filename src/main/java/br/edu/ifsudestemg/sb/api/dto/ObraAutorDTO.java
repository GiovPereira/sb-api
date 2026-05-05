package br.edu.ifsudestemg.sb.api.dto;

import br.edu.ifsudestemg.sb.model.entity.ObraAutor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ObraAutorDTO {
    private Long id;
    private String nome;
    private Long idObra;
    private Long idAutor;

    public static ObraAutorDTO create(ObraAutor autor) {
        ModelMapper modelMapper = new ModelMapper();
        ObraAutorDTO dto = modelMapper.map(autor, ObraAutorDTO.class);
        return dto;
    }
}
