package br.edu.ifsudestemg.sb.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dataHoraReserva;

    @Column(nullable = false)
    private LocalDate dataMaximaPrevistaColeta;

    private LocalDateTime dataHoraColetaEfetiva;

    @Column(nullable = false)
    private Integer posicaoFila;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne(optional = false)
    @JoinColumn(name = "obra_id")
    private Obra obra;

    @ManyToOne
    @JoinColumn(name = "exemplar_id")
    private Exemplar exemplar;

    @ManyToOne(optional = false)
    @JoinColumn(name = "status_reserva_id")
    private StatusReserva statusReserva;

    @ManyToOne(optional = false)
    @JoinColumn(name = "duracao_padrao_reserva_id")
    private DuracaoPadraoReserva duracaoPadraoReserva;
}