package br.edu.ifsudestemg.sb.api.dto;

import br.edu.ifsudestemg.sb.model.entity.DuracaoPadraoEmprestimo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class DuracaoPadraoEmprestimoDTO {
    private Long Id;
    private int diasUteis;
    private Data dataHoraAlteracao;

    public static DuracaoPadraoEmprestimoDTO create(DuracaoPadraoEmprestimo duracaoPadraoEmprestimo) {
        ModelMapper modelMapper = new ModelMapper();
        DuracaoPadraoEmprestimoDTO dto = modelMapper.map(duracaoPadraoEmprestimo, DuracaoPadraoEmprestimoDTO.class);
        return dto;
    }
}
