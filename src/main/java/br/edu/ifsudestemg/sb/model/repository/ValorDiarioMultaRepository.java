package br.edu.ifsudestemg.sb.model.repository;

import br.edu.ifsudestemg.sb.model.entity.ValorDiarioMulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ValorDiarioMultaRepository extends JpaRepository<ValorDiarioMulta, Long> {

    Optional<ValorDiarioMulta> findTopByOrderByDataHoraAlteracaoDesc();
}