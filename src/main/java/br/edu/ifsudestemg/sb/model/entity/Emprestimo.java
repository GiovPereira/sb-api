package br.edu.ifsudestemg.sb.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Emprestimo
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private Data dataEmprestimo;
    private Data dataEntrega;

    @ManyToOne
    private Cliente cliente;
    private Exemplar exemplar;
    private DuracaoPadraoEmprestimo duracaoPadraoEmprestimo;
}
