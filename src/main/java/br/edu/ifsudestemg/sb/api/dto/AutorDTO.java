package br.edu.ifsudestemg.sb.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class AutorDTO {
    private Long id;
    private String nome;
    private Long idObra;
}
