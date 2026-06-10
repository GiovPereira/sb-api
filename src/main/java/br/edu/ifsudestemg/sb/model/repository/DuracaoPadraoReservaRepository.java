package br.edu.ifsudestemg.sb.model.repository;

import br.edu.ifsudestemg.sb.model.entity.DuracaoPadraoEmprestimo;
import br.edu.ifsudestemg.sb.model.entity.DuracaoPadraoReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DuracaoPadraoReservaRepository extends JpaRepository<DuracaoPadraoReserva, Long> {

    Optional<DuracaoPadraoReserva> findTopByOrderByDataHoraAlteracaoDesc();
}