package br.edu.ifsudestemg.kb.api.dto;

import br.edu.ifsudestemg.kb.model.entity.Secao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class SecaoDTO {
    private Long id;
    private String nome;

    public static SecaoDTO create(Secao secao) {
        ModelMapper modelMapper = new ModelMapper();
        SecaoDTO dto = modelMapper.map(secao, SecaoDTO.class);
        return dto;
    }
}
