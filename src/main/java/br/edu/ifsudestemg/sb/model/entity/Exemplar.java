package br.edu.ifsudestemg.sb.model.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Exemplar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataHoraAquisicao;

    @ManyToOne
    private Obra obra;

    @ManyToOne
    private StatusExemplar statusExemplar;

    @ManyToOne
    private Secao secao;
}
