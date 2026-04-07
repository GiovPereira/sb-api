package br.edu.ifsudestemg.sb.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MultaDTO {

    private Long id;
    private Float valor;

    private EmprestimoDTO emprestimo; // CONVERTE Emprestimo PARA EmprestimoDTO

    private Long valorDiarioMultaId;
}
