package br.edu.ifsudestemg.sb.model.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor

public class ValorDiarioMulta
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Float valorDia;
    private LocalDateTime dataHoraAlteracao;

    @ManyToOne
    private StatusReserva status;
}
