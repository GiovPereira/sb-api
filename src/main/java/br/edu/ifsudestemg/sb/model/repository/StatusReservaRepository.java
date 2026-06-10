package br.edu.ifsudestemg.sb.model.repository;

import br.edu.ifsudestemg.sb.model.entity.StatusReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusReservaRepository extends JpaRepository<StatusReserva, Long> {

    Optional<StatusReserva> findByNomeIgnoreCase(String nome);

    boolean existsByNomeIgnoreCase(String nome);
}