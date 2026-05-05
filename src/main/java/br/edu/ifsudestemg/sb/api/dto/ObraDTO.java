package br.edu.ifsudestemg.sb.api.dto;

import br.edu.ifsudestemg.sb.model.entity.Obra;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ObraDTO {
    private Long id;
    private String titulo;
    private String isbn;
    private String edicao;
    private Long idAutor;
    private Long idEditora;
    private Long idGenero;
    private Long idIdioma;

    public static ObraDTO create(Obra obra) {
        ModelMapper modelMapper = new ModelMapper();
        ObraDTO dto = modelMapper.map(obra, ObraDTO.class);
        return dto;
    }
}
