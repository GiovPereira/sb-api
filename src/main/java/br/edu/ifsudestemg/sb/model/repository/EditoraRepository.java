package br.edu.ifsudestemg.sb.model.repository;

import br.edu.ifsudestemg.sb.model.entity.Editora;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EditoraRepository extends JpaRepository<Editora, Long> {

    Optional<Editora> findByNomeIgnoreCase(String nome);

    boolean existsByNomeIgnoreCase(String nome);
}