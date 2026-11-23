package br.edu.ifsudestemg.kb.model.repository;

import br.edu.ifsudestemg.kb.model.entity.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GeneroRepository extends JpaRepository<Genero, Long> {

    Optional<Genero> findByNomeIgnoreCase(String nome);
}
