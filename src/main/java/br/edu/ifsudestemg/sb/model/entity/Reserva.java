package br.edu.ifsudestemg.sb.model.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Reserva
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Data dataReserva;
    private int posicaoFila;

    @ManyToOne
    private Cliente cliente;
    private Obra obra;
    private StatusReserva status;

}