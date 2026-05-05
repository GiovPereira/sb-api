package br.edu.ifsudestemg.sb.api.dto;

import br.edu.ifsudestemg.sb.model.entity.Cliente;
import br.edu.ifsudestemg.sb.model.entity.Emprestimo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.persistence.Entity;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class EmprestimoDTO {
    private Long Id;
    private LocalDate dataEmprestimo;
    private LocalDate dataPrevistaDevolucao;
    private LocalDate dataEntrega;
    private Float multa;
    private Long idCliente;
    private Long idExemplar;
    private Long idDuracaoPadraoEmprestimo;
    private Long idValorDiarioMulta;

    public static EmprestimoDTO create(Emprestimo emprestimo) {
        ModelMapper modelMapper = new ModelMapper();
        EmprestimoDTO dto = modelMapper.map(emprestimo, EmprestimoDTO.class);
        return dto;
    }

}
