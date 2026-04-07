package br.edu.ifsudestemg.sb.model.repository;

import br.edu.ifsudestemg.sb.model.entity.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
}
