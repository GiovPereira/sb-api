package br.edu.ifsudestemg.kb.model.repository;

import br.edu.ifsudestemg.kb.model.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    Optional<Autor> findByNomeIgnoreCase(String nome);
}
