package br.edu.ifsudestemg.sb.model.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Obra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Long idAutor;
    private Long idEditora;
    private Long idGenero;
    private Long idIdioma;
    private String titulo;
    private String isbn; //USAR INTEGER MESMO OU PASSAR PARA STRING
    private String edicao; //USAR INTEGER MESMO OU PASSAR PARA STRING
}
