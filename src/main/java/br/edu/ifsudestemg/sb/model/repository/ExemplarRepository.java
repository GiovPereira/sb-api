package br.edu.ifsudestemg.sb.model.repository;

import br.edu.ifsudestemg.sb.model.entity.Exemplar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExemplarRepository extends JpaRepository<Exemplar, Long> {

    Optional<Exemplar>
    findFirstByObraIdAndStatusExemplarId(
            Long obraId,
            Long statusExemplarId
    );
}
