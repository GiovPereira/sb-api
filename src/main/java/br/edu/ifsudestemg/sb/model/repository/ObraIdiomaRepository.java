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
            "SELECT oi.idioma " +
                    "FROM ObraIdioma oi " +
                    "WHERE oi.obra.id = :obraId"
    )
    List<Idioma> buscarIdiomasDaObra(
            @Param("obraId") Long obraId);

    @Query(
            "SELECT oi.obra " +
                    "FROM ObraIdioma oi " +
                    "WHERE oi.idioma.id = :idiomaId"
    )
    List<Obra> buscarObrasDoIdioma(
            @Param("idiomaId") Long idiomaId);

    @Query(
            "SELECT oi.idioma.id " +
                    "FROM ObraIdioma oi " +
                    "WHERE oi.obra.id = :obraId"
    )
    List<Long> buscarIdsIdiomasDaObra(
            @Param("obraId") Long obraId);

    boolean existsByObra_IdAndIdioma_Id(
            Long obraId,
            Long idiomaId);

    void deleteByObra_Id(
            Long obraId);
}