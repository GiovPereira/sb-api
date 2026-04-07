package br.edu.ifsudestemg.sb.model.repository;

import br.edu.ifsudestemg.sb.model.entity.Exemplar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExemplarRepository extends JpaRepository<Exemplar, Long> {
}
