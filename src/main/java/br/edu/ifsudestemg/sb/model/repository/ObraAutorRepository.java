package br.edu.ifsudestemg.sb.model.repository;

import br.edu.ifsudestemg.sb.model.entity.Autor;
import br.edu.ifsudestemg.sb.model.entity.Obra;
import br.edu.ifsudestemg.sb.model.entity.ObraAutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObraAutorRepository
        extends JpaRepository<ObraAutor, Long> {

    @Query(
            "SELECT oa.autor " +
                    "FROM ObraAutor oa " +
                    "WHERE oa.obra.id = :obraId"
    )
    List<Autor> buscarAutoresDaObra(
            @Param("obraId") Long obraId);

    @Query(
            "SELECT oa.obra " +
                    "FROM ObraAutor oa " +
                    "WHERE oa.autor.id = :autorId"
    )
    List<Obra> buscarObrasDoAutor(
            @Param("autorId") Long autorId);

    @Query(
            "SELECT oa.autor.id " +
                    "FROM ObraAutor oa " +
                    "WHERE oa.obra.id = :obraId"
    )
    List<Long> buscarIdsAutoresDaObra(
            @Param("obraId") Long obraId);

    boolean existsByObra_IdAndAutor_Id(
            Long obraId,
            Long autorId);

    void deleteByObra_Id(
            Long obraId);
}