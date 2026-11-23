package br.edu.ifsudestemg.sb.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exemplar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 9)
    private String tombo;

    @Column(nullable = false, unique = true, length = 100)
    private String codigoBarras;

    private LocalDate dataAquisicao;

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