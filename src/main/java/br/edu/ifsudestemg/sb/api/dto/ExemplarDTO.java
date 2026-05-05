package br.edu.ifsudestemg.sb.api.dto;

import br.edu.ifsudestemg.sb.model.entity.Exemplar;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ExemplarDTO {
    private Long id;
    private LocalDate dataAquisicao;
    private Long idObra;
    private Long idStatusExemplar;
    private Long idSecao;
    private String tituloObra;
    private String nomeStatusExemplar;
    private String nomeSecao;

    public static ExemplarDTO create(Exemplar exemplar) {
        ModelMapper modelMapper = new ModelMapper();
        ExemplarDTO dto = modelMapper.map(exemplar, ExemplarDTO.class);
        dto.tituloObra = exemplar.getObra().getTitulo();
        dto.nomeStatusExemplar = exemplar.getStatusExemplar().getNome();
        dto.nomeSecao = exemplar.getSecao().getNome();
        return dto;
    }
}
