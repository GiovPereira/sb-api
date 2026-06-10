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
        name = "obra_autor",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"obra_id", "autor_id"}
                )
        }
)
public class ObraAutor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "obra_id")
    private Obra obra;

    @ManyToOne(optional = false)
    @JoinColumn(name = "autor_id")
    private Autor autor;
}