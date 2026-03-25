package br.edu.ifsudestemg.sb.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Idioma
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO.IDENTITY)
    private Long Id;

    private String nome;

}