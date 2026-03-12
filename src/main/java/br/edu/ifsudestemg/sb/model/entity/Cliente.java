package br.edu.ifsudestemg.sb.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Integer cpf; //USAR INT MESMO OU PASSAR PARA STRING
    private LocalDate dataNascimento;
    private String email;
    private Integer telefone; //USAR INT MESMO OU PASSAR PARA STRING
    private Integer cep; //USAR INT MESMO OU PASSAR PARA STRING
    private String logradouro;
    private Integer numero; //USAR INT MESMO OU PASSAR PARA STRING
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;

}
