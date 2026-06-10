package br.edu.ifsudestemg.sb.model.repository;

import br.edu.ifsudestemg.sb.model.entity.Secao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SecaoRepository extends JpaRepository<Secao, Long> {

    Optional<Secao> findByNomeIgnoreCase(String nome);

    boolean existsByNomeIgnoreCase(String nome);
}