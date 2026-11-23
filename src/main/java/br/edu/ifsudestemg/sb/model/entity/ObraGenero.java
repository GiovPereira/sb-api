package br.edu.ifsudestemg.sb.model.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "obra_genero",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"obra_id", "genero_id"}
                )
        }
)
public class ObraGenero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "obra_id")
    private Obra obra;

    @ManyToOne(optional = false)
    @JoinColumn(name = "genero_id")
    private Genero genero;
}