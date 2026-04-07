package br.edu.ifsudestemg.sb.model.repository;

import br.edu.ifsudestemg.sb.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository <Cliente, Long> {
}
