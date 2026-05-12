package br.edu.ifsudestemg.sb.model.repository;

import br.edu.ifsudestemg.sb.model.entity.StatusExemplar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatusExemplarRepository extends JpaRepository<StatusExemplar, Long> {

    Optional<StatusExemplar> findByNomeIgnoreCase(String nome);

    boolean existsByNomeIgnoreCase(String nome);
}