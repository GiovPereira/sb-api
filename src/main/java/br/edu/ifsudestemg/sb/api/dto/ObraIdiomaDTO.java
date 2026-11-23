package br.edu.ifsudestemg.sb.api.dto;

import br.edu.ifsudestemg.sb.model.entity.ObraIdioma;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ObraIdiomaDTO
{
    private Long id;
    private String nome;
    private Long idObra;
    private Long idIdioma;

    public static ObraIdiomaDTO create(ObraIdioma autor) {
        ModelMapper modelMapper = new ModelMapper();
        ObraIdiomaDTO dto = modelMapper.map(autor, ObraIdiomaDTO.class);
        return dto;
    }
}
