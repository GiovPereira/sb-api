package br.edu.ifsudestemg.sb.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    @ManyToOne(optional = false)
    @JoinColumn(name = "obra_id", nullable = false)
    private Obra obra;

    @ManyToOne(optional = false)
    @JoinColumn(name = "status_exemplar_id", nullable = false)
    private StatusExemplar statusExemplar;

    @ManyToOne
    @JoinColumn(name = "secao_id")
    private Secao secao;
}