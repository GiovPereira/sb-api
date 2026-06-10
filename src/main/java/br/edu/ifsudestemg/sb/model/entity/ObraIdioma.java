package br.edu.ifsudestemg.sb.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "obra_idioma",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"obra_id", "idioma_id"}
                )
        }
)
public class ObraIdioma
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "obra_id")
    private Obra obra;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idioma_id")
    private Idioma idioma;
}