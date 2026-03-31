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

    private String titulo;
    private String isbn;
    private String edicao;

    @ManyToOne
    private Autor autor;

    @ManyToOne
    private Editora editora;

    @ManyToOne
    private Genero genero;

    @ManyToOne
    private Idioma idioma;
}
