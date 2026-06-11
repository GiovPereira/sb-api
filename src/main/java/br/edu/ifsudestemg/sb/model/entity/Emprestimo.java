package br.edu.ifsudestemg.sb.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dataHoraEmprestimo;

    @Column(nullable = false)
    private LocalDate dataPrevistaDevolucao;

    private LocalDateTime dataHoraEntrega;

    @Column(precision = 10, scale = 2)
    private BigDecimal multa;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne(optional = false)
    @JoinColumn(name = "exemplar_id")
    private Exemplar exemplar;

    @ManyToOne(optional = false)
    @JoinColumn(name = "duracao_padrao_emprestimo_id")
    private DuracaoPadraoEmprestimo duracaoPadraoEmprestimo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "valor_diario_multa_id")
    private ValorDiarioMulta valorDiarioMulta;

    @PrePersist
    public void prePersist() {

        if (this.dataHoraEmprestimo == null) {
            this.dataHoraEmprestimo = LocalDateTime.now();
        }

    }

    @Transient
    public BigDecimal calcularMultaAtual() {

        if (valorDiarioMulta == null) {
            return BigDecimal.ZERO;
        }

        if (dataHoraEntrega != null) {
            return multa == null ? BigDecimal.ZERO : multa;
        }

        LocalDate hoje = LocalDate.now();

        if (!hoje.isAfter(dataPrevistaDevolucao)) {
            return BigDecimal.ZERO;
        }

        long diasAtraso =
                ChronoUnit.DAYS.between(
                        dataPrevistaDevolucao,
                        hoje
                );

        return valorDiarioMulta
                .getValorDia()
                .multiply(BigDecimal.valueOf(diasAtraso));
    }
}