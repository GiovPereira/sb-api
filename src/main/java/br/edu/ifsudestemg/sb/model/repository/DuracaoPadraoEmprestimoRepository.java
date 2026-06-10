package br.edu.ifsudestemg.sb.model.repository;

import br.edu.ifsudestemg.sb.model.entity.DuracaoPadraoEmprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DuracaoPadraoEmprestimoRepository extends JpaRepository<DuracaoPadraoEmprestimo, Long> {

    Optional<DuracaoPadraoEmprestimo> findTopByOrderByDataHoraAlteracaoDesc();
}