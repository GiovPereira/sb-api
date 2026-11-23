package br.edu.ifsudestemg.sb.model.repository;

import br.edu.ifsudestemg.sb.model.entity.Exemplar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExemplarRepository extends JpaRepository<Exemplar, Long> {

    Optional<Exemplar> findFirstByObraIdAndStatusExemplarId(Long obraId, Long statusExemplarId);

    Optional<Exemplar> findTopByOrderByTomboDesc();

    Optional<Exemplar> findByCodigoBarras(String codigoBarras);

    List<Exemplar> findByObraId(Long obraId);

    List<Exemplar> findByObraIdAndStatusExemplarIdIn(Long obraId, List<Long> statusIds);

    long countByObraIdAndStatusExemplarIdIn(Long obraId, List<Long> statusIds);

    boolean existsByObraIdAndStatusExemplarId(Long obraId, Long statusId);
}