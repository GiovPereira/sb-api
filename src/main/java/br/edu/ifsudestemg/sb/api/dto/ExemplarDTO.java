package br.edu.ifsudestemg.sb.api.dto;

import br.edu.ifsudestemg.sb.model.entity.Exemplar;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExemplarDTO {

    private Long id;
    private String tombo;
    private String codigoBarras;
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

        if (exemplar.getObra() != null) {
            dto.setIdObra(exemplar.getObra().getId());
            dto.setTituloObra(exemplar.getObra().getTitulo());
        }

        if (exemplar.getStatusExemplar() != null) {
            dto.setIdStatusExemplar(exemplar.getStatusExemplar().getId());
            dto.setNomeStatusExemplar(exemplar.getStatusExemplar().getNome());
        }

        if (exemplar.getSecao() != null) {
            dto.setIdSecao(exemplar.getSecao().getId());
            dto.setNomeSecao(exemplar.getSecao().getNome());
        }

        return dto;
    }
}