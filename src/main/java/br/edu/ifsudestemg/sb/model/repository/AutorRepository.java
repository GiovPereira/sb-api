package br.edu.ifsudestemg.sb.model.repository;

import br.edu.ifsudestemg.sb.model.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Long> {

}
