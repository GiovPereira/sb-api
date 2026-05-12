package br.edu.ifsudestemg.sb.model.repository;

import br.edu.ifsudestemg.sb.model.entity.Secao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SecaoRepository extends JpaRepository<Secao, Long> {

    Optional<Secao> findByNomeIgnoreCase(String nome);

    boolean existsByNomeIgnoreCase(String nome);
}