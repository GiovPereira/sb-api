package br.edu.ifsudestemg.sb.api.dto;

import lombok.Data;

import javax.persistence.ManyToOne;

public class EmprestimoDTO {
    private Long Id;

    private Data dataEmprestimo;
    private Data dataEntrega;

}
