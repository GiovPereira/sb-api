package br.edu.ifsudestemg.sb.model.repository;

import br.edu.ifsudestemg.sb.model.entity.Genero;
import br.edu.ifsudestemg.sb.model.entity.Obra;
import br.edu.ifsudestemg.sb.model.entity.ObraGenero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObraGeneroRepository
        extends JpaRepository<ObraGenero, Long> {

    @Query(
            "SELECT og.genero " +
                    "FROM ObraGenero og " +
                    "WHERE og.obra.id = :obraId"
    )
    List<Genero> buscarGenerosDaObra(
            @Param("obraId") Long obraId);

    @Query(
            "SELECT og.obra " +
                    "FROM ObraGenero og " +
                    "WHERE og.genero.id = :generoId"
    )
    List<Obra> buscarObrasDoGenero(
            @Param("generoId") Long generoId);

    @Query(
            "SELECT og.genero.id " +
                    "FROM ObraGenero og " +
                    "WHERE og.obra.id = :obraId"
    )
    List<Long> buscarIdsGenerosDaObra(
            @Param("obraId") Long obraId);

    boolean existsByObra_IdAndGenero_Id(
            Long obraId,
            Long generoId);

    void deleteByObra_Id(
            Long obraId);
}