package br.edu.ifsudestemg.sb.model.repository;

import br.edu.ifsudestemg.sb.model.entity.Editora;
import br.edu.ifsudestemg.sb.model.entity.Obra;
import br.edu.ifsudestemg.sb.model.entity.ObraEditora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObraEditoraRepository
        extends JpaRepository<ObraEditora, Long> {

    @Query(
            "SELECT oe.editora " +
                    "FROM ObraEditora oe " +
                    "WHERE oe.obra.id = :obraId"
    )
    List<Editora> buscarEditorasDaObra(
            @Param("obraId") Long obraId);

    @Query(
            "SELECT oe.obra " +
                    "FROM ObraEditora oe " +
                    "WHERE oe.editora.id = :editoraId"
    )
    List<Obra> buscarObrasDaEditora(
            @Param("editoraId") Long editoraId);

    @Query(
            "SELECT oe.editora.id " +
                    "FROM ObraEditora oe " +
                    "WHERE oe.obra.id = :obraId"
    )
    List<Long> buscarIdsEditorasDaObra(
            @Param("obraId") Long obraId);

    boolean existsByObra_IdAndEditora_Id(
            Long obraId,
            Long editoraId);

    void deleteByObra_Id(
            Long obraId);
}