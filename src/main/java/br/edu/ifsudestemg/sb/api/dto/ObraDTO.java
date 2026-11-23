package br.edu.ifsudestemg.sb.api.dto;

import br.edu.ifsudestemg.sb.model.entity.Obra;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObraDTO {

    private Long id;

    private String titulo;

    private String isbn;

    private String edicao;


    private List<Long> autoresIds;
    private List<Long> editorasIds;
    private List<Long> generosIds;
    private List<Long> idiomasIds;

    private List<String> autores;
    private List<String> editoras;
    private List<String> generos;
    private List<String> idiomas;

    public static ObraDTO create(
            Obra obra) {

        ModelMapper modelMapper = new ModelMapper();

        ObraDTO dto =
                modelMapper.map(
                        obra,
                        ObraDTO.class);

        if (dto.getAutoresIds() == null) {
            dto.setAutoresIds(
                    new ArrayList<>());
        }

        if (dto.getEditorasIds() == null) {
            dto.setEditorasIds(
                    new ArrayList<>());
        }

        if (dto.getGenerosIds() == null) {
            dto.setGenerosIds(
                    new ArrayList<>());
        }

        if (dto.getIdiomasIds() == null) {
            dto.setIdiomasIds(
                    new ArrayList<>());
        }

        if (dto.getAutores() == null) {
            dto.setAutores(
                    new ArrayList<>());
        }

        if (dto.getEditoras() == null) {
            dto.setEditoras(
                    new ArrayList<>());
        }

        if (dto.getGeneros() == null) {
            dto.setGeneros(
                    new ArrayList<>());
        }

        if (dto.getIdiomas() == null) {
            dto.setIdiomas(
                    new ArrayList<>());
        }

        return dto;
    }
}