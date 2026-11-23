package br.edu.ifsudestemg.sb.model.repository;

import br.edu.ifsudestemg.sb.model.entity.DuracaoPadraoEmprestimo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DuracaoPadraoEmprestimoRepository
        extends JpaRepository<DuracaoPadraoEmprestimo, Long> {

    Optional<DuracaoPadraoEmprestimo> findTopByOrderByIdDesc();

}