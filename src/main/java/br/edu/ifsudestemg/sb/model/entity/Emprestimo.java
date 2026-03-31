package br.edu.ifsudestemg.sb.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Emprestimo
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private LocalDate dataEmprestimo;
    private LocalDate dataEntrega;

    @ManyToOne
    private Cliente cliente;

    @ManyToOne
    private Exemplar exemplar;

    @ManyToOne
    private DuracaoPadraoEmprestimo duracaoPadraoEmprestimo;
}
