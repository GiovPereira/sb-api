package br.edu.ifsudestemg.sb.model.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Reserva
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataReserva;
    private int posicaoFila;

    @ManyToOne
    private Cliente cliente;

    @ManyToOne
    private Obra obra;

    @ManyToOne
    private Exemplar exemplar;

    @ManyToOne
    private StatusReserva status;

}