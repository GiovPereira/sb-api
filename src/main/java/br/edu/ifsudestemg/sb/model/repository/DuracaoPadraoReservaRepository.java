package br.edu.ifsudestemg.sb.model.repository;

import br.edu.ifsudestemg.sb.model.entity.DuracaoPadraoEmprestimo;
import br.edu.ifsudestemg.sb.model.entity.DuracaoPadraoReserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DuracaoPadraoReservaRepository extends JpaRepository<DuracaoPadraoReserva, Long> {

    Optional<DuracaoPadraoReserva> findTopByOrderByDataHoraAlteracaoDesc();
}