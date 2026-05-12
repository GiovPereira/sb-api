package br.edu.ifsudestemg.sb.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class DuracaoPadraoEmprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer diasUteis;

    @Column(nullable = false)
    private LocalDateTime dataHoraAlteracao;

    @PrePersist
    @PreUpdate
    public void atualizarDataHora() {
        this.dataHoraAlteracao = LocalDateTime.now();
    }
}