package br.edu.ifsudestemg.sb.model.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ValorDiarioMulta
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO.IDENTITY)
    private Long Id;

    private Float valorDia;
    private Data dataHoraAlteracao;

    @ManyToOne
    private StatusReserva status;
}
