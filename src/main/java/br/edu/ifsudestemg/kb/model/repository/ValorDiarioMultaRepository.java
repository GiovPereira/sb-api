package br.edu.ifsudestemg.kb.model.repository;

import br.edu.ifsudestemg.kb.model.entity.ValorDiarioMulta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ValorDiarioMultaRepository
        extends JpaRepository<ValorDiarioMulta, Long> {

    Optional<ValorDiarioMulta> findTopByOrderByIdDesc();

}