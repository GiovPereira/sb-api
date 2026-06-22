package br.edu.ifsudestemg.sb.api.dto;

import br.edu.ifsudestemg.sb.model.entity.Emprestimo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmprestimoDTO {

    private Long id;

    private LocalDateTime dataHoraEmprestimo;
    private LocalDate dataPrevistaDevolucao;
    private LocalDateTime dataHoraEntrega;

    private BigDecimal multa;

    private Long idCliente;
    private Long idExemplar;

    private Long idDuracaoPadraoEmprestimo;
    private Long idValorDiarioMulta;

    private String nomeCliente;
    private String tituloObra;

    private String tomboExemplar;
    private String codigoBarrasExemplar;

    private Integer diasUteis;
    private BigDecimal valorDia;

    public static EmprestimoDTO create(
            Emprestimo emprestimo) {

        ModelMapper modelMapper =
                new ModelMapper();

        EmprestimoDTO dto =
                modelMapper.map(
                        emprestimo,
                        EmprestimoDTO.class
                );

        if (emprestimo.getCliente() != null) {

            dto.setIdCliente(
                    emprestimo.getCliente().getId()
            );

            dto.setNomeCliente(
                    emprestimo.getCliente().getNome()
            );
        }

        if (emprestimo.getExemplar() != null) {

            dto.setIdExemplar(
                    emprestimo.getExemplar().getId()
            );

            dto.setTomboExemplar(
                    emprestimo.getExemplar().getTombo()
            );

            dto.setCodigoBarrasExemplar(
                    emprestimo.getExemplar().getCodigoBarras()
            );

            if (emprestimo.getExemplar().getObra() != null) {

                dto.setTituloObra(
                        emprestimo.getExemplar()
                                .getObra()
                                .getTitulo()
                );
            }
        }

        if (emprestimo.getDuracaoPadraoEmprestimo() != null) {

            dto.setIdDuracaoPadraoEmprestimo(
                    emprestimo.getDuracaoPadraoEmprestimo().getId()
            );

            dto.setDiasUteis(
                    emprestimo.getDuracaoPadraoEmprestimo().getDiasUteis()
            );
        }

        if (emprestimo.getValorDiarioMulta() != null) {

            dto.setIdValorDiarioMulta(
                    emprestimo.getValorDiarioMulta().getId()
            );

            dto.setValorDia(
                    emprestimo.getValorDiarioMulta().getValorDia()
            );
        }

        return dto;
    }
}