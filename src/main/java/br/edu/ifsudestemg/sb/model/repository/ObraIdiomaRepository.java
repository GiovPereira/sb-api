package br.edu.ifsudestemg.sb.model.repository;

import br.edu.ifsudestemg.sb.model.entity.Idioma;
import br.edu.ifsudestemg.sb.model.entity.Obra;
import br.edu.ifsudestemg.sb.model.entity.ObraIdioma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObraIdiomaRepository
        extends JpaRepository<ObraIdioma, Long> {

    @Query(
            "SELECT oa.idioma " +
                    "FROM ObraIdioma oa " +
                    "WHERE oa.obra.id = :obraId"
    )
    List<Idioma> buscarIdiomaesDaObra(
            @Param("obraId") Long obraId);

    @Query(
            "SELECT oa.obra " +
                    "FROM ObraIdioma oa " +
                    "WHERE oa.idioma.id = :idiomaId"
    )
    List<Obra> buscarObrasDoIdioma(
            @Param("idiomaId") Long idiomaId);

    boolean existsByObra_IdAndIdioma_Id(
            Long obraId,
            Long idiomaId);

    void deleteByObra_IdAndIdioma_Id(
            Long obraId,
            Long idiomaId);
}